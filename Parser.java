import java.io.*;
import java.util.Scanner;

/* Just a simple parser class */
public class Parser
{
	static private int[] inputInts;
	static private char[][] map;

	/*
	 * Class Constructor creates the int array and
	 * the char grid
	 */
	public Parser(String fil)
	{
		try {
			char ch;
			int i;
			String str;

			BufferedReader in =
				new BufferedReader(new FileReader(fil));
			Scanner a = new Scanner(in);
			inputInts = new int[8];

			for (i = 0; i < 8; i++)
				inputInts[i] = a.nextInt();

			map = new char [inputInts[0]][inputInts[1]];


			for (i = 0; i < inputInts[0]; i++) {
				str = a.next();
				map[i] = str.toCharArray();
			}
			in.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int[] getInputInts()
	{
		return inputInts;
	}

	public char[][] getMap()
	{
		return map;
	}

	/*
	 * Creates the Vertex Map from the char map
	 * It initializes all Vertexes and their lists of
	 * neighbours to save time and space
	 */
	public Vertex[][] getVertexMap()
	{
		Vertex[][] vMap = new Vertex[inputInts[0]][inputInts[1]];
		for (int i = 0; i < inputInts[0]; i++)
			for (int j = 0; j < inputInts[1]; j++)
				vMap[i][j] = new Vertex(i, j, map[i][j]);

		for (int i = 0; i < inputInts[0]; i++)
			for (int j = 0; j < inputInts[1]; j++)
				vMap[i][j].findNeighbours(inputInts[0], inputInts[1], vMap);

		return vMap;
	}
}
