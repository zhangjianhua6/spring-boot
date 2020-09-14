package com.jason.web.websocket.spring;

import com.alibaba.fastjson.JSONObject;
import com.jason.web.websocket.javax.JavaxWebSocket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MyHandler extends TextWebSocketHandler implements ApplicationListener<ContextRefreshedEvent> {

    private static CopyOnWriteArraySet<WebSocketSession> webSockets = new CopyOnWriteArraySet<WebSocketSession>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        webSockets.add(session);
        log.info("spring【新建连接】，连接总数:{}", webSockets.size());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSockets.remove(session);
        log.info("spring【断开连接】，连接总数:{}", webSockets.size());
    }

    private void sendMessage(String message){
        if (webSockets.size() <= 0){
            return;
        }
        log.info("spring【广播发送】，信息:{}，总连接数:{}", message, webSockets.size());
        for (WebSocketSession webSocket : webSockets) {
            try {
                webSocket.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                log.info("spring【广播发送】，信息异常:{}", e.fillInStackTrace());
            }
        }
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject json = null;
                int index = 0;
                while(true){
                    try {
                        json = new JSONObject();
                        String message = "后台返回第{0}的消息！";
                        json.put("message", MessageFormat.format(message, ++index));
                        sendMessage(json.toString());
                        TimeUnit.SECONDS.sleep(5);
                    }catch (Exception ex){
                        ex.printStackTrace();
                    }
                }

            }
        }).start();
    }


}
