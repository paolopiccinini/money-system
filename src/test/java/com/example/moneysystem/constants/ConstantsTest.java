package com.example.moneysystem.constants;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConstantsTest {

    private final String regex = Constants.VALIDATION_REGEX;

    @ParameterizedTest(name = "{0} match")
    @MethodSource("validProvider")
    void regexTestValid(String candidate) {
        assertTrue(Pattern.matches(regex, candidate), candidate);
    }

    @ParameterizedTest(name = "{0} not match")
    @MethodSource("invalidProvider")
    void regexTestInvalid(String candidate) {
        assertFalse(Pattern.matches(regex, candidate), candidate);
    }

    private static Stream<Arguments> invalidProvider() {
        return Stream.of(
                "13d",
                "21s",
                "-1d",
                "-1s",
                "ciao",
                "0p -21s 2d",
                "0p 2s -2d",
                "0p2s2d",
                "0p  2s 2d",
                "0p  2s  2d",
                "-1p"
        ).map(Arguments::of);
    }

    private static Stream<Arguments> validProvider() {
        return Stream.of(
                "0d",
                "1d",
                "2d",
                "3d",
                "4d",
                "5d",
                "6d",
                "7d",
                "8d",
                "9d",
                "10d",
                "11d",
                "12d",
                "0s",
                "1s",
                "2s",
                "3s",
                "4s",
                "5s",
                "6s",
                "7s",
                "8s",
                "9s",
                "10s",
                "11s",
                "12s",
                "13s",
                "14s",
                "15s",
                "16s",
                "17s",
                "18s",
                "19s",
                "20s",
                "0p",
                "1p",
                "2p",
                "3p",
                "4p",
                "5p",
                "6p",
                "7p",
                "8p",
                "9p",
                "10p",
                "11p",
                "12p",
                "13p",
                "14p",
                "15p",
                "16p",
                "17p",
                "18p",
                "19p",
                "20p",
                "0s 0d",
                "0s 1d",
                "0s 2d",
                "0s 3d",
                "0s 4d",
                "0s 5d",
                "0s 6d",
                "0s 7d",
                "0s 8d",
                "0s 9d",
                "0s 10d",
                "0s 11d",
                "0s 12d",
                "1s 0d",
                "2s 1d",
                "3s 2d",
                "4s 3d",
                "5s 4d",
                "6s 5d",
                "7s 6d",
                "8s 7d",
                "9s 8d",
                "10s 9d",
                "11s 10d",
                "12s 11d",
                "13s 12d",
                "14s 12d",
                "15s 12d",
                "16s 12d",
                "17s 12d",
                "18s 12d",
                "19s 12d",
                "20s 12d",
                "0p 0d",
                "0p 1d",
                "0p 2d",
                "0p 3d",
                "0p 4d",
                "0p 5d",
                "0p 6d",
                "0p 7d",
                "0p 8d",
                "0p 9d",
                "0p 10d",
                "0p 11d",
                "0p 12d",
                "1p 0d",
                "2p 1d",
                "3p 2d",
                "4p 3d",
                "5p 4d",
                "6p 5d",
                "7p 6d",
                "8p 7d",
                "9p 8d",
                "10p 9d",
                "11p 10d",
                "12p 11d",
                "13p 12d",
                "14p 12d",
                "15p 12d",
                "16p 12d",
                "17p 12d",
                "18p 12d",
                "19p 12d",
                "20p 12d",
                "0p 0s",
                "0p 1s",
                "0p 2s",
                "0p 3s",
                "0p 4s",
                "0p 5s",
                "0p 6s",
                "0p 7s",
                "0p 8s",
                "0p 9s",
                "0p 10s",
                "0p 11s",
                "0p 12s",
                "1p 0s",
                "2p 1s",
                "3p 2s",
                "4p 3s",
                "5p 4s",
                "6p 5s",
                "7p 6s",
                "8p 7s",
                "9p 8s",
                "10p 9s",
                "11p 10s",
                "12p 11s",
                "13p 12s",
                "14p 13s",
                "15p 14s",
                "16p 15s",
                "17p 16s",
                "18p 17s",
                "19p 18s",
                "20p 19s",
                "20p 20s",
                "20p 20s 12d",
                "0p 0s 2d"
        ).map(Arguments::of);
    }
}
