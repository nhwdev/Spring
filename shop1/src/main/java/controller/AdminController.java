package controller;

import dto.Mail;
import dto.User;
import exception.ShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/*
 * AdminController의 모든 메서드는 관리자로 로그인된 경우만 실행
 * → AOP 설정 필요 (AdminLoginAspect 클래스)
 */
@Controller                  // 이 클래스가 Spring MVC의 컨트롤러임을 선언 (요청을 받아 처리하는 역할)
@RequestMapping("admin")     // 이 컨트롤러의 모든 요청 URL 앞에 "admin/"이 붙음 (예: admin/list, admin/mail)
public class AdminController {

    @Autowired               // Spring이 UserService 구현체를 자동으로 찾아서 주입해줌 (직접 new 안 해도 됨)
    private UserService service;

    @RequestMapping("list")  // GET/POST 구분 없이 "admin/list" 요청을 이 메서드가 처리
    public ModelAndView list(HttpSession session) {
        ModelAndView mav = new ModelAndView(); // 뷰 이름과 데이터를 함께 담는 객체 생성
        // list : 모든 useraccount 테이블의 정보
        List<User> list = service.listUser();  // DB에서 전체 회원 목록 조회
        mav.addObject("list", list);           // 뷰(JSP)에서 "list"라는 이름으로 데이터를 꺼내 쓸 수 있게 저장
        return mav;                            // 뷰 이름은 자동으로 "admin/list"로 매핑됨 (RequestMapping 경로 기반)
    }

    //String[] idchks : 화면에서 전송된 idchks 파라미터가 여러개인 경우. request.getParameterValues(파라미터이름)
    @PostMapping("mailform")  // POST 방식으로 "admin/mailform" 요청이 들어올 때 실행
    public String mailform(String[] idchks, Model model) {
        if (idchks == null || idchks.length == 0) {
            // 체크박스를 하나도 선택하지 않은 경우 예외 발생 → "list" 페이지로 이동
            throw new ShopException("메일을 보낼 회원을 선택하세요.", "list");
        }
        // DB에서 idchks내의 userid값에 해당하는 User 객체들 조회
        List<User> list = service.listUser(idchks);  // 선택된 회원들만 조회
        dto.Mail mail = new Mail();                  // 메일 정보를 담을 DTO 객체 생성
        StringBuilder recipient = new StringBuilder(); // 수신자 목록을 문자열로 만들기 위한 빌더
        for (User u : list) {
            // 테스트1<test1@aaa.bbb>,테스트2<test2@aaa.bbb>, ...
            // "이름<이메일>," 형식으로 수신자를 이어붙임 → 다수 수신자를 한 번에 처리
            recipient.append(u.getUsername()).append("<").append(u.getEmail()).append(">,");
        }
        mail.setRecipient(recipient.toString()); // 완성된 수신자 목록을 mail 객체에 저장
        mail.setGoogleid("nhw.dev");             // 발신자 구글 계정 ID (앱 비밀번호 사용 계정)
        mail.setGooglepw("ffzlunjluxzygeta");    // 앱 비밀번호 (구글 2단계 인증 후 발급받은 비밀번호)
        model.addAttribute("mail", mail);        // 뷰(메일 작성 폼)에 mail 객체를 전달
        return "admin/mail";                     // admin/mail.jsp(또는 .html)로 이동
    }

    /*
     * 구글 smtp 서버를 이용하여 메일 전송하기
     * 1. 구글계정에 접속하여 2단계 인증 설정하기
     * 2. 앱비밀번호 생성하기
     * 3. 생성된 앱비밀번호를 메모장을 이용하여 저장하기
     * 4. pom.xml에 mail 관련 설정 추가
     * 5. mail.properties 파일 /resources/mail.properties
     */
    @PostMapping("mail")  // POST 방식으로 "admin/mail" 요청이 들어올 때 실행 (메일 전송 처리)
    public String mail(
            @Valid Mail mail,              // mail 객체의 유효성 검사 실행 (@NotBlank 등 어노테이션 기반)
            BindingResult bindingResult,   // @Valid 결과를 담는 객체. 오류가 있으면 hasErrors()가 true
            Model model,
            HttpServletRequest request) {

        if (bindingResult.hasErrors()) {   // 유효성 검사 실패 시 (예: 제목이 비어있는 경우)
            return null;                   // null 반환 시 현재 URL에 해당하는 뷰로 다시 이동
        }

        Properties prop = new Properties(); // SMTP 서버 설정값(host, port 등)을 담을 Properties 객체
        try {
            // getRealPath("/")로 웹 앱의 실제 배포 경로를 가져와 mail.properties 파일 경로 조합
            String path = request.getServletContext().getRealPath("/") + "/WEB-INF/classes/mail.properties";
            FileInputStream fis = new FileInputStream(path); // 파일을 바이트 스트림으로 읽기
            prop.load(fis);                                  // 파일 내용을 Properties 객체에 로드
            prop.put("mail.smtp.user", mail.getGoogleid()); // 발신자 계정을 prop에 추가로 세팅
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sendMail(mail, prop)) {                          // 실제 메일 전송 메서드 호출
            model.addAttribute("message", "메일 전송이 완료되었습니다.");
        } else {
            model.addAttribute("message", "메일 전송을 실패했습니다.");
        }
        model.addAttribute("url", "list");  // alert 페이지에서 확인 후 이동할 URL 지정
        return "alert";                     // alert.jsp로 이동 (메시지 출력 후 list로 리다이렉트)
    }

    private boolean sendMail(Mail mail, Properties prop) {
        MyAuthenticator auth = new MyAuthenticator(mail.getGoogleid(), mail.getGooglepw()); // 구글 계정 인증 객체 생성
        Session session = Session.getInstance(prop, auth); // SMTP 설정 + 인증 정보로 메일 세션 생성
        MimeMessage mailmsg = new MimeMessage(session);    // 실제로 전송할 메일 메시지 객체 생성
        try {
            // 발신자 주소 설정 (googleid + "@gmail.com" 형태)
            mailmsg.setFrom(new InternetAddress(mail.getGoogleid() + "@gmail.com"));

            List<InternetAddress> addrs = new ArrayList<InternetAddress>(); // 수신자 주소 리스트
            String[] emails = mail.getRecipient().split(",");               // "이름<이메일>," 형식을 ","로 분리
            for (String email : emails) {
                try {
                    // 한글 이름이 깨지지 않도록 UTF-8 → ISO-8859-1로 인코딩 변환 후 InternetAddress 생성
                    addrs.add(new InternetAddress(new String(email.getBytes("utf-8"), "8859_1")));
                } catch (UnsupportedEncodingException ue) {
                    ue.printStackTrace();
                }
            }

            // List를 배열로 변환 (setRecipients 메서드가 배열을 받기 때문)
            InternetAddress[] arr = new InternetAddress[emails.length];
            for (int i = 0; i < addrs.size(); i++) {
                arr[i] = addrs.get(i);
            }
            mailmsg.setRecipients(Message.RecipientType.TO, arr); // 수신자(TO) 설정
            mailmsg.setSentDate(new Date());                      // 발송 날짜/시간 설정 (현재 시각)
            mailmsg.setSubject(mail.getTitle());                  // 메일 제목 설정

            // MimeMultipart: 메일 본문 + 첨부파일을 함께 담기 위한 멀티파트 구조
            MimeMultipart multipart = new MimeMultipart();
            MimeBodyPart message = new MimeBodyPart();            // 메일 본문을 담을 파트
            message.setContent(mail.getContents(), mail.getMtype()); // 내용 + MIME 타입(text/html 등) 설정
            multipart.addBodyPart(message);                       // 멀티파트에 본문 추가

            // 첨부파일이 있는 경우 각각을 멀티파트에 추가
            for (MultipartFile mf : mail.getFile1()) {
                if ((mf != null) && (!mf.isEmpty())) {    // 파일이 실제로 존재하는 경우만 처리
                    multipart.addBodyPart(bodyPart(mf));  // 첨부파일 파트 생성 후 추가
                }
            }
            mailmsg.setContent(multipart); // 완성된 멀티파트(본문+첨부)를 메일에 최종 세팅
            Transport.send(mailmsg);       // 메일 전송 실행
            return true;                  // 전송 성공
        } catch (MessagingException me) {
            me.printStackTrace();
        }
        return false; // 예외 발생 시 전송 실패
    }

    private BodyPart bodyPart(MultipartFile mf) {
        MimeBodyPart body = new MimeBodyPart();      // 첨부파일을 담을 파트 객체 생성
        String orgFile = mf.getOriginalFilename();   // 업로드된 파일의 원본 파일명 추출
        String path = "c:/Dev/spring/mailupload/"; // 서버에 임시 저장할 경로
        File f1 = new File(path);
        if (!f1.exists()) f1.mkdirs(); // 경로가 없으면 디렉토리 생성 (mkdirs: 중간 경로도 모두 생성)
        File f2 = new File(path + orgFile); // 저장할 실제 파일 객체 생성
        try {
            mf.transferTo(f2); // MultipartFile(메모리/임시파일)을 실제 디스크 경로에 저장
            body.attachFile(f2); // 저장된 파일을 첨부파일로 설정
            // 한글 파일명 깨짐 방지: UTF-8 → ISO-8859-1 변환 (메일 표준 인코딩 방식)
            body.setFileName(new String(orgFile.getBytes("UTF-8"), "8859_1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return body; // 완성된 첨부파일 파트 반환
    }

    // 인증객체. AdminController 클래스의 내부클래스로 구현함.
    // Authenticator를 상속받아 SMTP 로그인 시 사용할 계정/비밀번호를 제공하는 역할
    private static final class MyAuthenticator extends Authenticator {
        private final String id; // 구글 계정 ID
        private final String pw; // 앱 비밀번호

        public MyAuthenticator(String id, String pw) {
            this.id = id;
            this.pw = pw;
        }

        @Override
        protected PasswordAuthentication getPasswordAuthentication() {
            // SMTP 인증 시 JavaMail이 이 메서드를 자동으로 호출하여 계정 정보를 가져감
            return new PasswordAuthentication(id, pw);
        }
    }
}