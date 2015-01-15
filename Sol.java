import java.util.Arrays;
import java.util.ArrayList;
import java.util.*;

public class Sol{
    public static void main(String[] args){

        Parser eleni = new Parser(args[0]);

        /* BACKWARDS PATH DEMO */
        /*
           Vertex vert = new Vertex(-1, -1);
           Vertex v2 = null;
           for (int i = 0 ; i < 40  ; i += 2) {
           v2 = new Vertex(i, i+1);
           v2.setParent(vert);
           vert = v2;
           }
           ArrayList<Vertex> list = v2.path();


           Iterator iterator = list.iterator();
           while(iterator.hasNext()){
           Vertex tmp = (Vertex) iterator.next();
           tmp.printme();
           }
           */
        Vertex[][] myMap = eleni.getVertexMap();
        int[] inputs = eleni.getInputInts();
        /*
           for(int i=0;i<inputs[1];i++) {
           for (int j=0;j<inputs[0];j++) {
           myMap[i][j].printme();
           System.out.println("_________________START_____________________");
           Iterator iterator = myMap[i][j].neighbourList.iterator();
           while(iterator.hasNext()){
           Vertex tmp = (Vertex) iterator.next();
           tmp.printme();

           }
           System.out.println("_________________END_NEIGHBOURS_____________________");
           }
           }
           */

        StarSolver meSol = new StarSolver(inputs[1],inputs[0], 9, 13, 9, 2, myMap);

        ArrayList<Vertex> list = meSol.solver();
        Iterator<Vertex> iterator = list.iterator();

        int num = 0;
        System.out.println("__PATH__");
        while(iterator.hasNext()){
            Vertex tmp = iterator.next();
            myMap[tmp.x][tmp.y].type = 'R';
            num++;
        }

        for(int i=0;i<13;i++){
            for(int j=0;j<20;j++){
                if(myMap[i][j].type == 'R'){
                    System.out.print("\033[31m" + 'O');
                    System.out.print("\033[0m");
                }
                else
                    System.out.print(myMap[i][j].type);
            }
            System.out.println();
        }
        System.out.println("Path Length:" + (num-1));

    }

}

