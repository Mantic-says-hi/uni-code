import java.util.*;
public class Towers
{
    public static void main (String[] args)
    {
        System.out.println("\n     Welcome to the 3 peg towers of Hanoi\n\n" + 
                           "Please enter the number of disks to start at peg one.\n");

        Scanner sc = new Scanner(System.in);
        int n = 1;
        n = sc.nextInt();
        System.out.println("\n");
        tower(n, 1, 3);

        System.out.println("\nProblem solved.");
    }


    public static void tower(int n, int src, int dest)
    {
        int tmp;

        if(n == 1){
            moveDisk(src, dest);
        }else {
            tmp = 6 - src - dest;
            tower(n-1 ,src, tmp);

            moveDisk(src, dest);
            tower(n-1, tmp, dest);
        }
    }

    public static void moveDisk(int src, int dest)
    {
        System.out.println("Moving top disk from peg " + src + 
                           " to peg " + dest + "...");
    }
}