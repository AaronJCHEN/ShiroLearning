package com.sjw.ShiroTest.Config;

import com.sjw.ShiroTest.Msg.SocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class TestWebSocketConfig implements WebSocketConfigurer {
    @Bean
    public SocketHandler socketHandler() {
        SocketHandler socketHandler = new SocketHandler();
        return socketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(socketHandler(),"/websocket")
                .setAllowedOrigins("http://localhost:8082")
                .withSockJS();

    }
}
