package NS.pgmg.controller.chat;

import NS.pgmg.dto.chat.FindChatDto;
import NS.pgmg.dto.chat.SavePromiseDto;
import NS.pgmg.dto.chat.SendMessageDto;
import NS.pgmg.dto.chat.SendPromiseDto;
import NS.pgmg.service.chat.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
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
        log.info("Call MessageController.sendMessage");
        return messageService.sendMessage(roomId, request);
    }

    @Operation(summary = "전체 채팅 조회")
    @PostMapping("/chat")
    public ResponseEntity<?> getChats(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindChatDto request
    ) {
        log.info("Call MessageController.getChats");
        return messageService.getMessages(token, request);
    }

    @MessageMapping("/{roomId}/promise")
    @SendTo("/sub/{roomId}")
    public ResponseEntity<?> sendPromise(
            @DestinationVariable(value = "roomId") String roomId,
            @Payload SendPromiseDto request
    ) {
        log.info("Call MessageController.sendPromise");
        return messageService.sendPromise(roomId, request);
    }

    @Operation(summary = "약속 저장")
    @PostMapping("/promise")
    public ResponseEntity<?> savePromise(
            @RequestBody SavePromiseDto request
    ) {
        log.info("Call MessageController.savePromise");
        return messageService.savePromise(request);
    }
}
