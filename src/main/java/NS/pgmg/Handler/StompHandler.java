package NS.pgmg.Handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;


import static NS.pgmg.service.CommonMethod.emailCheck;
import static NS.pgmg.service.CommonMethod.tokenCheck;

@Slf4j
@Component
public class StompHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        log.info(message.getHeaders().toString());

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // 헤더 토큰 얻기
            String token = String.valueOf(accessor.getNativeHeader("Token"))
                    .replace("[", "")
                    .replace("]", "");
            String senderEmail = String.valueOf(accessor.getNativeHeader("SenderEmail"))
                    .replace("[", "")
                    .replace("]", "");

            log.info("senderEmail={}", senderEmail);

            if(token.equals("null")){
                throw new MessageDeliveryException("UNAUTHORIZED");
            }

            try {
                String requestEmail = tokenCheck(token);
                log.info("requestEmail={}", requestEmail);
                emailCheck(requestEmail, senderEmail);
            } catch (RuntimeException e) {
                throw new MessageDeliveryException("UNAUTHORIZED");
            }
        }

        return message;
    }
}