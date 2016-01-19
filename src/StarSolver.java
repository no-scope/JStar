import java.util.*;

/*
 * This is the A* search implemetation
 *
 * It's a pretty basic A* search with the
 * difference that it doesnt create states
 * itself but uses the already created and initialized
 * Vertex[][] storing the gscores and the parents in
 * the corresponding fields of each Vertex.
 *
 * We have implemented the solve method to reach either
 * the goal vertex  or any of its neighbours in case
 * a robot reach the goal so the other
 * just has to reach a neighbour of the goal.
 */
public class StarSolver
{
	/*
	 * The standard comparator change uCost to oCost if
	 * you want to use overestimating heurestic.
	 */
	private Comparator<Vertex> gCompare = new Comparator<Vertex>()
	{
		@Override
		public int compare(Vertex v1, Vertex v2) {
			return (v1.gScore+v1.uCost(goalX, goalY) -
					(v2.gScore+v2.uCost(goalX, goalY)));
		}
	};

	/*
	 * The comparator in case we have a set of goals
	 * to reach.
	 * We just substract 1 of the standard heurestic cost
	 * so that it remains underestimation of the real cost
	 * regardless of the neighbour of the goal we are
	 * calculating the cost.
	 */
	private Comparator<Vertex> gSetCompare = new Comparator<Vertex>()
	{
		@Override
		public int compare(Vertex v1, Vertex v2) {
			return (v1.gScore+v1.uCost(goalX, goalY) -
					(v2.gScore+v2.uCost(goalX, goalY)) - 1);
		}
	};


	public static int xMax, yMax, goalX, goalY;
	Vertex[][] map;
	private int statesSum = 0;

	private PriorityQueue<Vertex> openQueue
		= new PriorityQueue<Vertex>(10, gCompare);
	private PriorityQueue<Vertex> openSetQueue
		= new PriorityQueue<Vertex>(10, gSetCompare);
	private HashSet<Vertex> exploredSet
		= new HashSet<Vertex>();

	public StarSolver(int xM,int yM, Vertex[][] grid)
	{
		xMax = xM;
		yMax = yM;
		map = grid;
	}

	/*
	 * The Solver for neighbour goalset.
	 */
	public Vertex solve(Vertex start, Vertex goal,
			Vertex other, boolean reachNeighbour)
	{
		PriorityQueue<Vertex> queue  = openQueue;
		goalX = goal.x;
		goalY = goal.y;

		/*
		 * Refresh the Vertex map between each call of the solve()
		 * method.
		 */
		for (int i = 0; i < xMax ; i++){
			for(int j = 0; j < yMax ; j++){
				map[i][j].parent = null;
				map[i][j].gScore = 0;
			}
		}

		if (reachNeighbour)
			queue = openSetQueue;
		else
			queue = openQueue;

		/* Clear the Queues and the Sets */
		openQueue.clear();
		openSetQueue.clear();
		exploredSet.clear();
		queue.add(start);

		/* Go ! */
		while (!queue.isEmpty()) {

			/* Get the Vertex with min gscore */
			Vertex curr = queue.remove();

			if ((curr.isNeighbour(goal) && reachNeighbour) ||
					curr.equals(goal))
				return curr.validMove();
			if (curr.equals(other)){
				/* DEBUG */
				//System.out.print("Detected Possible Conflict at : ");
				//curr.printme();
				continue;
			}

			exploredSet.add(curr);

			/* DEBUG */
			//curr.printme();

			for (Vertex tmp : curr.neighbourList) {
				int gSc = curr.gScore + 1;

				if ((exploredSet.contains(tmp)) && (gSc < tmp.gScore))
					exploredSet.remove(tmp);

				if ((queue.contains(tmp)) && (gSc < tmp.gScore))
					queue.remove(tmp);

				if ((!openQueue.contains(tmp)) && (!exploredSet.contains(tmp))) {
					tmp.setParent(curr);
					tmp.gScore = gSc;
					queue.add(tmp);
					statesSum++;
				}
			}
		}
		return null;
	}

	public int getNumberOfStates()
	{
		return statesSum;
	}

}
