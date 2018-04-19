import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Calculate {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("please input the function");
        try {
            String str = reader.readLine();
            String nums[] = str.split("\\+"); // +:前面字符或组匹配1或多个, 需转义
            double ans = 0.0;
            for (int i = 0; i < nums.length; i++) {
                ans += Double.parseDouble(nums[i]);
            }
            System.out.println(str + '=' + ans);
        } catch (IOException ioe) {
            System.out.println("illeagal input");
            System.out.println(ioe.getMessage());
        } catch (NumberFormatException nfe) {
            System.out.println("illegal input");
            System.out.println(nfe.getMessage());
        } catch (Exception e) {
            System.out.println("illegal input");
            System.out.println(e.getMessage());
        }

    }
}