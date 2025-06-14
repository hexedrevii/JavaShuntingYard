# Shunting Yard Parser

A tiny Java program/library to parse arithmatical expressions using the Shunting Yard Algorithm.

# Example
```java
import xyz.itsEve.ShuntingYard

public class Entry {
    public static void main(String[] args) {
        try {
            ShuntingYard.ArithmeticExpression expr =
                new ShuntingYard.ArithmeticExpression("3+5*(4/2)^5");

            System.out.println(expr.evaluate());
        } catch (Exception e) {
            System.err.println("Invalid expression.");
        }
    }
}
```

# License
GPL 3.0