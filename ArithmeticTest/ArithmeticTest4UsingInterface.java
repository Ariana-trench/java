import java.io.*;
import java.util.*;

public interface OperationInterface {
    public static final Random rand = new Random(new Date().getTime());
    public static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public abstract void showQuestion();

    public abstract void showResult();

    public abstract boolean judge();
}

class AdditionUsingInterface implements OperationInterface {
    int x, y;
    int len;
    int u_ans, c_ans;
    static final char op = '+';

    AdditionUsingInterface(int len) {
        this.len = len;
        this.x = rand.nextInt((int) Math.pow(10, len));
        this.y = rand.nextInt((int) Math.pow(10, len));
        this.c_ans = x + y;
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

class SubtractionUsingInterface implements OperationInterface {
    int x, y;
    int len;
    int u_ans, c_ans;
    static final char op = '-';

    SubtractionUsingInterface(int len) {
        this.len = len;
        this.x = rand.nextInt((int) Math.pow(10, len));
        this.y = rand.nextInt((int) Math.pow(10, len));
        if (y > x) {
            y = y ^ x;
            x = y ^ x;
            y = y ^ x;
        }
        this.c_ans = x - y;
    }

    public boolean judge() {
        return u_ans == c_ans;
    }

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

class MultiplicationUsingInterface implements OperationInterface {
    int x, y;
    int len;
    int u_ans, c_ans;
    static final char op = '*';

    MultiplicationUsingInterface(int len) {
        this.len = len;
        this.x = rand.nextInt((int) Math.pow(10, len));
        this.y = rand.nextInt((int) Math.pow(10, len));
        this.c_ans = x * y;
    }

    public boolean judge() {
        return u_ans == c_ans;
    }

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

class DivisionUsingInterface implements OperationInterface {
    int x, y;
    int len;
    int u_ans, c_ans;
    int u_remain, c_remain;
    static final char op = '*';

    DivisionUsingInterface(int len) {
        this.len = len;
        this.x = rand.nextInt((int) Math.pow(10, len));
        this.y = rand.nextInt((int) Math.pow(10, len));
        this.c_ans = x / y;
        this.c_remain = x % y;
    }

    public boolean judge() {
        return u_ans == c_ans;
    }

    public void showQuestion() {
        System.out.print(x + "\t" + op + "\t" + y + "\t=\t");
        try {
            u_ans = Integer.parseInt(reader.readLine());
            System.out.print("remain: ");
            u_remain = Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            u_ans = -1;
            u_remain = -1;
            System.out.println("illegal input");
        }
    }

    public void showResult() {
        System.out.printf("%1$" + len + "d / %2$" + len + "d = %3$" + len + "d...%4$" + len + "d\t%5$" + len + "d...%6$"
                + len + "d\n", x, y, c_ans, c_remain, u_ans, u_remain);
    }
}

public class ArithmeticTest4UsingInterface {
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
