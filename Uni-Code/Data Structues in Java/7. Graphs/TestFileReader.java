import java.io.IOException;
import java.util.Arrays;

public class TestFileReader {
    public static void main(String[] args) {
        FileReader fr = new FileReader();

        try {
            fr.readFile("5.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[][] numbers = fr.getFileArray();
        String[] number = numbers[0];
        System.out.println("How many lines: " + fr.getLines("5.txt"));
        System.out.println("Values of rows separated by commas : " + Arrays.deepToString(numbers));
        System.out.println("Length of height : " + numbers.length);
        System.out.println("Length of width : " + number.length);
        System.out.println("Printing full table : ");
        /*for(int x = 0; x<numbers.length; x++)
        {
            for(int i = 0; i<numbers[x].length; i++)
            {
                System.out.print("| " + numbers[x][i] + " ");
            }
            System.out.println("|" + "\r");
        }*/
        for(int x = 0; x<numbers.length; x++)
        {
            for(int i = 0; i<numbers[x].length; i++)
            {   
                if(i < numbers[x].length - 1){
                    System.out.print(numbers[x][i] + ",");
                }else{
                    System.out.print(numbers[x][i]);
                }    
            }
            System.out.println("\r");
        }
    }
}