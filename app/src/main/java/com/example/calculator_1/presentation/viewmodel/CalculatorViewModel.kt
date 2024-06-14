package com.example.calculator_1.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.calculator_1.domain.entity.CalculatorAction
import com.example.calculator_1.domain.entity.CalculatorOperation
import com.example.calculator_1.domain.entity.CalculatorState
import com.example.calculator_1.domain.strategy.StrategyContext
import com.example.calculator_1.domain.usecase.AdditionUseCase
import com.example.calculator_1.domain.usecase.DivisionUseCase
import com.example.calculator_1.domain.usecase.MultiplyUseCase
import com.example.calculator_1.domain.usecase.SubtractionUseCase

class CalculatorViewModel : ViewModel() {
    private val _calculatorLD = MutableLiveData<CalculatorState>().apply {
        value = CalculatorState()
    }
    val calculatorLD: LiveData<CalculatorState>
        get() = _calculatorLD

    private val calculatorContext = StrategyContext()

    fun onAction(action: CalculatorAction) {
        when (action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Delete -> delete()
            is CalculatorAction.Clear -> _calculatorLD.value = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Calculate -> calculate()
            is CalculatorAction.Percent -> percentage()
            is CalculatorAction.ChangeNumberFamiliarity -> changeNumberFamiliarity()
        }
    }

    private fun calculate() {
        val currentState = _calculatorLD.value ?: return
        val number1 = currentState.number1.toDoubleOrNull()
        val number2 = currentState.number2.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result = when (currentState.operation) {
                is CalculatorOperation.Add -> calculatorContext.useStrategy(
                    number1,
                    number2,
                    AdditionUseCase()
                )

                is CalculatorOperation.Subtract -> calculatorContext.useStrategy(
                    number1,
                    number2,
                    SubtractionUseCase()
                )

                is CalculatorOperation.Multiply -> calculatorContext.useStrategy(
                    number1,
                    number2,
                    MultiplyUseCase()
                )

                is CalculatorOperation.Divide -> calculatorContext.useStrategy(
                    number1,
                    number2,
                    DivisionUseCase()
                )

                null -> return
            }
            _calculatorLD.value = CalculatorState(
                number1 = currentState.number1,
                number2 = currentState.number2,
                operation = currentState.operation,
                result = formatDecimal(result.toString())
            )
        }
    }


    private fun enterOperation(operation: CalculatorOperation) {
        val currentState = _calculatorLD.value ?: CalculatorState()
        if (currentState.number1.isNotBlank()) {
            _calculatorLD.value = currentState.copy(operation = operation)
        }
    }

    private fun enterNumber(number: Int) {
        val currentState = _calculatorLD.value ?: CalculatorState()
        if (currentState.operation == null) {
            if (currentState.number1.length >= MAX_NUM_LENGTH) {
                return
            }
            _calculatorLD.value = currentState.copy(
                number1 = currentState.number1 + number
            )
            return
        }
        if (currentState.number2.length >= MAX_NUM_LENGTH) {
            return
        }
        _calculatorLD.value = currentState.copy(
            number2 = currentState.number2 + number
        )
    }

    private fun delete() {
        val currentState = _calculatorLD.value ?: CalculatorState()
        when {
            currentState.number2.isNotBlank() -> _calculatorLD.value = currentState.copy(
                number2 = currentState.number2.dropLast(1)
            )

            currentState.operation != null -> _calculatorLD.value = currentState.copy(
                operation = null
            )

            currentState.number1.isNotBlank() -> _calculatorLD.value = currentState.copy(
                number1 = currentState.number1.dropLast(1)
            )
        }
    }

    private fun enterDecimal() {
        val currentState = _calculatorLD.value ?: return
        if (currentState.operation == null && !currentState.number1.contains(".") && currentState.number1.isNotBlank()) {
            _calculatorLD.value = currentState.copy(
                number1 = currentState.number1 + "."
            )
            return
        } else if (!currentState.number2.contains(".") && currentState.number2.isNotBlank()) {
            _calculatorLD.value = currentState.copy(
                number2 = currentState.number2 + "."
            )
        }
    }

    private fun percentage() {
        val currentState = _calculatorLD.value ?: return
        if (currentState.operation == null) {
            _calculatorLD.value = currentState.copy(
                number1 = (currentState.number1.toDoubleOrNull()?.div(100)).toString()
            )
            return
        }
        if (currentState.operation == CalculatorOperation.Add || currentState.operation == CalculatorOperation.Subtract) {
            _calculatorLD.value = currentState.copy(
                number2 = (currentState.number2.toDoubleOrNull()?.div(100)
                    ?.times(currentState.number1.toDouble())).toString()
            )
        } else {
            _calculatorLD.value = currentState.copy(
                number2 = (currentState.number2.toDoubleOrNull()?.div(100)).toString()
            )
        }
    }

    private fun changeNumberFamiliarity() {
        val currentState = _calculatorLD.value ?: return
        if (currentState.operation == null) {
            _calculatorLD.value = if (currentState.number1.contains("-")) {
                currentState.copy(number1 = currentState.number1.removePrefix("-"))
            } else {
                _calculatorLD.value?.copy(
                    number1 = "-" + currentState.number1
                )
            }
            return
        }
        _calculatorLD.value = if (currentState.number2.contains("-")) {
            currentState.copy(number2 = currentState.number2.removePrefix("-"))
        } else {
            _calculatorLD.value?.copy(
                number2 = "-" + currentState.number2
            )
        }
    }

    private fun formatDecimal(number: String): String {
        var formattedNumber = number
        val numericNumber: Double? = formattedNumber.toDoubleOrNull()
        if (formattedNumber.endsWith(".0") && numericNumber?.toLong()
                ?.toDouble() == numericNumber
        ) {
            formattedNumber = formattedNumber.trimEnd('0').trimEnd('.')
        }
        return formattedNumber
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8
    }
}
