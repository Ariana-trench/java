import java.util.*;
import java.io.*;
public class ArithmeticTest1{
    
    public static void main(String[] args) throws Exception{
        // init
        char op = args[0].charAt(0);
        int lenOfNum = Integer.parseInt(args[1]);
        int numOfQes = 10;
        int round = 0;
        int correctNum = 0;
        Random rand=new Random(new Date().getTime());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int store[][] = new int[numOfQes][6];

        // create question and get corrent answer
        while(round!=numOfQes){
            int x = rand.nextInt((int)Math.pow(10,lenOfNum));
            int y = rand.nextInt((int)Math.pow(10, lenOfNum));
            int correctAns = 0;
            int rest = 0;
            switch (op) {
                case '+':
                    correctAns = x + y;
                    break;
                case '-':
                    if(y>x){
                        x = x^y;
                        y = x^y;
                        x = x^y;
                    }
                    correctAns = x-y;
                    break;
                case 'X':
                    correctAns = x*y;
                    break;
                case '/':
                    while(y==0){
                        y = rand.nextInt((int)Math.pow(10, lenOfNum));
                    }
                    correctAns = x/y;
                    rest = x%y;
                    break;

                default:
                    System.out.println("wrong operator");
                    return;
            }

            // show question and get user's answer
            System.out.println("Question "+(round+1));
            System.out.print(""+x+" "+op+ " "+y+" = ");
            int ans = Integer.parseInt(reader.readLine());
            int ans_rest = 0;
            if(op=='/'){
                System.out.print("rest num: ");
                ans_rest =Integer.parseInt(reader.readLine());
            }
            if(ans==correctAns && ans_rest==rest){
                correctNum++;
            }
            
            // store data
            store[round][0] = x;
            store[round][1] = y;
            store[round][2] = correctAns;
            store[round][3] = rest;
            store[round][4] = ans;
            store[round][5] = ans_rest;
            round++;
        }

        // show question and answer
        if(op=='/'){
            System.out.println("Ques \t CorAns \t UsrAns");
            String formatString="%1$"+lenOfNum+"d "+op+" %2$"+lenOfNum+"d = %3$"+lenOfNum+"d...%4$"+lenOfNum+"d\t%5$"+lenOfNum+"d...%6$"+lenOfNum+"d\n";
            for(int i=0; i<numOfQes; i++){
                System.out.printf(formatString,store[i][0], store[i][1],store[i][2],store[i][3],store[i][4],store[i][5]);
            }
        }
        else{
            System.out.println("Ques\tCorAns\tUsrAns");
            String formatString="%1$"+lenOfNum+"d "+op+" %2$"+lenOfNum+"d = %3$"+(lenOfNum+2)+"d %4$"+(lenOfNum+2)+"d\n";
            for(int i=0; i<numOfQes; i++){
                System.out.printf(formatString,store[i][0], store[i][1],store[i][2],store[i][4]);
            }
        }
        System.out.println("Score: "+correctNum*10);
    }
}