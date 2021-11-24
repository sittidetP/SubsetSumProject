
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

public class InputSetUI {

    boolean isError = false;
    boolean fromRandom = false;

    long[] setNumber;
    long targetNum;

    JRadioButton bottomrandom;
    JRadioButton bottomBacktracking;
    JRadioButton bottombranch;
    JLabel Header;
    JLabel showtextset;//ข้อความช่องใส่เซต
    JLabel showtextnumber;//ข้อความช่องใส่เป้าหมาย
    JLabel scope;//ขอบเขตของจำนวน
    JLabel quantity;//จำนวนตัวเลข
    JLabel Answer;//ขอบเขตของเลขเป้าหมาย

    JSpinner spinnerscope;//ช่องใส่ขอบเขตของจำนวน
    JSpinner spinnerquantity;//ช่องใส่จำนวนตัวเลข
    JSpinner spinnerAnswer;//ช่องใส่ขอบเขตของเลขเป้าหมาย
    JSpinner tospinnerscope;//ช่องใส่ขอบเขตของจำนวนสูงสุด

    JSpinner tospinnerAnswer;//ช่องใส่ขอบเขตของเลขเป้าหมายสูงสุด
    JTextArea inputset;//ช่องใส่เซต
    JTextArea inputnumber;//ช่องใส่เป้าหมาย
    JFrame frame = new JFrame();
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    JButton submitButton = new JButton("SUBMIT");
    JButton OKButton;
    JButton ClearButton;
    JScrollPane scrollReport;//ที่เก็บช่องใส่เซต
    JScrollPane scrollReportin;//ที่เก็บช่องใส่เป้าหมาย

    JMenu menuinput;

    GraphDrawing g;

    void startAndInput() {
        Color back = new Color(230, 230, 250);
        Color backbottom = new Color(240, 248, 255);
        JPanel panel1 = new JPanel();
        panel1.setBackground(back);
        panel1.setBounds(0, 0, 600, 400);
        //ส่วนของหัวเรื่อง
        Header = new JLabel("Sum Of Subsets Problem");
        Header.setFont(new java.awt.Font("Tahoma", 0, 30));

        Header.setBounds(150, 0, 500, 100);
        frame.setTitle("Subset Sum");

        submitButton.setFont(new java.awt.Font("Tahoma", 0, 18));
        submitButton.setBackground(backbottom);

        JLabel Textto1 = new JLabel("To");
        Textto1.setFont(new java.awt.Font("Tahoma", 0, 18));
        Textto1.setBounds(890, 100, 150, 40);

        JLabel Textto3 = new JLabel("To");
        Textto3.setFont(new java.awt.Font("Tahoma", 0, 18));
        Textto3.setBounds(890, 200, 150, 40);

        //ส่วนใส่เซต
        showtextset = new JLabel("Numbers in set:");
        showtextset.setFont(new java.awt.Font("Tahoma", 0, 18));
        showtextset.setBounds(50, 100, 150, 50);

        inputset = new JTextArea();
        inputset.setEditable(true);
        inputset.setFont(new java.awt.Font("Tahoma", 0, 18));
        scrollReport = new JScrollPane(inputset);
        scrollReport.setBounds(200, 110, 250, 40);

        showtextnumber = new JLabel("Target number:");
        showtextnumber.setFont(new java.awt.Font("Tahoma", 0, 18));
        showtextnumber.setBounds(50, 150, 150, 50);

        bottomrandom = new JRadioButton("Random number");
        bottomrandom.setBounds(200, 200, 200, 30);
        bottomrandom.setFont(new java.awt.Font("Tahoma", 0, 18));
        bottomrandom.setBackground(back);
        bottomrandom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (bottomrandom.isSelected()) {

                    spinnerscope.setVisible(true);
                    spinnerquantity.setVisible(true);
                    spinnerAnswer.setVisible(true);
                    tospinnerscope.setVisible(true);
                    Header.setBounds(400, 0, 500, 100);
                    tospinnerAnswer.setVisible(true);
                    scope.setVisible(true);
                    quantity.setVisible(true);
                    Answer.setVisible(true);
                    OKButton.setVisible(true);
                    Textto1.setVisible(true);

                    Textto3.setVisible(true);
                    panel1.setBounds(0, 0, 1200, 400);
                    frame.setSize(1200, 400);

                } else {

                    spinnerscope.setVisible(false);
                    spinnerquantity.setVisible(false);
                    spinnerAnswer.setVisible(false);
                    tospinnerscope.setVisible(false);
                    Header.setBounds(150, 0, 500, 100);
                    tospinnerAnswer.setVisible(false);
                    scope.setVisible(false);
                    quantity.setVisible(false);
                    Answer.setVisible(false);
                    OKButton.setVisible(false);
                    Textto1.setVisible(false);
                    panel1.setBounds(0, 0, 600, 400);
                    Textto3.setVisible(false);
                    frame.setSize(600, 400);

                }
            }
        });
        scope = new JLabel("Scope of set:");
        scope.setFont(new java.awt.Font("Tahoma", 0, 18));
        scope.setBounds(550, 100, 280, 50);

        quantity = new JLabel("Quantity of set:");
        quantity.setFont(new java.awt.Font("Tahoma", 0, 18));
        quantity.setBounds(550, 150, 150, 50);

        Answer = new JLabel("Scope of Target:");
        Answer.setFont(new java.awt.Font("Tahoma", 0, 18));
        Answer.setBounds(550, 200, 280, 50);

        inputnumber = new JTextArea();
        inputnumber.setFont(new java.awt.Font("Tahoma", 0, 18));
        scrollReportin = new JScrollPane(inputnumber);
        scrollReportin.setBounds(200, 160, 250, 40);

        SpinnerModel sm = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        SpinnerModel sm1 = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        SpinnerModel sm2 = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);
        spinnerquantity = new JSpinner(sm);
        spinnerquantity.setFont(new java.awt.Font("Tahoma", 0, 18));
        spinnerquantity.setBounds(700, 150, 150, 40);

        spinnerscope = new JSpinner(sm1);
        spinnerscope.setFont(new java.awt.Font("Tahoma", 0, 18));
        spinnerscope.setBounds(700, 100, 150, 40);

        spinnerAnswer = new JSpinner(sm2);
        spinnerAnswer.setFont(new java.awt.Font("Tahoma", 0, 18));
        spinnerAnswer.setBounds(700, 200, 150, 40);

        int nscope = Integer.parseInt(spinnerscope.getValue().toString());
        int nquantity = Integer.parseInt(spinnerquantity.getValue().toString());
        int nanwer = Integer.parseInt(spinnerAnswer.getValue().toString());

        SpinnerModel tosm11 = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);//(ค่าเริ่มต้น,ค่าน้อยสุด,ค่าสูงสุด,เพิ่มที่ละเท่าไหร่)
        SpinnerModel tosm12 = new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1);//(ค่าเริ่มต้น,ค่าน้อยสุด,ค่าสูงสุด,เพิ่มที่ละเท่าไหร่)
        tospinnerscope = new JSpinner(tosm11);
        tospinnerscope.setFont(new java.awt.Font("Tahoma", 0, 18));
        tospinnerscope.setBounds(950, 100, 150, 40);

        tospinnerAnswer = new JSpinner(tosm12);
        tospinnerAnswer.setFont(new java.awt.Font("Tahoma", 0, 18));
        tospinnerAnswer.setBounds(950, 200, 150, 40);

        OKButton = new JButton("OK");
        OKButton.setBounds(700, 260, 150, 50);
        OKButton.setFont(new java.awt.Font("Tahoma", 0, 18));
        OKButton.setBackground(backbottom);

        OKButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int scope = Integer.parseInt(spinnerscope.getValue().toString());
                int toscope = Integer.parseInt(tospinnerscope.getValue().toString());

                int quantity = Integer.parseInt(spinnerquantity.getValue().toString());
                int anwer = Integer.parseInt(spinnerAnswer.getValue().toString());
                int toanwer = Integer.parseInt(tospinnerAnswer.getValue().toString());
                setNumber = new long[quantity];
                if (toscope < scope || toanwer < anwer) {
                    spinnerscope.setValue(1);
                    spinnerquantity.setValue(1);
                    spinnerAnswer.setValue(1);
                    tospinnerscope.setValue(1);
                    tospinnerAnswer.setValue(1);
                    JOptionPane.showMessageDialog(null,
                            "กรุณาใส่ข้อมูลใหม่", "ERROR", JOptionPane.ERROR_MESSAGE
                    );

                } else {
                    int press = -1;
                    if (inputset.getText().length() > 0 || inputnumber.getText().length() > 0) {
                        press = JOptionPane.showConfirmDialog(null, "If you press this button, your input will disappear. Are you sure?", "", JOptionPane.WARNING_MESSAGE);
                    }
                    if (press != 2) { // 2 คือ เลขที่ return ตอนกด cancel
                        inputset.setText("");
                        inputnumber.setText("");
                        for (int i = 0; i < quantity; i++) {
                            int number = new Random().nextInt(toscope - scope + 1) + scope;
                            if (i == setNumber.length - 1) {
                                inputset.append(number + "");
                            } else {
                                inputset.append(number + ", ");
                            }
                        }
                        int target = new Random().nextInt(toanwer - anwer + 1) + anwer;
                        targetNum = target;
                        inputnumber.append(target + "");
                        OKButton.setEnabled(true);
                    } else {
                        inputset.setText("");
                        inputnumber.setText("");
                    }
                }
            }
        });

        ClearButton = new JButton("CLEAR");
        ClearButton.setBounds(410, 260, 150, 50);
        ClearButton.setFont(new java.awt.Font("Tahoma", 0, 18));
        ClearButton.setBackground(backbottom);

        ClearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                inputset.setText("");
                inputnumber.setText("");
                spinnerscope.setValue(1);
                spinnerquantity.setValue(1);
                spinnerAnswer.setValue(1);
                tospinnerscope.setValue(1);

                tospinnerAnswer.setValue(1);
                OKButton.setEnabled(true);
            }
        });

        JMenuBar menuBar = new JMenuBar();
        menuinput = new JMenu("Flie");
        menuinput.setFont(new java.awt.Font("Tahoma", 0, 16));
        JMenuItem inputtxt = new JMenuItem("Import Text File");
        inputtxt.setFont(new java.awt.Font("Tahoma", 0, 16));
        inputtxt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                FileDialog fd = new FileDialog(frame, "import", FileDialog.LOAD);
                fd.setDirectory("C:\\");
                fd.setFile("*.txt");
                fd.setVisible(true);

                String fileName = fd.getFile();
                String filePath = fd.getDirectory();
                String path = "";

                if (fileName != null) {
                    path = filePath + fileName;
                    try {
                        inputset.setText("");
                        inputnumber.setText("");
                        Path file = Paths.get(path);
                        BufferedReader reader = Files.newBufferedReader(file,
                                StandardCharsets.UTF_8);
                        String inputtxtset = reader.readLine();
                        String inputtxtnunber = reader.readLine();
                        System.out.println(inputtxtset);

                        String[] str = inputtxtset.split(" ");
                        for (int i = 0; i < str.length; i++) {
                            if (i == str.length - 1) {
                                inputset.append(str[i] + "");
                            } else {
                                inputset.append(str[i] + ", ");
                            }
                        }
                        inputnumber.append(inputtxtnunber);
                        reader.close();
                    } catch (IOException e) {
                        System.err.println("IOException: " + e.getMessage());
                    }
                }

            }
        });

        menuinput.add(inputtxt);
        menuBar.add(menuinput);

        bottomBacktracking = new JRadioButton("Backtracking");
        bottomBacktracking.setBounds(50, 250, 200, 30);
        bottomBacktracking.setFont(new java.awt.Font("Tahoma", 0, 18));
        bottomBacktracking.setBackground(back);
        bottombranch = new JRadioButton("Branch And Bound");
        bottombranch.setBounds(50, 280, 200, 30);
        bottombranch.setFont(new java.awt.Font("Tahoma", 0, 18));
        bottombranch.setBackground(back);
        bottomBacktracking.setSelected(true);
        ButtonGroup group = new ButtonGroup();
        group.add(bottomBacktracking);
        group.add(bottombranch);

        //ปุ่มกดตกลง
        frame.setSize(600, 400);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

        frame.setLayout(null);
        frame.setResizable(false);

        submitButton.setBounds(250, 260, 150, 50);
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                translateToNumber();
                if (!isError) {
                    String algor = "";
                    if (bottombranch.isSelected()) {
                        algor = "bb";
                    } else {
                        algor = "bt";
                    }
                    for (int i = 0; i < setNumber.length; ++i) {
                        System.out.print(setNumber[i] + ", ");
                    }
                    System.out.println("target : " + targetNum);
                    g = new GraphDrawing();
                    g.setVisible(true);
                    g.setInputSetTargetAlgorithm(setNumber, targetNum, algor);
                }

            }
        });

        frame.add(submitButton);
        frame.add(Header);
        frame.add(showtextset);
        frame.add(showtextnumber);
        frame.add(scrollReport);
        frame.add(scrollReportin);
        frame.add(bottomrandom);
        frame.add(bottomBacktracking);
        frame.add(bottombranch);
        frame.add(scope);
        frame.add(quantity);
        frame.add(Answer);
        frame.add(Textto1);

        frame.setJMenuBar(menuBar);

        frame.add(Textto3);
        frame.add(spinnerscope);
        frame.add(spinnerquantity);
        frame.add(spinnerAnswer);
        frame.add(tospinnerscope);

        frame.add(tospinnerAnswer);
        frame.add(OKButton);
        frame.add(ClearButton);
        frame.add(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        spinnerscope.setVisible(false);
        spinnerquantity.setVisible(false);
        spinnerAnswer.setVisible(false);
        tospinnerscope.setVisible(false);

        tospinnerAnswer.setVisible(false);
        scope.setVisible(false);
        quantity.setVisible(false);
        Answer.setVisible(false);
        OKButton.setVisible(false);
        Textto1.setVisible(false);

        Textto3.setVisible(false);
    }

    void translateToNumber() {
        isError = false;
        String errorCase = "";
        String text = inputset.getText();
        String answer = inputnumber.getText();
        String removeWhSText = text.replaceAll("\\s", "");

        int indexComma1 = removeWhSText.indexOf(",");
        int indexLastComma = removeWhSText.lastIndexOf(",");
        int indexComma2 = removeWhSText.indexOf(",", indexComma1 + 1);
        int indexMinus = removeWhSText.indexOf('-');

        if (text.length() == 0 || answer.length() == 0) { //เช็คว่า ที่ใส่เซตหรือเลขเป้าหมายว่างไหม
            isError = true;
            errorCase = "No input set or target";
        }

//        if (indexComma1 == -1) {
//            isError = true;
//            errorCase = "comma not found";
//        }
        if (!isError && ((indexComma1 == 0 || indexLastComma == removeWhSText.length() - 1) && removeWhSText.length() > 0)) {
            isError = true;
            errorCase = "too many commas";
        }

        if (!isError) {
            int countComma = 0;
            for (int i = 0; i < removeWhSText.length(); ++i) {
                char ch = removeWhSText.charAt(i);
                if (ch == ',') {
                    countComma++;
                    if (countComma > 1) {
                        isError = true;
                        errorCase = "too many commas";
                        break;
                    }
                } else {
                    countComma = 0;
                }
            }
        }

//        if (indexMinus != -1) { //เช็คว่า ที่ใส่เซตมีเลขติดลบไหม
//            isError = true;
//            errorCase = "Negative number found!";
//        }
        if (!isError) {
            String[] str = removeWhSText.split(",");
            setNumber = new long[str.length];
            int countNum = 0;
            for (int i = 0; i < text.length(); ++i) {
                char ch = text.charAt(i);
                if (ch == ' ' || ch == ',') {
                    char beforeCh = text.charAt(i - 1);
                    if (Character.isDigit(beforeCh)) {
                        countNum++;
                    }
                }
            }
            if (Character.isDigit(text.charAt(text.length() - 1))) {
                countNum++;
            }

            //System.out.println("countNum = " + countNum);
            if (!isError) {
                long total = 0;
                long totalMax = 0;
                for (int i = 0; i < str.length; i++) {
                    try {
                        long num = Long.parseLong(str[i]);
                        total += num;

                        if (num <= 0) { //เช็คว่า ที่ใส่เซตมีเลขที่น้อยกว่า 1 ไหม
                            isError = true;
                            errorCase = "number in set less than 1!";
                            break;
                        }

                        if (num == Long.MAX_VALUE) { //เช็คว่า ที่ผลรวมของเลขในเซตเป็นเลขติดลบไหม
                            totalMax += num;
                            if (totalMax <= 0) {
                                isError = true;
                                errorCase = "Overloaded sum!";
                                break;
                            }
                        }

                        if (total <= 0) { //เช็คว่า ที่ผลรวมของเลขในเซตเป็นเลขติดลบไหม
                            isError = true;
                            errorCase = "Overloaded sum!";
                            break;
                        }

                        setNumber[i] = num;

                    } catch (NumberFormatException ex) { //เช็คว่า ที่ใส่เซตเป็นตัวอักษรไหม
                        isError = true;
                        errorCase = "Character found.";
                        break;
                    }
                }
//                if (total <= 0 && !isError) {//เช็คว่า ที่ผลรวมของเลขในเซตเป็นเลขติดลบไหม
//                    isError = true;
//                    errorCase = "Overloaded sum!";
//                }
                try {
                    String answerWhS = answer.replaceAll("\\s", "");
                    long target = Long.parseLong(answerWhS);
                    if (target <= 0) { //เช็คว่า เลขเป้าหมายน้อยกว่า 0 ไหม
                        isError = true;
                        errorCase = "Negative target or zero number found!";
                    } else {
                        targetNum = target;
                    }
                } catch (NumberFormatException ex) { //เช็คว่า เลขเป้าหมายเป็นตัวอักษรไหม
                    errorCase = "Character found.";
                    isError = true;
                }

                if (countNum != str.length && !isError) { //เช็คว่า ที่ใส่เซตมี","พอไหม
                    isError = true;
                    errorCase = "Not enough commas.";
                }
            }
        }

        if (isError) {
            JOptionPane.showMessageDialog(null,
                    "Invalid input (" + errorCase + ")", "ERROR", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        InputSetUI f = new InputSetUI();
        f.startAndInput();
    }
}
