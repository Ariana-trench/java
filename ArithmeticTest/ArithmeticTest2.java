import java.io.*;
import java.util.*;

public class ArithmeticTest2 {
    public static void main(String[] args) throws Exception {
        int numOfQues = 10;
        int numOfCor = 0;
        int len = Integer.parseInt(args[0]);
        Addition question[] = new Addition[numOfQues];
        for (int i = 0; i < numOfQues; i++) {
            System.out.println("Ques " + (i + 1));
            question[i] = new Addition(len);
            question[i].showQuestion();
            if (question[i].judge()) {
                numOfCor++;
            }
        }
        System.out.println("Ques  Correct  User");
        for (int i = 0; i < numOfQues; i++) {
            question[i].showResult();
        }
        System.out.println("Score: " + (numOfCor * 10));
    }
}

class Addition {
    int x, y;
    int u_ans, c_ans;
    int len;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    static Random rand = new Random(new Date().getTime());

    Addition() {
    }

    Addition(int len) {
        this.len = len;
        x = rand.nextInt((int) Math.pow(10, len));
        y = rand.nextInt((int) Math.pow(10, len));
        c_ans = x + y;
    }

    public boolean judge() {
        return u_ans == c_ans;
    }

    public void showQuestion() {
        System.out.print(x + "\t+\t" + y + "\t=\t");
        try {
            u_ans = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            u_ans = -1;
            System.out.println("illegal input!");
        }
    }

    public void showResult() {
        System.out.printf("%1$" + len + "d + %2$" + len + "d = %3$" + (len + 2) + "d\t%4$" + (len + 2) + "d\n",
                        x, y, c_ans, u_ans);
    }
}