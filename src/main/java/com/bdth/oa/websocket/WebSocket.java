package com.bdth.oa.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author hj
 * @create 2019-04-22 11:21
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;

    //初始化websocket容器
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSocketSet.add(this);
        log.info("【webSocket消息 ====> 有新的连接 ， 当前总数为 : 】 {}",webSocketSet.size());
    }

    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        log.info("【webSocket消息 ====> 断开连接 ， 当前总数为 : 】 {}",webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String msg) {
        log.info("【webSocket消息 ====>收到客户端发来的消息, message :  {}】",msg);
    }

    public void sendMessage(String msg){

        try {
            log.info("【websocket发送消息----> message = {}】",msg);
            if (session != null) {
                session.getBasicRemote().sendText("you have a message publish ,please confirm ~");
            }
        } catch (IOException e) {
            log.warn("【websocket发送消息失败  error : {}】",e.getMessage());
        }

//        for (WebSocket webSocket : webSocketSet){
//            log.info("【websocket消息--->广播消息，】message={}",msg);
//            try {
//                webSocket.session.getBasicRemote().sendText(msg);
//            } catch (IOException e) {
//                log.warn("【websocket消息--->广播消息失败，{}】",e);
//                e.printStackTrace();
//            }
//        }
    }

    /**
     * 注入WebSocket
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
