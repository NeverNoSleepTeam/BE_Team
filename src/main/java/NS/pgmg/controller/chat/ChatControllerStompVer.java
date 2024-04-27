package NS.pgmg.controller.chat;

import NS.pgmg.dto.chat.SendMessageDto;
import NS.pgmg.service.chat.ChatServiceStompVer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatControllerStompVer {

    private final ChatServiceStompVer chatServiceStompVer;

    @MessageMapping("/{roomId}")
    @SendTo("/sub/{roomId}")
    public ResponseEntity<?> sendMessage(
            @DestinationVariable(value = "roomId") String roomId,
            @Payload SendMessageDto request
            ) {
        return chatServiceStompVer.sendMessage(roomId, request);
    }
}
