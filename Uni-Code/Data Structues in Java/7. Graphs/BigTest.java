import java.io.IOException;

public class BigTest {

    public static void main(String[] args) throws IOException
    {
        FileReader fio = new FileReader();
        fio.readFile("prac6_1.txt");//Loading file 
        String[][] file = fio.getFileArray();
        Graph g = new Graph();
        
        System.out.println("Adding data");

        for(int i = 0; i < file.length; i++)
        {
            g.addVertex(file[i][0], "entry " + (i + 1));

        }
        for(int i = 0; i < file.length; i++)
        {
            g.addVertex(file[i][1], "entry " + (i + 1));

        }

        for(int i = 0; i < file.length; i++)
        {

            g.addEdge(file[i][0],file[i][1] );
        }

        g.matrixOut();

        g.listOut();

        g.depthWSearch("A");
        System.out.println();
        g.breathWSearch("F");

       
    }
}