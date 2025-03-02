package com.Ecotrack.IotService.websocket;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.Ecotrack.IotService.Models.KafkaUsageEntity;
import com.Ecotrack.IotService.Models.Usage;
import com.Ecotrack.IotService.Services.UsageService;
import com.Ecotrack.IotService.kafka.KafkaMessagePublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
public class UsageWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    KafkaMessagePublisher kafkaMessagePublisher;


    
    private static final Logger logger = LoggerFactory.getLogger(UsageWebSocketHandler.class);
    private final UsageService usageService;
    private final String AUTH_SERVICE_URL = "http://localhost:8065/auth/validateToken";
    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private static final String TOKEN_PREFIX = "token=";
    private static final String UNAUTHORIZED_MESSAGE = "Unauthorized";
    private static final String USAGE_SAVED_MESSAGE = "Usage data saved successfully";
    private static final String ERROR_PROCESSING_REQUEST = "Error processing request";

   private final ObjectMapper objectMapper;
public UsageWebSocketHandler(UsageService usageService) {
    this.usageService = usageService;
    this.objectMapper = new ObjectMapper();
    this.objectMapper.registerModule(new JavaTimeModule()); // Enable LocalDateTime support
}
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        URI uri = session.getUri();
        if (uri == null || uri.getQuery() == null || !uri.getQuery().contains(TOKEN_PREFIX)) {
            logger.warn("WebSocket connection rejected: Missing token");
            session.close(CloseStatus.POLICY_VIOLATION);
            return;
        }

        String token = extractToken(uri.getQuery());
        if (token == null) {
            logger.warn("WebSocket connection rejected: Invalid token format");
            session.close(CloseStatus.POLICY_VIOLATION);
            return;
        }

        String email = validateToken(token);
        if (email == null) {
            logger.warn("WebSocket connection rejected: Token validation failed");
            session.sendMessage(new TextMessage(UNAUTHORIZED_MESSAGE));
            session.close(CloseStatus.POLICY_VIOLATION);
            return;
        }

        userSessions.put(email, session);
        session.sendMessage(new TextMessage("Connected & Authenticated: " + email));
        logger.info("WebSocket Authenticated for: {}", email);
    }

    private String extractToken(String query) {
        String[] params = query.split("&");
        for (String param : params) {
            if (param.startsWith(TOKEN_PREFIX)) {
                String token = param.substring(TOKEN_PREFIX.length());
                logger.debug("Extracted token: {}", token);
                return token;
            }
        }
        logger.warn("Token not found in query: {}", query);
        return null;
    }

    private String validateToken(String token) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + token);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    AUTH_SERVICE_URL, HttpMethod.POST, entity, String.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                logger.debug("Token validation successful, email: {}", response.getBody());
                return response.getBody();
            } else {
                logger.warn("Token validation failed, status: {}, body: {}", response.getStatusCode(), response.getBody());
            }

        } catch (RestClientException e) {
            logger.error("Token validation failed: {}", e.getMessage());
        }
        return null;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        try {
            Usage usage = objectMapper.readValue(message.getPayload(), Usage.class);

            KafkaUsageEntity entity = new KafkaUsageEntity(usage);
            kafkaMessagePublisher.sendMessage(entity); 
            
            usageService.save(usage);
            session.sendMessage(new TextMessage(USAGE_SAVED_MESSAGE));
            System.out.println(message.toString());
            logger.debug("Usage data saved successfully");
        } catch (Exception e) {
            logger.error("Error processing message: {}", e.getMessage());
            try {
                session.sendMessage(new TextMessage(ERROR_PROCESSING_REQUEST));
            } catch (Exception ignored) {}
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        userSessions.values().remove(session);
        logger.info("WebSocket Disconnected: {}, status: {}", session.getId(), status);
    }
}