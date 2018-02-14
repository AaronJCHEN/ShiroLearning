package com.sjw.ShiroTest.Msg;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:8082")
public class SocketHandler extends TextWebSocketHandler {
    
}
