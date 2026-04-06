package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@ToString
public class Comment {
    int num;
    int seq;
    @NotEmpty(message = "작성자를 입력하세요.")
    String writer;
    @NotEmpty(message = "내용를 입력하세요.")
    String content;
    @NotEmpty(message = "비밀번호를 입력하세요.")
    String pass;
    Date regdate;
}
