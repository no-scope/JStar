import java.util.Arrays;
import java.util.ArrayList;
import java.util.*;

public class DummySolver
{

	static int xMax, yMax;
	static Vertex[][] myMap;

	/*
	 * Return the first collision vertex of path1 and path2
	 * or null if no collision is happening
	 */
	private static Vertex checkPath(Path path1, Path path2)
	{

		Collections.reverse(path1);
		Collections.reverse(path2);
		Iterator<Vertex> iter1 = path1.iterator();
		Iterator<Vertex> iter2 = path2.iterator();

		while (iter1.hasNext() && iter2.hasNext()) {
			Vertex tmp1 = iter1.next();
			Vertex tmp2 = iter2.next();
			if (tmp1.equals(tmp2))
				return tmp1;

		}
		return null;
	}

	/*
	 * Prints the two paths in the same Gridmap
	 * with different colours with some ASCII
	 * escape char magic
	 */
	public static void printPaths(Path[] paths)
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

	private static boolean solved(Vertex pos1, Vertex pos2, Vertex goal)
	{
		if ((pos1.equals(goal) && pos1.isNeighbour(pos2)) ||
				(pos2.equals(goal) && pos2.isNeighbour(pos1)))
			return true ;

		return false;
	}

	private static Vertex follow (Vertex posR1, Vertex posR2, StarSolver zol)
	{
		Vertex ret = zol.solve(posR1, posR2, true);

		if (ret == null)
			return posR1;

		return ret;

	}

	/*
	 * Avoid Collision algorithm
	 * We use A* search for each move of each robot
	 * After say Robot1 moves to a vertex we mark this
	 * with 'C' which is ignored (as an obstacle) by
	 * the A* algorithm.
	 * If there is no alternative move the robot is
	 * stalling
	 */
	private static Path[] robotSolver(Vertex initR1,
			Vertex initR2, Vertex goal)
	{
		Path[] paths = new Path[2];
		Path path0 = new Path();
		Path path1 = new Path();

		path0.add(initR1);
		path1.add(initR2);

		StarSolver zol = new StarSolver(xMax, yMax, myMap);

		Vertex posR1 = initR1;
		Vertex posR2 = initR2;
		Vertex tmp ;

		while(!solved(posR1, posR2, goal)) {

			if (posR1.equals(goal)) {
				posR2 = zol.solve(posR2, goal, true);
				path1.add(posR2);
			}
			else if (posR2.equals(goal)) {
				posR1  = zol.solve(posR1, goal, true);
				path0.add(posR1);
			}
			else {
				tmp = zol.solve(posR1, goal, false);
				if (tmp == null)
					posR1 = follow(posR1, posR2, zol);
				else
					posR1 = tmp;

				posR1.type = 'C';
				posR2.type = 'O';

				tmp = zol.solve(posR2, goal, false);
				if (tmp == null)
					posR2 = follow(posR2, posR1, zol);
				else
					posR2 = tmp;

				posR2.type = 'C';
				posR1.type = 'O';

				path0.add(posR1);
				path1.add(posR2);
			}
		}
		System.out.println("Total Number of States = " + zol.getNumberOfStates());
		paths[0] = path0;
		paths[1] = path1;
		return paths;
	}

	/*
	 * The same as robotSolver but prints it step
	 * by step for demo and debug purposes
	 */
	private static void robotSteps(Vertex initR1,
			Vertex initR2, Vertex goal)
	{
		final String ANSI_CLS = "\u001b[2J";
		final String ANSI_HOME = "\u001b[H";
		StarSolver zol = new StarSolver(xMax, yMax, myMap);
		Path[] paths = new Path[2];
		Path path0 = new Path();
		Path path1 = new Path();

		path0.add(initR1);
		path1.add(initR2);

		Vertex posR1 = initR1;
		Vertex posR2 = initR2;
		Vertex tmp ;

		while(!solved(posR1, posR2, goal)) {

			if (posR1.equals(goal)) {
				posR2 = zol.solve(posR2, goal, true);
				path1.add(posR2);
			}
			else if (posR2.equals(goal)) {
				posR1  = zol.solve(posR1, goal, true);
				path0.add(posR1);
			}
			else {
				tmp = zol.solve(posR1, goal, false);
				if (tmp == null)
					posR1 = follow(posR1, posR2, zol);
				else
					posR1 = tmp;

				posR1.type = 'C';
				posR2.type = 'O';

				tmp = zol.solve(posR2, goal, false);
				if (tmp == null)
					posR2 = follow(posR2, posR1, zol);
				else
					posR2 = tmp;

				posR2.type = 'C';
				posR1.type = 'O';

				path0.add(posR1);
				path1.add(posR2);
			}

			/* A time interval to distinguish the steps */
			try {
				Thread.sleep(1000);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			/* refresh terminal screen */
			System.out.print(ANSI_CLS + ANSI_HOME);
			System.out.flush();
			paths[0] = path0;
			paths[1] = path1;

			/* print new gridmap */
			printPaths(paths);
		}
	}

	public static void main(String[] args)
	{

		Parser pars = new Parser(args[0]);
		myMap = pars.getVertexMap();
		int[] inputs = pars.getInputInts();
		xMax = inputs[0];
		yMax = inputs[1];
		Vertex initR1 = myMap[inputs[2]][inputs[3]];
		Vertex initR2 = myMap[inputs[4]][inputs[5]];
		Vertex goal   = myMap[inputs[6]][inputs[7]];

		//robotSteps(initR1,initR2, goal);
		printPaths(robotSolver(initR1,initR2,goal));
	}
}

