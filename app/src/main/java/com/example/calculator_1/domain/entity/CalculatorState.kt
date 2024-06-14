package com.example.calculator_1.domain.entity

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null,
    val result: String = ""
)
