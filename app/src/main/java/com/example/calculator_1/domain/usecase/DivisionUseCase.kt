package com.example.calculator_1.domain.usecase

import android.content.ContentValues.TAG
import android.util.Log
import com.example.calculator_1.domain.strategy.StrategyCalculator

class DivisionUseCase : StrategyCalculator {
    override fun calculate(num1: Double?, num2: Double?): Double {
        return try {
            (num1 ?: 0.0) / (num2 ?: 0.0)
        } catch (e: Exception) {
            Log.i(TAG, "Divide By Zero in DivisionUseCase")
            0.0
        }
    }
}