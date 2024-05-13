package com.example.moneysystem.service;

import com.example.moneysystem.domain.SterlingCurrency;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceImplTest {
    CalculatorService sut = new CalculatorServiceImpl();

    @Test
    public void sumTest() {
        SterlingCurrency first = new SterlingCurrency(8, 17, 5);
        SterlingCurrency second = new SterlingCurrency(10, 4, 3);
        SterlingCurrency result = sut.sum(first, second);
        assertEquals(9, result.getPound());
        assertEquals(2, result.getShilling());
        assertEquals(6, result.getPence());
    }

    @Test
    public void subtractTest() {
        SterlingCurrency first = new SterlingCurrency(8, 17, 5);
        SterlingCurrency second = new SterlingCurrency(10, 4, 3);
        SterlingCurrency result = sut.subtract(first, second);
        assertEquals(2, result.getPound());
        assertEquals(12, result.getShilling());
        assertEquals(10, result.getPence());
    }

    @Test
    public void multiplyTest() {
        SterlingCurrency first = new SterlingCurrency(8, 17, 5);
        SterlingCurrency result = sut.multiply(first, 2);
        assertEquals(11, result.getPound());
        assertEquals(15, result.getShilling());
        assertEquals(4, result.getPence());
    }

    @Test
    public void divideFirstTest() {
        SterlingCurrency first = new SterlingCurrency(8, 17, 5);
        SterlingCurrency[] result = sut.divide(first, 3);
        assertEquals(1, result[0].getPound());
        assertEquals(19, result[0].getShilling());
        assertEquals(2, result[0].getPence());
        assertEquals(0, result[1].getPound());
        assertEquals(0, result[1].getShilling());
        assertEquals(2, result[1].getPence());
    }

    @Test
    public void divideSecondTest() {
        SterlingCurrency first = new SterlingCurrency(1, 16, 18);
        SterlingCurrency[] result = sut.divide(first, 15);
        assertEquals(1, result[0].getPound());
        assertEquals(5, result[0].getShilling());
        assertEquals(0, result[0].getPence());
        assertEquals(0, result[1].getPound());
        assertEquals(1, result[1].getShilling());
        assertEquals(1, result[1].getPence());
    }
}
