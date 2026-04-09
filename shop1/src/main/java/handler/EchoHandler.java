package handler;

import dto.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class EchoHandler extends TextWebSocketHandler implements InitializingBean {
    private Set<WebSocketSession> clients; // 현재 접속된 모든 브라우저(클라이언트)의 WebSocketSession 객체

    /**
     * ================================
     * [필수 작성] 초기화 함수
     * ================================
     * Spring Bean이 만들어질 때 딱 한 번 자동 실행됨
     * clients 변수를 여기서 초기화해야 함
     * 안 하면 clients가 null이라서 접속하자마자 NullPointerException 터짐
     * <p>
     * TODO: 아래 코드 작성
     * clients = new HashSet<>();
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 동기화 기능을 추가한 형태로 변환
        clients = Collections.synchronizedSet(new HashSet<WebSocketSession>());
    }

    /**
     * ================================
     * [필수 작성] 클라이언트 접속 함수
     * ================================
     * 새 클라이언트가 서버에 접속하면 자동 실행됨
     * 여기서 접속한 세션을 clients에 추가해야
     * 나중에 메시지를 그 클라이언트에게 보낼 수 있음
     * <p>
     * TODO: 아래 코드 작성
     * clients.add(session);                         // 세션 등록
     * <p>
     * 추가하면 좋은 것:
     * String msg = "님이 입장했습니다.";
     * 접속한 모든 클라이언트에게 입장 알림 브로드캐스트
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("클라이언트 접속 : " + session.getId());
        clients.add(session);
        // WebSocketConfig 클래스에서 .addInterceptors(new HttpSessionHandshakeInterceptor()) 설정이 필요
        Map<String, Object> map = session.getAttributes();
        User loginUser = (User) map.get("loginUser"); // HttpSession에 설정된 로그인 정보 조회
        System.out.println(loginUser.getUserid()); // 로그인된 아이디정보 화면에 출력
    }

    /**
     * ================================
     * [선택] 모든 타입 메시지 수신 함수
     * ================================
     * 클라이언트로부터 메시지가 오면 가장 먼저 실행됨
     * 텍스트, 바이너리, Pong 등 모든 메시지 타입을 여기서 받음
     * 내부적으로 타입을 분기해서 아래 함수들을 호출함
     * <p>
     * handleMessage (여기)
     * ↓
     * 텍스트 → handleTextMessage
     * Pong  → handlePongMessage
     * <p>
     * ⚠️ 주의: handleTextMessage와 동시에 구현하면 텍스트 메시지가 두 번 처리됨
     * 일반 채팅은 handleTextMessage만 구현할 것
     * 이 함수는 건드리지 않아도 됨
     * <p>
     * TODO: 건드리지 말 것 (handleTextMessage에 작성할 것)
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String loadMessage = (String) message.getPayload(); // 클라이언트가 전송한 메시지
        System.out.println(session.getId() + " : 클라이언트 메시지 : " + loadMessage);
        clients.add(session); // Set 같은 객체를 추가 안됨. 필요❌
        for (WebSocketSession s : clients) {
            s.sendMessage(new TextMessage(loadMessage)); // 모든 클라이언트에게 수신된 메시지 전송. 브로드캐스트(broadcast)
        }
    }

    /**
     * ================================
     * [필수 작성] 텍스트 메시지 수신 함수
     * ================================
     * 클라이언트가 텍스트 메시지를 보내면 자동 실행됨
     * 실질적인 채팅 로직을 여기에 작성함
     * handleMessage가 내부적으로 타입 확인 후 이 함수를 호출해줌
     *
     * TODO: 아래 코드 작성
     * String msg = message.getPayload();            // 클라이언트가 보낸 메시지 꺼내기
     * for (WebSocketSession s : clients) {          // 모든 클라이언트에게 전송 (브로드캐스트)
     *     if (s.isOpen()) {                         // 열려있는 세션에만 전송
     *         s.sendMessage(new TextMessage(msg));
     *     }
     * }
     *
     * 추가하면 좋은 것:
     * 닉네임 기능: "홍길동: 안녕하세요" 형식으로 붙여서 전송
     * 귓속말 기능: 메시지가 "/귓속말 대상ID 내용" 이면 해당 세션에만 전송
     */
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//
//    }

    /**
     * ================================
     * [선택] Pong 수신 함수
     * ================================
     * 서버가 클라이언트에게 Ping을 보냈을 때
     * 클라이언트가 살아있으면 Pong으로 응답함
     * 그 Pong을 받으면 이 함수가 실행됨 (생존 확인 용도)
     *
     * 지금 단계에서는 구현하지 않아도 됨
     *
     * 추가하면 좋은 것:
     * 마지막 Pong 수신 시간을 기록해두고
     * 일정 시간 Pong이 안 오는 세션은 끊긴 것으로 판단해서 제거
     *
     * TODO: 지금은 생략 가능
     */
//    @Override
//    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
//
//    }

    /**
     * ================================
     * [필수 작성] 에러 발생 함수
     * ================================
     * 통신 중 네트워크 오류나 예외가 발생하면 자동 실행됨
     * 에러가 난 세션을 clients에서 제거하지 않으면
     * 이후에도 계속 메시지를 보내려다 에러가 반복 발생함
     * <p>
     * TODO: 아래 코드 작성
     * clients.remove(session);                      // 에러난 세션 제거
     * if (session.isOpen()) session.close();        // 열려있으면 강제 종료
     * <p>
     * 추가하면 좋은 것:
     * 에러 로그 저장 (어떤 에러인지 기록)
     * exception.getMessage() 로 에러 내용 확인 가능
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception); // TextWebSocketHandler 클래스의 메서드
        System.out.println("오류발생 : " + exception.getMessage());
    }

    /**
     * ================================
     * [필수 작성] 연결 종료 함수
     * ================================
     * 클라이언트가 브라우저를 닫거나 연결을 끊으면 자동 실행됨
     * 여기서 clients에서 세션을 제거하지 않으면
     * 없는 클라이언트에게 계속 메시지를 보내려다 에러가 터짐
     * <p>
     * TODO: 아래 코드 작성
     * clients.remove(session);                      // 세션 제거 (가장 중요!)
     * <p>
     * 추가하면 좋은 것:
     * String msg = "님이 퇴장했습니다.";
     * 나머지 클라이언트에게 퇴장 알림 브로드캐스트
     * <p>
     * CloseStatus로 정상종료인지 비정상종료인지 구분 가능
     * CloseStatus.NORMAL    = 정상 종료
     * CloseStatus.NO_STATUS = 비정상 종료
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println("클라이언트 접속 해제 : " + status.getReason());
        clients.remove(session); // 클라이언트 목록에서 제거
    }

    /**
     * ================================
     * [선택] 분할 메시지 지원 여부 함수
     * ================================
     * 큰 메시지를 여러 조각으로 나눠서 받을지 결정하는 함수
     * <p>
     * false = 메시지를 한 번에 완성된 형태로만 받음 (기본값, 일반 채팅에 적합)
     * true  = 메시지가 조각나서 와도 처리 가능 (대용량 파일 전송 등에 사용)
     * <p>
     * 일반 채팅이면 건드리지 않아도 됨
     * <p>
     * TODO: 일반 채팅이면 생략 가능
     */
    @Override
    public boolean supportsPartialMessages() { // 수신된 메시지를 한번에 수신
        return false;
    }
}