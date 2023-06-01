import java.util.*;
public class SolverTest
{
    public static void main(String[] args)
    {
        try
        {
            EquationSolver solve = new EquationSolver();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter equation");
            String equation = sc.nextLine();

            //Print out equation it will try
            System.out.println("Trying " + equation);

            DSAQueue solution = solve.solve(equation);

            do{
                System.out.println(solution.remove());
            }while(solution.isEmpty() == false);
            //Prints solution
            //System.out.println("Solution is : " + solution);
        }
        catch(IllegalArgumentException e)
        {
            System.out.println(e.getMessage());
        }
    }
}