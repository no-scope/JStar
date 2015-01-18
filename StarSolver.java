import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.*;

public class StarSolver
{
	private Comparator<Vertex> gCompare = new Comparator<Vertex>()
	{
		@Override
		public int compare(Vertex v1, Vertex v2) {
			return(v1.gScore+v1.uCost(goalX, goalY) -
					(v2.gScore+v2.uCost(goalX, goalY)));
		}
	};

	private Comparator<Vertex> gSetCompare = new Comparator<Vertex>()
	{
		@Override
		public int compare(Vertex v1, Vertex v2) {
			return(v1.gScore+v1.uCost(goalX, goalY) -
					(v2.gScore+v2.uCost(goalX, goalY)) - 1);
		}
	};


	public static int xMax, yMax, goalX, goalY;
	Vertex[][] map;
	private PriorityQueue<Vertex> openQueue
		= new PriorityQueue<Vertex>(10, gCompare);
	private PriorityQueue<Vertex> openSetQueue
		= new PriorityQueue<Vertex>(10, gSetCompare);
	private HashSet<Vertex> exploredSet = new HashSet<Vertex>();

	public StarSolver(int xM,int yM, Vertex[][] grid)
	{
		xMax = xM;
		yMax = yM;
		map = grid;
	}

	public Vertex solve(Vertex start, Vertex goal, ArrayList<Vertex> goalSet)
	{
		goalX = goal.x;
		goalY = goal.y;

		for (int i = 0; i < xMax ; i++){
			for(int j = 0; j < yMax ; j++){
				map[i][j].parent = null;
				map[i][j].gScore = 0;
			}
		}

		openSetQueue.clear();
		exploredSet.clear();
		openSetQueue.add(start);
		int num = 0;

		while (!openSetQueue.isEmpty()) {

			Vertex curr = openSetQueue.remove();
			if (goalSet.contains(curr)){
				return curr.validMove();
			}
			exploredSet.add(curr);

			for (Vertex tmp : curr.neighbourList) {
				num++;
				int gSc = curr.gScore + 1;

				if (tmp.type == 'C')
					continue;

				if ((exploredSet.contains(tmp)) && (gSc < tmp.gScore)) {
					exploredSet.remove(tmp);
				}

				if ((openSetQueue.contains(tmp)) && (gSc < tmp.gScore)) {
					openSetQueue.remove(tmp);
				}

				if ((!openSetQueue.contains(tmp)) && (!exploredSet.contains(tmp))) {
					tmp.setParent(curr);
					tmp.gScore = gSc;
					openSetQueue.add(tmp);
				}
			}
		}
		return null;
	}

	public Vertex solve(Vertex start, Vertex goal)
	{
		goalX = goal.x;
		goalY = goal.y;
		for (int i = 0; i < xMax ; i++){
			for(int j = 0; j < yMax ; j++){
				map[i][j].parent = null;
				map[i][j].gScore = 0;
			}
		}

		openQueue.clear();
		exploredSet.clear();
		openQueue.add(start);
		int num = 0;

		while (!openQueue.isEmpty()) {

			Vertex curr = openQueue.remove();
			if (curr.equals(goal)){
				return curr.validMove();
			}
			exploredSet.add(curr);

			for (Vertex tmp : curr.neighbourList) {
				num++;
				int gSc = curr.gScore + 1;

				if (tmp.type == 'C')
					continue;

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
