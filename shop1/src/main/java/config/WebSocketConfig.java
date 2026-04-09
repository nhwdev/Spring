package config;

import handler.EchoHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration // 환경설정
@EnableWebSocket // 웹소켓의 기능 활성화
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    EchoHandler echoHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(echoHandler, "/chatting") // ws://서버주소/chatting 요청시 EchoHandler 활용
                .setAllowedOrigins("*") // 외부 접속 허용. 모든 도메인 허용.
                .addInterceptors(new HttpSessionHandshakeInterceptor());
        /*
         * .addInterceptors(new HttpSessionHandshakeInterceptor()); → 웹소켓세션에 HttpSession 복사함
         *                                                          → HttpSession 사용 가능
         *   HttpSession : 웹에서 사용중인 session 객체
         *   WebSocketSession : 웹 소켓에서 사용중인 session 객체
         *
         *   → 웹소켓세션에 로그인 정보 조회가능
         */
    }
}