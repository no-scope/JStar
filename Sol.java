import java.util.Arrays;
import java.util.ArrayList;
import java.util.*;

public class Sol{

	static int xMax, yMax;

	private static Vertex checkPath(Path path1, Path path2)
	{

		Collections.reverse(path1);
		Collections.reverse(path2);
		Iterator<Vertex> iter1 = path1.iterator();
		Iterator<Vertex> iter2 = path2.iterator();
		while (iter1.hasNext() && iter2.hasNext()){
			Vertex tmp1 = iter1.next();
			Vertex tmp2 = iter2.next();
			if (tmp1.equality(tmp2)){
				return tmp1;
			}
		}
		return null;
	}


	private static void printPaths(Path[] paths, Vertex myMap[][])
	{
		System.out.println("__PATHS__");
		for (Vertex tmp : paths[0])
			myMap[tmp.x][tmp.y].type = '1';

		for (Vertex tmp : paths[1])
			myMap[tmp.x][tmp.y].type = '2';

		for(int i=0;i<13;i++){
			for(int j=0;j<20;j++){
				if(myMap[i][j].type == '1'){
					System.out.print("\033[31m" + 'O');
					System.out.print("\033[0m");
				}
				else if (myMap[i][j].type =='2') {
					System.out.print("\033[35m" + 'O');
					System.out.print("\033[0m");
				}
				else
					System.out.print(myMap[i][j].type);
			}
			System.out.println();
		}
	}

	private static Path findAlter(,
			Vertex init, HashSet<Vertex> goalSet)
	{
		ArrayList<Vertex> collSet = new ArrayList<Vertex>();
		Path alterPath = new Path;
		alterPath = oriPath;
		while ((collisionVertex != null) && (alterPath != null)) {
			collSet.add(collisionVertex);
			alterPath = zol.solve(init, goalSet);

			if (alterPath.size() > oriPath.size() + 2)
				return null;
			collisionVertex = checkPath(oriPath, alterPaths);
		}
		return alterPath;
	}

	private static Path[] robotSolver(Vertex initR1, Vertex initR2,
			Vertex goal, Vertex[][] myMap)
	{
		Path[] paths = new Path[2];
		StarSolver zol = new StarSolver(xMax, yMax, myMap);
		HashSet<Vertex> goalSet = new HashSet<Vertex>();
		zol.setGoal(goal);

		goalSet.add(goal);
		paths[0] = zol.solve(initR1, goalSet);
		goalSet.remove(goal);
		for (Vertex tmp : goal.neighbourList)
			goalSet.add(tmp);

		paths[1] = zol.solve(initR2, goalSet);
		Path altPaths[] = new Path[2];


		altPaths[0] = paths[0];

		if ((altPaths[0] != null) && (altPath.size() < paths[1].size() +2 ))
			paths[1] = altPath;

		return paths;
	}

	public static void main(String[] args){

		Parser pars = new Parser(args[0]);
		Vertex[][] myMap = pars.getVertexMap();
		int[] inputs = pars.getInputInts();
		xMax = inputs[1];
		yMax = inputs[0];
		Path[] paths = robotSolver(myMap[4][8], myMap[1][11], myMap[6][12], myMap);
		printPaths(paths, myMap);
	}

}

