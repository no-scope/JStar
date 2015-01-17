import java.util.ArrayList;
import java.lang.Math;

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

    public boolean equality(Vertex vert)
    {
        return ((x == vert.x)&&(y == vert.y));
    }

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

    public int uCost(int xGoal, int yGoal)
    {
        return Math.abs(x - xGoal) + Math.abs(y - yGoal);
    }

    public int oCost(int xGoal, int yGoal)
    {
        return 2 * Math.max(Math.abs(x - xGoal), Math.abs(y - yGoal));
    }

    public boolean isNeighbor(Vertex other)
    {
        if ((x-1 >= 0) && (other.x + 1 == x) && (other.y == y))
            return true;
        else if ((x+1 < StarSolver.xMax) && (other.x - 1 == x) && (other.y == y))
            return true;
        else if ((y-1 >= 0) && (other.y + 1 == y) && (other.x == x))
            return true;
        else if ((y+1 < StarSolver.yMax) && (other.y -1 == y) && (other.x == x))
            return true;
        else
            return false;
    }

}
