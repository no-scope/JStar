public class Robot
{
	private StarSolver search;
	private Vertex position;
	private Vertex goal;

	/*
	 * states number this robot produced during
	 * his A* searches that we will use for the graphs
	 */
	private int statesNum = 0;

	public Robot(int xMax, int yMax, Vertex pos, Vertex goal, Vertex[][] myMap)
	{
		search = new StarSolver(xMax, yMax, myMap);
		position = pos;
		this.goal = goal;
	}

	public Vertex getPos()
	{
		return position;
	}

	public int getStates()
	{
		return search.getNumberOfStates();
	}

	private Vertex follow(Vertex other)
	{
		Vertex ret = search.solve(position, other, other, true);

		return (ret == null ? position : ret);
	}

	public void moveAndAvoid(Vertex other)
	{
		Vertex tmp;

		if (other.equals(goal)) {
			position = search.solve(position, goal, other, true);
		} else {
			tmp = search.solve(position, goal, other, false);
			position = (tmp == null ? follow(other) : tmp);
		}
	}
}
