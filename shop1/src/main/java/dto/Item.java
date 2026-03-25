package dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@ToString
public class Item {
    private int id;
    // null 또는 공백인 경우 오류 검증
    @NotEmpty(message = "상품명을 입력하세요.")
    private String name;
    @Min(value = 10, message = "가격은 10원 이상이어야 합니다.")
    @Max(value = 100000, message = "가격은 100,000원을 초과할 수 없습니다.")
    private int price;
    @NotEmpty(message = "상품설명을 입력하세요.")
    private String description;
    private String pictureUrl;
    // <input type="file" name="picture"/> 에서 선택된 파일 정보 저장
    private MultipartFile picture;
}
