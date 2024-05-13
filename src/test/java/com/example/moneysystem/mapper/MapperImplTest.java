package com.example.moneysystem.mapper;

import com.example.moneysystem.domain.SterlingCurrency;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class MapperImplTest {
    Mapper sut = new MapperImpl();

    @Test
    public void toSterlingCurrencyTest() {
        SterlingCurrency result = sut.toSterlingCurrency("5p 17s 8d");
        assertEquals(5, result.getPound());
        assertEquals(17, result.getShilling());
        assertEquals(8, result.getPence());
    }

    @Test
    public void toStringTest() {
        SterlingCurrency toConvert = new SterlingCurrency(8, 17, 5);
        String result = sut.toString(toConvert);
        assertEquals("5p 17s 8d", result);

    }

    @Test
    public void toStringNoPoundTest() {
        SterlingCurrency toConvert = new SterlingCurrency(8, 17, 0);
        String result = sut.toString(toConvert);
        assertEquals("17s 8d", result);

    }

    @Test
    public void toStringAll0Test() {
        SterlingCurrency toConvert = new SterlingCurrency(0, 0, 0);
        String result = sut.toString(toConvert);
        assertEquals("", result);

    }

    @Test
    public void toStringWithReminderTest() {
        SterlingCurrency first = new SterlingCurrency(8, 17, 5);
        SterlingCurrency second = new SterlingCurrency(1, 1, 1);
        String result = sut.toString(first, second);
        assertEquals("5p 17s 8d (1p 1s 1d)", result);

    }
}
