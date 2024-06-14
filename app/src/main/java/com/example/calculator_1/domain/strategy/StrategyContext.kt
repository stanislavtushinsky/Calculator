package com.example.calculator_1.domain.strategy

class StrategyContext {
    fun useStrategy(num1: Double?, num2: Double?, strategyCalculator: StrategyCalculator) =
        strategyCalculator.calculate(num1, num2)
}