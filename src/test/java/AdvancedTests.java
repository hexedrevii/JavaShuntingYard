import org.junit.jupiter.api.Test;
import xyz.itsEve.ShuntingYard.ArithmeticExpression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AdvancedTests {
    @Test
    void testOrderOfOperations() {
        ArithmeticExpression expr = new ArithmeticExpression("2+3*4");
        assertEquals(14.0, expr.evaluate(), 1e-9);
    }

    @Test
    void testParenthesesOverridePrecedence() {
        ArithmeticExpression expr = new ArithmeticExpression("(2+3)*4");
        assertEquals(20.0, expr.evaluate(), 1e-9);
    }

    @Test
    void testNestedParentheses() {
        ArithmeticExpression expr = new ArithmeticExpression("((2+3)*2)^2");
        assertEquals(100.0, expr.evaluate(), 1e-9);
    }

    @Test
    void testDecimalSupport() {
        ArithmeticExpression expr = new ArithmeticExpression("3.5*2");
        assertEquals(7.0, expr.evaluate(), 1e-9);
    }

    @Test
    void testNegativeResult() {
        ArithmeticExpression expr = new ArithmeticExpression("3-5");
        assertEquals(-2.0, expr.evaluate(), 1e-9);
    }

    @Test
    void testZeroDivision() {
        ArithmeticExpression expr = new ArithmeticExpression("4/0");
        Exception e = assertThrows(ArithmeticException.class, expr::evaluate);
        assertEquals("Attempted to divide by 0.", e.getMessage());
    }

    @Test
    void testZeroPowerZero() {
        ArithmeticExpression expr = new ArithmeticExpression("0^0");
        Exception e = assertThrows(ArithmeticException.class, expr::evaluate);
        assertEquals("Attempted to raise 0 by 0.", e.getMessage());
    }

    @Test
    void testComplexExpression() {
        ArithmeticExpression expr = new ArithmeticExpression("3+4*2/(1-5)^2^3");
        assertEquals(3.0001220703125, expr.evaluate(), 1e-9);
    }

    @Test
    void testMultipleOperatorsSamePrecedence() {
        ArithmeticExpression expr = new ArithmeticExpression("8/2*4");
        assertEquals(16.0, expr.evaluate(), 1e-9); // left associativity
    }
}
