
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author vento
 */
public class GraphDrawing extends JFrame implements WindowListener {

    static int countPic = 0;
    boolean debugModeOn = false;
    BufferedImage grid = null;
    //Data of graph
    ArrayList<Vertex> Vertexs = new ArrayList<>();
    ArrayList<Edge_> Edge_s = new ArrayList<>();

    //Data of answer
    ArrayList<ArrayList<Long>> answers;
    ArrayList<int[]> answersX;

    //UI 
    JScrollPane scrollDrawingArea;
    //String mode = "Vertex"; //Vertex,Edge_

    //set font 
    Font sanSerifFont = new Font("SanSerif", Font.PLAIN, 24);

    //find size monitor
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    JFrame selectAnswerFrame = new JFrame("Select Answer");

    JMenuBar menuBar;
    JMenu Jmenu;
    JMenu JEditMenu;
    JMenu JShowAnswerMenu;
    JMenuItem selectAnswer;
    JMenuItem exportImageMenuItem;
    JMenuItem JmenuItem2;
    JMenuItem changeVertexColor;
    JMenuItem changeBackgroundVertexColor;
    JMenuItem changeAnswerPathEdgeColor;
    JMenuItem changeVertexsTextColor;
    JMenuItem resetStateSpaceTreeColor;

    JComboBox answerComboBox;
    JButton selectAnsButton = new JButton("Select");

    JPanel drawingArea = new JPanel();

    JTabbedPane tabbedPane = new JTabbedPane();
    JPanel resultArea = new JPanel();
    JTextArea boxAnswer = new JTextArea();
    JScrollPane scrollBoxAnswer;
    JScrollPane scrollTable;
    JPopupMenu PopUpMenu = new JPopupMenu();
    JScrollPane scrollPopupMenu;
    //-----###e
    String longestInfo = "";
    int distanceY = 200; //150, 250

    String setwriter = "";
    String targetwriter = "";

    AffineTransform affinetransform;
    FontRenderContext frc;

    long[] inputSet;

    GraphDrawing() {
        super("Subset Sum Result");
        setLayout(new BorderLayout());
        // create a empty canvas 

        this.answers = new ArrayList<>();
        this.answersX = new ArrayList<>();

        affinetransform = new AffineTransform();
        frc = new FontRenderContext(affinetransform, true, true);

        drawingArea = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
                drawOnJPanel(g);
            }
        };

        drawingArea.setSize(screenSize.width, screenSize.height);
        drawingArea.setLocation(0, 0);
        setBounds(0, 0, screenSize.width, screenSize.height);

        menuBar = new JMenuBar();
        Jmenu = new JMenu("File");
        exportImageMenuItem = new JMenuItem("Export as Image");
        exportImageMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                save();
            }
        });

        Jmenu.add(exportImageMenuItem);
        JmenuItem2 = new JMenuItem("Export as Text Output");
        JmenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportAsText();
            }
        });

        Jmenu.add(JmenuItem2);

        menuBar.add(Jmenu);

        JEditMenu = new JMenu("Edit");

        changeVertexColor = new JMenuItem("Change Answer Vertex Color");
        changeVertexColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Vertex.colorVertex();
                drawingArea.repaint();
            }
        });
        JEditMenu.add(changeVertexColor);
        menuBar.add(JEditMenu);

        changeBackgroundVertexColor = new JMenuItem("Change Background Vertex Color");
        changeBackgroundVertexColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Vertex.colorBackgroundVertex();
                drawingArea.repaint();
            }
        });
        JEditMenu.add(changeBackgroundVertexColor);
        menuBar.add(JEditMenu);

        changeAnswerPathEdgeColor = new JMenuItem("Change Answer Path Edges Color");
        changeAnswerPathEdgeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Edge_.color();
                drawingArea.repaint();
            }
        });
        JEditMenu.add(changeAnswerPathEdgeColor);

        changeVertexsTextColor = new JMenuItem("Change Vertexs Text Color");
        changeVertexsTextColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Vertex.colorSelectAnswerTextVertex();
                drawingArea.repaint();
            }
        });
        JEditMenu.add(changeVertexsTextColor);

        resetStateSpaceTreeColor = new JMenuItem("Reset State Space Tree Color");
        resetStateSpaceTreeColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Vertex.setColorsDefault();
                Edge_.setDefaultColor();
                drawingArea.repaint();
            }
        });
        JEditMenu.add(resetStateSpaceTreeColor);
        menuBar.add(JEditMenu);

        JShowAnswerMenu = new JMenu("ShowAnswer");
        selectAnswer = new JMenuItem("Select Answer...");
        selectAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (answersX.size() > 0) {
                    String[] ansChoice = new String[answersX.size() + 2];
                    for (int i = 0; i < answersX.size(); ++i) {
                        ansChoice[i] = "Answer " + (i + 1);
                    }
                    ansChoice[answersX.size()] = "All Answers";
                    ansChoice[answersX.size() + 1] = "No Answer";
                    answerComboBox = new JComboBox(ansChoice);
                    answerComboBox.setFont(sanSerifFont);
                    answerComboBox.setBounds(20, 20, 300, 30);
                    selectAnsButton.setBounds(130, 60, 100, 40);
                    selectAnswerFrame.add(answerComboBox);
                    selectAnswerFrame.add(selectAnsButton);
                    selectAnswerFrame.setLocation(500, 500);
                    selectAnswerFrame.setSize(360, 150);
                    selectAnswerFrame.setResizable(false);
                    selectAnswerFrame.setLayout(null);
                    selectAnswerFrame.setVisible(true);
                }

            }
        });
        selectAnsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = answerComboBox.getSelectedIndex();
                if (index + 1 <= answersX.size()) {
                    int[] aX = answersX.get(index);
                    deselectAllEdge();
                    traveseAnswerSet(inputSet, aX);
                    drawingArea.repaint();
                } else {
                    if (index + 1 == answersX.size() + 1) {
                        deselectAllEdge();
                        for (int[] ansX : answersX) {
                            traveseAnswerSet(inputSet, ansX);
                        }
                        drawingArea.repaint();
                    } else if (index + 1 == answersX.size() + 2) {
                        deselectAllEdge();
                        drawingArea.repaint();
                    }
                }
                //System.out.println("indexAnswer : " + index);
            }
        });
        JShowAnswerMenu.add(selectAnswer);
        menuBar.add(JShowAnswerMenu);

        boxAnswer.setFont(sanSerifFont);
        scrollDrawingArea = new JScrollPane(drawingArea);
        scrollDrawingArea.setLocation(0, 0);
        scrollDrawingArea.setSize(screenSize.width, screenSize.height);
        scrollDrawingArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        resultArea.setLayout(null);
        tabbedPane.addTab("Answer", resultArea);

        scrollBoxAnswer = new JScrollPane(boxAnswer);
        scrollBoxAnswer.setBounds(0, 0, 500, 500);
        resultArea.add(scrollBoxAnswer);
        getContentPane().add(tabbedPane);
        setJMenuBar(menuBar);
        addWindowListener(this);

    }

    void setJpopMenu(long[] set) {
        if (answersX.size() == 0) {

        } else {
            JMenuItem[] ansTarget = new JMenuItem[answersX.size()];
            JMenuItem targetAll = new JMenuItem("All Answers");
            JMenuItem noTarget = new JMenuItem("No Answer");
            for (int i = 0; i < answersX.size(); i++) {
                int[] aX = answersX.get(i);
                ansTarget[i] = new JMenuItem("Answer " + (i + 1));
                ansTarget[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        deselectAllEdge();
                        traveseAnswerSet(set, aX);
                        drawingArea.repaint();
                    }
                });
                PopUpMenu.add(ansTarget[i]);
            }
            targetAll.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deselectAllEdge();
                    for (int[] ansX : answersX) {
                        traveseAnswerSet(set, ansX);
                    }
                    drawingArea.repaint();
                }
            });
            noTarget.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    deselectAllEdge();
                    drawingArea.repaint();
                }
            });
            PopUpMenu.add(targetAll);
            PopUpMenu.add(noTarget);
            drawingArea.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        PopUpMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            });
        }

    }

    //set canvas is white
    public void clear() {
        Graphics2D g = (Graphics2D) drawingArea.getGraphics();
        initializeGraph();
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    void deselectAllEdge() {
        for (Edge_ e : Edge_s) {
            e.setSelectEdgeAndVertexAB(false);
        }
    }

    void initializeGraph() {
        Vertexs = new ArrayList<>();
        Edge_s = new ArrayList<>();
    }

    void initializeAnswers() {
        countPic = 0;
        this.answers = new ArrayList<>();
        this.answersX = new ArrayList<>();
        longestInfo = "";
    }

    private void drawOnJPanel(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;

        g2D.setFont(sanSerifFont);

        g2D.setColor(Color.WHITE);
        g2D.fillRect(0, 0, drawingArea.getWidth(), drawingArea.getHeight());

        for (Edge_ t : Edge_s) {
            t.draw(g2D);
        }
        for (Vertex s : Vertexs) {
            s.draw(g2D);
        }
    }

    int calMostInfoWidth(String info) {
        int textwidth = (int) (sanSerifFont.getStringBounds(info, frc).getWidth());
        return textwidth;
    }

    void generateStateSpaceTreeDFS(int level) {
        initializeGraph();
        int mostInfoWidth = calMostInfoWidth("100, 100");
        int drawingAreaWidth = calDrawingAreaWidth(level, mostInfoWidth + Vertex.paddingWidth + 50);
        int drawingAreaHeight = calDrawingHeigth(level);
        System.out.println("canvasWidth = " + drawingAreaWidth + ", screenWidth = " + screenSize.width);
        System.out.println("canvasHeigth = " + drawingAreaHeight + ", screenHeigth = " + screenSize.height);
        drawingArea.setPreferredSize(new Dimension(drawingAreaWidth, drawingAreaHeight));
        Vertex vFirst = new Vertex((drawingArea.getWidth() / 2), 36 + 20, "100, 100");
        Vertexs.add(vFirst);

        DFS(0, level, vFirst, "center", vFirst.x);
    }

    void DFS(int currentLevel, int level, Vertex v, String align, int base) {
        if (currentLevel == level) {
            return;
        } else {
            int offsetX = base / 2;
            Vertex vLeft = new Vertex(v.x - offsetX, v.y + distanceY, "100, 100");
            Vertexs.add(vLeft);
            Edge_ eLeft = new Edge_(v, vLeft, "1", -10);
            Edge_s.add(eLeft);
            DFS(currentLevel + 1, level, vLeft, "Left", offsetX);
            Vertex vRight = new Vertex(v.x + offsetX, v.y + distanceY, "100, 100");
            Vertexs.add(vRight);
            Edge_ eRight = new Edge_(v, vRight, "0", 10);
            Edge_s.add(eRight);
            DFS(currentLevel + 1, level, vRight, "Right", offsetX);
        }
    }

    void solveSubsetSumWBacktracking(long[] set, long target) {
        initializeGraph();
        initializeAnswers();
        int[] x = new int[set.length];
        long sum = sum(set, 0);
        int mostInfoWidth = calMostInfoWidth(sum + ", " + sum);
        int w = calDrawingAreaWidth(set.length, mostInfoWidth + Vertex.paddingWidth + 50);
        int h = calDrawingHeigth(set.length);
        drawingArea.setPreferredSize(new Dimension(w, h));
        System.out.println("w : " + w + ", h : " + h);
        Vertex v0 = new Vertex((drawingArea.getWidth() / 2), Vertex.paddingHeigth + 20, "0, " + sum);
        Vertexs.add(v0);
        backtrackingSubsetSum(set, target, x, -1, v0, v0.x);
        setJpopMenu(set);
        setBoxAnswer("Backtracking", set, target);
        if(answersX.size() == 0){
            JShowAnswerMenu.setEnabled(false);
        }
    }

    void backtrackingSubsetSum(long[] set, long target, int[] x, int index, Vertex v, int base) {
        long sum = sum(set, x, index);
        long other = sum(set, index + 2);
        debugModeSaveImage("bt" + countPic, true);
        if (index == set.length - 1) {
            if (sum == target) {
                System.out.println("index : " + index + ", other : " + other);
                System.out.print(" => Answer : ");
                printAnswerSubset(set, x);
                printArray1D(x);
                int[] aX = new int[x.length];
                for (int i = 0; i < x.length; ++i) {
                    aX[i] = x[i];
                }
                answersX.add(aX);
                answers.add(getAnswerSubset(set, x));
            }
        } else {
            int offsetX = base / 2;
            long nextNum = set[index + 1];
            if (sum + nextNum <= target) {
                x[index + 1] = 1;
                Vertex v1 = new Vertex(v.x - offsetX, v.y + distanceY, (sum + nextNum) + ", " + other);
                Vertexs.add(v1);
                Edge_ e1 = new Edge_(v, v1, "1", -10, index + 1, sum(set, x, index + 1));
                Edge_s.add(e1);
                backtrackingSubsetSum(set, target, x, index + 1, v1, offsetX);
            }
            x[index + 1] = 0;
            Vertex v0 = new Vertex(v.x + offsetX, v.y + distanceY, (sum) + ", " + other);
            Vertexs.add(v0);
            Edge_ e0 = new Edge_(v, v0, "0", 10, index + 1, sum(set, x, index + 1));
            Edge_s.add(e0);
            backtrackingSubsetSum(set, target, x, index + 1, v0, offsetX);
        }

    }

    void solveSubsetSumWBranchAndBound(long[] set, long target) {
        initializeGraph();
        initializeAnswers();
        long sum = sum(set, 0);

        int mostInfoWidth = calMostInfoWidth(sum + "");
        int w = calDrawingAreaWidth(set.length, mostInfoWidth + Vertex.paddingWidth + 50);
        int h = calDrawingHeigth(set.length);
        drawingArea.setPreferredSize(new Dimension(w, h));

        PriorityQueue<BranchNode> pq = new PriorityQueue<>();
        int[] x = new int[0];
        long cost = 0;
        Vertex v = new Vertex((drawingArea.getWidth() / 2), Vertex.paddingHeigth + 20, cost + "");
        int base = v.x;
        BranchNode bnF = new BranchNode(x, cost, v, base);
        pq.add(bnF);
        Vertexs.add(v);
        while (!pq.isEmpty()) {
            BranchNode bn = pq.remove();
            long bnCost = bn.cost;
            System.out.println(bn);
            int[] bnX = bn.x;
            Vertex vF = bn.v;
            debugModeSaveImage("bb" + countPic, true);
            if (bnCost <= target && bnCost >= 0) {
                if (bnX.length == set.length) {
                    if (bnCost == target) {
                        System.out.print(" => Answer : ");
                        printAnswerSubset(set, bnX);
                        printArray1D(bnX);
                        int[] aX = new int[bnX.length];
                        for (int i = 0; i < bnX.length; ++i) {
                            aX[i] = bnX[i];
                        }
                        answersX.add(aX);
                        answers.add(getAnswerSubset(set, aX));
                        break;
                    }
                } else {
                    int[] bnX1 = Arrays.copyOf(bnX, bnX.length + 1);
                    bnX1[bnX.length] = 1;
                    long bnCost1 = sum(set, bnX1, bnX1.length - 1);
                    String bnCost1Str = bnCost1 + "";
                    if (bnCost1 > target) {
                        bnCost1 = Long.MIN_VALUE;
                        bnCost1Str = "-∞";
                    }
                    Vertex v1 = new Vertex(vF.x - bn.base / 2, vF.y + distanceY, bnCost1Str);
                    BranchNode bn1 = new BranchNode(bnX1, bnCost1, v1, bn.base / 2);
                    Vertexs.add(v1);

                    int[] bnX0 = Arrays.copyOf(bnX, bnX.length + 1);
                    bnX0[bnX.length] = 0;
                    long bnCost0 = sum(set, bnX0, bnX.length - 1);
                    Vertex v0 = new Vertex(vF.x + bn.base / 2, vF.y + distanceY, bnCost0 + "");
                    BranchNode bn0 = new BranchNode(bnX0, bnCost0, v0, bn.base / 2);
                    Vertexs.add(v0);

                    pq.add(bn0);
                    pq.add(bn1);
                    Edge_ e1 = new Edge_(vF, v1, "1", -10, bnX.length, sum(set, bnX1, bnX.length));
                    Edge_ e0 = new Edge_(vF, v0, "0" + "", 10, bnX.length, sum(set, bnX0, bnX.length));
                    Edge_s.add(e1);
                    Edge_s.add(e0);
                }
            }
        }

        setBoxAnswer("Branch And Bound", set, target);
        setJpopMenu(set);
        if(answersX.size() == 0){
            JShowAnswerMenu.setEnabled(false);
        }
    }

    void debugModeSaveImage(String picName, boolean canCountPic) {
        if (debugModeOn) {
            saveAsImage(picName);
            if (canCountPic) {
                countPic++;
            }
        }
    }

    long sum(long[] set, int[] x, int index) {
        long total = 0;
        if (index < set.length) {
            for (int i = 0; i <= index; ++i) {
                total += (set[i] * x[i]);
            }
        }
        return total;
    }

    long sum(long[] set, int startIndex) {
        long total = 0;
        for (int i = startIndex; i < set.length; ++i) {
            total += (set[i]);
        }
        return total;
    }

    ArrayList<Long> getAnswerSubset(long[] set, int[] x) {
        ArrayList<Long> arrL = new ArrayList<>();
        for (int i = 0; i < set.length; ++i) {
            if ((set[i] * x[i]) > 0) {
                arrL.add(set[i]);
            }
        }
        return arrL;
    }

    int calDrawingAreaWidth(int n, int length) {
        int newWidth = (int) (Math.pow(2, n) * length);
        if (newWidth > drawingArea.getWidth()) {
            drawingArea.setSize(newWidth, drawingArea.getHeight());
        }
        return newWidth;
    }

    int calDrawingHeigth(int n) {
        int newHeigth = (Vertex.paddingHeigth + 20) * 2 + (n * distanceY) + 20;
        if (newHeigth > drawingArea.getHeight()) {
            drawingArea.setSize(drawingArea.getWidth(), newHeigth);
        }
        return newHeigth;
    }

    String printAnswerSubset(long[] set, int[] x) {
        String ans = "";
        ans += "{";
        int count = 0;
        for (int i = 0; i < set.length; ++i) {
            if ((set[i] * x[i]) > 0) {
                if (x[i] != 0 && count != 0) {
                    ans += ", ";
                }
                ans += set[i];
                count++;
            }
        }
        ans += "}";
        return ans;
    }

    void printArray1D(int[] x) {
        for (int i = 0; i < x.length; ++i) {
            System.out.print(x[i] + " ");
        }
        System.out.println("");
    }

    void traveseAnswerSet(long[] set, int[] x) {
        int level = 0;
        for (Edge_ e : Edge_s) {
            if (e.level == level && Integer.parseInt(e.edgeInfo) == x[level] && e.cost == sum(set, x, level)) {
                e.setSelectEdgeAndVertexAB(true);
                level++;
            }
            if (level > x.length - 1) {
                break;
            }
        }
    }

    void setInputSetTargetAlgorithm(long[] set, long target, String algorithm) {
        long[] set1 = {5, 10, 12, 13, 15, 18};
        long target1 = 30;
        long[] set2 = {6, 2, 4};
        long[] set3 = {Long.MAX_VALUE, 2, 4};
        long target2 = 6;
        inputSet = set;
        for (int i = 0; i < set.length; i++) {
            setwriter = setwriter + (set[i] + " ");

        }
        targetwriter = target + "";

        if (set.length <= 15) {
            tabbedPane.addTab("StateSpaceTree", scrollDrawingArea);
        } else {
            exportImageMenuItem.setEnabled(false);
            JEditMenu.setEnabled(false);
            JShowAnswerMenu.setEnabled(false);
        }
        switch (algorithm) {
            case "bt":
                setTitle("Subset Sum Result - Backtracking");
                solveSubsetSumWBacktracking(set, target);
                break;
            case "bb":
                setTitle("Subset Sum Result - Branch And Bound");
                solveSubsetSumWBranchAndBound(set, target);
                break;
            default:
                generateStateSpaceTreeDFS(3);
                break;
        }
    }

    public void save() {
        FileDialog fd = new FileDialog(this, "Save", FileDialog.SAVE);
        fd.setDirectory("C:\\");
        fd.setVisible(true);

        String fileName = fd.getFile();
        String filePath = fd.getDirectory();
        String path = "";
        if (fileName != null) {
            path = filePath + fileName;
            saveAsImage(path);
        }
    }

    public void saveAsImage(String path) {
        BufferedOutputStream imageOutStream = null;
        try {
            System.out.println(drawingArea.getWidth() + ", " + drawingArea.getHeight());
            grid = (BufferedImage) createImage(drawingArea.getWidth(), drawingArea.getHeight());
            Graphics2D g2 = grid.createGraphics();
            drawingArea.paint(g2);
            g2.dispose();
            imageOutStream = new BufferedOutputStream(new FileOutputStream(new File(path + ".png")));

            ImageIO.write(grid, "png", imageOutStream);
            imageOutStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (OutOfMemoryError e) {
            JOptionPane.showMessageDialog(null,
                    "This StateSpaceTree is too large to save.", "ERROR", JOptionPane.ERROR_MESSAGE
            );
        } catch (NegativeArraySizeException e) {
            JOptionPane.showMessageDialog(null,
                    "This StateSpaceTree is too large to save.", "ERROR", JOptionPane.ERROR_MESSAGE
            );
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (imageOutStream != null) {
                try {
                    imageOutStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setBoxAnswer(String algor, long[] set, long target) {
        String[] header = new String[set.length + 1];
        String[][] data = new String[answersX.size()][header.length];
        TextTableCenter textCenter = new TextTableCenter();
        for (int i = 0; i < answersX.size(); i++) {
            data[i][0] = (i + 1) + "";
            int[] a = answersX.get(i);
            for (int j = 1; j < header.length; j++) {
                data[i][j] = a[j - 1] + "";
            }
        }
        for (int i = 0; i < header.length; i++) {
            if (i == 0) {
                header[i] = " ";
            } else {
                header[i] = " " + set[i - 1];
            }
        }
        DefaultTableModel tableModel1 = new DefaultTableModel(data, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable jTable = new JTable(tableModel1);
        jTable.setFont(sanSerifFont);
        jTable.getTableHeader().setFont(sanSerifFont);
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.setRowHeight(30);
        for (int i = 0; i <= set.length; i++) {
            jTable.getColumnModel().getColumn(i).setCellRenderer(textCenter);
        }
        if (jTable.getColumnCount() > 6) {
            for (int i = 0; i <= set.length; i++) {
                jTable.getColumnModel().getColumn(i).setPreferredWidth(130);
                jTable.getColumnModel().getColumn(i).setMaxWidth(130);

            }
            jTable.setAutoResizeMode(jTable.AUTO_RESIZE_OFF);
        }
        scrollTable = new JScrollPane(jTable);
        scrollTable.setBounds(520, 0, 900, 300);
        resultArea.add(scrollTable);
        boxAnswer.setText(algor + "\n");
        boxAnswer.append("Your number in set: ");
        boxAnswer.append(printAnswerSubset(set));
        boxAnswer.append("\nTarget: " + target + "\n");
        if (answersX.size() == 0) {
            boxAnswer.append("No answer.");
        } else {
            for (int i = 0; i < answersX.size(); i++) {
                boxAnswer.append("Subset " + (i + 1) + "#: ");
                boxAnswer.append(printAnswerSubset(set, answersX.get(i)));
                boxAnswer.append("\n");
            }
        }
        boxAnswer.setEditable(false);
    }

    String printAnswerSubset(long[] set) {
        String ans = "";
        ans += "{";
        for (int i = 0; i < set.length; ++i) {
            if (i > 0 && i < set.length) {
                ans += ", ";
            }
            ans += set[i];
        }
        ans += "}";
        return ans;
    }

    public void exportAsText() {
        FileDialog fc = new FileDialog(this, "export", FileDialog.SAVE);
        fc.setDirectory("C:\\");
        fc.setFile("*.txt");
        fc.setVisible(true);

        String fileName = fc.getFile();
        String filePath = fc.getDirectory();
        String path = "";

        if (fileName != null) {
            String realName = fileName;
            if (fileName.lastIndexOf('.') != -1) {
                realName = fileName.substring(0, fileName.lastIndexOf('.'));
            }
            path = filePath + realName;

        }
        FileWriter writer = null;
        try {
            int n[];
            String txt = "";
            for (int i = 0; i < answersX.size(); i++) {
                n = answersX.get(i);
                for (int j = 0; j < n.length; j++) {
                    txt += (n[j] + " ");
                }
                txt += "\n";
            }

            writer = new FileWriter(path + ".txt");
            writer.write(setwriter + "\n");
            writer.write(targetwriter + '\n');
            writer.write(txt);
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(GraphDrawing.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                writer.close();
            } catch (IOException ex) {
                Logger.getLogger(GraphDrawing.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        //System.out.println("win open");
        Vertex.setColorsDefault();
        Edge_.setDefaultColor();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        Vertex.setColorsDefault();
        Edge_.setDefaultColor();
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
