package com.Ecotrack.IotService.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.Ecotrack.IotService.websocket.UsageWebSocketHandler;


@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final UsageWebSocketHandler usageWebSocketHandler;

    public WebSocketConfig(UsageWebSocketHandler usageWebSocketHandler) {
        this.usageWebSocketHandler = usageWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(usageWebSocketHandler, "/updateUsage").setAllowedOrigins("*");
    }
}
