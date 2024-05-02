package NS.pgmg.controller.chat;

import NS.pgmg.dto.chat.CreateRoomDto;
import NS.pgmg.dto.chat.DeleteRoomDto;
import NS.pgmg.dto.chat.FindAllRoomDto;
import NS.pgmg.service.chat.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Chat", description = "채팅 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "채팅방 생성")
    @PostMapping("/room")
    public ResponseEntity<?> createRoom(
            @RequestHeader(value = "Token") String token,
            @RequestBody CreateRoomDto request
    ) {
        return roomService.createRooms(token, request);
    }

    @Operation(summary = "채팅방 전체 조회")
    @PostMapping("/rooms")
    public ResponseEntity<?> getRooms(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindAllRoomDto request
    ) {
        return roomService.getChatRooms(token, request);
    }

    @MessageMapping("/{roomId}/exit")
    @SendTo("/sub/{roomId}")
    public ResponseEntity<?> deleteRoom(
            @DestinationVariable(value = "roomId") String roomId,
            @Payload DeleteRoomDto request
    ) {
        return roomService.deleteChatRoom(roomId, request);
    }
}
