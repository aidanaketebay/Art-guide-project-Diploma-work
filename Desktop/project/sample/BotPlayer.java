
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class BotPlayer implements Player{

    private int[][] map;
    private boolean[][] graph;
    private boolean[] visited;

    @Override
    public void moveRight() {

    }

    @Override
    public void moveLeft() {

    }

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public Position getPosition() {
        return null;
    }


    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public String searchPath(int v, int w) {
        Queue<Integer> q = new LinkedList<Integer>();
        boolean[] visited = new boolean[this.map[0].length * this.map[0].length];
        String[] pathTo = new String[this.map[0].length * this.map[0].length];

        q.add(v);
        pathTo[v] = v+" ";
        while(q.peek() != null) {
            if(runBFS(q.poll(),w,visited,q,pathTo))
                break;
        }
        return pathTo[w];
    }

    private boolean runBFS(int v, int w, boolean[] visited, Queue<Integer> q, String[] pathTo) {
        int vertexNum = this.map[0].length * this.map[0].length;
        if (visited[v]) {
        } else if (v == w)
            return true;
        else {
            visited[v] = true;
            for (int nv = 0; nv < vertexNum; ++nv){
                if (!visited[nv] && graph[v][nv]) {
                    pathTo[nv] = pathTo[v] + nv + " ";
                    q.add(nv);
                }
            }
        }
        return false;
    }

    public void setGraph() {

        int dimension = this.map[0].length;
        System.out.println(dimension);
        this.graph = new boolean[dimension * dimension][dimension * dimension];

        this.visited = new boolean[dimension * dimension];
        Arrays.fill(this.visited, false);

        for (int j = 0; j < dimension; ++j) {
            for (int i = 0; i < dimension; ++i) {
                if (i - 1 >= 0 && this.map[i - 1][j] != 1) {
                    this.graph[i * dimension + j][(i - 1) * dimension + j] = true;
                    this.graph[(i - 1) * dimension + j][i * dimension + j] = true;
                }

                if (j - 1 >= 0 && this.map[i][j - 1] != 1) {
                    this.graph[i * dimension + j][i * dimension + j - 1] = true;
                    this.graph[i * dimension + j - 1][i * dimension + j] = true;
                }

                System.out.println(i + 1);
                if (i + 1 < dimension && this.map[i + 1][j] != 1) {
                    this.graph[i * dimension + j][(i + 1) * dimension + j] = true;
                    this.graph[(i + 1) * dimension + j][i * dimension + j] = true;
                }

                if (j + 1 < dimension && this.map[i][j + 1] != 1) {
                    this.graph[i * dimension + j][i * dimension + j + 1] = true;
                    this.graph[i * dimension + j + 1][i * dimension + j] = true;
                }
            }
        }
    }

    public String[] makePathReadable(String path) {

        String[] ans = new String[path.split(" ").length];
        int index = -1;
        for (String temp : path.split(" ")) {
            if (temp == null)
                break;
            ans[++index] = temp;
        }

        int cur = Integer.parseInt(ans[0]);
        System.out.println(Arrays.toString(ans));
        for (int i = 1; i < ans.length; ++i) {
            int next = Integer.parseInt(ans[i]);
            if (next < cur) {
                if (next == cur - 1) {
                    ans[i - 1] = "LEFT";
                }
                else
                    ans[i - 1] = "UP";
            }
            else if (next > cur) {
                if (next == cur + 1) {
                    ans[i - 1] = "RIGHT";
                }
                else
                    ans[i - 1] = "DOWN";
            }
            cur = next;
        }
        return ans;
    }

}
