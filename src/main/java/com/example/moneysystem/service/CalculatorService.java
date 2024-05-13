package com.example.moneysystem.service;

import com.example.moneysystem.domain.SterlingCurrency;

public interface CalculatorService {

    SterlingCurrency sum(SterlingCurrency first, SterlingCurrency second);
    SterlingCurrency subtract(SterlingCurrency first, SterlingCurrency second);
    SterlingCurrency multiply(SterlingCurrency first, int second);
    SterlingCurrency[] divide(SterlingCurrency first, int second);
}
