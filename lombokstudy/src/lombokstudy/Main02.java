package lombokstudy;

public class Main02 {
	/*
	 * Ex02_User.builder() : new Builder() 생성
	 * Ex02_User.builder().id("hong") : Builder 객체의 id 멤버에 "hong" 저장
	 * Ex02_User.builder().id("hong").pw("1234") : Builder 객체의 id 멤버에 "hong" 저장, pw 멤버에 "1234" 저장.
	 * ...builder() : new Ex02_User(this)
	 */
    public static void main(String[] args) {
        Ex02_User user = Ex02_User.builder().id("hong").pw("1234").builder();
        System.out.println(user);
    }
}
