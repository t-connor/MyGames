package ffc;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ffc extends JFrame implements MouseListener, Runnable {
    int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    int height = Toolkit.getDefaultToolkit().getScreenSize().height;

    //BufferedImage bjImage = null;

    int x, y;

    //save the frame:
    //0 ->  no chess
    //1 -> black chess
    //2 -> white chess
    int [][] allChess = new int[20][20];
    Boolean [][][] ptable = new Boolean[19][19][2048];
    Boolean [][][] ctable = new Boolean[19][19][2048];
    int [][] cgrades = new int[19][19];
    int [][] pgrades = new int[19][19];
    int cgrade, pgrade;
    int [][] win = new int[2][2048];
    int ccount, pcount;

    int m, n, icount;
    int mat, nat, mde, nde;

    Boolean isBlack = true;
    Boolean canPlay = true;
    Boolean isclean = false;

    String message = "Black first";

    int [] chessX = new int[255];
    int [] chessY = new int[255];

    int countX, countY;

    int maxTime = 0;

    String blackMessage = "No Limit";
    String whiteMessage = "No Limit";

    int blackTime = 0;
    int whiteTime = 0;

    Thread timer = new Thread(this);

    public ffc() {
        this.setTitle("Five Finger Chess");
        this.setSize(500, 500);
        this.setLocation((width - 500) / 2, (height - 500) / 2);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.repaint();
        this.addMouseListener(this);
        timer.start();
        timer.suspend();
    }

    public void paint(Graphics g) {
        BufferedImage bi = new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
        Graphics g2 = bi.createGraphics();

        g2.setColor(new Color(0, 169, 158));
        g2.fill3DRect(0,10, 500, 500, true);

        //g.drawImage(bjImage, 0, 20, this);

        for (int i = 0; i < 19; i++) {
            g2.setColor(Color.WHITE);
            g2.drawLine(20, 40 + 20 * i, 380, 40 + 20 * i);
            g2.drawLine(20 + 20 * i, 40, 20 + 20 * i, 400);
        }


        g2.fillOval(77, 97, 5, 5);
        g2.fillOval(197, 97, 5, 5);
        g2.fillOval(317, 97, 5, 5);

        g2.fillOval(77, 217, 5, 5);
        g2.fillOval(197, 217, 5, 5);
        g2.fillOval(317, 217, 5, 5);

        g2.fillOval(77, 337, 5, 5);
        g2.fillOval(197, 337, 5, 5);
        g2.fillOval(317, 337, 5, 5);


        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 20; j++) {
                if (allChess[i][j] == 1) {
                // black chess
                    int tempX = i * 20 + 20;
                    int tempY = j * 20 + 20;
                    g2.setColor(Color.BLACK);
                    g2.fillOval(tempX - 7, tempY - 7, 14, 14);
                }
                if (allChess[i][j] == 2) {
                    // wgite chess
                    int tempX = i * 20 + 20;
                    int tempY = j * 20 + 20;
                    g2.setColor(Color.WHITE);
                    g2.fillOval(tempX - 7, tempY - 7, 14, 14);
                    g2.setColor(Color.BLACK);
                    g2.drawOval(tempX - 7, tempY - 7, 14, 14);
                }
            }
        }

        g2.setColor(Color.WHITE);
        g2.drawRect(428, 100, 50, 20);
        g2.drawString("clean", 435, 114);

       g.drawImage(bi, 0, 0, this);
    }

    

    @Override
    public void mouseClicked(MouseEvent arg0) {
    }

    @Override
    public void mouseEntered(MouseEvent arg0) {
    }

    @Override
    public void mouseExited(MouseEvent arg0) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Boolean checkWin = false;

        if (canPlay) {
            x = e.getX();
            y = e.getY();

            if (x >= 0 && x <= 440 && y >= 0 && y <= 500) {
                if ((x - 20) % 20 > 11)
                    x = (x - 20) / 20 + 1;
                else
                    x = (x - 20) / 20;
                if ((y - 20) % 20 > 11)
                    y = (y - 20) / 20 + 1;
                else
                    y = (y - 20) / 20;

                if (y == 19) y = 18;
                if (allChess[x][y] == 0) {
 
                    chessX[countX++] = x;
                    chessY[countY++] = y;
                     

                    if (isBlack) {
                        allChess[x][y] = 1;
                        isBlack = false;
                        message = "white chess playing";

                    } 
                    else {
                        
                        allChess[x][y] = 2;
                        isBlack = true;
                        message = "black chess playing";

                    }
                    this.repaint();
                    checkWin = isWin();
                    if (checkWin) {
                        if (allChess[x][y] == 1)
                            JOptionPane.showMessageDialog(this, "GAME OVER, black win the game");
                        else
                            JOptionPane.showMessageDialog(this, "GAME OVER, white win the game");
                        canPlay = false;
                    }
                }
            }
        } else {
            x = e.getX();
            y = e.getY();

            if (x >= 0 && x <= 420 && y >= 0 && y <= 460) {
                if ((x - 20) % 20 > 12)
                    x = (x - 20) / 20 + 1;
                else
                    x = (x - 20) / 20;
                if ((y - 20) % 20 > 12)
                    y = (y - 20) / 20 + 1;
                else
                    y = (y - 20) / 20;

                allChess[x][y] = 0;
                this.repaint();
            }
        }

        //428, 100, 50, 20
        if ((e.getX() >= 428 && e.getX() <= 478 && e.getY() >= 100 && e.getY() <= 120) )
        {
            isclean = !isclean;
            if (isclean)
            {
                canPlay = false;
            } else {
                canPlay = true;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent arg0) {

    }

    private Boolean isWin() {
        boolean flag = false;
       
        int count = 1;
       
        int color = allChess[x][y];
       
        count = this.checkCount(1, 0, color);
        if (count >= 5) {
            flag = true;
        } else {
       
            count = this.checkCount(0, 1, color);
            if (count >= 5) {
                flag = true;
            } else {
       
                count = this.checkCount(1, -1, color);
                if (count >= 5) {
                    flag = true;
                } else {
       
                    count = this.checkCount(1, 1, color);
                    if (count >= 5) {
                        flag = true;
                    }
                }
            }
        }
 
        return flag;
    }

    private int checkCount(int xChange, int yChange, int color) {
        int count = 1;
        int tempX = xChange;
        int tempY = yChange;
        while (x + xChange >= 0 && x + xChange < 19 && y + yChange >= 0
                && y + yChange < 19
                && color == allChess[x + xChange][y + yChange]) {
            count++;
            if (xChange != 0)
                xChange++;
            if (yChange != 0) {
                if (yChange > 0)
                    yChange++;
                else {
                    yChange--;
                }
            }
        }
        xChange = tempX;
        yChange = tempY;
        while (x - xChange >= 0 && x - xChange < 19 && y - yChange >= 0
                && y - yChange < 19
                && color == allChess[x - xChange][y - yChange]) {
            count++;
            if (xChange != 0)
                xChange++;
            if (yChange != 0) {
                if (yChange > 0)
                    yChange++;
                else {
                    yChange--;
                }
            }
        }
        return count;
    }

    @Override
    public void run() {

    }

    public static void main(String[] args) {
        ffc mf = new ffc();
    }
} 
