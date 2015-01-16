import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.ArrayList;

public class StarSolver
{
	private Comparator<Vertex> gCompare = new Comparator<Vertex>()
	{
		@Override
		public int compare(Vertex v1, Vertex v2) {
			return (v1.gScore+v1.uCost(goalX, goalY) -
					(v2.gScore+v2.uCost(goalX, goalY)));
		}
	};


	public static int xMax, yMax, goalX, goalY;
	Vertex[][] map;
	private PriorityQueue<Vertex> openQueue
		= new PriorityQueue<Vertex>(10, gCompare);
	private ArrayList<Vertex> exploredSet = new ArrayList<Vertex>();

	public StarSolver(int xM,int yM, Vertex[][] grid)
	{
		xMax = xM;
		yMax = yM;
		map = grid;
	}

	public void setGoal(int goalX, int goalY)
	{
		this.goalX = goalX;
		this.goalY = goalY;
	}

	public ArrayList<Vertex> solver(int initX, int initY)
	{
		Vertex start = map[initX][initY];
		Vertex goal = map[goalX][goalY];
		for (int i=0; i<xMax ; i++)
			for(int j=0; j<yMax ; j++)
				map[i][j].parent = null;

		openQueue.clear();
		exploredSet.clear();
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
