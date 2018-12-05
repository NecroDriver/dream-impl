package com.xin.lemontree.controller.websocket;

import com.xin.lemontree.tools.request.RequestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author creator mafh 2018/2/10 17:02
 * @author updater mafh
 * @version 1.0.0
 * @description
 */
@ServerEndpoint(value = "/tokenWebSocket")
@Component
public class TokenWebSocket {

    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<Session> SESSIONS = new CopyOnWriteArraySet<>();

    /**
     * 用于区分存放session，key为当前用户token
     */
    private static ConcurrentHashMap<String, Session> WEBSOCKETS = new ConcurrentHashMap<>();

    /**
     * 日志记录
     */
    private static Logger logger = LoggerFactory.getLogger(TokenWebSocket.class);

    /**
     * 连接建立成功调用的方法
     *
     * @param session 会话
     */
    @OnOpen
    public void onOpen(Session session) {
        // 获取token参数值
        String token = RequestUtils.getUrlValue(session.getQueryString(), "token");
        // 存入map中
        WEBSOCKETS.put(token, session);
        // 存入set中
        SESSIONS.add(session);
        // 在线数 + 1
        addOnlineCount();
        logger.debug("有新连接【" + token + "】加入！当前在线人数为" + onlineCount);
        try {
            sendMessage(session, "0");
        } catch (IOException e) {
            logger.error("IO异常", e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session) {
        // 获取token参数值
        String token = RequestUtils.getUrlValue(session.getQueryString(), "token");
        // 从map中删除
        WEBSOCKETS.remove(token);
        // 从set中删除
        SESSIONS.remove(session);
        // 在线数 - 1
        reduceOnlineCount();
        logger.debug("连接【" + token + "】关闭！当前在线人数为" + onlineCount);
    }

    /**
     * 发生错误时调用
     *
     * @param session 会话
     * @param error   错误
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.debug("发生错误");
        error.printStackTrace();
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 会话
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        // 获取token参数值
        String token = RequestUtils.getUrlValue(session.getQueryString(), "token");
        logger.debug("客户端连接【" + token + "】发送来的消息：" + message);
        // 群发消息
        for (Session everySession : SESSIONS) {
            try {
                sendMessage(everySession, message);
            } catch (IOException e) {
                logger.error("群发消息异常", e);
            }
        }
    }


    /**
     * 发送消息
     *
     * @param session session
     * @param message 消息内容
     * @throws IOException io异常
     */
    public void sendMessage(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
        //session.getAsyncRemote().sendText(message);
    }

    /**
     * 根据口令发送消息
     *
     * @param token   token
     * @param message 消息内容
     * @throws IOException io异常
     */
    public void sendMessageByToken(String token, String message) throws IOException {
        Session session = WEBSOCKETS.get(token);
        session.getBasicRemote().sendText(message);
    }

    /**
     * 自增（线程安全）
     */
    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    /**
     * 自减（线程安全）
     */
    public static synchronized void reduceOnlineCount() {
        onlineCount--;
    }
}
