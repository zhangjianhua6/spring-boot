package com.jason.web.websocket.javax;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@ConditionalOnProperty(prefix = "websocket", name = "type", havingValue = "javax")
public class JavaxWebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public JavaxWebSocket javaxWebSocket(){
        return new JavaxWebSocket();
    }
}
