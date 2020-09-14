package com.jason.web.websocket.javax;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

@Slf4j
@ServerEndpoint("/websocket/javax")
public class JavaxWebSocket implements ApplicationListener<ContextRefreshedEvent> {

    private static CopyOnWriteArraySet<JavaxWebSocket> webSockets = new CopyOnWriteArraySet<JavaxWebSocket>();
    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {

        this.session = session;
        webSockets.add(this);
        log.info("javax【新建连接】，连接总数:{}", webSockets.size());
    }

    @OnClose
    public void onClose() throws IOException {
        webSockets.remove(this);
        log.info("javax【断开连接】，连接总数:{}", webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("javax【收到】，客户端的信息:{}，连接总数:{}", message, webSockets.size());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public static void sendMessage(String message) throws IOException {
        if (webSockets.size() <= 0){
            return;
        }
        log.info("javax【广播发送】，信息:{}，总连接数:{}", message, webSockets.size());
        for (JavaxWebSocket webSocket : webSockets) {
            try {
                webSocket.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                log.info("【广播发送】，信息异常:{}", e.fillInStackTrace());
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
