package com.crazy_ataman.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.TimeUnit;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    Calculator calculator;

    @BeforeEach
    void init() {
        calculator = new Calculator();
    }

      @Test
    public void testAddition() {
        String expression = "2+2";
        Optional<Double> result = calculator.solve(expression);
        assertTrue(result.isPresent());
        assertEquals(4.0, result.get());
    }

    @Test
    public void testSubtraction() {
        String expression = "5-3";
        Optional<Double> result = calculator.solve(expression);
        assertTrue(result.isPresent());
        assertEquals(2.0, result.get());
    }

    @Test
    public void testMultiplication() {
        String expression = "3*4";
        Optional<Double> result = calculator.solve(expression);
        assertTrue(result.isPresent());
        assertEquals(12.0, result.get());
    }
  @Test
    public void testNumberTokenCreation() {
        CalculatorToken token = CalculatorToken.number(5.0, 1);
        assertTrue(token.isNumber());
        assertFalse(token.isOperation());
        assertEquals(5.0, token.getNumber());
        assertNull(token.getOperation());
        assertEquals(1, token.getIndex());
    }

    @Test
    public void testOperationTokenCreation() {
        CalculatorToken token = CalculatorToken.operation('+', 2);
        assertFalse(token.isNumber());
        assertTrue(token.isOperation());
        assertNull(token.getNumber());
        assertEquals(Character.valueOf('+'), token.getOperation());
        assertEquals(2, token.getIndex());
    }

    @Test
    public void testEquals() {
        CalculatorToken token1 = CalculatorToken.number(5.0, 1);
        CalculatorToken token2 = CalculatorToken.number(5.0, 1);
        CalculatorToken token3 = CalculatorToken.operation('+', 2);
        
        assertEquals(token1, token2);
        assertNotEquals(token1, token3);
        assertNotEquals(token2, token3);
    }

    @Test
    public void testHashCode() {
        CalculatorToken token1 = CalculatorToken.number(5.0, 1);
        CalculatorToken token2 = CalculatorToken.number(5.0, 1);
        CalculatorToken token3 = CalculatorToken.operation('+', 2);

        assertEquals(token1.hashCode(), token2.hashCode());
        assertNotEquals(token1.hashCode(), token3.hashCode());
    }

    @Test
    public void testToString() {
        CalculatorToken token = CalculatorToken.number(5.0, 1);
        String expected = "CalculatorToken{number=5.0, operation=null, index=1}";
        assertEquals(expected, token.toString());
    }


    @Test
    public void testTokenCreationWithNegativeIndex() {
    
        CalculatorToken token = CalculatorToken.number(5.0, -1);
        assertTrue(token.getIndex() < 0);
    }

    @Test
    @Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
    public void testTokenToStringPerformance() {
        CalculatorToken token = CalculatorToken.number(5.0, 1);
        token.toString();
    }
    @Test
    public void testDivision() {
        String expression = "8/2";
        Optional<Double> result = calculator.solve(expression);
        assertTrue(result.isPresent());
        assertEquals(4.0, result.get());
    }

    @Test
    public void testComplexExpression() {
        String expression = "3+5*2";
        Optional<Double> result = calculator.solve(expression);
        assertTrue(result.isPresent());
        assertEquals(13.0, result.get());
    }

    @Test
    public void testInvalidExpression() {
        String expression = "3+";
        Optional<Double> result = calculator.solve(expression);
        assertFalse(result.isPresent());
    }
 @Test
    public void testSolveValidExpression() {
        String expression = "2+2";
        Optional<Double> result = calculator.solve(expression);
        assertTrue(result.isPresent());
        assertEquals(4.0, result.get(), 0.01);
    }

    @Test
    public void testSolveInvalidExpression() {
        String expression = "2+";
        Optional<Double> result = calculator.solve(expression);
        assertFalse(result.isPresent());
    }
    @Test
    void testNotNullLineShouldNotTrow() {
        String line = "123+45";
        assertDoesNotThrow(new Executable() {
            @Override
            public void execute() {
                calculator.solve(line);
            }
        });
    }

    @Test
    void testPlusOperation() {
        String line = "111+12";
        Optional<Double> actual = calculator.solve(line);
        double expected = 123;
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testMinusOperation() {
        String line = "534-690";
        Optional<Double> actual = calculator.solve(line);
        double expected = -156;
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testMultiplyOperation() {
        String line = "17*42";
        Optional<Double> actual = calculator.solve(line);
        double expected = 714;
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testDivideOperation() {
        String line = "1593/59";
        Optional<Double> actual = calculator.solve(line);
        double expected = 27;
        assertTrue(actual.isPresent());
        assertEquals(expected, actual.get());
    }

    @Test
    void testNullLineShouldThrow() {
        assertThrows(NullPointerException.class, new Executable() {
            @Override
            public void execute() {
                calculator.solve(null);
            }
        });
    }

    @Test
    void testEmptyLineShouldProduceEmptyResult() {
        String line = "     ";
        Optional<Double> actual = calculator.solve(line);
        assertFalse(actual.isPresent());
    }

    @Test
    void testSingleNumberShouldProduceEmptyResult() {
        String line = "123";
        Optional<Double> actual = calculator.solve(line);
        assertFalse(actual.isPresent());
    }

    @Test
    void testResultShouldBeEmptyIfLineInvalid() {
        String line = "*438+";
        Optional<Double> actual = calculator.solve(line);
        assertFalse(actual.isPresent());
    }

    @Test
    void testResultShouldBeEmptyIfLineIsWord() {
        String line = "Hello, world!";
        Optional<Double> actual = calculator.solve(line);
        assertFalse(actual.isPresent());
    }

    @Test
    void testResultShouldBeEmptyIfLineHasInvalidOperations() {
        String line = "83(34&23)11=79";
        Optional<Double> actual = calculator.solve(line);
        assertFalse(actual.isPresent());
    }

}