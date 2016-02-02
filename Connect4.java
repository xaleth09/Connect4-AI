/*
 * Roberto Garza - xaleth09 - U89265073
 * John Martin - jcmartin - U06614256
 * 
 * CS112
 * Professor Snyder
 * 
 * HW06: Connect4.java
 * -Simple Graphical User Interface which allows the user to play Connect4 against a Computer
 * 
 * (i) John Martin (up to two people), 
 * (ii) Firstly, we used a graphic user interface which made use of JCanvases, JBoxes, and JEventQueues.
 * These objects along with the methods supplied with them were used to construct a gui that used jboxes 
 * to arrange items in either a horizontal or a vertical manner. JCanvas was used to draw all of these
 * organized jboxes and the event queue was used to record user inputs by button clicks. The queue worked
 * in a familiar FIFO pattern, and stored the buttons pressed by the user. While these elements took user
 * inputs and put them into a board (stored with matrix arrays), the computers moves were calculated with a heuristic function. By adding
 * every possible set of four on the board (to do this, it must exclude sets with other players pieces and
 * sets that run off the board) with a higher rank for sets that are closer to four in a row (or equal it). 
 * By excluding sets of 1, the sum of all these sets is the score for that instance for the board. 
 * 
 * 
 */

import java.awt.*;
import javax.swing.*;
import java.util.EventObject;

public class Connect4 {
  
  final static int BOARD_SIZE=8;
  
  static JButton myButton(String s){
    JButton button=new JButton(s);
    button.setSize(75, 23);
    return button;
  }
  
  
  static void drawBoard(int[][] b,JCanvas c){
    for(int i=0;i<BOARD_SIZE;i++){
      for(int j=0;j<BOARD_SIZE;j++){
        if(b[j][i]==1){
          c.setPaint(Color.blue);
        }else if(b[j][i]==2){
          c.setPaint(Color.red);
        }else{
          c.setPaint(Color.lightGray);
        }
        c.fillRect(75*i, 75*j, 75, 75);
        c.setPaint(Color.black);
        c.drawRect(75*i, 75*j, 75, 75);
      }
    }
  }
  
  static int hWinner(int gT,int[][] b){
    int[] a=new int[3];
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 4; j++) {
        for (int x = j; x < j + 4; x++) { 
          ++a[b[x][i]];
        }
        if (a[1] == 4) {
          System.out.println("4 1's in a row");
        	gT=-1;
        } else if (a[2] == 4) {
          System.out.println("4 2's in a row");
        	gT=-1;
        }
        a=new int[3];
      }
    }
    
    return gT;
  }
  
  static int vWinner(int gT,int[][] b){
    int[] a=new int[3];
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 4; j++) {
        for (int x = j; x < j + 4; x++) { //reversesd signs
          ++a[b[i][x]];
        }
        if (a[1] == 4) {
          gT=-1;
        } else if (a[2] == 4) {
          gT=-1;
        }
        a=new int[3];
      }
    }
    return gT;
  }
  
  static int dLeft(int gT,int[][] b){
    int[] a=new int[3];
    for (int i = 0; i <= 4; i++) {
      for (int j = 0; j <= 4; j++) {
        int incrementedShift = i;
        for (int x = j; x < j + 4; x++) { //reversesd signs
          ++a[b[x][incrementedShift++]];  
        }
        if (a[1] == 4) {
          gT=-1;
        } else if (a[2] == 4) {
          gT=-1;
        }
        a=new int[3];
      }
    }
    return gT;
  }
  
  static int dRight(int gT,int[][] b){
    int[] a=new int[3];
    for (int i = 3; i <= 7; i++) {
      for (int j = 0; j <= 4; j++) {
        int incrementedShift = i;
        for (int x = j; x < j + 4; x++) { //reversesd signs
          ++a[b[x][incrementedShift--]];  
        }
        if (a[1] == 4) {
          gT=-1;
        } else if (a[2] == 4) {
          gT=-1;
        }
        a=new int[3];
      }
    }
    
    return gT;
  }
  
  
  static int[][] gravity(int player,int clm,JCanvas c,int [][] b){
    clm=clm-1;
    int count=BOARD_SIZE-1;
    for(int i=BOARD_SIZE-1;i>=0;i--){
      if(b[i][clm]==0){
        b[i][clm]=player;
        break;
      }
      count--;
    }
    
    
    return b;
  }
  
  
  public static void main(String[] args) {
    
    int gType=-1;
    
    //Frame
    JFrame frame=new JFrame("Connect4"); 
    frame.setSize(900,800);
    frame.setMinimumSize(new Dimension(900,800));
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //Canvas
    int [][] bGame=new int[8][8];
    JCanvas board=new JCanvas();
    board.setBackground(Color.lightGray);
    
    //Buttons
    JButton game0= myButton("H vs C");
    game0.setSize(200,100);
    JButton reset=new JButton("Reset");
    
    //Labels
    JLabel whosOnFirst=new JLabel("PLAYER: CHOOSE GAME TYPE");
    JBox.setSize(whosOnFirst, 300, 30);
    whosOnFirst.setFont(new Font("Arial",Font.PLAIN,18));
    
    //Box
    JBox game= JBox.hbox(
                         JBox.hspace(20),
                         JBox.vbox(JBox.CENTER_ALIGNMENT,whosOnFirst,JBox.vspace(60),board,JBox.vglue()),
                         JBox.hspace(40),
                         JBox.vbox(JBox.CENTER_ALIGNMENT,JBox.vglue(),JBox.hbox(game0,JBox.hspace(10),reset),
                                   JBox.vglue()),
                         JBox.hspace(30)
                        );
    
    JBox.setSize(board,600,600);
    
    
    //EventQueue for Buttons
    JEventQueue events=new JEventQueue();
    events.listenTo(game0, "pve");
    events.listenTo(reset, "reset");
    
    //Adding insert Buttons
    int bx=-55; int by=66;
    for(int i=1;i<=BOARD_SIZE;i++){
      JButton b= myButton(Integer.toString(i));
      b.setLocation(bx+=75, by);
      frame.add(b);
      events.listenTo(b, Integer.toString(i));
    }
    
    
    frame.add(game);
    drawBoard(bGame,board);
    frame.setVisible(true);
    
    
    int player=1;
    
    while(true){
      
    	//Computers Move
    	if(gType==0 && player==2){
    		board.startBuffer();
    		gravity(player,Player.move(bGame, player)+1,board,bGame);
    		drawBoard(bGame,board);
    		board.endBuffer();
    		
    		 //check if winner
            gType=vWinner(gType,bGame);
            gType=hWinner(gType,bGame);
            gType=dLeft(gType,bGame);
            gType=dRight(gType,bGame);
            
            if(gType==-1){
            	whosOnFirst.setText("YAY Player "+player+" WON!");
            }else{
            player=1;
            whosOnFirst.setText("Player: "+Integer.toString(player)+"   GO!!!!");
            }
            
        }
    	
    	EventObject event; String name;
      
      
      
      if(gType==-1){
    	  event=events.waitEvent();
          name=events.getName(event);  
    	  
      board.startBuffer();
      if(name.equals("pve")){
        gType=0;
        whosOnFirst.setText("Player: 1   GO!!!!");
        game0.setVisible(false);
        drawBoard(bGame, board);
        player= 1;
      }
     
    	
      if(name.equals("reset")){
    	  game0.setVisible(true);
    	  bGame=new int[8][8];
    	  gType=-1;
    	  board.startBuffer();
    	  drawBoard(bGame,board);
    	  board.endBuffer();
    	  
      }
      
      }
      
      if(gType!=-1 && player==1){ 
    	  event=events.waitEvent();
          name=events.getName(event);
    	 
          
         if(name.equals("reset")){
        	 game0.setVisible(true);
       	  bGame=new int[8][8];
       	  gType=-1;
       	  board.startBuffer();
       	  drawBoard(bGame,board);
       	  board.endBuffer();
         }
          
        if(name.equals("1")){
          bGame=gravity(player,1,board,bGame);
        }
        
        if(name.equals("2")){
          bGame=gravity(player,2,board,bGame);
        }
        
        if(name.equals("3")){
          bGame=gravity(player,3,board,bGame);
        }
        
        if(name.equals("4")){
          bGame=gravity(player,4,board,bGame);          
        }
        
        if(name.equals("5")){
          bGame=gravity(player,5,board,bGame);
        }
        
        if(name.equals("6")){
          bGame=gravity(player,6,board,bGame);
          drawBoard(bGame,board);
        }
        
        if(name.equals("7")){
          bGame=gravity(player,7,board,bGame);          
        }
        
        if(name.equals("8")){
          bGame=gravity(player,8,board,bGame);
          
        }
        
      
        
        drawBoard(bGame,board);
        board.endBuffer();
       
        player=2;
        
      } 
        
        //check if winner
        gType=vWinner(gType,bGame);
        gType=hWinner(gType,bGame);
        gType=dLeft(gType,bGame);
        gType=dRight(gType,bGame);
        
        //changes turns or displays if there was a winner
        if(gType==-1){
          whosOnFirst.setText("YAY Player "+player+" WON!");
        }else{
        	 whosOnFirst.setText("Player: "+Integer.toString(player)+"   GO!!!!");
        }
        
   
        
        
       
     
      
      
      
    }
    
  }
  
}
