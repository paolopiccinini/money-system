package com.example.moneysystem.domain;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.*;

public class SterlingCurrencyTest {

    @ParameterizedTest
    @MethodSource("providerSum")
    public void sumTest(SterlingCurrency first, SterlingCurrency second, SterlingCurrency expected) {
        SterlingCurrency result = first.sum(second);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("providerSubtract")
    public void subtractTest(SterlingCurrency first, SterlingCurrency second, SterlingCurrency expected) {
        SterlingCurrency result = first.subtract(second);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("providerMultiply")
    public void multiplyTest(SterlingCurrency first, int second, SterlingCurrency expected) {
        SterlingCurrency result = first.multiply(second);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @MethodSource("providerDivide")
    public void divideFirstTest(SterlingCurrency first, int second, SterlingCurrency[] expected) {
        SterlingCurrency[] result = first.divide(second);
        assertArrayEquals(expected, result);
    }

    static Stream<Arguments> providerSum() {
        return Stream.of(
                arguments(new SterlingCurrency(8, 17, 5), new SterlingCurrency(10, 4, 3), new SterlingCurrency(6, 2, 9)),
                arguments(new SterlingCurrency(0, 0, 5), new SterlingCurrency(0, 0, 3), new SterlingCurrency(0, 0, 8))
        );
    }

    static Stream<Arguments> providerSubtract() {
        return Stream.of(
                arguments(new SterlingCurrency(8, 17, 5), new SterlingCurrency(10, 4, 3), new SterlingCurrency(10, 12, 2)),
                arguments(new SterlingCurrency(0, 0, 3), new SterlingCurrency(0, 0, 5), new SterlingCurrency(0, 0, -2)),
                arguments(new SterlingCurrency(10, 4, 3), new SterlingCurrency(8, 17, 5), new SterlingCurrency(-10, -12, -2))
        );
    }

    static Stream<Arguments> providerMultiply() {
        return Stream.of(
                arguments(new SterlingCurrency(8, 17, 5), 2, new SterlingCurrency(4, 15, 11)),
                arguments(new SterlingCurrency(0, 0, 3), 5, new SterlingCurrency(0, 0, 15)),
                arguments(new SterlingCurrency(0, 0, 3), 0, new SterlingCurrency(0, 0, 0))
        );
    }

    static Stream<Arguments> providerDivide() {
        return Stream.of(
                arguments(new SterlingCurrency(8, 17, 5), 3, new SterlingCurrency[]{new SterlingCurrency(2, 19, 1), new SterlingCurrency(2, 0, 0)}),
                arguments(new SterlingCurrency(1, 16, 18), 15, new SterlingCurrency[]{new SterlingCurrency(0, 5, 1), new SterlingCurrency(1, 1, 0)})
        );
    }
}
