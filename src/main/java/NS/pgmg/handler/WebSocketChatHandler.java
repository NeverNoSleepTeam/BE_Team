package NS.pgmg.handler;

import NS.pgmg.dto.chat.ChatMessage;
import NS.pgmg.dto.chat.ChatRoom;
import NS.pgmg.service.chat.ChatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ChatService chatService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload = {}", payload);

        ChatMessage chatMessage = objectMapper.readValue(payload, ChatMessage.class);
        ChatRoom room = chatService.findRoomById(chatMessage.getRoomId());
        room.handlerActions(session, chatMessage, chatService);
    }
}
