package util;

import java.security.SecureRandom;

/*
 * 랜덤 문자열을 생성
 *
 * @param count  생성할 문자열의 길이
 * @param letter 영문자(대소문자) 포함 여부
 * @param number 숫자 포함 여부
 * @return 랜덤으로 생성된 문자열 (letter, number 둘 다 false면 빈 문자열 반환)
 *
 * @example
 * getRandomString(8, true, true)   // 영문+숫자 8자리  ex) "aB3kR7mZ"
 * getRandomString(6, false, true)  // 숫자만 6자리     ex) "483920"
 * getRandomString(4, true, false)  // 영문만 4자리     ex) "kRmZ"
 */
public class ShopUtil {
    public static String getRandomString(int count, boolean letter, boolean number) {
        StringBuilder pool = new StringBuilder();
        if (letter) {
            for (char ch = 'A'; ch <= 'Z'; ch++) { // 대문자 추가
                pool.append(ch);
            }
            for (char ch = 'a'; ch <= 'z'; ch++) { // 소문자 추가
                pool.append(ch);
            }
        }
        if (number) {
            for (int n = 0; n <= 9; n++) {
                pool.append(n);
            }
        }
        // SecureRandom : Random(난수발생) 클래스의 보안성을 강화한 클래스. 암호학적으로 강력한 난수 생성 클래스
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        if (letter || number) {
            while (count > 0) { // count 갯수만큼 list객체에서 임의로 요소를 builder 추가
                sb.append(pool.charAt(secureRandom.nextInt(pool.length())));
                count--;
            }
        }
        return sb.toString(); // String 객체 리턴
    }
}
