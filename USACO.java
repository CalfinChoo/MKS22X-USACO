
import java.util.*;
import java.io.*;
public class USACO{
  public static void main(String[] args) {
    System.out.println(bronze("makelake.in"));
    System.out.println(silver("ctravel.in"));
  }
  public static int bronze(String filename) {
    int R = 0, C = 0, E = 0, N = 0;
    int[][] map = new int[0][0];
    ArrayList<Stomp> instructs = new ArrayList<Stomp>();
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

    for (int r = 0; r < map.length; r++) {
      for (int c = 0; c < map[r].length; c++) {
        if (E - map[r][c] <= 0) map[r][c] = 0;
        else map[r][c] = E - map[r][c];
      }
    }

    int totalDepth = 0;
    for (int r = 0; r < map.length; r++) {
      for (int c = 0; c < map[r].length; c++) {
        if (map[r][c] > 0) totalDepth += map[r][c];
      }
    }

    return totalDepth * 5184;
  }
  public static int silver(String filename) {
    int N = 0, M = 0, T = 0, R1 = 0, C1 = 0, R2 = 0, C2 = 0;
    char[][] map = new char[0][0];
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
            if (index == 0) R1 = s.nextInt();
            if (index == 1) C1 = s.nextInt();
            if (index == 2) R2 = s.nextInt();
            if (index == 3) C2 = s.nextInt();
            index++;
          }
        }
        line++;
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
      return 0;
    }

    for (int r = 0; r < map.length; r++) {
      for (int c = 0; c < map[r].length; c++) {
        System.out.print(map[r][c] + " ");
      }
      System.out.println();
    }
    return 0;
  }
}
class Stomp {
  int row, col, depth;
  public Stomp(int r, int c, int d) {
    row = r;
    col = c;
    depth = d;
  }
}
