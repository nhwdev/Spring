package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("chat")
public class ChatController {

    @RequestMapping("*")
    public String getForm() {
        return null;
    }
    
    /*
     * ChatBot 구현하기
     * 1. ChatGPT를 활용ㅇ 
     */
}
