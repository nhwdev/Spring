package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@ToString
public class Mail {
    @NotEmpty(message="아이디를 입력하세요.")
    private String googleid;
    @NotEmpty(message="패스워드를 입력하세요.")
    private String googlepw;
    private String recipient;
    @NotEmpty(message="제목을 입력하세요.")
    private String title;
    private String mtype;
    private List<MultipartFile> file1;
    @NotEmpty(message="내용을 입력하세요.")
    private String contents;
}
