package NS.pgmg.service.chat;

import NS.pgmg.domain.chat.ChatRoom;
import NS.pgmg.domain.chat.ChatType;
import NS.pgmg.domain.chat.Message;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.chat.*;
import NS.pgmg.repository.chat.MessageRepository;
import NS.pgmg.repository.chat.RoomRepository;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static NS.pgmg.service.CommonMethod.emailCheck;
import static NS.pgmg.service.CommonMethod.tokenCheck;


@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public ResponseEntity<?> sendMessage(String roomId, SendMessageDto request) {

        User findUser = userRepository.findByEmail(request.getReceiverEmail());

        String senderEmail = request.getSenderEmail();
        String receiverEmail = findUser.getEmail();

        ChatRoom findSenderRoom = roomRepository.findBySenderEmailAndReceiverEmail(senderEmail, receiverEmail);
        ChatRoom findReceiverRoom = roomRepository.findBySenderEmailAndReceiverEmail(receiverEmail, senderEmail);

        if (findReceiverRoom == null) {
            findReceiverRoom = ChatRoom.builder()
                    .roomId(roomId)
                    .senderEmail(receiverEmail)
                    .receiverEmail(senderEmail)
                    .build();
        }

        findSenderRoom.updateLastMessage(request.getContent());
        findReceiverRoom.updateLastMessage(request.getContent());

        roomRepository.save(findSenderRoom);
        roomRepository.save(findReceiverRoom);

        Message senderChat = Message.builder()
                .type(ChatType.TALK)
                .roomId(roomId)
                .senderEmail(request.getSenderEmail())
                .receiverEmail(findUser.getEmail())
                .content(request.getContent())
                .build();

        messageRepository.save(senderChat);

        ChatViewDto view = ChatViewDto.builder()
                .type(ChatType.TALK)
                .roomId(roomId)
                .senderEmail(request.getSenderEmail())
                .content(senderChat.getContent())
                .createdAt(senderChat.getCreatedAt())
                .build();

        return ResponseEntity.ok().body(view);
    }

    @Transactional
    public ResponseEntity<?> getMessages(String token, FindChatDto request) {

        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getSenderEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        List<Message> findChatList = messageRepository.findAllByRoomIdOrderByCreatedAtDesc(request.getRoomId());

        return ResponseEntity.ok().body(findChatList);
    }

    public ResponseEntity<?> sendPromise(String roomId, SendPromiseDto request) {

        Message message = Message.builder()
                .type(ChatType.PROMISE)
                .roomId(roomId)
                .senderEmail(request.getSenderEmail())
                .receiverEmail(request.getReceiverEmail())
                .content(request.getContent())
                .date(request.getDate())
                .place(request.getPlace())
                .price(request.getPrice())
                .build();

        return ResponseEntity.ok().body(message);
    }

    public ResponseEntity<?> savePromise(SavePromiseDto request) {
        Message message = Message.builder()
                .type(ChatType.PROMISE)
                .roomId(request.getRoomId())
                .senderEmail(request.getSenderEmail())
                .receiverEmail(request.getReceiverEmail())
                .content(request.getContent())
                .date(request.getDate())
                .place(request.getPlace())
                .price(request.getPrice())
                .build();

        return ResponseEntity.ok().body(messageRepository.save(message));
    }
}
