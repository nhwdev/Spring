package annotation;

import org.springframework.stereotype.Component;

@Component
public class MemberService {
    public void regist(Member member) {
        System.out.println("MemberService.regist(Member) 메서드 실행");
    }
    public boolean update(String memberid, UpdateInfo info) {
        System.out.println("MemberService.update(String, UpdateInfo) 메서드 실행");
        return true;
    }
    public boolean delete(String memberid, String name, UpdateInfo info) {
        System.out.println("MemberService.delete(String, String) 메서드 실행");
        return false;
    }

}
