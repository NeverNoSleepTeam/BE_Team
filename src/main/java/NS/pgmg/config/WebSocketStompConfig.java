package NS.pgmg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.setPathMatcher(new AntPathMatcher(".")); // url / -> .
        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");
//        registry.enableStompBrokerRelay("/queue", "/topic", "/exchange", "/amq/queue");
    }
}
