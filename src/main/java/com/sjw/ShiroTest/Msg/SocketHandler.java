package com.sjw.ShiroTest.Msg;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:8082")
public class SocketHandler extends TextWebSocketHandler {
    public static Map<String,WebSocketSession> sessionList = new HashMap<String,WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        String sessionName = session.getId();
        if(sessionName != null && sessionName != "")
            sessionList.put(sessionName,session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessionList.remove(session.getId());
    }
}
