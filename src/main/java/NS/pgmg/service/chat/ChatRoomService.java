package NS.pgmg.service.chat;

import NS.pgmg.domain.chat.ChatRoom;
import NS.pgmg.dto.chat.EnterAndExitDto;
import NS.pgmg.dto.chat.FindAllRoomDto;
import NS.pgmg.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

import static NS.pgmg.service.CommonMethod.emailCheck;
import static NS.pgmg.service.CommonMethod.tokenCheck;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ResponseEntity<?> createRoom(String token, String roomId, EnterAndExitDto request) {

        try {
            String tokenEmail = tokenCheck(token);
            emailCheck(tokenEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e));
        }

        ChatRoom senderRoom = ChatRoom.builder()
                .roomId(roomId)
                .sender(request.getSender())
                .receiver(request.getReceiver())
                .build();

        ChatRoom receiverRoom = ChatRoom.builder()
                .roomId(roomId)
                .sender(request.getReceiver())
                .receiver(request.getSender())
                .build();

        chatRoomRepository.save(senderRoom);
        chatRoomRepository.save(receiverRoom);

        return ResponseEntity.ok(Map.of("message", "채팅방이 생성됐습니다."));
    }

    public ResponseEntity<?> findAllRooms(String token, FindAllRoomDto request) {

        try {
            String tokenEmail = tokenCheck(token);
            emailCheck(tokenEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e));
        }

        return ResponseEntity.ok().body(chatRoomRepository.findAllBySender(request.getSender()));
    }

    public ResponseEntity<?> deleteRoom(String token, String roomId, EnterAndExitDto request) {

        try {
            String tokenEmail = tokenCheck(token);
            emailCheck(tokenEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e));
        }

        chatRoomRepository.deleteByRoomIdAndSender(roomId, request.getSender());

        return ResponseEntity.ok().body(Map.of("message", "채팅방이 삭제됐습니다."));
    }
}
