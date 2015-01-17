import java.util.Arrays;
import java.util.ArrayList;
import java.util.*;

public class Sol
{

	static int xMax, yMax;
	static Vertex[][] myMap;

	private static Vertex checkPath(Path path1, Path path2)
	{

		Collections.reverse(path1);
		Collections.reverse(path2);
		Iterator<Vertex> iter1 = path1.iterator();
		Iterator<Vertex> iter2 = path2.iterator();

		while (iter1.hasNext() && iter2.hasNext()) {
			Vertex tmp1 = iter1.next();
			Vertex tmp2 = iter2.next();
			if (tmp1.equality(tmp2))
				return tmp1;

		}
		return null;
	}


	private static void printPaths(Path[] paths)
	{
		System.out.println("__PATHS__");
		if (paths[0] != null)
			for (Vertex tmp : paths[0])
				myMap[tmp.x][tmp.y].type = '1';
		if (paths[1] != null)
			for (Vertex tmp : paths[1])
				myMap[tmp.x][tmp.y].type = '2';

		for (int i=0;i<xMax; i++) {
			for (int j=0; j<yMax; j++) {
				if(myMap[i][j].type == '1') {
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
		if (paths[0] != null)
			for (Vertex tmp : paths[0])
				myMap[tmp.x][tmp.y].type = 'O' ;
		if (paths[1] != null)
			for (Vertex tmp : paths[1])
				myMap[tmp.x][tmp.y].type = 'O' ;

	}

	private static Path findAlter(Path staticPath, Path initPath,
			Vertex init, ArrayList<Vertex> collSet,
			HashSet<Vertex> goalSet, StarSolver zol)
	{
		Path alterPath = initPath;

		Vertex collisionVertex = checkPath(staticPath, initPath);
		while ((collisionVertex != null) && (alterPath != null)) {
			System.out.print("__COLLISION__");
			collisionVertex.printme();
			collSet.add(collisionVertex);
			collisionVertex.type = 'C';
			alterPath = zol.solve(init, goalSet);

			/* no point for a path > init +2 */
			if (alterPath.size() >= initPath.size() + 2) {
				alterPath = null;
				break;
			}

			collisionVertex = checkPath(staticPath, alterPath);
		}

		/* make all collision vertexes again accessible */
		for (Vertex tmp : collSet)
			myMap[tmp.x][tmp.y].type = 'O';

		/* clear the set of collision vertexes */
		collSet.clear();

		/* return null if no good enough alt path was found */
		return alterPath;
	}

	private static Path[] robotSolver(Vertex initR1,
			Vertex initR2, Vertex goal)
	{
		Path[] paths = new Path[2];
		StarSolver zol = new StarSolver(xMax, yMax, myMap);
		HashSet<Vertex> goalSet = new HashSet<Vertex>();
		ArrayList<Vertex> collSet = new ArrayList<Vertex>();

		zol.setGoal(goal);

		goalSet.add(goal);
		paths[0] = zol.solve(initR1, goalSet);
		goalSet.remove(goal);
		for (Vertex tmp : goal.neighbourList)
			goalSet.add(tmp);
		paths[1] = zol.solve(initR2, goalSet);

		Path altPaths[] = new Path[2];
		altPaths[1] = findAlter(paths[0], paths[1],
				initR2, collSet, goalSet, zol);
		goalSet.clear();
		goalSet.add(goal);
		altPaths[0] = findAlter(paths[1], paths[0],
				initR1, collSet, goalSet, zol);


		printPaths(altPaths);

		//if (altPaths[0] != null)
		//	paths[0] = altPaths[0];
		//else if (altPaths[1] != null)
		//	paths[1] = altPaths[1];

		return paths;
	}

	public static void main(String[] args)
	{

		Parser pars = new Parser(args[0]);
		myMap = pars.getVertexMap();
		int[] inputs = pars.getInputInts();
		xMax = inputs[1];
		yMax = inputs[0];
		Path[] paths = robotSolver(myMap[4][7],
				myMap[0][11], myMap[6][12]);
		printPaths(paths);
	}

}

