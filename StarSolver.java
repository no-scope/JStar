import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class StarSolver
{
    public static int xMax, yMax;
    public int initX, initY, goalX, goalY;
    Vertex[][] map;

    public StarSolver(int xM,int yM, int inX, int inY,
            int gX, int gY, Vertex[][] grid)
    {
        xMax = xM;
        yMax = yM;
        initX = inX;
        initY = inY;
        goalX = gX;
        goalY = gY;
        map = grid;
    }

    private Comparator<Vertex> gCompare = new Comparator<Vertex>()
    {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            return (v1.gScore+v1.uCost(goalX, goalY) -
                    (v2.gScore+v2.uCost(goalX, goalY)));
        }
    };

    public ArrayList<Vertex> solver()
    {
        Vertex start = map[initX][initY];
        Vertex goal = map[goalX][goalY];

        PriorityQueue<Vertex> openQueue = new PriorityQueue<Vertex>(100, gCompare);
        ArrayList<Vertex> exploredSet = new ArrayList<Vertex>();

        openQueue.add(start);
        int num = 0;

        while (!openQueue.isEmpty()) {

            Vertex curr = openQueue.remove();
            if (curr.equals(goal)){
                System.out.println("NUMBER OF ITERS " +num);
                return curr.path();
            }
            exploredSet.add(curr);

            for (Vertex tmp : curr.neighbourList) {
                num++;
                int gSc = curr.gScore + 1;

                if ((exploredSet.contains(tmp)) && (gSc < tmp.gScore)) {
                    exploredSet.remove(tmp);
                }

                if ((openQueue.contains(tmp)) && (gSc < tmp.gScore)) {
                    openQueue.remove(tmp);
                }

                if ((!openQueue.contains(tmp)) && (!exploredSet.contains(tmp))) {
                    tmp.setParent(curr);
                    tmp.gScore = gSc;
                    openQueue.add(tmp);
                }
            }
        }
        return null;
    }
}
