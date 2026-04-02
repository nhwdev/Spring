package dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class Board {
    private int num;
    private String boardid;
    @NotEmpty(message = "글쓴이를 입력하세요.")
    private String writer;
    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String pass;
    @NotEmpty(message = "제목을 입력하세요.")
    private String title;
    @NotEmpty(message = "내용을 입력하세요.")
    private String content;
    private MultipartFile file1;
    private String fileurl;
    private Date regdate;
    private int readcnt;
    private int grp;
    private int grplevel;
    private int grpstep;

    public String getBoardName() {
        String boardName = null;
        switch (boardid) {
            case "1":
                boardName = "공지사항";
                break;
            case "2":
                boardName = "자유게시판";
                break;
            case "3":
                boardName = "도움말";
                break;
            default:
                boardName = "게시판 번호 오류";
                break;
        }
        return boardName;
    }
}

