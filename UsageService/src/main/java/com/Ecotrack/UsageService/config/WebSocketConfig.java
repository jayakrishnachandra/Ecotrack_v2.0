package com.Ecotrack.UsageService.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.Ecotrack.UsageService.websocket.GetUsageWebSocketHandler;




@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final GetUsageWebSocketHandler getUsageWebSocketHandler;

    public WebSocketConfig(GetUsageWebSocketHandler getUsageWebSocketHandler) {
        this.getUsageWebSocketHandler = getUsageWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(getUsageWebSocketHandler, "/getUsage").setAllowedOrigins("*");
    }
}
