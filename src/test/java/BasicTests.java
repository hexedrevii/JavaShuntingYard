import org.junit.jupiter.api.Test;
import xyz.itsEve.ShuntingYard.ArithmeticExpression;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicTests {
    @Test
    void testSimpleAddition() {
        ArithmeticExpression expr = new ArithmeticExpression("2+3");
        assertEquals(5.0, expr.evaluate(), 1e-9);
    }

    @Test
    void testSimpleSubtraction() {
        ArithmeticExpression expr = new ArithmeticExpression("7-5");
        assertEquals(2.0, expr.evaluate(), 1e-9);
    }

    @Test
    void testSimpleMultiplication() {
        ArithmeticExpression expr = new ArithmeticExpression("3*4");
        assertEquals(12.0, expr.evaluate(), 1e-9);
    }

    @Test
    void testSimpleDivision() {
        ArithmeticExpression expr = new ArithmeticExpression("10/2");
        assertEquals(5.0, expr.evaluate(), 1e-9);
    }

    @Test
    void testSimplePower() {
        ArithmeticExpression expr = new ArithmeticExpression("2^3");
        assertEquals(8.0, expr.evaluate(), 1e-9);
    }
}
