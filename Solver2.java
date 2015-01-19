import java.util.*;

public class Solver2
{

	static int xMax, yMax;
	static Vertex[][] myMap;

	/*
	 * Prints the two paths in the same Gridmap
	 * with different colours with some Bash
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
				if (myMap[tmp.x][tmp.y].type == '1')
					myMap[tmp.x][tmp.y].type = '3';
				else
					myMap[tmp.x][tmp.y].type = '2';


		for (int i=0;i<xMax; i++) {
			for (int j=0; j<yMax; j++) {
				if(myMap[i][j].type == '1') {
					System.out.print("\033[31m" + 'O');
					System.out.print("\033[0m");
				}
				else if (myMap[i][j].type =='2') {
					System.out.print("\033[33m" + 'O');
					System.out.print("\033[0m");
				}
				else if (myMap[i][j].type =='3') {
					System.out.print("\033[35m" + 'O');
					System.out.print("\033[0m");
				}

				else if (myMap[i][j].type =='X') {
					System.out.print("\033[34m" + 'X');
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

		Robot r0 = new Robot(xMax, yMax, initR1, goal, myMap);
		Robot r1 = new Robot(xMax, yMax, initR2, goal, myMap);

		Path[] paths = new Path[2];
		Path path0 = new Path();
		Path path1 = new Path();

		path0.add(initR1);
		path1.add(initR2);

		while(!solved(r0.getPos(), r1.getPos(), goal)) {

			r0.moveAndAvoid(r1.getPos());
			r1.moveAndAvoid(r0.getPos());
			path0.add(r0.getPos());
			path1.add(r1.getPos());
		}
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

		Robot r0 = new Robot(xMax, yMax, initR1, goal, myMap);
		Robot r1 = new Robot(xMax, yMax, initR2, goal, myMap);

		Path[] paths = new Path[2];
		Path path0 = new Path();
		Path path1 = new Path();

		path0.add(initR1);
		path1.add(initR2);

		while(!solved(r0.getPos(), r1.getPos(), goal)) {

			r0.moveAndAvoid(r1.getPos());
			r1.moveAndAvoid(r0.getPos());
			path0.add(r0.getPos());
			path1.add(r1.getPos());

			/* A time interval to distinguish the steps */
			try {
				Thread.sleep(200);
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

