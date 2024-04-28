package NS.pgmg.service.chat;

import NS.pgmg.domain.chat.Chat;
import NS.pgmg.domain.chat.ChatRoom;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.chat.ChatViewDto;
import NS.pgmg.dto.chat.FindChatDto;
import NS.pgmg.dto.chat.SendMessageDto;
import NS.pgmg.repository.chat.ChatRepository;
import NS.pgmg.repository.chat.ChatRoomRepository;
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
public class ChatService {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public ResponseEntity<?> sendMessage(String roomId, SendMessageDto request) {

        User findUser = userRepository.findByName(request.getReceiverName());

        String senderEmail = request.getSenderEmail();
        String receiverEmail = findUser.getEmail();

        ChatRoom findSenderRoom = chatRoomRepository.findBySenderEmailAndReceiverEmail(senderEmail, receiverEmail);
        ChatRoom findReceiverRoom = chatRoomRepository.findBySenderEmailAndReceiverEmail(receiverEmail, senderEmail);

        if (findReceiverRoom == null) {
            findReceiverRoom = ChatRoom.builder()
                    .roomId(roomId)
                    .senderEmail(receiverEmail)
                    .receiverEmail(senderEmail)
                    .build();
        }

        findSenderRoom.updateLastMessage(request.getContent());
        findReceiverRoom.updateLastMessage(request.getContent());

        chatRoomRepository.save(findSenderRoom);
        chatRoomRepository.save(findReceiverRoom);

        Chat senderChat = Chat.builder()
                .roomId(roomId)
                .senderEmail(request.getSenderEmail())
                .receiverEmail(findUser.getEmail())
                .content(request.getContent())
                .build();

        chatRepository.save(senderChat);

        ChatViewDto view = ChatViewDto.builder()
                .roomId(roomId)
                .senderEmail(request.getSenderEmail())
                .content(senderChat.getContent())
                .createdAt(senderChat.getCreatedAt())
                .build();

        return ResponseEntity.ok().body(view);
    }

    @Transactional
    public ResponseEntity<?> getChats(String token, FindChatDto request) {

        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getSenderEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        List<Chat> findChatList = chatRepository.findAllByRoomId(request.getRoomId());

        return ResponseEntity.ok().body(findChatList);
    }
}
