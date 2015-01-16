import java.util.*;

public class Robot
{
    public int id;
    public int goalX, goalY;
    public Vertex current;
    public ArrayList<Vertex> path;

    public Robot(int initX,int initY, int goalX, int goalY, int id)
    {
        this.goalX = goalX;
        this.goalY = goalY;
        this.id = id;
        this.path = new ArrayList<Vertex>();
    }

    public Vertex checkPath(ArrayList<Vertex> otherPath)
    {

	Iterator<Vertex> iter1 = path.iterator();
	Iterator<Vertex> iter2 = otherPath.iterator();
	int collisionStep = 0;
	while (iter1.hasNext() && iter2.hasNext()){
		Vertex tmp1 = iter1.next();
		Vertex tmp2 = iter2.next();
		if (tmp1.equals(tmp2))
			return tmp1;
		collisionStep++;
	}
	return null;

    }

    public boolean checkFinish(Robot other)
    {
        return  current.isNeighbor(other.current);
    }
}
