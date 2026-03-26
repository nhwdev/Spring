package controller;

import dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService service;

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

    @GetMapping("mypage")
    public ModelAndView mypage(HttpSession session) {
        ModelAndView mav = new ModelAndView();
        User user = (User) session.getAttribute("loginUser");
        mav.addObject("user", user);
        return mav;
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
            service.userInsert(user);
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
}
