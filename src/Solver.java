import java.util.*;

public class Solver
{
	static int xMax, yMax;
	static Vertex[][] myMap;

	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_RESET = "\u001B[0m";

	public static final Map<Character, String> codes =
	new HashMap<Character, String>() {{
		/* Unnamed init */
		put('1', ANSI_RED + 'O' + ANSI_RESET);
		put('2', ANSI_YELLOW + 'O' + ANSI_RESET);
		put('3', ANSI_BLUE + 'O' + ANSI_RESET);
		put('4', ANSI_PURPLE + 'X' + ANSI_RESET);
		put('O', "O");
		put('X', "X");
	}};

	/*
	 * Shows the whole map using 
	 * designated colourcodes 
	 */
	public static void showMap()
	{
		for (int i = 0; i < xMax; i++) {
			for (int j = 0; j < yMax; j++)
				System.out.print(codes.get(myMap[i][j].type));
		
			System.out.println();
		}
	}

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

		showMap();
		
		if (paths[0] != null)
			for (Vertex tmp : paths[0])
				myMap[tmp.x][tmp.y].type = 'O';
		if (paths[1] != null)
			for (Vertex tmp : paths[1])
				myMap[tmp.x][tmp.y].type = 'O';

	}

	/*
	 * less fancy printing method without bash escape
	 * seqs just outputs a list of the path vertexes
	 */
	private static void simplePrint(Path path)
	{
		for (Vertex tmp : path)
			tmp.printme();
	}

	private static boolean solved(Vertex pos1, Vertex pos2, Vertex goal)
	{
		return ((pos1.equals(goal) && pos1.isNeighbour(pos2)) ||
				(pos2.equals(goal) && pos2.isNeighbour(pos1)));
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

		Path path0 = new Path();
		Path path1 = new Path();

		path0.add(initR1);
		path1.add(initR2);

		/* use 2 vertices to avoid multiple method calls */
		Vertex r0_pos = r0.getPos();
		Vertex r1_pos = r1.getPos();

		while (!solved(r0_pos, r1_pos, goal)) {
			r0.moveAndAvoid(r1_pos);
			r0_pos = r0.getPos();
			r1.moveAndAvoid(r0_pos);
			r1_pos = r1.getPos();
			path0.add(r0_pos);
			path1.add(r1_pos);
		}

		return (new Path[] {path0, path1});
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

		Path path0 = new Path();
		Path path1 = new Path();

		path0.add(initR1);
		path1.add(initR2);

		Vertex p0 = r0.getPos();
		Vertex p1 = r1.getPos();

		while(!solved(p0, p1, goal)) {

			r0.moveAndAvoid(p1);
			p0 = r0.getPos();

			r1.moveAndAvoid(p0);
			p1 = r1.getPos();

			path0.add(p0);
			path1.add(p1);

			/* A time interval to distinguish the steps */
			try {
				Thread.sleep(200);
			} catch(InterruptedException ex) {
				Thread.currentThread().interrupt();
			}

			/* refresh terminal screen */
			System.out.print(ANSI_CLS + ANSI_HOME);
			System.out.flush();

			/* print new gridmap */
			printPaths(new Path[] {path0, path1});

		}
	}

	public static void main(String[] args)
	{
		if (args.length == 2) {
			Parser pars = new Parser(args[0]);
			myMap = pars.getVertexMap();
			int[] inputs = pars.getInputInts();
			xMax = inputs[0];
			yMax = inputs[1];
			Vertex initR1 = myMap[inputs[2]][inputs[3]];
			Vertex initR2 = myMap[inputs[4]][inputs[5]];
			Vertex goal   = myMap[inputs[6]][inputs[7]];

			if (args[1].equals("simple")) {
				Path[] ansPaths = new Path[2];
				ansPaths = robotSolver(initR1,initR2,goal);
				System.out.println("__Path of ROBOT1__");
				simplePrint(ansPaths[0]);
				System.out.println("__Path of ROBOT2__");
				simplePrint(ansPaths[1]);
			} else if (args[1].equals("map")) {
				printPaths(robotSolver(initR1,initR2,goal));
			} else if (args[1].equals("step")) {
				robotSteps(initR1,initR2,goal);
			} else {
				System.out.println("Wrong option");
				System.out.println("Available options : [simple|map|step]");
			}
		} else {
			System.out.println("Usage: java Solver input_file option");
			System.out.println("Available options : [simple|map|step]");
		}
	}
}
