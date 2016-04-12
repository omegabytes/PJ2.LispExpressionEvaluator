/************************************************************************************
 *
 *  		CSC220 Programming Project#2
 *
 * Specification:
 *
 * Taken from Project 7, Chapter 5, Page 178
 * I have modified specification and requirements of this project
 *
 * Ref: http://www.gigamonkeys.com/book/        (see chap. 10)
 *      http://joeganley.com/code/jslisp.html   (GUI)
 *
 * In the language Lisp, each of the four basic arithmetic operators appears
 * before an arbitrary number of operands, which are separated by spaces.
 * The resulting expression is enclosed in parentheses. The operators behave
 * as follows:
 *
 * (+ a b c ...) returns the sum of all the operands, and (+ a) returns a.
 *
 * (- a b c ...) returns a - b - c - ..., and (- a) returns -a.
 *
 * (* a b c ...) returns the product of all the operands, and (* a) returns a.
 *
 * (/ a b c ...) returns a / b / c / ..., and (/ a) returns 1/a.
 *
 * Note: + * - / must have at least one operand
 *
 * You can form larger arithmetic expressions by combining these basic
 * expressions using a fully parenthesized prefix notation.
 * For example, the following is a valid Lisp expression:
 *
 * 	(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ 1))
 *
 * This expression is evaluated successively as follows:
 *
 *	(+ (- 6) (* 2 3 4) (/ 3 1 -2) (+ 1))
 *	(+ -6 24 -1.5 1.0)
 *	17.5
 *
 * Requirements:
 *
 * - Design and implement an algorithm that uses LinkedStack class to evaluate a
 *   valid Lisp expression composed of the four basic operators and integer values.
 * - Valid tokens in an expression are '(',')','+','-','*','/',and positive integers (>=0)
 * - Display result as floating point number with at 2 decimal places
 * - Negative number is not a valid "input" operand, e.g. (+ -2 3)
 *   However, you may create a negative number using parentheses, e.g. (+ (-2)3)
 * - There may be any number of blank spaces, >= 0, in between tokens
 *   Thus, the following expressions are valid:
 *   	(+   (-6)3)
 *   	(/(+20 30))
 *
 * - Must use LinkedStack class in this project. (*** DO NOT USE Java API Stack class ***)
 * - Must throw LispExpressionException to indicate errors
 * - Must not add new or modify existing data fields
 * - Must implement these methods in LispExpressionEvaluator class:
 *
 *   	public LispExpressionEvaluator()
 *   	public LispExpressionEvaluator(String inputExpression)
 *      public void reset(String inputExpression)
 *      public double evaluate()
 *      private void evaluateCurrentOperation()
 *
 * - You may add new private methods
 *
 *************************************************************************************/

import java.util.*;

public class LispExpressionEvaluator
{
    // Current input Lisp expression
    private String currentExpr;

    // Main expression stack, see algorithm in evaluate()
    private LinkedStack<Object> tokensStack;
    private LinkedStack<Double> currentOpStack;


    // default constructor
    // set currentExpr to ""
    // create LinkedStack objects
    public LispExpressionEvaluator()
    {
        // add statements
        currentExpr = "";
        tokensStack = new LinkedStack<Object>();
        currentOpStack = new LinkedStack<Double>();
    }

    // constructor with an input expression
    // set currentExpr to inputExpression
    // create LinkedStack objects
    public LispExpressionEvaluator(String inputExpression)
    {
        // add statements
        if (inputExpression == null) {
            throw new LispExpressionException("Input expression was null. (Constructor error)");
        }
        currentExpr = inputExpression;
        tokensStack = new LinkedStack<Object>();
        currentOpStack = new LinkedStack<Double>();
    }

    // set currentExpr to inputExpression
    // clear stack objects
    public void reset(String inputExpression)
    {
        // add statements
        if (inputExpression == null) {
            throw new LispExpressionException("Input expression was null. (reset() error)");
        }
        currentExpr = inputExpression;
        tokensStack.clear();
        currentOpStack.clear();
    }

    // This function evaluates current operator with its operands
    // See complete algorithm in evaluate()
    //
    // Main Steps:
    // 		Pop operands from tokensStack and push them onto
    // 			currentOpStack until you find an operator
    //  	Apply the operator to the operands on currentOpStack
    //          Push the result into tokensStack
    //
    private void evaluateCurrentOperation()
    {
        // add statements
        Object currentOperation;                  // stores the operation popped from  tokenStack AEG
        Double result = 0.0;                      // stores the algebraic result of the expression AEG
        //Boolean operand = true;

        if (tokensStack.empty()){
            throw new LispExpressionException("The token stack is empty, nothing to evaluate.");
        }

        currentOperation = tokensStack.pop();

        while (currentOperation instanceof String ) {
            try {
                double value = Double.parseDouble((String) currentOperation);
                currentOpStack.push(value);
            } catch (NumberFormatException nfe) {
                System.out.println(nfe.getMessage());
            }
            if(tokensStack.empty() ){
                throw new LispExpressionException("Operator doesn't exist or can't be found.");
            }
            currentOperation = tokensStack.pop();
        }


        //do{ // try using instanceOf
        //    currentOperation = String.valueOf(tokensStack.pop());
        //    try {
        //        Double number = Double.parseDouble(currentOperation);
        //        currentOpStack.push(number);
        //    }catch (NumberFormatException nfe){ //can I use NFE?
        //        operand = false;
        //    }
        //} while(operand);


        //while(!operand){
        //    currentOperation = String.valueOf(tokensStack.pop());
        //
        //    if (currentOperation instanceof Number) {
        //        currentOpStack.push(String.currentOperation);
        //    } else {
        //        operand = true;
        //    }
        //}

        String aToken = currentOperation.toString() ;
        char item = aToken.charAt(0);

        // use switch statements to evaluate the expression
        switch (item) {
            case '+':
                //result = currentOpStack.pop();
                result = 0.0;

                while (!currentOpStack.empty()) {
                    result += currentOpStack.pop();
                }
                tokensStack.push(String.valueOf(result));
                break;
            case '-':
                result = 0.0;
                result = currentOpStack.pop();

                if (currentOpStack.empty()) {
                    result = -result;
                    tokensStack.push(String.valueOf(result));
                } else {
                    while(!currentOpStack.empty()){
                        result -= currentOpStack.pop();
                    }
                    tokensStack.push(String.valueOf(result));
                }

                //result = 0.0;
                //result = currentOpStack.pop();
                //
                //if (currentOpStack.empty()){
                //    result = -result;
                //    tokensStack.push(String.valueOf(result));
                //}
                //
                ////if (currentOpStack.size() == 1 && result == 0.0) {
                ////    result = -1.0 * currentOpStack.pop();
                ////}
                //    //result = currentOpStack.pop();
                //
                //    while (!currentOpStack.empty()) {
                //        result -= currentOpStack.pop();
                //    }
                //tokensStack.push(String.valueOf(result));
                break;
            case '/':
                result = 1.0;
                result = currentOpStack.pop();
                if (currentOpStack.empty()) {
                    if (result == 0) {
                        throw new LispExpressionException("Cannot divide by zero");
                    }
                    result = 1 / result;
                    tokensStack.push(String.valueOf(result));
                } else {
                    //result = currentOpStack.pop();
                    while (!currentOpStack.empty()) {
                        if (currentOpStack.peek() == 0) {
                            throw new LispExpressionException("cannot divide by zero");
                        }
                        result /= currentOpStack.pop();
                    }
                    tokensStack.push(String.valueOf(result));
                }
                break;
            case '*':
                //result = currentOpStack.pop();
                result = 1.0;
                while (!currentOpStack.empty()) {
                    result *= currentOpStack.pop();
                }
                tokensStack.push(String.valueOf(result));
                break;
            default:
                throw new LispExpressionException(item + " not a legal operator");
        }
        //tokensStack.push(result); // I'm pushing result = null onto the tokenStack here. Why?
    }



    /**
     * This function evaluates current Lisp expression in currentExpr
     * It return result of the expression
     *
     * The algorithm:
     *
     * Step 1   Scan the tokens in the string.
     * Step 2		If you see an operand, push operand object onto the tokensStack
     * Step 3  	    	If you see "(", next token should be an operator
     * Step 4  		If you see an operator, push operator object onto the tokensStack
     * Step 5		If you see ")"  // steps in evaluateCurrentOperation() :
     * Step 6			Pop operands and push them onto currentOpStack
     * 					until you find an operator
     * Step 7			Apply the operator to the operands on currentOpStack
     * Step 8			Push the result into tokensStack
     * Step 9    If you run out of tokens, the value on the top of tokensStack is
     *           is the result of the expression.
     */
    public double evaluate()
    {
        //boolean open = true;
        //int openCount = 0;
        // only outline is given...
        // you need to add statements/local variables
        // you may delete or modify any statements in this method

        // use scanner to tokenize currentExpr
        Scanner currentExprScanner = new Scanner(currentExpr);

        // Use zero or more white space as delimiter,
        // which breaks the string into single character tokens
        currentExprScanner = currentExprScanner.useDelimiter("\\s*"); // this is regex \ 'special char' \s 'classical way stands for space' * 'wildcard, can repeat 0+ times'

        // Step 1: Scan the tokens in the string.
        while (currentExprScanner.hasNext())
        {

            // Step 2: If you see an operand, push operand object onto the tokensStack
            if (currentExprScanner.hasNextInt())
            {
                // This force scanner to grab all of the digits
                // Otherwise, it will just get one char
                String dataString = currentExprScanner.findInLine("\\d+"); //regex / one or more digits

                // more ...
                tokensStack.push(dataString);
            }
            else
            {
                try {
                    // Get next token, only one char in string token
                    String aToken = currentExprScanner.next();
                    //System.out.println("Other: " + aToken);
                    char item = aToken.charAt(0);

                    switch (item) {
                        // Step 3: If you see "(", next token should be an operator
                        case '(':
                            //open = true;
                            //openCount++;
                            aToken = currentExprScanner.next();
                            item = aToken.charAt(0);

                            switch (item) {
                                case '+':
                                case '-':
                                case '/':
                                case '*':
                                    tokensStack.push(item);
                                    break;
                                default:
                                    throw new LispExpressionException(item + " not a legal operator");
                            }
                            break;
                        // Step 5: If you see ")"  // steps in evaluateCurrentOperation() :
                        case ')':
                            //if (open) {
                            //    open = false;
                            //    openCount--;
                            //    evaluateCurrentOperation();
                            //    break;
                            //} else if(!open && openCount != 0) {
                            //    throw new LispExpressionException("The expression is unbalanced, check parentheses (error 2)");
                            //} else {
                            //    throw new LispExpressionException("The expression is unbalanced, check parentheses");
                            //}
                            //
                            // else
                            // throw exception
                            //
                            //if (currentOpStack.empty()){
                            //    throw new LispExpressionException("Expression is unbalanced, nothing to evaluate.");
                            //}
                            evaluateCurrentOperation();
                            break;
                        default:  // error
                            throw new LispExpressionException(item + " is not a legal expression operator");
                    } // end switch
                }catch (LispExpressionException e) {
                    System.out.println("Exception: " + e.getMessage());
                }
            } // end else
        } // end while

        // Step 9: If you run out of tokens, the value on the top of tokensStack is
        //         is the result of the expression.
        //
        //         return result

        return Double.parseDouble(String.valueOf(tokensStack.pop())); // return result
    }


    //=====================================================================
    // DO NOT MODIFY ANY STATEMENTS BELOW
    // Quick test is defined in main()
    //=====================================================================

    // This static method is used by main() only
    private static void evaluateExprTest(String s, LispExpressionEvaluator expr, String expect)
    {
        Double result;
        System.out.println("Expression " + s);
        System.out.printf("Expected result : %s\n", expect);
        expr.reset(s);
        try {
            result = expr.evaluate();
            System.out.printf("Evaluated result : %.2f\n", result);
        }
        catch (LispExpressionException e) {
            System.out.println("Evaluated result :"+e);
        }

        System.out.println("-----------------------------");
    }

    // define few test cases, exception may happen
    public static void main (String args[])
    {
        LispExpressionEvaluator expr= new LispExpressionEvaluator();
        //expr.setDebug();
        String test1 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ 1))";
        String test2 = "(+ (- 632) (* 21 3 4) (/ (+ 32) (* 1) (- 21 3 1)) (+ 0))";
        String test3 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 1) (- 2 1 ))(* 1))";
        String test4 = "(+ (/2)(+ 1))";
        String test5 = "(+ (/2 3 0))";
        String test6 = "(+ (/ 2) (* 2) (/ (+ 1) (+ 3) (- 2 1 ))))";
        String test7 = "(+ (*))";
        String test8 = "(+ (- 6) (* 2 3 4) (/ (+ 3) (* 1) (- 2 3 1)) (+ ))";
        evaluateExprTest(test1, expr, "17.50");
        evaluateExprTest(test2, expr, "-378.12");
        evaluateExprTest(test3, expr, "4.50");
        evaluateExprTest(test4, expr, "1.5");
        evaluateExprTest(test5, expr, "Infinity or LispExpressionException");
        evaluateExprTest(test6, expr, "LispExpressionException");
        evaluateExprTest(test7, expr, "LispExpressionException");
        evaluateExprTest(test8, expr, "LispExpressionException");
    }
}
