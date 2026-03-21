package lombokstudy;
/*
 * 1. 롬복(lombok)
 * 	 편집기와 빌드 도구에 연결하여 자바기능을 추가.
 * 2. 사이트 : https://projectlombok.org
 * 3. cmd 창에서 다운받은 폴더로 이동
 * 4. java -jar lombok.jar cmd 창에 명령 입력 → 이클립스 종료 → 설치 
 * 5. 이클립스 폴더로 찾아가서 lombok.jar 파일 확인, eclipse.ini 파일을 열어 lombok.jar 설정 확인(수정❌)
 * 6. 이클립스 실행하기
 * 7. classPath에 추가하기
 * 	 - 프로젝트 우클릭 → Build Path → configure build path → Libraries 탭 → ClassPath 선택 → Add External Jars → lombok.jar 파일 선택
 */

public class Main01 {
	public static void main(String[] args) {
		Ex01_User user = new Ex01_User();
		user.setId("hong");
		user.setPw("1234");
		System.out.println(user);
		System.out.println(user.getId());
		System.out.println(user.getPw());
		Ex01_User user2 = new Ex01_User("Kim", "5678");
		System.out.println(user2);
	}
}
