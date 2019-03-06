
import java.util.*;
import java.io.*;
public class USACO{
  public static int bronze(String filename) {
    File f = new File(filename);
    Scanner in = new Scanner(f);
    int R = 0, C = 0, E = 0, N = 0;
    int[][] map;
    ArrayList<Stomp> instructs = new ArrayList<Stomp>();

    int line = 0;
    while(in.hasNext()) {
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
      if (line > 0 && line < R + 1) {
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
        int r = 0, c = 0, d = 0;
        while (s.hasNextInt()) {
          if (index == 0) r = in.nextInt();
          if (index == 1) c = in.nextInt();
          else d = in.nextInt();
        }
        instructs.add(new Stomp());
      }
    }
  }
  public static int silver(String filename) {
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
