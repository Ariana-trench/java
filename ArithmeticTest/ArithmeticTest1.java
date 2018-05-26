import java.util.*;
import java.io.*;
public class ArithmeticTest1{
    
    public static void main(String[] args) throws Exception{
        // init
        char op = args[0].charAt(0);
        char ops[] = {'+','-','X','/'};
        boolean isR = op=='r'?true:false;
        int lenOfNum = Integer.parseInt(args[1]);
        int numOfQes = 10;
        int round = 0;
        int correctNum = 0;
        Random rand=new Random(new Date().getTime());
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int store[][] = new int[numOfQes][7];  // x op y c_ans c_rest u_ans u_rest


        // create question and get corrent answer
        while(round!=numOfQes){
            int x = rand.nextInt((int)Math.pow(10,lenOfNum));
            int y = rand.nextInt((int)Math.pow(10, lenOfNum));
            int correctAns = 0;
            int rest = 0;
            if(isR){
                op = ops[rand.nextInt(4)];
            }
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
            store[round][1] = op=='+'?0:(op=='-'?1:(op=='X'?2:3));
            store[round][2] = y;
            store[round][3] = correctAns;
            store[round][4] = rest;
            store[round][5] = ans;
            store[round][6] = ans_rest;
            round++;
        }

        // show question and answer
        System.out.println("Ques\tCAns\tUAns");
        for(int i=0; i<numOfQes; i++){
            if(ops[store[i][1]]=='/'){
                String formatString="%1$"+lenOfNum+"d "+ops[store[i][1]]+" %2$"+lenOfNum+"d = %3$"+lenOfNum+"d...%4$"+lenOfNum+"d\t%5$"+lenOfNum+"d...%6$"+lenOfNum+"d\n";
                System.out.printf(formatString,store[i][0], store[i][2],store[i][3],store[i][4],store[i][5],store[i][6]);
            }
            else{
                String formatString="%1$"+lenOfNum+"d "+ops[store[i][1]]+" %2$"+lenOfNum+"d = %3$"+(lenOfNum+2)+"d\t%4$"+(lenOfNum+2)+"d\n";
                System.out.printf(formatString,store[i][0], store[i][2],store[i][3],store[i][5]);
            }
        }
        System.out.println("Score: "+correctNum*10);
    }
}