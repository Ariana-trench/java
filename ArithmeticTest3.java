import java.io.*;
import java.util.*;

abstract class Operation {
    int x, y;
    int len;
    int u_ans, c_ans;
    char op;
    static Random rand = new Random(new Date().getTime());
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    Operation(int len, char op) {
        this.len = len;
        this.op = op;
        this.x = rand.nextInt((int) Math.pow(10, len));
        this.y = rand.nextInt((int) Math.pow(10, len));
    }

    public boolean judge() {
        return u_ans == c_ans;
    }

    // public abstract void calculate();
    public void showQuestion() {
        System.out.print(x + "\t" + op + "\t" + y + "\t=\t");
        try {
            u_ans = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            u_ans = -1;
            System.out.println("illegal input");
        }
    }

    public void showResult() {
        System.out.printf("%1$" + len + "d " + op + " %2$" + len + "d = %3$" + (len + 2) + "d\t%4$" + (len + 2) + "d\n",
                x, y, c_ans, u_ans);
    }
}

class Addition extends Operation {
    static final char op = '+';

    Addition(int len) {
        super(len, op);
        c_ans = x + y;
    }
}

class Subtraction extends Operation {
    static final char op = '-';

    Subtraction(int len) {
        super(len, op);
        if (y > x) {
            y = y ^ x;
            x = y ^ x;
            y = y ^ x;
        }
        c_ans = x - y;
    }
}

class Multiplication extends Operation {
    static final char op = '*';

    Multiplication(int len) {
        super(len, op);
        c_ans = x * y;
    }
}

class Devision extends Operation {
    static final char op = '/';
    int c_remain;
    int u_remain;

    Devision(int len) {
        super(len, op);
        while (y == 0) {
            y = rand.nextInt((int) Math.pow(10, len));
        }
        c_ans = x / y;
        c_remain = x % y;
    }

    public boolean judge() {
        return super.judge() && u_remain == c_remain;
    }

    public void showQuestion() {
        super.showQuestion();
        try {
            System.out.print("remain: ");
            u_remain = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            u_remain = -1;
            System.out.println("illegal input");
        }
    }

    public void showResult() {
        System.out.printf("%1$" + len + "d / %2$" + len + "d = %3$" + len + "d...%4$" + len + "d\t%5$" + len + "d...%6$"
                + len + "d\n", x, y, c_ans, c_remain, u_ans, u_remain);
    }
}

public class ArithmeticTest3 {
    public static void main(String[] args) {
        char op = args[0].charAt(0);
        int len = Integer.parseInt(args[1]);
        int numQues = 10;
        int numCor = 0;
        boolean isR = false;
        Operation question[] = new Operation[numQues];
        char ops[] = { '+', '-', 'X', '/' };
        Random rand = new Random(new Date().getTime());

        if (op == 'r') {
            isR = true;
        }
        for (int i = 0; i < numQues; i++) {
            if (isR) {
                op = ops[rand.nextInt(4)];
            }
            System.out.println("Ques " + (i + 1));
            switch (op) {
            case '+':
                question[i] = new Addition(len);
                question[i].showQuestion();
                break;
            case '-':
                question[i] = new Subtraction(len);
                question[i].showQuestion();
                break;
            case 'X':
                question[i] = new Multiplication(len);
                question[i].showQuestion();
                break;
            case '/':
                question[i] = new Devision(len);
                question[i].showQuestion();
                break;
            default:
                System.out.println("illegal operator!");
                return;
            }
            if (question[i].judge()) {
                numCor++;
            }
        }
        System.out.println("Question Correct User's");
        for (int i = 0; i < numQues; i++)
            question[i].showResult();
        System.out.println("score: " + (numCor * 10));
    }
}
