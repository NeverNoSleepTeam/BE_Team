package NS.pgmg.service.chat;

import NS.pgmg.domain.chat.ChatRoom;
import NS.pgmg.domain.chat.RoomHistory;
import NS.pgmg.domain.user.User;
import NS.pgmg.dto.chat.CreateRoomDto;
import NS.pgmg.dto.chat.DeleteRoomDto;
import NS.pgmg.dto.chat.FindAllRoomDto;
import NS.pgmg.dto.chat.FindAllRoomResponseDto;
import NS.pgmg.repository.chat.ChatRoomRepository;
import NS.pgmg.repository.chat.RoomHistoryRepository;
import NS.pgmg.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static NS.pgmg.service.CommonMethod.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RoomHistoryRepository roomHistoryRepository;

    @Transactional
    public ResponseEntity<?> createRooms(String token, CreateRoomDto request) {

        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getSenderEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        User findUser = userRepository.findByName(request.getReceiverName());

        String senderEmail = request.getSenderEmail();
        String receiverEmail = findUser.getEmail();

        ChatRoom findRoom = chatRoomRepository.findBySenderEmailAndReceiverEmail(senderEmail, receiverEmail);

        RoomHistory findHistory = roomHistoryRepository.findBySenderEmailAndReceiverEmail(senderEmail, receiverEmail);

        String roomId = createRandomUuid().substring(16) + createRandomUuid().substring(0, 15);

        if (findRoom != null) {
            return ResponseEntity.badRequest().body(Map.of("message", "이미 존재하는 채팅방입니다."));
        } else if (findHistory != null) {
            ChatRoom chatRoom = ChatRoom.builder()
                    .roomId(findHistory.getRoomId())
                    .senderEmail(senderEmail)
                    .receiverEmail(receiverEmail)
                    .build();
            chatRoomRepository.save(chatRoom);

            return ResponseEntity.ok().body(Map.of("message", "채팅방이 생성됐습니다."));
        }

        ChatRoom savedSenderRoom = ChatRoom.builder()
                .roomId(roomId)
                .senderEmail(senderEmail)
                .receiverEmail(receiverEmail)
                .build();

        ChatRoom savedReceiverRoom = ChatRoom.builder()
                .roomId(roomId)
                .senderEmail(receiverEmail)
                .receiverEmail(senderEmail)
                .build();

        RoomHistory savedSenderHistory = RoomHistory.builder()
                .roomId(roomId)
                .senderEmail(senderEmail)
                .receiverEmail(receiverEmail)
                .build();

        RoomHistory savedReceiverHistory = RoomHistory.builder()
                .roomId(roomId)
                .senderEmail(receiverEmail)
                .receiverEmail(senderEmail)
                .build();

        chatRoomRepository.save(savedSenderRoom);
        chatRoomRepository.save(savedReceiverRoom);
        roomHistoryRepository.save(savedSenderHistory);
        roomHistoryRepository.save(savedReceiverHistory);

        return ResponseEntity.ok().body(Map.of("message", "채팅방이 생성됐습니다."));
    }

    @Transactional
    public ResponseEntity<?> getChatRooms(String token, FindAllRoomDto request) {

        log.info("call getChatRooms");
        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getSenderEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        List<ChatRoom> findRooms = chatRoomRepository.findAllBySenderEmail(request.getSenderEmail());

        List<FindAllRoomResponseDto> responseRooms = new ArrayList<>();
        for (ChatRoom findRoom : findRooms) {
            User receiverUser = userRepository.findByEmail(findRoom.getReceiverEmail());

            FindAllRoomResponseDto responseRoom = FindAllRoomResponseDto.builder()
                    .roomId(findRoom.getRoomId())
                    .senderEmail(findRoom.getSenderEmail())
                    .receiverName(receiverUser.getName())
                    .lastMessage(findRoom.getLastMessage())
                    .build();

            responseRooms.add(responseRoom);
        }
        return ResponseEntity.ok().body(responseRooms);
    }

    @Transactional
    public ResponseEntity<?> deleteChatRoom(String token, DeleteRoomDto request) {

        try {
            String requestEmail = tokenCheck(token);
            emailCheck(requestEmail, request.getSenderEmail());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }

        chatRoomRepository.deleteByRoomIdAndSenderEmail(request.getRoomId(), request.getSenderEmail());
        return ResponseEntity.ok().body(Map.of("message", "채팅방이 삭제됐습니다."));
    }
}
