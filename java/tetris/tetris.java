package tetris;
 
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
 
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Tetris extends JFrame{

  public static class ret {
   public static  boolean ret = false;
 }
public static void main(String[] args) {

Tetris te = new Tetris();
te.setVisible(true);

}
 private TetrisPanel tp;
 JMenuItem itemPause; 
 JMenuItem itemContinue;
 
 public Tetris() {
 this.setDefaultCloseOperation(EXIT_ON_CLOSE);
 this.setLocation(700, 200);
 this.setSize(400, 500);
 this.setResizable(false);
 tp = new TetrisPanel();
 this.getContentPane().add(tp);
 
   ret.ret = false;  // hzj 

 JMenuBar menubar = new JMenuBar();
 this.setJMenuBar(menubar);
 JMenu menuGame = new JMenu("game");
 menubar.add(menuGame);
 
 JMenuItem itemNew = new JMenuItem("new Game");
 itemNew.setActionCommand("new");
 itemPause = new JMenuItem("stop");
 itemPause.setActionCommand("pause");
 
 itemContinue = new JMenuItem("continue");
 itemContinue.setActionCommand("continue");
 itemContinue.setEnabled(false);
 
 menuGame.add(itemNew);
 menuGame.add(itemPause);
 menuGame.add(itemContinue);
 
 MenuListener menuListener = new MenuListener();
 itemNew.addActionListener(menuListener);
 itemPause.addActionListener(menuListener);
 itemContinue.addActionListener(menuListener);
 
 

 this.addKeyListener( tp.listener );
 }
 class MenuListener implements ActionListener{
 @Override
 public void actionPerformed(ActionEvent e) {

  if(e.getActionCommand().equals("new")){
  tp.newGame();
  }
  if(e.getActionCommand().equals("pause")){
  timer.stop();
  itemContinue.setEnabled(true);
  itemPause.setEnabled(false);
  }
  if(e.getActionCommand().equals("continue")){
  timer.restart();
  itemContinue.setEnabled(false);
  itemPause.setEnabled(true);
  }
 }
 }
 
 private Timer timer;
 
 class TetrisPanel extends JPanel{

  int shapes[][][] = new int[][][] {


   { { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 },
    { 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0 } },
   // S
   { { 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 },
    { 0, 0, 1, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0 } },
   // Z
   { { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 } },
   // J
   { { 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
    { 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
   // O
   { { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
   // L
   { { 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
    { 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 } },
   // T
   { { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
    { 1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
    { 0, 1, 0, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0 } }
    };
 
 private int blockType;
 private int turnState;
 private int x;
 private int y;
 
 
 private int map[][]=new int[13][23];
 
 
 private int delay=100;
 public TimerKeyLister listener=new TimerKeyLister(); 
 
 private int score=0;
 
 public TetrisPanel(){
  newGame();
  nextBlock();
 }


 
 public void newGame() {
  blockType = (int)(Math.random()*1000)%7;
  turnState = (int)(Math.random()*1000)%4;
  x=4;
  y=0;
 
  for( int i=0;i<12;i++){
  for(int j=0;j<21;j++){
   if(i==0 || i==11){
   map[i][j]=3;
   }else{
   map[i][j]=0;
   }
  }
  map[i][21]=3;
  }
 
  if(timer!=null){
  timer.stop();
  }
 
  delay=1000;
  timer = new Timer(delay,listener);
  timer.start();
 }
 
 private void nextBlock() {
  blockType = (int)(Math.random()*1000)%7;
  turnState = (int)(Math.random()*1000)%4;
  x=4;
  y=0;


  if(crash(x,y,blockType,turnState)==0){
  timer.stop();
  int option = JOptionPane.showConfirmDialog(this,
   "Game Over!! try again?...");
  if (option == JOptionPane.OK_OPTION) {
   newGame();
  } else if (option == JOptionPane.NO_OPTION) {
   System.exit(0);
  }
  }
 }
 
  private void down() {
      if( crash(x,y+1,blockType,turnState)==0 ){
          add(x,y,blockType,turnState);
          nextBlock();

      }else{
          y++;
      }
      repaint();
  }

 private void left() {
  if(x >= 0){
    x -= crash(x-1,y,blockType,turnState);
  }
  repaint();
 }
 private void right() {
  if(x<8){
  x += crash(x+1,y,blockType,turnState);
  }
  repaint();
 }
 private void turn() {

  if(crash(x,y,blockType,(turnState+1)%4)==1 ){
  turnState = (turnState+1)%4;
  }
  repaint();
 }
 
 private void add(int x, int y, int blockType, int turnState) {

  for( int a=0; a<4; a++){
  for(int b=0; b<4; b++){
   if( shapes[blockType][turnState][a*4+b] ==1 ){
   map[x+b+1][y+a] = 1;
   }
  }
  }
  tryDelLine();
 }

 private void tryDelLine(){
  for(int b=0;b<21;b++){
  int c=1;
  for(int a=0;a<12;a++){
   c &= map[a][b];
  }
  if(c==1){

   for(int d=b; d>0; d--){
   for(int e=0;e<11;e++){
    map[e][d] = map[e][d-1];
   }
   }

   score +=100;
   delay /=1.05;
   timer.setDelay(delay);
  }
  }
 
 }
 
 
 private int crash(int x, int y, int blockType, int turnState){
  for( int a=0; a<4; a++){
  for(int b=0; b<4; b++){

   if( (shapes[blockType][turnState][a*4+b] & map[x+b+1][y+a]) ==1 ){
   return 0;
   }
  }
  }
  return 1;
 }
 
 @Override
 public void paint(Graphics g) {
  int temp_x = 0, temp_y = 0;

  super.paint(g);

  g.setColor(new Color(153,51,205));

/*
  if(ret.ret == false)
  {
    temp_x = x;
    temp_y = y;
    my_blockType = blockType;
    my_turnstate = turnState;

    ret.ret = true;
  }*/

  for(int j=0;j<16;j++){
      if(shapes[blockType][turnState][j]==1){
      g.fillRect((j%4+temp_x+1)*20 + 300, (j/4+temp_y)*20, 20, 20);
      g.setColor(Color.cyan);
      g.drawRect((j%4+temp_x+1)*20 + 300, (j/4+temp_y)*20, 20, 20);
      g.setColor(new Color(153,51,205));
    }
  }

  for(int j=0;j<16;j++){
    if(shapes[blockType][turnState][j]==1){
    g.fillRect((j%4+x+1)*20, (j/4+y)*20, 20, 20);
    g.setColor(Color.cyan);
    g.drawRect((j%4+x+1)*20, (j/4+y)*20, 20, 20);
    g.setColor(new Color(153,51,205));
    
    }
  }
 

  g.setColor(Color.red);
  for( int i=0;i<12;i++){
    for(int j=0;j<22;j++){
      if(map[i][j]==3){
        g.drawRect(i*20, j*20, 20, 20);
      }else if(map[i][j]==1){
        g.fillRect(i*20, j*20, 20, 20);
        g.setColor(Color.GREEN);
        g.drawRect(i*20, j*20, 20, 20);
        g.setColor(Color.red);
      } 
    }
  }

  //g.setColor(Color.red);
  //g.setFont(new Font("aa", Font.BOLD, 11));
  //g.drawString("score=" + score, 140, 20);
 
  //g.setFont(new Font("aa", Font.PLAIN, 13));
  //g.setColor(Color.blue);
 }
 
 class TimerKeyLister extends KeyAdapter implements ActionListener{
  @Override
  public void actionPerformed(ActionEvent e) {
   down();
  }
 
  @Override
  public void keyPressed(KeyEvent e) {
  switch(e.getKeyCode()) {
   case KeyEvent.VK_DOWN:
   down(); break;
   case KeyEvent.VK_LEFT:
   left();break;
   case KeyEvent.VK_RIGHT:
   right();break;
   case KeyEvent.VK_UP:
   turn();break;
   case KeyEvent.VK_F1:
   plug();
   case KeyEvent.VK_F2:
   time();
  }
  }
 
 }
 
 public void plug() {
  score+=100;
 }
 
 public void time() {
  delay =1000;
  timer.setDelay(delay);
 }
 
 }
}
