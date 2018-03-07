// calculate the number of e
public class CalculateE {
    public static void main(String args[]) {
        double e = 0.0;
        int n = 0, num = 1;
        while (1.0 / num >= 0.0001) {
            e += 1.0 / num;
            n++;
            num *= n;
        }
        System.out.print(e);
    }
}