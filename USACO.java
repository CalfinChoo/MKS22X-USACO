
import java.util.*;
import java.io.*;
public class USACO{
  public static void main(String[] args) {
    //System.out.println(bronze("makelake.in"));
    //System.out.println(silver("ctravel.in"));
  }
  public static int bronze(String filename) {
    //  initializes variables to store future values
    int R = 0, C = 0, E = 0, N = 0;
    int[][] map = new int[0][0];
    ArrayList<Stomp> instructs = new ArrayList<Stomp>();

    //  reads the file and stores values
    try {
      File f = new File(filename);
      Scanner in = new Scanner(f);
      int line = 0;
      while(in.hasNextLine()) {
        if (line == 0) {
          Scanner s = new Scanner(in.nextLine());
          int index = 0;
          while (s.hasNextInt()) {
            if (index == 0) R = s.nextInt();
            if (index == 1) C = s.nextInt();
            if (index == 2) E = s.nextInt();
            if (index == 3) N = s.nextInt();
            index++;
          }
          map = new int[R][C];
        }
        else if (line > 0 && line < R + 1) {
          Scanner s = new Scanner(in.nextLine());
          int row = line - 1;
          int col = 0;
          while (s.hasNextInt()) {
            map[row][col] = s.nextInt();
            col++;
          }
        }
        else {
          Scanner s = new Scanner(in.nextLine());
          int r = 0, c = 0, d = 0, index = 0;
          while (s.hasNextInt()) {
            if (index == 0) r = s.nextInt();
            if (index == 1) c = s.nextInt();
            if (index == 2) d = s.nextInt();
            index++;
          }
          instructs.add(new Stomp(r, c, d));
        }
        line++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return 0;
    }

    //  performs stomp instructions
    for (int i = 0; i < N; i++) {
      Stomp ins = instructs.get(i);
      int threshold = 0;
      for (int r = ins.row - 1; r < ins.row + 2; r++) {
        for (int c = ins.col - 1; c < ins.col + 2; c++) {
          if (map[r][c] > threshold) threshold = map[r][c];
        }
      }
      threshold -= ins.depth;
      for (int r = ins.row - 1; r < ins.row + 2; r++) {
        for (int c = ins.col - 1; c < ins.col + 2; c++) {
          if (map[r][c] < threshold) map[r][c] = map[r][c];
          else if (map[r][c] - ins.depth < threshold) map[r][c] = threshold;
          else map[r][c] -= ins.depth;
        }
      }
    }

    //  converts elevations into final depths
    for (int r = 0; r < map.length; r++) {
      for (int c = 0; c < map[r].length; c++) {
        if (E - map[r][c] <= 0) map[r][c] = 0;
        else map[r][c] = E - map[r][c];
      }
    }

    //  finds total aggregated depths
    int totalDepth = 0;
    for (int r = 0; r < map.length; r++) {
      for (int c = 0; c < map[r].length; c++) {
        if (map[r][c] > 0) totalDepth += map[r][c];
      }
    }

    //  returns volume
    return totalDepth * 5184;
  }
  public static int silver(String filename) {
    //  initializes variables to store future values
    int N = 0, M = 0, T = 0, R1 = 0, C1 = 0, R2 = 0, C2 = 0;
    char[][] map = new char[0][0];

    //  reads the file and stores values
    try {
      File f = new File(filename);
      Scanner in = new Scanner(f);
      int line = 0;
      while (in.hasNextLine()) {
        if (line == 0) {
          Scanner s = new Scanner(in.nextLine());
          int index = 0;
          while (s.hasNextInt()) {
            if (index == 0) N = s.nextInt();
            if (index == 1) M = s.nextInt();
            if (index == 2) T = s.nextInt();
            index++;
          }
          map = new char[N][M];
        }
        if (line > 0 && line < N + 1) {
          String s = in.nextLine();
          int index = 0;
          for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) != ' ') {
              map[line - 1][index] = s.charAt(i);
              index++;
            }
          }
        }
        if (line == N + 1) {
          Scanner s = new Scanner(in.nextLine());
          int index = 0;
          while (s.hasNextInt()) {
            if (index == 0) R1 = s.nextInt() - 1;
            if (index == 1) C1 = s.nextInt() - 1;
            if (index == 2) R2 = s.nextInt() - 1;
            if (index == 3) C2 = s.nextInt() - 1;
            index++;
          }
        }
        line++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return 0;
    }

    //  calls on helper function to return answer
    return silverSolve(map, T, R1, C1, R2, C2);
  }
  //  helper function makes a new 2D array of Tiles the same size as the map, initializes it with values,
  //  and loops thru number of T to set the values of adjacent Tiles;  Returns the value at (R2, C2)
  private static int silverSolve(char[][] map, int time, int sRow, int sCol, int eRow, int eCol) {
    int[][] moves = {{1,0}, {-1,0}, {0,1}, {0,-1}};
    Tile[][] optimizedBoard = new Tile[map.length][map[0].length];
    for (int r = 0; r < optimizedBoard.length; r++) {
      for (int c = 0; c < optimizedBoard[r].length; c++) {
        if (map[r][c] == '*') optimizedBoard[r][c] = new Tile(-1, -1);
        else optimizedBoard[r][c] = new Tile(0, 0);
      }
    }
    optimizedBoard[sRow][sCol] = new Tile(1, 0);
    for (int counter = time; counter > 0; counter--) {
      for (int r = 0; r < optimizedBoard.length; r++) {
        for (int c = 0; c < optimizedBoard[r].length; c++) {
          if (optimizedBoard[r][c].dom > 0) {
            for (int i = 0; i < moves.length; i++) {
              int row = r + moves[i][0];
              int col = c + moves[i][1];
              if (row >= 0 && row < optimizedBoard.length && col >= 0 && col < optimizedBoard[r].length) {
                if (optimizedBoard[row][col].dom != -1) optimizedBoard[row][col].setHid(optimizedBoard[r][c].dom);
              }
            }

          }
        }
      }
      for (int r = 0; r < optimizedBoard.length; r++) {
        for (int c = 0; c < optimizedBoard[r].length; c++) {
          optimizedBoard[r][c].dom = optimizedBoard[r][c].hid;
          if (optimizedBoard[r][c].hid != -1) optimizedBoard[r][c].hid = 0;
        }
      }
    }
    return optimizedBoard[eRow][eCol].dom;
  }

}

//  helper class to store values easier
class Stomp {
  int row, col, depth;
  public Stomp(int r, int c, int d) {
    row = r;
    col = c;
    depth = d;
  }
}
//  helper class to store dominant and hidden values easier; switch between dominant and hidden is made in silverSolve's T loop itself
class Tile {
  int dom, hid;
  public Tile(int dominant, int hidden) {
    dom = dominant;
    hid = hidden;
  }
  public void setHid(int value) {
    hid += value;
  }
}
