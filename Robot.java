public class Robot
{
	private StarSolver search;
	private Vertex position;
	private Vertex goal;

	public Robot(int xMax, int yMax, Vertex pos, Vertex goal, Vertex[][] myMap)
	{
		search = new StarSolver(xMax, yMax, myMap);
		position = pos ;
		this.goal = goal;
	}

	public Vertex getPos()
	{
		return position;
	}

	private Vertex follow(Vertex other)
	{
		Vertex ret = search.solve(position, other, other, true);

		if (ret == null){
			return position;
		}
		return ret;
	}

	public void moveAndAvoid(Vertex other)
	{
		Vertex tmp;

		if (other.equals(goal))
			position = search.solve(position, goal, other, true);

		else {
			tmp = search.solve(position, goal, other, false);
			if (tmp == null)
				position = follow(other);
			else
				position = tmp;
		}
	}
}
