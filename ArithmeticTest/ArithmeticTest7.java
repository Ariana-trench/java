import java.io.*;
import java.util.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

abstract class Operation {
    int x, y;
    int len;
    int u_ans, c_ans;
    char op;
    static Random rand = new Random(new Date().getTime());

    Operation(int len, char op) {
        this.len = len;
        this.op = op;
        this.x = rand.nextInt((int) Math.pow(10, len));
        this.y = rand.nextInt((int) Math.pow(10, len));
    }

    Operation(int x, int y, int c_ans, int u_ans, char op) {
        this.x = x;
        this.y = y;
        this.c_ans = c_ans;
        this.u_ans = u_ans;
        this.op = op;
    }

    public boolean judge(String input) {
        try {
            u_ans = Integer.parseInt(input);
        } catch (Exception e) {
            u_ans = -1;
        }
        return u_ans == c_ans;
    }

    public String getQuestion() {
        return "" + x + op + y + "=";
    }

    public String getResult() {
        return "" + c_ans;
    }

    public String getInput() {
        return "" + u_ans;
    }

    public String getText() {
        return "" + x + "," + op + "," + y + ",=," + c_ans + "," + u_ans + ",0,0\n";
    }

}

class Addition extends Operation {
    static final char op = '+';

    Addition(int len) {
        super(len, op);
        c_ans = x + y;
    }

    Addition(int x, int y, int c_ans, int u_ans) {
        super(x, y, c_ans, u_ans, op);
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

    Subtraction(int x, int y, int c_ans, int u_ans) {
        super(x, y, c_ans, u_ans, op);
    }
}

class Multiplication extends Operation {
    static final char op = 'X';

    Multiplication(int len) {
        super(len, op);
        c_ans = x * y;
    }

    Multiplication(int x, int y, int c_ans, int u_ans) {
        super(x, y, c_ans, u_ans, op);
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

    Devision(int x, int y, int c_ans, int u_ans, int c_remain, int u_remain) {
        super(x, y, c_ans, u_ans, op);
        this.c_remain = c_remain;
        this.u_remain = u_remain;
    }

    public boolean judge(String input) {
        try {
            String tmp[] = input.split(" ");
            u_remain = Integer.parseInt(tmp[1]);
            return super.judge(tmp[0]) && u_remain == c_remain;
        } catch (Exception e) {
            u_ans = -1;
            u_remain = -1;
            return false;
        }
    }

    public String getResult() {
        return "" + c_ans + "   " + c_remain;
    }

    public String getInput() {
        return "" + u_ans + "   " + u_remain;
    }

    public String getText() {
        return "" + x + "," + op + "," + y + ",=," + c_ans + "," + u_ans + "," + c_remain + "," + u_remain + "\n";
    }
}

class Gui {
    String username = "";
    JLabel label_functions[] = new JLabel[10];
    JTextField textField_input[] = new JTextField[10];
    JTextField textField_name = new JTextField(8);
    // JTextField textField_path = new JTextField();
    JLabel label_output[] = new JLabel[10];
    JLabel label_score = new JLabel("0", JLabel.CENTER);
    char ops[] = { '+', '-', 'X', '/', 'r' };
    int lens[] = { 1, 2, 3, 4 };
    int score = 0;
    JButton button_judge = new JButton("Judge");
    JButton button_load = new JButton("Load");
    JButton button_redo = new JButton("ReDo");
    JButton button_start = new JButton("Start");
    Operation questions[] = new Operation[10];
    static Random rand = new Random(new Date().getTime());

    Gui() {
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        JFrame frame = new JFrame("calculator");
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
        frame.setSize(600, 600);
        frame.setLocation(233, 233);
        JPanel North = get_North();
        JPanel Center = get_Center();
        JPanel South = get_South();
        frame.add("North", North);
        frame.add("Center", Center);
        frame.add("South", South);
        frame.show();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        button_judge.setEnabled(false);
    }

    private JPanel get_North() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 3));
        JComboBox comboBox_op = new JComboBox<String>();
        for (char c : ops)
            comboBox_op.addItem(c);
        JComboBox comboBox_len = new JComboBox<Integer>();
        for (int i : lens)
            comboBox_len.addItem(i);
        // Label label_name = new Label("name");
        JPanel panel_name = new JPanel();
        panel_name.setLayout(new FlowLayout());
        panel_name.add(new Label("name"));
        panel_name.add(textField_name);
        JPanel panel_op = new JPanel();
        panel_op.setLayout(new FlowLayout());
        panel_op.add(new Label("op"));
        panel_op.add(comboBox_op);
        JPanel panel_len = new JPanel();
        panel_len.setLayout(new FlowLayout());
        panel_len.add(new Label("len"));
        panel_len.add(comboBox_len);

        panel.add(panel_name);
        panel.add(panel_op);
        panel.add(panel_len);

        panel.add(button_start);
        panel.add(button_load);
        panel.add(button_redo);
        button_start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                username = textField_name.getText();
                int len = lens[comboBox_len.getSelectedIndex()];
                char op = ops[comboBox_op.getSelectedIndex()];

                boolean isR = false;
                if (op == 'r') {
                    isR = true;
                }
                try {
                    for (int i = 0; i < questions.length; i++) {
                        if (isR) { // annotation: random op
                            op = ops[rand.nextInt(4)];
                        }
                        switch (op) {
                        case '+': // annotation: op is +
                            questions[i] = new Addition(len);
                            break;
                        case '-': // annotation: op is -
                            questions[i] = new Subtraction(len);
                            break;
                        case 'X': // annotation: op is x
                            questions[i] = new Multiplication(len);
                            break;
                        case '/': // annotation: op is /
                            questions[i] = new Devision(len);
                            break;
                        default:
                            throw new IllegalArgumentException(); // annotation: illegal op
                        }
                        label_functions[i].setText(questions[i].getQuestion());
                        textField_input[i].setEditable(true);
                        textField_input[i].setText("");
                    }
                } catch (IllegalArgumentException iae) { // annotation: handle exception
                    System.out.println("illegal operator!");
                    return;
                }
                button_start.setText("restart");
                button_judge.setText("Judge");
                button_judge.setEnabled(true);

            }
        });
        button_load.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    username = textField_name.getText();
                    if (username == "")
                        username = "null";
                    String filename = username + ".his";
                    Vector<String> vStrings = new Vector<String>();
                    File file = new File(filename);
                    if (file.exists()) {
                        FileReader reader = new FileReader(file);
                        BufferedReader bufferedReader = new BufferedReader(reader);
                        int row = 0;
                        String tmp = bufferedReader.readLine(); 
                        while (tmp != null){
                            vStrings.add(tmp);
                            row++;
                            tmp = bufferedReader.readLine();
                        }
                        reader.close();
                        if (row == 0 || row % 11 != 0)
                            throw new Exception("row number err");
                        int lines = vStrings.size();
                        label_score.setText(vStrings.elementAt(lines-11).split(",")[1]);
                        for (int i = 0; i < 10; i++) {
                            String paras[] = vStrings.elementAt(lines-10+i).split(",");
                            int x = Integer.parseInt(paras[0]);
                            char op = paras[1].charAt(0);
                            int y = Integer.parseInt(paras[2]);
                            int c_ans = Integer.parseInt(paras[4]);
                            int u_ans = Integer.parseInt(paras[5]);
                            int c_remain = Integer.parseInt(paras[6]);
                            int u_remain = Integer.parseInt(paras[7]);

                            switch (op) {
                            case '+':
                                questions[i] = new Addition(x, y, c_ans, u_ans);
                                break;
                            case '-':
                                questions[i] = new Subtraction(x, y, c_ans, u_ans);
                                break;
                            case 'X':
                                questions[i] = new Multiplication(x, y, c_ans, u_ans);
                                break;
                            case '/':
                                questions[i] = new Devision(x, y, c_ans, u_ans, c_remain, u_remain);
                                break;
                            default:
                                break;
                            }
                            label_functions[i].setText(questions[i].getQuestion());
                            textField_input[i].setText(questions[i].getInput());
                            textField_input[i].setEditable(false);
                            ;
                            label_output[i].setText(questions[i].getResult());
                            button_judge.setEnabled(false);
                            button_judge.setText("Judge");
                        }

                        reader.close();
                    } else {
                        JOptionPane.showMessageDialog(null, "no file " + filename + " found!", "alert",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(null, "error happened!", "alert", JOptionPane.ERROR_MESSAGE);

                }
                button_redo.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < questions.length; i++) {
                            textField_input[i].setText("");
                            textField_input[i].setEditable(true);
                            ;
                            label_output[i].setText("zzz");
                            button_judge.setText("Judge");
                            button_judge.setEnabled(true);
                            score = 0;
                            label_score.setText("0");
                        }
                    }
                });
            }
        });
        return panel;
    }

    private JPanel get_Center() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(11, 3));
        // JPanel head = new JPanel();
        panel.add(new JLabel("function", JLabel.CENTER));
        panel.add(new JLabel("user", JLabel.CENTER));
        panel.add(new JLabel("correct", JLabel.CENTER));
        // panel.add(head);
        for (int i = 0; i < 10; i++) {
            label_functions[i] = new JLabel("xxx op yyy = ", JLabel.CENTER);
            panel.add(label_functions[i]);
            textField_input[i] = new JTextField();
            panel.add(textField_input[i]);
            label_output[i] = new JLabel("zzz", JLabel.CENTER);
            panel.add(label_output[i]);
        }
        return panel;
    }

    private JPanel get_South() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 3));
        panel.add(new JLabel("score", JLabel.CENTER));
        panel.add(label_score);
        panel.add(button_judge);

        button_judge.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (button_judge.getText() == "Judge") {
                    score = 0;
                    for (int i = 0; i < questions.length; i++) {
                        if (questions[i].judge(textField_input[i].getText())) {
                            score += 10;
                        }
                        label_output[i].setText(questions[i].getResult());
                        textField_input[i].setEditable(false);
                    }
                    label_score.setText(String.valueOf(score));
                    button_judge.setText("Save");
                } else if (button_judge.getText() == "Save") {
                    if (saveFile())
                        button_judge.setEnabled(false);
                }
            }
        });
        return panel;
    }

    private boolean saveFile() {
        if (username == "")
            username = "null";
        String filename = username + ".his";
        File file = new File(filename);
        try {
            if (file.exists()) {
                FileWriter writer = new FileWriter(file, true);
                FileReader reader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(reader);
                int row = 0;
                while (bufferedReader.readLine() != null)
                    row++;
                writer.write("" + (row / 11 + 1) + "," + score + '\n');
                for (Operation it : questions)
                    writer.write(it.getText());
                writer.close();
                reader.close();
            } else {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                writer.write("1," + score + '\n');
                for (Operation it : questions)
                    writer.write(it.getText());
                writer.close();
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}

public class ArithmeticTest7 {
    public static void main(String[] args) {
        Gui gui = new Gui();
    }
}