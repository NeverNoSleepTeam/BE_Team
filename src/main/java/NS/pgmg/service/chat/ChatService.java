package NS.pgmg.service.chat;

import NS.pgmg.domain.chat.Chat;
import NS.pgmg.domain.chat.ChatRoom;
import NS.pgmg.domain.chat.ChatType;
import NS.pgmg.dto.chat.EnterAndExitDto;
import NS.pgmg.dto.chat.FindRoomDto;
import NS.pgmg.dto.chat.SendMessageDto;
import NS.pgmg.repository.chat.ChatRepository;
import NS.pgmg.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

import static NS.pgmg.service.CommonMethod.emailCheck;
import static NS.pgmg.service.CommonMethod.tokenCheck;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final RabbitTemplate rabbitTemplate;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    public ResponseEntity<?> sendMessage(String roomId, SendMessageDto request) {

//        try {
//            String tokenEmail = tokenCheck(token);
//            emailCheck(tokenEmail, request.getEmail());
//        } catch (RuntimeException e) {
//            return ResponseEntity.badRequest().body(Map.of("message", e));
//        }

        Chat chat = Chat.builder()
                .type(ChatType.TALK)
                .roomId(roomId)
                .sender(request.getSender())
                .receiver(request.getReceiver())
                .content(request.getContent())
                .build();
//
//        ChatRoom findSenderRoom = chatRoomRepository.findByRoomIdAndSender(roomId, request.getSender());
//        ChatRoom findReceiverRoom = chatRoomRepository.findByRoomIdAndSender(roomId, request.getReceiver());
//
//        if (findReceiverRoom == null) {
//            ChatRoom newRoom = ChatRoom.builder()
//                    .roomId(roomId)
//                    .lastMessage(request.getContent())
//                    .sender(request.getReceiver())
//                    .receiver(request.getSender())
//                    .build();
//
//            chatRoomRepository.save(newRoom);
//        } else {
//            findReceiverRoom.updateLastMessage(request.getContent());
//            chatRoomRepository.save(findReceiverRoom);
//        }
//
//        findSenderRoom.updateLastMessage(request.getContent());
//        chatRoomRepository.save(findSenderRoom);

        rabbitTemplate.convertAndSend("chat.exchange", "room." + roomId, chat);
        return ResponseEntity.ok().body(chatRepository.save(chat));
    }

    public ResponseEntity<?> findAllMessage(String token, FindRoomDto request) {

        try {
            String tokenEmail = tokenCheck(token);
            emailCheck(tokenEmail, request.getEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e));
        }

        return ResponseEntity.ok().body(chatRepository.findAllByRoomIdAndSender(request.getRoomId(), request.getSender()));
    }
}
