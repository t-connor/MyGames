package ffc;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import javax.swing.*;
import javax.swing.JOptionPane;

class ffc1 extends JFrame {
    //public class GobangGame {
        public static void main(String[] args) {
            ffc1 game = new ffc1();
            game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            game.show();
        }   
   // }

    public ffc1() {
        Container contentPane = getContentPane();
        final Panel panel = new Panel();
        panel.setBackground(new Color(20, 169, 158));
        contentPane.setBackground(new Color(0, 169, 158));
        contentPane.add(panel);
        setSize(700, 700);
        setResizable(false);
        panel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        JMenuBar menuBar=new JMenuBar();
        JMenu menu=new JMenu("option");
        JMenuItem menuStart=new JMenuItem("start game");

        menuStart.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
              panel.ResetGame();
              panel.repaint();
           }
        });

        JMenuItem menuExit =new JMenuItem("exit");
        menuExit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                  System.exit(0);
            }
         });

        menuBar.add(menu);
        menu.add(menuStart);
        menu.add(menuExit);
        this.setJMenuBar(menuBar);
      }
}
class Panel extends JPanel {

    static final int MAX_ROW = 19;
    static final int MAX_LINE = 19;
    static final int MAX_RANK_OF_NUMBER = 2048;
    static final int THE_SIZE_OF_GRID = 30;

    private int[][] board = new int [MAX_ROW][MAX_LINE];
    private boolean[][][] ptable = new boolean[MAX_ROW][MAX_LINE][MAX_RANK_OF_NUMBER];
    private boolean[][][] ctable = new boolean[MAX_ROW][MAX_LINE][MAX_RANK_OF_NUMBER];
    private int[][] cgrades = new int[MAX_ROW][MAX_LINE];
    private int[][] pgrades = new int[MAX_ROW][MAX_LINE];
    private int cgrade, pgrade;
    private int[][] win = new int[2][MAX_RANK_OF_NUMBER];
    private int oldx,oldy;
    private int bout = 1;
    private int pcount, ccount;
    private boolean player, computer, over, pwin, cwin, tie, start;
    private int mat, nat, mde, nde;
    private int i, j, k, icount, m, n;
    private int last_i, last_j;

    public Panel() {
        addMouseListener(new Xiazi());
        this.ResetGame();
     }
     public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < MAX_ROW; i++) {
            for (int j = 0; j < MAX_LINE; j++) {
                g.drawLine(50, 50 + j * THE_SIZE_OF_GRID, 590, 50 + j * THE_SIZE_OF_GRID);
            }
        }
        for (int i = 0; i < MAX_ROW; i++) {
            for (int j = 0; j < MAX_LINE; j++) {
                g.drawLine(50 + j * THE_SIZE_OF_GRID, 50, 50 + j * THE_SIZE_OF_GRID, 590);
            }
        }

        g.setColor(Color.BLACK);
        g.fillOval(47 + 3 * THE_SIZE_OF_GRID, 47 + 3 * THE_SIZE_OF_GRID, 7, 7);
        g.fillOval(47 + 3 * THE_SIZE_OF_GRID, 47 + 9 * THE_SIZE_OF_GRID, 7, 7);
        g.fillOval(47 + 3 * THE_SIZE_OF_GRID, 47 + 15 * THE_SIZE_OF_GRID, 7, 7);

        g.fillOval(47 + 9 * THE_SIZE_OF_GRID, 47 + 3 * THE_SIZE_OF_GRID, 7, 7);
        g.fillOval(47 + 9 * THE_SIZE_OF_GRID, 47 + 9 * THE_SIZE_OF_GRID, 7, 7);
        g.fillOval(47 + 9 * THE_SIZE_OF_GRID, 47 + 15 * THE_SIZE_OF_GRID, 7, 7);

        g.fillOval(47 + 15 * THE_SIZE_OF_GRID, 47 + 3 * THE_SIZE_OF_GRID, 7, 7);
        g.fillOval(47 + 15 * THE_SIZE_OF_GRID, 47 + 9 * THE_SIZE_OF_GRID, 7, 7);
        g.fillOval(47 + 15 * THE_SIZE_OF_GRID, 47 + 15 * THE_SIZE_OF_GRID, 7, 7);

        updatePaint(g);
    }
    class Xiazi extends MouseAdapter{
        public void mouseClicked(MouseEvent e) {
            if(!over)
            {
                oldx = e.getX();
                oldy = e.getY(); 
                mouseClick();
                repaint();
            }
        }
     }

        public void ResetGame()
        { 
            
            for(i = 0; i < MAX_ROW; i++) 
                for(j = 0; j < MAX_LINE; j++)
                {
                    this.pgrades[i][j] = 0;
                    this.cgrades[i][j] = 0;
                    this.board[i][j] = 2;
                }
            
            //
            for(i = 0; i < MAX_ROW; i++)
                for(j = 0; j < MAX_LINE - 4; j++){
                    for(k = 0; k < 5; k++){
                        this.ptable[j+k][i][icount] = true;
                        this.ctable[j+k][i][icount] = true;
                    }
                    icount++;
                }
            //
            for(i = 0; i < MAX_ROW; i++)
                for(j = 0; j < MAX_LINE - 4; j++) {
                    for(k = 0; k < 5; k++){
                        this.ptable[i][j+k][icount] = true;
                        this.ctable[i][j+k][icount] = true;
                    }
                    icount++;
                }
            //
            for(i = 0; i < MAX_ROW - 4; i++)
                for(j = 0; j < MAX_LINE - 4; j++) {
                    for(k = 0; k < 5; k++){
                        this.ptable[j+k][i+k][icount] = true;
                        this.ctable[j+k][i+k][icount] = true;
                    }
                    icount++;
                }
            //
            for(i = 0; i < MAX_ROW - 4; i++)
                for(j = MAX_LINE - 1; j >= 4; j--) {
                    for(k = 0; k < 5; k++){
                        this.ptable[j-k][i+k][icount] = true;
                        this.ctable[j-k][i+k][icount] = true;
                    }
                    icount++;
                }
            for(i = 0; i <= 1; i++)  
                for(j = 0; j < MAX_RANK_OF_NUMBER; j++)
                    this.win[i][j] = 0;
            this.player = true;
            this.icount = 0;
            this.ccount = 0;
            this.pcount = 0;
            this.start = true;
            this.over = false;
            this.pwin = false;
            this.cwin = false;
            this.tie = false;
            this.bout=1;
        }   再(ye)(bu)见,深圳
    public void ComTurn() {    
           for(i = 0; i < MAX_ROW; i++)   
                for(j = 0; j < MAX_LINE; j++) {   
                    this.pgrades[i][j]=0; 
                    if(this.board[i][j] == 2) 
                        for(k = 0; k < MAX_RANK_OF_NUMBER; k++)   
                            if(this.ptable[i][j][k]){
                                switch(this.win[0][k]){   
                                    case 1: 
                                        this.pgrades[i][j] += 5;
                                        break;
                                    case 2: 
                                        this.pgrades[i][j] += 80;
                                        break;
                                    case 3: 
                                        this.pgrades[i][j] += 250;
                                        break;
                                    case 4: 
                                        this.pgrades[i][j] += 500;
                                        break;
                                }
                            }
                    this.cgrades[i][j] = 0;
                    if(this.board[i][j] == 2)  
                        for(k = 0; k < MAX_RANK_OF_NUMBER; k++)    
                            if(this.ctable[i][j][k]) {
                                switch(this.win[1][k]) {  
                                    case 1:  
                                        this.cgrades[i][j] += 5;
                                        break;
                                    case 2:  
                                        this.cgrades[i][j] += 50;
                                        break;
                                    case 3: 
                                        this.cgrades[i][j] += 200;
                                        break;
                                    case 4:  
                                        this.cgrades[i][j] += 400;
                                        break;
                                }
                            }
                }
            java.util.Random RandomLocation = new java.util.Random();
            int Random_m = RandomLocation.nextInt(3);
            int Random_n = RandomLocation.nextInt(3);

            if(this.start) {      
                if(this.board[9][9] == 2) {
                    m = n = 9;
                } else {
                    if (Random_m == 1 && Random_n == 1) {
                        Random_m = 0;
                        Random_n = 0;
                    }
                    m = Random_m + 8;
                    n = Random_n + 8;

                }
                this.start = false;
            }else{
                for(i = 0; i < MAX_ROW; i++)
                    for(j = 0; j < MAX_LINE; j++)
                        if(this.board[i][j] == 2) {  
                            if(this.cgrades[i][j] >= this.cgrade) {
                                this.cgrade = this.cgrades[i][j];   
                                this.mat = i;
                                this.nat = j;
                            }
                            if(this.pgrades[i][j] >= this.pgrade) {
                                this.pgrade = this.pgrades[i][j];   
                                this.mde = i;
                                this.nde = j;
                            }
                        }
                if(this.cgrade >= this.pgrade) {  
                    m = mat;
                    n = nat;
                }else{
                    m = mde;
                    n = nde;
                }
            }
            this.cgrade = 0;        
            this.pgrade = 0;
            this.board[m][n] = 1;  
            ccount++;
            if((ccount >= 80) && (pcount >= 80)) {
                this.tie = true;
                this.over = true;
            }
            for(i = 0; i < MAX_RANK_OF_NUMBER; i++) {
                if(this.ctable[m][n][i] && this.win[1][i] != 7)
                    this.win[1][i]++;     
                if(this.ptable[m][n][i]){
                    this.ptable[m][n][i] = false;
                    this.win[0][i] = 7;
                }
            }
            this.player = true;    
            this.computer = false;  
        } 
    public void mouseClick() {
        if(!this.over)
            if(this.player){
                if(this.oldx < 600 && this.oldy < 600) {
                    int m1 = m, n1 = n;
                    m = (oldx - THE_SIZE_OF_GRID) / THE_SIZE_OF_GRID;
                    n = (oldy - THE_SIZE_OF_GRID) / THE_SIZE_OF_GRID;
                    if(this.board[m][n] == 2) {   
                        this.bout++;
                        this.board[m][n] = 0;   
                        pcount++;
                        if((ccount == 50) && (pcount == 50)) {
                            this.tie = true;
                            this.over = true;
                        }
                        for(i = 0; i < MAX_RANK_OF_NUMBER; i++) {
                            if(this.ptable[m][n][i] && this.win[0][i] != 7)
                                this.win[0][i]++;   
                            if(this.ctable[m][n][i]){
                                this.ctable[m][n][i] = false;
                                this.win[1][i] = 7;
                            }
                        }
                        this.player = false;      
                        this.computer = true;
                    } else {
                         m = m1; n = n1;
                    }
                }
            }
      }
     public void updatePaint(Graphics g) {
        if(!this.over) { 
            if(this.computer)
                this.ComTurn(); 

            for(i = 0; i <= 1; i++) {
                for(j = 0; j < MAX_RANK_OF_NUMBER; j++) {   
                    if(this.win[i][j] == 5) {
                        if(i == 0){               
                            this.pwin = true;
                            this.over = true;  
                            break;
                        }else{
                            this.cwin = true;   
                            this.over = true;
                            break;
                        }
                    }
                    if(this.over)              
                        break;
                }
            }

            for(i = 0; i < MAX_ROW; i++) {
                for(j = 0; j < MAX_LINE; j++) {  
                    if(this.board[i][j] == 0) {
                        g.setColor(Color.BLACK);
                        g.fillOval(i * THE_SIZE_OF_GRID + 40, j * THE_SIZE_OF_GRID + 40, 20, 20);
                    }
           
                    if(this.board[i][j] == 1) {
                        g.setColor(Color.WHITE);
                        g.fillOval(i * THE_SIZE_OF_GRID + 40, j * THE_SIZE_OF_GRID + 40, 20, 20);            

                        g.setColor(Color.RED);
                        g.fillOval(m * THE_SIZE_OF_GRID + 47, n * THE_SIZE_OF_GRID + 47, 5, 5);

                    }
                }
            }
            
            g.setColor(Color.BLACK);
            if(this.pwin)
                //JOptionPane.showMessageDialog(this, "CANGRATULATION! YOU WIN!");
                g.drawString("you win the game..", 20, 200);
         
            if(this.cwin)
                //JOptionPane.showMessageDialog(this, "GAME OVER! YOU LOSE!");
                g.drawString("you lose the game..", 84, 190);
 
        //g.dispose();
     }
  } 
}
