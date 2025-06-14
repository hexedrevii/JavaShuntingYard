package xyz.itsEve.ShuntingYard;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a mathematical expression using the Shunting Yard Algorithm.
 * @author Evelyn Serra
 */
public class ArithmeticExpression {
    public String expression;

    private final Stack<String> operators = new Stack<String>();
    private final Stack<String> output = new Stack<String>();

    private final Map<String, Integer> precedence;
    private final Map<String, String> associativity;

    public ArithmeticExpression(String expr) {
        this.expression = expr;

        this.precedence = Map.of(
                "-", 2, "+", 2,
                "*", 3, "/", 3,
                "^", 4
        );
        this.associativity = Map.of(
                "+", "L", "-", "L",
                "*", "L","/", "L",
                "^", "R"
        );

        this.parse();
    }

    @Contract(pure = true)
    private boolean isNumber(@NotNull String token) {
        return token.matches("\\d+(\\.\\d+)?");
    }

    // This splits the expression without it having to be split by spaces...
    // Oh yeah, I did not write this shit :3333 (it was a robot)
    private @NotNull List<String> tokenise(@NotNull String input) {
        List<String> tokens = new ArrayList<String>();
        Matcher matcher = Pattern.compile("\\d\\.\\d+|\\d+|[+\\-*/^()]").matcher(input);
        while (matcher.find()) {
            tokens.add(matcher.group());
        }
        return tokens;
    }

    private void parse() {
        List<String> tokens = this.tokenise(expression);
        for (String token : tokens) {
            if (this.isNumber(token)) {
                this.output.push(token);
                continue;
            }

            // If token is base operator
            if (this.precedence.containsKey(token)) {
                // Source: https://mathcenter.oxford.emory.edu/site/cs171/shuntingYardAlgorithm/
                // Source: https://en.wikipedia.org/wiki/Shunting_yard_algorithm
                while (!this.operators.isEmpty() && !this.operators.peek().equals("(")) {
                    String top = this.operators.peek();
                    if (
                        this.precedence.get(top) > this.precedence.get(token) ||
                        (this.precedence.get(top).equals(this.precedence.get(token))
                            && this.associativity.get(token).equals("L"))
                    ) {
                        // Remove the top of the operator stack and add it to the bottom of output
                        this.output.push(this.operators.pop());
                    } else {
                        break;
                    }
                }
                this.operators.push(token);
            }
            else if (token.equals("(")) {
                this.operators.push(token);
            }
            else if (token.equals(")")) {
                // Move all the operators to the output stack
                while (!this.operators.isEmpty() && !this.operators.peek().equals("(")) {
                    this.output.push(this.operators.pop());
                }

                // Remove the last parenthesis
                if (!this.operators.isEmpty()) {
                  this.operators.pop();
                }
            }
        }

        // Get rid of the last operators by moving them to the output stack
        while (!this.operators.isEmpty()) {
            this.output.push(this.operators.pop());
        }
    }

    /**
     * Evaluates the parsed expression.
     * @return The number as {@link double}
     */
    public double evaluate() {
        Stack<Double> stack = new Stack<>();
        for (String token : this.output) {
            if (this.isNumber(token)) {
                stack.push(Double.parseDouble(token));
                continue;
            }

            double rhs = stack.pop();
            double lhs = stack.pop();

            switch (token) {
                case "+" -> stack.push(lhs + rhs);
                case "-" -> stack.push(lhs - rhs);
                case "*" -> stack.push(lhs * rhs);
                case "/" -> {
                    if (rhs == 0) {
                        throw new ArithmeticException("Attempted to divide by 0.");
                    }

                    stack.push(lhs / rhs);
                }
                case "^" -> {
                    if (lhs == 0 && rhs == 0) {
                        throw new ArithmeticException("Attempted to raise 0 by 0.");
                    }

                    stack.push(Math.pow(lhs, rhs));
                }
                default -> throw new RuntimeException("Invalid operator: " + token);
            }
        }

        return stack.pop();
    }
}
