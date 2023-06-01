import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;


public class EquationSolver
{
    public DSAQueue solve(String equation)
    {
        double solution;
        DSAQueue queue;

        queue = parseInfixToPostfix(equation);

        //solution = evaluatePostfix(queue);

        return queue;
    }

    private DSAQueue parseInfixToPostfix(String equation)
    {
        DSAQueue postFix = new DSAQueue();
        DSAStack opStack = new DSAStack();
        String[] inFix = toInfix(equation);
        int i = 0;

        while(i < inFix.length)
        {
            Object term = parseNextTerm(inFix[i]);

            if(term != null){

                if((term instanceof Character) && (Character)term == '('){

                    opStack.push('(');
                }else if((term instanceof Character) && (Character)term == ')'){

                    while((Character) opStack.top() != '(')
                    {
                        postFix.add(opStack.pop());
                    }
                    opStack.pop();
                 }else if(term instanceof Character && ((((Character)term).charValue() == '+') ||
                          (((Character)term).charValue()  == '-')||(((Character)term).charValue()  == '*')
                          || (((Character)term).charValue()  == '/')) ){
                        
                    while((!opStack.isEmpty()) && ((Character) opStack.top() != '(') &&
                         (precedenceOf((Character) opStack.top()) >= precedenceOf((Character)term)))
                    {
                        postFix.add(opStack.pop());
                    }
                    opStack.push((Character)term);
                }else{
                    postFix.add((Double)term);
                }
            }
            i++;
        }

        while(!opStack.isEmpty()){
            postFix.add(opStack.pop());
        }
       

        return postFix;
    }

    private Object parseNextTerm(String inFix)
    {
        Object term;

        if(inFix.equals("+") || inFix.equals("-") ||
           inFix.equals("/") || inFix.equals("*") ||
           inFix.equals("(") || inFix.equals(")") ){
            Character operator = inFix.charAt(0);
            term = operator;
        }else{
            Double operand = Double.parseDouble(inFix);
            term = operand;
        }
       
        return term;
    }    

    private double evaluatePostfix(DSAQueue postfixQueue)
    {
        double answer;
        DSAStack stack = new DSAStack();

        while(!postfixQueue.isEmpty()){

            if(postfixQueue.front() instanceof Double){

                    Double operand = (Double) postfixQueue.remove();
                    stack.push(operand);
            }else if(postfixQueue.front() instanceof Character){

                    char operator = ((Character) postfixQueue.remove()).charValue();
                    double op2 = ((Double) stack.pop()).doubleValue();
                    double op1 = ((Double) stack.pop()).doubleValue();
                    Double evaluation = Double.valueOf(executeOperation(operator, op1, op2));
                    stack.push(evaluation);
            }
        }

        answer = ((Double) stack.pop()).doubleValue();

        return answer;

    }

    //Helper for parseInfixToPostfix
    private int precedenceOf(char theOperator)
    {
        int precedence = 0;

        if(theOperator == '+' || theOperator == '-'){
            precedence = 1;
        }else if(theOperator == '*' || theOperator == '/'){
            precedence = 2;
        }

        return precedence;
    }

    //Helper for evaluatePostfix
    private double executeOperation(char op, double op1, double op2)
    {
        double evaluation = 0;

        if(op == '+'){

            evaluation = op1 + op2;
        }else if(op == '-'){

            evaluation = op1 - op2;
        }else if(op == '*'){

            evaluation = op1 * op2;
        }else if(op == '/'){

            evaluation = op1 / op2;
        }

        return evaluation;
    }

    private String[] toInfix(String equation)
    {
        equation = equation.replaceAll("\\s", "");
        String[] inFix = equation.split("(?=[-+*/()])|(?<=[^-+*/][-+*/])|(?<=[()])");

        return inFix;
    }

}