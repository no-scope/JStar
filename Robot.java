import java.util.ArrayList;

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

    }

    public goTo(Vertex pos)
    public void checkFinish(Robot other)
    {
        return  current.isNeighbor(other.current);
    }
}
