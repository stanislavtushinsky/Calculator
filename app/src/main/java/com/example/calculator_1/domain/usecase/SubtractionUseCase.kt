package com.example.calculator_1.domain.usecase

import com.example.calculator_1.domain.strategy.StrategyCalculator

class SubtractionUseCase : StrategyCalculator {
    override fun calculate(num1: Double?, num2: Double?): Double {
        return (num1 ?: 0.0) - (num2 ?: 0.0)
    }
}