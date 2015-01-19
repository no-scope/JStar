import java.util.ArrayList;
import java.lang.Math;

/* Vertex class
 *
 * In each vertex we store the gscore and the parent
 * vertex so it also serves as a state in the A* search
 *
 * We have reduced memory usage as we only create
 * Xmax * Ymax states/vertexes regardless of the A*
 * search (We don't create states during the A* search).
 */
public class Vertex
{
	public int x, y, gScore;
	public Vertex parent;
	public ArrayList <Vertex> neighbourList;
	char type ;

	public Vertex(int xCord, int yCord , char type)
	{
		x = xCord;
		y = yCord;
		gScore = 0;
		parent = null;
		this.type = type;
	}

	public void setParent(Vertex par)
	{
		parent = par;
	}

	public void printme()
	{
		System.out.println("X:" + x + " Y:" + y);
	}

	public boolean equals(Vertex vert)
	{
		return ((x == vert.x)&&(y == vert.y));
	}

	/*
	 * Creates ALL possible neighbours of a vertex
	 * and puts them in the field neighbourList.
	 * Should be called only once for each vertex
	 * to save memory and to have a consistent grid
	 */
	public void findNeighbours(int maxX, int maxY, Vertex[][] vMap)
	{
		neighbourList = new ArrayList<Vertex>();

		if ((x-1 >= 0) && (vMap[x-1][y].type == 'O'))
			neighbourList.add(vMap[x-1][y]);
		if ((x+1 < maxX) && (vMap[x+1][y].type == 'O'))
			neighbourList.add(vMap[x+1][y]);
		if ((y-1 >= 0) && (vMap[x][y-1].type == 'O'))
			neighbourList.add(vMap[x][y-1]);
		if ((y+1 < maxY) && (vMap[x][y+1].type == 'O'))
			neighbourList.add(vMap[x][y+1]);
	}

	/*
	 * Gives the path from a this vertex back to the
	 * start.
	 */
	public Path path()
	{
		Path vertList= new Path();

		Vertex curr = this;
		while (curr != null){
			vertList.add(curr);
			curr = curr.parent;
		}

		return vertList;
	}

	/*
	 * Gives the second node of a vertex path
	 *
	 * Suppose we have the path
	 * [0][0] [0][1] [1][1]
	 * it will return [0][1] which is the first node
	 * we should visit to follow the path to this
	 * vertex.
	 */
	public Vertex validMove()
	{
		Vertex curr = this;
		if ((curr != null) && (curr.parent != null)){
			Vertex next = curr.parent;
			while (next.parent != null) {
				curr = curr.parent;
				next = next.parent;
			}
		}
		return curr;
	}

	/*
	 * Manhattan distance heurestic (underestimate)
	 */
	public int uCost(int xGoal, int yGoal)
	{
		return Math.abs(x - xGoal) + Math.abs(y - yGoal);
	}

	/*
	 * Overestimating heurestic 2 * max(xDist, yDist)
	 */
	public int oCost(int xGoal, int yGoal)
	{
		return 3 * Math.max(Math.abs(x - xGoal), Math.abs(y - yGoal));
	}

	public boolean isNeighbour(Vertex other)
	{
		return this.neighbourList.contains(other);
	}

}
