package com.example.moneysystem.mapper;

import com.example.moneysystem.domain.SterlingCurrency;

public interface Mapper {
    SterlingCurrency toSterlingCurrency(String toConvert);
    String toString(SterlingCurrency toConvert);
    String toString(SterlingCurrency result, SterlingCurrency reminder);
}
