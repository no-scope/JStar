import java.io.*;
import java.util.Scanner;

public class Parser
{
    static int[] inputInts;
    static char[][] map;

    public Parser(String fil)
    {
        try {
            char ch;
            BufferedReader in =
                new BufferedReader(new FileReader(fil));
            int i;
            Scanner a = new Scanner(in);
            inputInts = new int[8];

            for (i = 0; i < 8; i++)
                inputInts[i] = a.nextInt();

            map = new char [inputInts[1]][inputInts[0]];

            String str;

            for (i = 0; i < inputInts[1]; i++){
                str = a.next();
                map[i] = str.toCharArray();
            }
            in.close();
        }
        catch (Exception e){
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

    public Vertex[][] getVertexMap()
    {
        Vertex[][] vMap = new Vertex[inputInts[1]][inputInts[0]];
        for (int i = 0; i < inputInts[1]; i++)
            for (int j = 0; j < inputInts[0]; j++)
                vMap[i][j] = new Vertex(i, j, map[i][j]);

        for (int i = 0; i < inputInts[1]; i++)
            for (int j = 0; j < inputInts[0]; j++)
                vMap[i][j].findNeighbours(inputInts[1], inputInts[0], vMap);
        return vMap;
    }


}
