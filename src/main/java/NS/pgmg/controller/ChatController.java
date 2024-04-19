package NS.pgmg.controller;

import NS.pgmg.dto.chat.MessageRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final RabbitTemplate rabbitTemplate;

    // 채팅방 입장
    @MessageMapping("chat.enter.{roomId}")
    public MessageRequestDto enter(
            @DestinationVariable(value = "roomId") String roomId,
            @Payload MessageRequestDto message) {
        message.setContent(message.getSender() + "님이 채팅방에 입장했습니다.");
        log.info("content={}", message.getContent());
        rabbitTemplate.convertAndSend("chat.exchange", "enter.room." + roomId, message);
        return message;
    }

    // 채팅방 대화
    @MessageMapping("chat.talk.{roomId}")
    public void talk(
            @DestinationVariable(value = "roomId") String roomId,
            @Payload MessageRequestDto message) {
        rabbitTemplate.convertAndSend("chat.exchange", "*.room." + roomId, message);
        log.info("CHAT_TALK_{}", message);
    }

    // 채팅방 퇴장
    @MessageMapping("chat.exit.{roomId}")
    public void exit(
            @DestinationVariable(value = "roomId") String roomId,
            @Payload MessageRequestDto message) {
        message.setContent(message.getSender() + "님이 채팅방에서 퇴장했습니다.");
        rabbitTemplate.convertAndSend("chat.exchange", "exit.room." + roomId, message);
    }
}
