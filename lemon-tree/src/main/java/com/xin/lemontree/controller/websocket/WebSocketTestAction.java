package com.xin.lemontree.controller.websocket;

import com.xin.lemontree.common.base.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author creator mafh 2018/5/4 14:24
 * @author updater mafh
 * @version 1.0.0
 * @description
 */
@RestController
@RequestMapping("/api/webSocket")
public class WebSocketTestAction extends BaseAction {

    /**
     * websocket处理类
     */
    @Autowired
    private TokenWebSocket tokenWebSocket;


    /**
     * 手动发送webSocket消息
     *
     * @param token   口令
     * @param message 信息
     */
    @RequestMapping(value = "/sendWebSocketByToken", method = RequestMethod.POST)
    public void sendWebSocketByToken(String token, String message) {
        try {
            tokenWebSocket.sendMessageByToken(token, message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
