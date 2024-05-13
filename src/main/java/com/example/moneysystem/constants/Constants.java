package com.example.moneysystem.constants;

public class Constants {

    private Constants(){};

    public static final String VALIDATION_REGEX_PSD = "(\\d+p)(\\s([0-9]|1[0-9])s)?(\\s([0-9]|1[0-1])d)?";
    public static final String VALIDATION_REGEX_SD = "(([0-9]|1[0-9])s)?(\\s([0-9]|1[0-1])d)?";
    public static final String VALIDATION_REGEX_D = "(([0-9]|1[0-1])d)?";
    public static final String VALIDATION_REGEX = "^(" + VALIDATION_REGEX_PSD + "|" + VALIDATION_REGEX_SD + "|" + VALIDATION_REGEX_D + ")";
    public static final int PENCE_IN_POUND = 240;
    public static final int PENCE_IN_SCHILLING = 12;
    public static final String PENCE_SUFFIX = "d";
    public static final String SHILLING_SUFFIX = "s";
    public static final String POUND_SUFFIX = "p";
    public static final String VERSION_1_HEADER = "application/vnd.com.example.moneysystem.v1+json";

}
