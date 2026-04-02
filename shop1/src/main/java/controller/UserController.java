package controller;

import dto.Mail;
import dto.Order;
import dto.User;
import dto.UserPassword;
import exception.ShopException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.ItemService;
import service.UserService;
import util.ShopUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService service;
    @Autowired
    private ItemService itemService;

    // http://localhost:8080/shop1/user/join → /WEB-INF/view/user/join.jsp
    @GetMapping("*") // Get 방식의 모든 요청
    public ModelAndView form() {
        ModelAndView mav = new ModelAndView();
        mav.addObject(new User());
        return mav; // View : null → url과 같은 위치의 jsp 페이지 요청
    }

    /*
     * @Valid 어노테이션으로 유효성 검증시 User 클래스의 userid, password 외에 name, email, birthday 등의 입력되어야 햠
     * 직접 @Valid 역할 구현해야함
     */
    @PostMapping("login")
    public ModelAndView login(User user, BindingResult bindingResult, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        // 직접 입력값 검증 구현
        if (user.getUserid() == null || (user.getUserid().trim().length() < 3 || user.getUserid().trim().length() > 10)) {
            bindingResult.rejectValue("userid", "error.required"); // (프로퍼티, 오류코드)
        }
        if (user.getPassword() == null || (user.getPassword().trim().length() < 3 || user.getPassword().trim().length() > 10)) {
            bindingResult.rejectValue("password", "error.required");
        }
        if (bindingResult.hasErrors()) {
            bindingResult.reject("error.login.check");
            return mav;
        }
        /*
         * 아이디와 비밀번호가 정상적으로 입력된 경우
         * 1. userid에 맞는 정보를 DB에서 조회
         *    userid가 없으면 아이디가 존재하지 않습니다. (error.login.userid)
         * 2. DB에 등록된 비밀번호와, 입력된 비밀번호를 비교
         *  일치 : session 객체에 loginUser이름으로 User 객체를 속성 등록
         *         페이지를 myPage로 페이지 이동
         *  불일치 : 비밀번호를 확인하세요. (error.login.password)
         */
        User dbUser = service.getUser(user.getUserid());
        if (dbUser == null) {
            bindingResult.reject("error.login.userid");
            return mav;
        }
        if (dbUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("loginUser", dbUser);
            mav.setViewName("redirect:mypage?userid=" + user.getUserid());
        } else {
            bindingResult.reject("error.login.password");
            return mav;
        }
        return mav;
    }

    /*
     * AOP 클래스로 설정
     * 1. 로그인 상태
     * 2. 관리자만 제외하고 본인 정보만 조회
     */
    @RequestMapping("mypage")
    public ModelAndView idCheckMypage(String userid, HttpSession session) {
        ModelAndView mav = new ModelAndView();
//        User user = (User) session.getAttribute("loginUser");
        User user = service.getUser(userid);
        // orderlist : 사용자가 주문한 주문데이터 정보 목록. order 테이블의 정보 + orderItem 테이블 정보 + item 테이블의 정보
        List<Order> orderlist = itemService.orderList(userid);
        mav.addObject("user", user);
        mav.addObject("orderlist", orderlist);
        return mav;
    }

    // AOP 설정되도록 메서드의 선언부 구현 필요
    @GetMapping({"update", "delete"})
    public ModelAndView idCheckUser(String userid, HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User user = service.getUser(userid);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("update")
    public String Update(@Valid User user, BindingResult bindingResult, HttpSession session) {
        // 입력값 검증
        if (bindingResult.hasErrors()) {
            bindingResult.reject("error.update.user");
            return null;
        }
        // 비밀번호 검증
        User loginUser = (User) session.getAttribute(("loginUser"));
        if (!loginUser.getPassword().equals(user.getPassword())) {
            bindingResult.reject("error.update.password");
            return null;
        }
        // 비밀번호 일치
        try {
            service.updUser(user);
            if (!loginUser.getUserid().equals(user.getUserid())) { // 본인 정보 수정하는 경우
                session.setAttribute("loginUser", user); // 로그인 정보 변경
            }
            return "redirect:mypage?userid=" + user.getUserid();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ShopException("고객 수정시 오류 발생", "update?userid=" + user.getUserid());
        }
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }

    @PostMapping("join")
    public ModelAndView join(@Valid User user, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView();
        if (bindingResult.hasErrors()) { // @Valid에서 검증한 오류 존재?
            // messages.properties 파일에서 코드를 찾아서 메시지를 출력
            bindingResult.reject("error.input.user"); // 글로벌 오류
            bindingResult.reject("error.input.check");
            return mav;
        }
        // DB에 등록
        try {
            service.insUser(user);
        } catch (DataIntegrityViolationException e) { // 키값 중복된 경우
            e.printStackTrace();
            bindingResult.reject("error.duplicate.user");
            return mav;
        } catch (Exception e) { // 중복 예외의 예외
            e.printStackTrace();
            return mav;
        }
        mav.setViewName("redirect:login"); // http://localhost:8080/shop1/user/login 페이지를 재요청(redirect)
        return mav;
    }

    /*
     * UserLoginAspect.userIdCheck() 메서드 실행 과정
     * 탈퇴 검증
     * 1. 관리자인 경우 탈퇴 불가
     * 2. 비밀번호 검증 → 로그인된 비밀번호와 비교
     *      본인탈퇴시 : 본인 비밀번호로 검증
     *      관리자타인탈퇴 : 관리자 비밀번호로 검증
     * 3. 비밀번호 불일치
     *    메시지 출력 후 delete 페이지로 이동
     * 4. 비밀번호 일치
     *    DB에서 사용자정보 삭제
     *    본인탈퇴 : 로그아웃. login 페이지로 이동
     *    관리자 타인 탈퇴 : admin/list 페이지 이동
     */
    @PostMapping("delete")
    public String idCheckDelete(String password, String userid, HttpSession session) {
        if (userid.equals("admin")) {
            throw new ShopException("관리자는 탈퇴할 수 없습니다.", "mypage?userid=admin");
        }
        // 비밀번호 검증 → 로그인 정보로 비교
        User loginUser = (User) session.getAttribute("loginUser");
        // password : 입력된 비밀번호
        // loginUser.getPassword() : db에 등록된 비밀번호
        if (!password.equals(loginUser.getPassword())) {
            throw new ShopException("비밀번호를 확인하세요", "delete?userid=" + userid);
        }
        // 비밀번호가 일치 : DB에서 userid에 해당하는 정보 삭제
        try {
            service.delUser(userid);
            // 회원 정보 삭제 성공
            if (loginUser.getUserid().equals("admin")) {
                return "redirect:../admin/list";
            } else {
                session.invalidate();
                return "redirect:login";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ShopException("탈퇴시 오류가 발생하였습니다.", "delete?userid=" + userid);
        }
    }

    /*
     * 비밀번호 변경 화면 출력
     * 1. login 검증 → AOP 클래스
     *    LoginAspect.loginCheck()
     *      → pointcut : UserController 클래스에서 메서드 이름이 loginCheck로 시작하고, 매개변수의 마지막 HttpSession인 메서드로 설정
     *         advice : Around
     */
    @GetMapping("password")
    public String loginCheckForm(HttpSession session) {
        return null;
    }

    /*
     * 1. login 검증 → AOP 클래스
     * 2. 현재비밀번호와 입력비밀번호 비교
     *    일치 : DB 수정. 로그인정보 수정. mypage로 페이지 이동
     *    불일치 : 오류메시지 출력. password로 페이지 이동
     */
    @PostMapping("password")
    public String loginCheckpassword(String password, String chgpass, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if (!password.equals(loginUser.getPassword())) {
            throw new ShopException("비밀번호 오류입니다.", "password");
        }
        try {
            service.pwUser(loginUser.getUserid(), chgpass);
            loginUser.setPassword(chgpass);
        } catch (Exception e) { // DB 수정시 오류 발생
            e.printStackTrace();
            throw new ShopException("비밀번호 변경시 DB 오류입니다.", "password");
        }
        return "redirect:mypage?userid=" + loginUser.getUserid();
    }

    /*
     * @PathVariable : {url} 값을 배개변수로 전달. url에 해당하는 값을 String url 매개변수로 전달
     *  idsearch 요청 : url = id
     *  pwsearch 요청 : url = pw
     */
    @PostMapping("{url}search") // xxsearch 요청시 호출되는 메서드(idsearch, pwsearch 요청)
    public ModelAndView search(User user, BindingResult bindingResult, HttpServletRequest request, @PathVariable String url) {
        ModelAndView mav = new ModelAndView();
        String code = "error.userid.search";
        if (url.equals("pw")) { // pwsearch 요청인 경우
                code = "error.password.search";
            if (user.getUserid() == null || user.getUserid().trim().equals("")) {
                bindingResult.rejectValue("userid", "error.required");
            }
        }
        if (user.getEmail() == null || user.getEmail().trim().equals("")) {
            bindingResult.rejectValue("email", "error.required");
        }
        if (user.getPhoneno() == null || user.getPhoneno().trim().equals("")) {
            bindingResult.rejectValue("phoneno", "error.required");
        }
        if (bindingResult.hasErrors()) {
            bindingResult.reject("error.input.check");
            return mav;
        }
        // 입력값이 정상인 경우
        // result = DB에서 조회한 아이디값 또는 비밀번호값
        String result = service.searchUser(user, url);
        if (result == null) { // 아이디 또는 비밀번호를 찾지 못함
            bindingResult.reject(code);
            return mav;
        }
        // 비밀번호 검색인 경우 비밀번호를 임의의 문자로 변경
        if(url.equals("pw")){
            result = ShopUtil.getRandomString(6, true, true);
            service.pwUser(user.getUserid(), result); // 비밀번호 변경
            Mail mail = new Mail();
            String id = "";
            String pw = "";
            mail.setGoogleid(id);
            mail.setGooglepw(pw);
            Properties prop = new Properties();
            try{
                // 1. properties 파일 로드 (SMTP 설정 불러오기)
                String path = request.getServletContext().getRealPath("/") + "/WEB-INF/CLasses/mail.properties";
                FileInputStream fis = new FileInputStream(path);
                prop.load(fis);
                // 2. Gmail 계정 인증 객체 생성
                Authenticator auth = new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(mail.getGoogleid(), mail.getGooglepw());
                    }
                };
                // 3. 수신자 이메일 설정
                mail.setRecipient(user.getEmail());
                // 4. SMTP 설정 + 인증 정보로 Gmail 연결 세션 생성
                Session session = Session.getInstance(prop, auth);
                // 5. 메일 메시지 객체 생성
                MimeMessage mailmsg = new MimeMessage(session);
                // 6. 발신자 설정
                mailmsg.setFrom(new InternetAddress(mail.getGoogleid() + "@gmail.com"));
                // 7. 수신자 설정 (인코딩 처리)
                String email = mail.getRecipient();
                InternetAddress addrs = new InternetAddress(new String(email.getBytes("utf-8"), "8859_1"));
                mailmsg.setRecipient(Message.RecipientType.TO, addrs);
                // 8. 발송 날짜 설정
                mailmsg.setSentDate(new Date());
                // 9. 제목 설정
                mailmsg.setSubject("비밀번호 변경 메일");
                // 10. 본문 작성
                MimeBodyPart message = new MimeBodyPart();
                message.setContent("변경된 비밀번호: " + result, "text/plain; charset=utf-8"); // text/plain: html❌
                // 11. 본문을 Multipart로 감싸서 메일에 첨부
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(message);
                mailmsg.setContent(multipart);
                // 12. 메일 전송
                Transport.send(mailmsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mav.addObject("result", result);
        mav.addObject("title", url.equals("pw") ? "비밀번호" : "아이디");
        mav.setViewName("search"); // 뷰이름. /WEB-INF/view/search.jsp 페이지 선택
        return mav;
    }
//=================================================================================================================
    @GetMapping("password2")
    public String chgUserForm(UserPassword userPassword, HttpSession session) {
        if (session.getAttribute("loginUser") == null) {
            throw new ShopException("로그인 하셔야합니다.", "login");
        }
        return null;
    }
    @PostMapping("password2")
    public String chgUser(UserPassword userPassword, BindingResult bindingResult, HttpSession session) {
        if (userPassword.getPassword().trim().length() < 3 || userPassword.getPassword().trim().length() > 10) {
            bindingResult.rejectValue("password", "error.required");
        }
        if (userPassword.getChgpass().trim().length() < 3 || userPassword.getChgpass().trim().length() > 10) {
            bindingResult.rejectValue("chgpass", "error.required");
        }
        // 비밀번호 변경과 비밀번호 변경 확인 입력 값이 같은지 검증
        if (userPassword.getChgpass2().equals("") || !userPassword.getChgpass().equals(userPassword.getChgpass2())) {
            bindingResult.rejectValue("chgpass2", "error.required");
        }
        if (bindingResult.hasErrors()) {
            return "user/password2";
        }
        try {
            service.pwUser(userPassword.getUserid(), userPassword.getChgpass2());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ShopException("비밀번호 변경에 실패했습니다.", "redirect:password2");
        }
        return "redirect:mypage?userid=" + userPassword.getUserid();
    }
}
