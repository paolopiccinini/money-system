package com.example.moneysystem.service;

import com.example.moneysystem.domain.SterlingCurrency;
import org.springframework.stereotype.Service;

@Service
public class CalculatorServiceImpl implements CalculatorService{
    @Override
    public SterlingCurrency sum(SterlingCurrency first, SterlingCurrency second) {
        return first.sum(second);
    }

    @Override
    public SterlingCurrency subtract(SterlingCurrency first, SterlingCurrency second) {
        return first.subtract(second);
    }

    @Override
    public SterlingCurrency multiply(SterlingCurrency first, int second) {
        return first.multiply(second);
    }

    @Override
    public SterlingCurrency[] divide(SterlingCurrency first, int second) {
        return first.divide(second);
    }
}
