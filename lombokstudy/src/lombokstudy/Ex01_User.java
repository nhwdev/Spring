package lombokstudy;

import lombok.AllArgsConstructor;
import lombok.Data;
// import lombok.Getter;
import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;

// @Setter
// @Getter
// @ToString
@Data // Setter, Getter, ToString 생성
@AllArgsConstructor
@NoArgsConstructor
public class Ex01_User {
	private String id;
	private String pw;
}
/*
 * lombok 에서 사용되는 주요 어노테이션
 * 	@Getter/Setter : getter, setter 소스 자동 생성
 *  @ToString : toString() 메서드 오버라이딩. 현재 멤버에 맞도록 to STirng 메서드 생성
 *  @NoArgsConstructor : 매개변수 없는 생성자 생성함
 *  @AllArgsConstructor : 모든 멤버를 매개변수로 가진 생성자 생성함
 *  @Data : @ToString / @Getter / @Setter / @EqualsAndHashCode / @RequitedArgsConstructor 기능 전부
 * ====================================================
 *  @EqualsAndHashCode : equals 메서드, hashCode 메서드를 오버라이딩 함.
 *  @RequiredArgsConstructor : 필수 멤버만 매개변수로 가진 생성자 생성
 *  						   필수 멤버 : final 또는 @NotNull이 설정된 멤버변수
 * ====================================================
 * @Builder : Builder 패턴의 객체 생성
 */