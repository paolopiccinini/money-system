package com.example.moneysystem.domain;

import com.example.moneysystem.constants.Constants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.Objects;

public class SterlingCurrency {
    private final int pence;
    private final int shilling;
    private final int pound;

    public SterlingCurrency(int pence, int shilling, int pound) {
        this.pence = pence;
        this.shilling = shilling;
        this.pound = pound;
    }

    public SterlingCurrency(int pence) {
        this.pence = (pence % Constants.PENCE_IN_POUND) % Constants.PENCE_IN_SCHILLING;
        this.shilling = (pence % Constants.PENCE_IN_POUND) / Constants.PENCE_IN_SCHILLING;
        this.pound = pence / Constants.PENCE_IN_POUND;
    }

    private int convertInPence() {
        return (pound * Constants.PENCE_IN_POUND) + (shilling * Constants.PENCE_IN_SCHILLING) + pence;
    }

    public SterlingCurrency sum(SterlingCurrency that) {
        return new SterlingCurrency(convertInPence() + that.convertInPence());
    }

    public SterlingCurrency subtract(SterlingCurrency that) {
        return new SterlingCurrency(convertInPence() - that.convertInPence());
    }

    public SterlingCurrency multiply(int operator) {
        return new SterlingCurrency(convertInPence() * operator);
    }

    public SterlingCurrency[] divide(int operator) {
        int pence = convertInPence();
        return new SterlingCurrency[]{new SterlingCurrency(pence / operator),
                new SterlingCurrency(pence % operator)};
    }

    public int getPence() {
        return pence;
    }

    public int getShilling() {
        return shilling;
    }

    public int getPound() {
        return pound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SterlingCurrency that = (SterlingCurrency) o;
        return pence == that.pence && shilling == that.shilling && pound == that.pound;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pence, shilling, pound);
    }
}
