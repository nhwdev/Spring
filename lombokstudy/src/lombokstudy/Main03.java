package lombokstudy;

public class Main03 {
    public static void main(String[] args) {
        Ex03_User user1 = Ex03_User.builder().id("hong").pw("1234").build();
        System.out.println(user1);
        // AllArgsConstructor 가능
        Ex03_User user2 = new Ex03_User("kim", "5678");
        System.out.println(user2.getId());
        System.out.println(user2.getPw());
        System.out.println(user2);
        Ex03_User user3 = Ex03_User.builder().build(); // 멤버가 초기화되지 않은 객체 리턴
        System.out.println(user3);
    }
}

