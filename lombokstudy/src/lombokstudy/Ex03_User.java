package lombokstudy;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@Getter
public class Ex03_User {
    private String id;
    private String pw;
}
