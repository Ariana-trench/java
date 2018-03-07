import java.lang.Math;

// create random student id
public class RandomRollCall01 {
    public static void main(String args[]) {
        int lenOfId = 10;  // the length of id
        while (true) {
            double num = Math.random();  // 0.xxxxx
            if (String.valueOf(num).length() > 1 + lenOfId) {  // most time the length is around 18, very few time less than 12
                System.out.println(String.valueOf(num).substring(2, 2 + lenOfId));
                break;
            }
        }
    }
}