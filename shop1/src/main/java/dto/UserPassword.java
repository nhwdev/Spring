package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class UserPassword {
    private String userid;
    private String password;
    private String chgpass;
    private String chgpass2;
}
