package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.util.Date;

@Getter
@Setter
@ToString
public class User {
    // @Size : 입력한 값의 길이가 3~10자 사이
    @Size(min = 3, max = 10, message = "아이디는 3~10자 사이로 입력해주세요.")
    private String userid;
    private String channel;
    @Size(min = 3, max = 10, message = "비밀번호는 3~10자 사이로 입력해주세요.")
    private String password;
    @NotEmpty(message = "이름을 입력해주세요.")
    private String username;
    private String phoneno;
    private String postcode;
    private String address;
    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식으로 입력해주세요.") // email 형식부분 검사
    private String email;
    @NotNull(message = "생년월일을 입력해주세요.")
    @Past(message = "생년월일은 오늘 이전 날짜로 입력해주세요.")
    @DateTimeFormat(pattern = "yyyy-MM-dd") // 날짜의 형식 지정
    private Date birthday;
}