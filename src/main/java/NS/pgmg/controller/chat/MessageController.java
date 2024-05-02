package NS.pgmg.controller.chat;

import NS.pgmg.dto.chat.FindChatDto;
import NS.pgmg.dto.chat.SavePromiseDto;
import NS.pgmg.dto.chat.SendMessageDto;
import NS.pgmg.dto.chat.SendPromiseDto;
import NS.pgmg.service.chat.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chat", description = "채팅 관련 API")
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @MessageMapping("/{roomId}/talk")
    @SendTo("/sub/{roomId}")
    public ResponseEntity<?> sendMessage(
            @DestinationVariable(value = "roomId") String roomId,
            @Payload SendMessageDto request
    ) {
        return messageService.sendMessage(roomId, request);
    }

    @Operation(summary = "전체 채팅 조회")
    @PostMapping("/chat")
    public ResponseEntity<?> getChats(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindChatDto request
    ) {
        return messageService.getMessages(token, request);
    }

    @MessageMapping("/{roomId}/promise")
    @SendTo("/sub/{roomId}")
    public ResponseEntity<?> sendPromise(
            @DestinationVariable(value = "roomId") String roomId,
            @Payload SendPromiseDto request
    ) {
        return messageService.sendPromise(roomId, request);
    }

    @Operation(summary = "약속 저장")
    @PostMapping("/promise")
    public ResponseEntity<?> savePromise(
            @RequestBody SavePromiseDto request
    ) {
        return messageService.savePromise(request);
    }
}
