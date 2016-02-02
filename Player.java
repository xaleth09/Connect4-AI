/*
 * Roberto Garza - xaleth09 - U89265073
 * John Martin - jcmartin - U06614256
 * 
 * CS112
 * Professor Snyder
 * 
 * HW06: Player.java
 * This java program is an attempt to create a "smart" computer player
 * which uses a heuristic function and minmax trees to find an optimal move;
 * 
 * (i) Roberto Garza (up to two people), 
 * (ii) Firstly, we used a graphic user interface which made use of JCanvases, JBoxes, and JEventQueues.
 * These objects along with the methods supplied with them were used to construct a gui that used jboxes 
 * to arrange items in either a horizontal or a vertical manner. JCanvas was used to draw all of these
 * organized jboxes and the event queue was used to record user inputs by button clicks. The queue worked
 * in a familiar FIFO pattern, and stored the buttons pressed by the user. While these elements took user
 * inputs and put them into a board (stored with matrix arrays), the computers moves were calculated with a heuristic function. By adding
 * every possible set of four on the board (to do this, it must exclude sets with other players pieces and
 * sets that run off the board) with a higher rank for sets that are closer to four in a row (or equal it). 
 * By excluding sets of 1, the sum of all these sets is the score for that instance of the board. By creating min/max spanning trees, we can permute
 * possible boards and allow for alpha beta pruning to stop the spanning if unnecessary.
 * 
 * 
 */

import java.util.Scanner;


public class Player {
 
 // initialize the search depth as well as 
 // an extremely large and extremely small
 // integer for alpha and beta
  static int maxDepth = 5;
  
  static int alpha = Integer.MIN_VALUE;
  static int beta = Integer.MAX_VALUE;
  
  //this method takes a column from a board and
  //figures out the next possible row to insert a piece
  public static int figureRow(int[][] b, int col) {
    int x = 0;
    
    for (int i = 0; i < 8; i++) {//for each row
      if (b[i][col] != 0) { //if the row is filled
        x = -1;
      } else if (i == 6 && b[i + 1][col] == 0) {
        x = 7;
        break;
      } else if (b[i + 1][col] != 0) {
        x = i;
        break;
      }
    }
    return x;
  }
  
  //this is my heuristic function that takes the number of adjacent with 
  public static int eval(int[][] b) {
    
    int[] a = new int[3];
    int sum = 0;
    
    
    //this for loop scans each vertical set of four starting with the [0][0] and then [1][0]
    //until [4][0] and then [0][1]
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 4; j++) {
        for (int x = j; x < j + 4; x++) { //reversesd signs
          ++a[b[x][i]];
        }
        if (a[1] == 4) {
          sum += 10000;
        } else if (a[2] == 4) {
          sum -= 10000;
        } else if (a[1] == 3 && a[2] == 0) {
          sum += 1000;
        } else if (a[1] == 2 && a[2] == 0) {
          sum += 10;
        } else if (a[2] == 3 && a[1] == 0) {
          sum -= 1000;
        } else if (a[2] == 2 && a[1] == 0) {
          sum -= 10;
        }   
        a = new int[3];
      
      }
      int d = 3;
    }
    
    //this for loop scans each horizontal set of four starting with the [0][0] and then [0][1]
    //until [0][4] and then [1][0]
    for (int i = 0; i <= 7; i++) {
      for (int j = 0; j <= 4; j++) {
        for (int x = j; x < j + 4; x++) { //reversesd signs
          ++a[b[i][x]];
        }
        if (a[1] == 4) {
          sum += 10000;
        } else if (a[2] == 4) {
          sum -= 10000;
        } else if (a[1] == 3 && a[2] == 0) {
          sum += 1000;
        } else if (a[1] == 2 && a[2] == 0) {
          sum += 10;
        } else if (a[2] == 3 && a[1] == 0) {
          sum -= 1000;
        } else if (a[2] == 2 && a[1] == 0) {
          sum -= 10;
        }   
        a = new int[3];
        
      }
    }
    
    //this for loop scans each \ pair of four starting with the [0][0] and then [1][0]
    //until [4][0] and then [0][1]    
    for (int i = 0; i <= 4; i++) {
      for (int j = 0; j <= 4; j++) {
        int incrementedShift = i;
        for (int x = j; x < j + 4; x++) { //reversesd signs
          ++a[b[x][incrementedShift++]];  
        }
        if (a[1] == 4) {
          sum += 10000;
        } else if (a[2] == 4) {
          sum -= 10000;
        } else if (a[1] == 3 && a[2] == 0) {
          sum += 1000;
        } else if (a[1] == 2 && a[2] == 0) {
          sum += 10;
        } else if (a[2] == 3 && a[1] == 0) {
          sum -= 1000;
        } else if (a[2] == 2 && a[1] == 0) {
          sum -= 10;
        }   
        a = new int[3];
        
      }
    }
      
    
    //this for loop scans each / pair of four starting with the [0][3] and then [1][3]
    //until [4][3] and then [0][1]    
    for (int i = 3; i <= 7; i++) {
      for (int j = 0; j <= 4; j++) {
        int incrementedShift = i;
        for (int x = j; x < j + 4; x++) { //reversesd signs
          ++a[b[x][incrementedShift--]];  
        }
        if (a[1] == 4) {
          sum += 10000;
        } else if (a[2] == 4) {
          sum -= 10000;
        } else if (a[1] == 3 && a[2] == 0) {
          sum += 1000;
        } else if (a[1] == 2 && a[2] == 0) {
          sum += 10;
        } else if (a[2] == 3 && a[1] == 0) {
          sum -= 1000;
        } else if (a[2] == 2 && a[1] == 0) {
          sum -= 10;
        }   
        a = new int[3];
        
      }
    }
    
    
    return sum; 
  }
  
  
  public static int move(int[][] b, int player) {  
    return minMax(b, player, 0, alpha, beta);    
  }
    
    
  public static int minMax(int[][] b, int player, int depth, int alpha, int beta) { // ad alpha and beta
    
    
    //alpa min bebta min
    
    int max = Integer.MIN_VALUE;
    int min = Integer.MAX_VALUE;
    int bestCol = -1;
    int bestScore = 0;
    
    if (depth == maxDepth){
      return eval(b);
    } else {
      for (int col = 0; col < 8; ++ col) {
        
        int row = figureRow(b, col);
        if (row == -1) {
        } else {
          b[row][col] = player;
          int temp = minMax(b, player% 2 + 1, depth + 1, alpha, beta);// commpare this instance
          b[row][col] = 0;
          
          if (player == 1) {
            if (temp > max) {                
              max = temp;
              bestCol = col;
              bestScore = max; // if max > alpha alpha = max
              if(max > alpha) {
                  alpha = max;
                }
                if (beta < alpha) {
                  break;
                }
            } //if beta less than alpha
          } else {
            if (temp < min) {
              min = temp;
              bestCol = col; 
              bestScore = min;
              if(min > beta) {
                  beta = min;
                }
                if (beta < alpha) {
                  break;
                }
          
          }//if betta < alpha
        }
      }
    }
      //System.out.println("bestcol = " + bestCol);
      
      if (depth == 0) {
            return bestCol; 
          } else {
            return bestScore;
          }
    }
  }
      
  
  /*public static void main(String[] args) {
    
    int[][] matrix = {
      { 0, 0, 0, 0, 0, 0, 0, 0},
      { 0, 0, 0, 0, 0, 0, 0, 0},
      { 0, 0, 0, 0, 0, 0, 0, 0},
      { 0, 0, 0, 0, 0, 0, 0, 0},
      { 0, 0, 0, 0, 0, 0, 0, 0},
      { 0, 0, 0, 0, 0, 0, 0, 0},
      { 0, 0, 0, 0, 0, 0, 0, 0},
      { 0, 0, 0, 0, 0, 0, 0, 0},
    };
     // int[][] board = new int [8] [8];
    
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        board[i][j] = 1;
      }
    }
    
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        System.out.print(matrix[i][j] + " ");
      }
      System.out.print("\n");
    }
    
  //  int temp = move(matrix, 1);
    //System.out.println(temp);
   
    int player = 1;
    while (true) {
    Scanner input = new Scanner(System.in);

    
    
    if (player == 1)  {
     
    
    int move = input.nextInt();
    int row = figureRow(matrix, move);
    
    matrix[row][move] = player;
    
      
    } else {
     int aiMove = move(matrix, player);
        int aiRow = figureRow(matrix, aiMove);

        matrix[aiRow][aiMove] = player;

    }
   player = player % 2 + 1;
   
   for (int i = 0; i < 8; i++) {
       for (int j = 0; j < 8; j++) {
         System.out.print(matrix[i][j] + " ");
       }
       System.out.print("\n");
     }
    
  }
  
  }*/
  
  

}
    