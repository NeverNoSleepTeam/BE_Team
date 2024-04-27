package NS.pgmg.service.chat;

import NS.pgmg.domain.chat.Chat;
import NS.pgmg.domain.chat.ChatType;
import NS.pgmg.dto.chat.FindRoomDto;
import NS.pgmg.dto.chat.SendMessageDto;
import NS.pgmg.repository.chat.ChatRepository;
import NS.pgmg.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

import static NS.pgmg.service.CommonMethod.emailCheck;
import static NS.pgmg.service.CommonMethod.tokenCheck;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceStompVer {

    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ResponseEntity<?> sendMessage(String roomId, SendMessageDto request) {

//        log.info("token check");
//        if (token.isEmpty()) {
//            ResponseEntity.badRequest().body("token is empty");
//        }

        Chat chat = Chat.builder()
                .type(ChatType.TALK)
                .roomId(roomId)
                .sender(request.getSender())
                .receiver(request.getReceiver())
                .content(request.getContent())
                .build();

        return ResponseEntity.ok().body(chatRepository.save(chat));
    }

    public ResponseEntity<?> findAllMessage(String token, FindRoomDto request) {
        return ResponseEntity.ok().body(chatRepository.findAllByRoomIdAndSender(request.getRoomId(), request.getSender()));
    }
}
