import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

abstract class Operation {
    int x, y; // annotation: num x and y
    int len; // annotation: length of num
    int max; // annotation: maximum of x op y
    int u_ans, c_ans; // annotation: user's answer and correct answer
    char op; // annotation: operator
    static Random rand = new Random(new Date().getTime()); // annotation: a object to create random num x and y
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in)); // annotation: a object to read
                                                                                         // keyboard input

    Operation(int len, char op, int max) { // annotation: the constructor
        this.len = len;
        this.op = op;
        this.max = max;
        this.x = rand.nextInt((int) Math.pow(10, len));
        this.y = rand.nextInt((int) Math.pow(10, len));
    }

    public boolean judge() { // annotation: judge whether correct
        return u_ans == c_ans;
    }

    public void showQuestion() { // annotation: a function to show question and get input
        String tmp = "";
        boolean flag = true; // annotation: a flag of having error in input
        while (flag) { // annotation: while having error
            try {
                System.out.print(x + "\t" + op + "\t" + y + "\t=\t"); // annotation: show question
                tmp = reader.readLine(); // annotation: get input string
                u_ans = Integer.parseInt(tmp); // annotation: string to int
                if (u_ans > max) { // annotation: user input is too big
                    throw new NumberTooBigException(max);
                }
                flag = false; // annotation: no error any more
            } catch (NumberFormatException nfe) { // annotation: handle error in parseInt
                System.out.println(tmp + " contains illegal digits");
                System.out.println("please input again: ");
            } catch (NumberTooBigException ntbe) { // annotation: handle error in input num too big
                System.out.println(ntbe.getMessage());
                System.out.println("please input again: ");
            } catch (Exception e) { // annotation: handle other error
                u_ans = -1;
                System.out.println("error happened");
            }
            if (!flag) // annotation: no error then break
                break;
        }

    }

    public void showResult() { // annotation: show result using format string
        System.out.printf("%1$" + len + "d " + op + " %2$" + len + "d = %3$" + (len + 2) + "d\t%4$" + (len + 2) + "d\n",
                x, y, c_ans, u_ans);
    }

    public String getResult() { // annotation: return the info to save
        return "" + x + "," + op + "," + y + ",=," + c_ans + "," + u_ans + ",0,0\n";
    }
}

class Addition extends Operation { // annotation: this is the + class
    static final char op = '+'; // annotation: class op is +

    Addition(int len) { // annotation: constructor
        super(len, op, 2 * (int) Math.pow(10, len));
        c_ans = x + y;
    }
}

class Subtraction extends Operation { // annotation: this is the - class
    static final char op = '-'; // annotation: class op is -

    Subtraction(int len) { // annotation: constructor
        super(len, op, (int) Math.pow(10, len));
        if (y > x) { // annotation: handle y>x condition
            y = y ^ x;
            x = y ^ x;
            y = y ^ x;
        }
        c_ans = x - y;
    }
}

class Multiplication extends Operation { // annotation: this is the X class
    static final char op = '*'; // annotation: class op is *

    Multiplication(int len) { // annotation: constructor
        super(len, op, (int) Math.pow(10, 2 * len));
        c_ans = x * y;
    }
}

class Devision extends Operation { // annotation: this is the / class
    static final char op = '/'; // annotation: class op is /
    int c_remain; // annotation: correct remain
    int u_remain; // annotation: user remain
    int max_remain; // annotation: maximum remain

    Devision(int len) { // annotation: constructor
        super(len, op, (int) Math.pow(10, len));
        while (y == 0) { // annotation: handle y==0 condition
            y = rand.nextInt((int) Math.pow(10, len));
        }
        c_ans = x / y;
        c_remain = x % y;
        max_remain = (int) Math.pow(10, len);
    }

    public boolean judge() { // annotation: override judge function
        return super.judge() && u_remain == c_remain;
    }

    public void showQuestion() { // annotation: override showQuestion function
        super.showQuestion();
        this.getRemain();
    }

    public void getRemain() { // annotation: get remain input
        String tmp = "";
        boolean flag = true; // annotation: a flag of having error in input
        while (flag) { // annotation: while having error
            try {
                System.out.print("remain: ");
                tmp = reader.readLine();
                u_remain = Integer.parseInt(tmp);
                if (u_remain > max_remain) {
                    throw new NumberTooBigException(max_remain);
                }
                flag = false;
            } catch (NumberFormatException nfe) { // annotation: handle error in parseInt
                System.out.println(tmp + " contains illegal digits");
                System.out.println("please input the remain again: ");

            } catch (NumberTooBigException ntbe) { // annotation: handle error in input num too big
                System.out.println(ntbe.getMessage());
                System.out.println("please input the remain again: ");
            } catch (Exception e) { // annotation: handle other error
                u_remain = -1;
                System.out.println("error happened");
            }
            if (flag) // annotation: no error then break
                break;
        }
    }

    public void showResult() { // annotation: override showResult
        System.out.printf("%1$" + len + "d / %2$" + len + "d = %3$" + len + "d...%4$" + len + "d\t%5$" + len + "d...%6$"
                + len + "d\n", x, y, c_ans, c_remain, u_ans, u_remain);
    }

    public String getResult() { // annotation: override getQuestion
        return "" + x + "," + op + "," + y + ",=," + c_ans + "," + u_ans + "," + c_remain + "," + u_remain + "\n";
    }
}

class NumberTooBigException extends Exception {
    NumberTooBigException(int num) {
        super("你输入的答案超过了可能的范围!\n答案应小于" + num);
    }
}

public class ArithmeticTest6 {
    static final DecimalFormat df = new DecimalFormat("0.00"); // annotation: doudle output format
    static final char ops[] = { '+', '-', 'X', '/' }; // annotation: all operators
    static final Random rand = new Random(new Date().getTime());
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws Exception {
        char op = args[0].charAt(0); // annotation: input op in args
        int len = Integer.parseInt(args[1]); // annotation: input len in args
        int numCor = 0; // annotation: number of correct answer
        double perScore = 0.0; // annotation: score of each question
        boolean isR = false; // annotation: op is r
        Vector<Operation> question = new Vector<Operation>(); // annotation: vector to store question

        System.out.print("please input your name :");
        String name = reader.readLine();

        if (op == 'r') {
            isR = true;
        }
        try {
            for (int i = 0;; i++) {
                if (isR) { // annotation: random op
                    op = ops[rand.nextInt(4)];
                }
                System.out.println("Ques " + (i + 1));
                switch (op) {
                case '+': // annotation: op is +
                    question.add(new Addition(len));
                    question.get(i).showQuestion();
                    break;
                case '-': // annotation: op is -
                    question.add(new Subtraction(len));
                    question.get(i).showQuestion();
                    break;
                case 'X': // annotation: op is x
                    question.add(new Multiplication(len));
                    question.get(i).showQuestion();
                    break;
                case '/': // annotation: op is /
                    question.add(new Devision(len));
                    question.get(i).showQuestion();
                    break;
                default:
                    throw new IllegalArgumentException(); // annotation: illegal op
                }
                if (question.get(i).judge()) {  // annotation: count correct answer number
                    numCor++;
                }

                System.out.println("Continue?(Y/N)");  // annotation: to continue judge
                char in = 'n';
                try {
                    in = reader.readLine().charAt(0);
                } catch (Exception e) {
                    in = 'n';
                }
                if (in != 'y' && in != 'Y')
                    break;
            }
        } catch (IllegalArgumentException iae) {  // annotation: handle exception
            System.out.println("illegal operator!");
            return;
        }
        System.out.println("Question Correct User's");// annotation: show summary
        for (Operation it : question)
            it.showResult();
        perScore = 100.0 / question.size();  // annotation: get each question's score
        System.out.println("score: " + df.format(numCor * perScore));  // annotation: show score
        saveFile(name, question, numCor * perScore, numCor, question.size());
    }

    private static void saveFile(String name, Vector<Operation> questions, double score, int numCor, int numQue) throws IOException {
        Date today = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        FileWriter writer = new FileWriter("rec" + formater.format(today) + ".csv");
        writer.write("x,op,y,=,correct Ans,User's Ans,correct Remain,User's Remain\n");
        for (Operation it : questions)
            writer.write(it.getResult());
        writer.write("user name:" + name + ",score:" + df.format(score) + ",correct num:"+numCor+",question num:"+numQue+"\n");
        writer.close();
    }
}
