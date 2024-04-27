package NS.pgmg.controller.chat;

import NS.pgmg.dto.chat.FindRoomDto;
import NS.pgmg.dto.chat.SendMessageDto;
import NS.pgmg.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    // 채팅방 대화
    @MessageMapping("talk.{roomId}")
    public ResponseEntity<?> talk(
//            @Header(value = "Token", required = false) String token,
            @DestinationVariable(value = "roomId") String roomId,
            @Payload SendMessageDto request) {
//        return chatService.sendMessage(token, roomId, request);
        return chatService.sendMessage(roomId, request);
    }

    @PostMapping("/chat")
    public ResponseEntity<?> findAllMessage(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindRoomDto request) {
        return chatService.findAllMessage(token, request);
    }
}
