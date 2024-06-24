package com.jaykims.basic.demowebsocket;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

@Controller
public class NotificationController {

    private final SimpMessagingTemplate template;

    @Autowired
    public NotificationController(SimpMessagingTemplate template) {
        this.template = template;
    }

    //클라이언트로부터 직접 메세지를 받는다. (예:읽음처리)
    @MessageMapping("/notify")
    public String receivedMsgFromClient(String msg){
        System.out.println(msg);
        return msg;
    }

    // 웹 요청을 통해 메시지를 보낼 수 있는 간단한 API
    @GetMapping("/send")
    public @ResponseBody String sendNoticeNotification(@RequestParam(value = "msg", defaultValue = "TEST MESSAGE") String message) {
        template.convertAndSend("/topic/notice", message);

//        template.convertAndSendToUser("user123","/queue/notifications",message);
        System.out.println(message);
        return "Sended : "+message;

    }


}