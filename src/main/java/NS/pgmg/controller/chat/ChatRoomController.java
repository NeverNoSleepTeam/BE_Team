package NS.pgmg.controller.chat;

import NS.pgmg.dto.chat.CreateRoomDto;
import NS.pgmg.dto.chat.DeleteRoomDto;
import NS.pgmg.dto.chat.FindAllRoomDto;
import NS.pgmg.service.chat.ChatRoomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Chat", description = "채팅 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @Operation(summary = "채팅방 생성")
    @PostMapping("/room")
    public ResponseEntity<?> createRoom(
            @RequestHeader(value = "Token") String token,
            @RequestBody CreateRoomDto request
    ) {
        return chatRoomService.createRooms(token, request);
    }

    @Operation(summary = "채팅방 전체 조회")
    @PostMapping("/rooms")
    public ResponseEntity<?> getRooms(
            @RequestHeader(value = "Token") String token,
            @RequestBody FindAllRoomDto request
    ) {
        return chatRoomService.getChatRooms(token, request);
    }

    @Operation(summary = "채팅방 삭제")
    @DeleteMapping("/room")
    public ResponseEntity<?> deleteRoom(
            @RequestHeader(value = "Token") String token,
            @RequestBody DeleteRoomDto request
    ) {
        return chatRoomService.deleteChatRoom(token, request);
    }
}
