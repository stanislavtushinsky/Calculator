package com.example.calculator_1.presentation.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.calculator_1.databinding.ActivityMainBinding
import com.example.calculator_1.domain.entity.CalculatorAction
import com.example.calculator_1.domain.entity.CalculatorOperation
import com.example.calculator_1.domain.strategy.StrategyContext
import com.example.calculator_1.presentation.viewmodel.CalculatorViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CalculatorViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupButtonsActions()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[CalculatorViewModel::class.java]
        viewModel.calculatorLD.observe(this) {
            binding.countTextView.text = it.number1 + (it.operation?.symbol ?: "") + it.number2
            binding.resultTextView.text = it.result
        }
    }

    private fun setupButtonsActions() {
        binding.clearButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Clear)
        }
        binding.plusMinusButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.ChangeNumberFamiliarity)
        }
        binding.percentButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Percent)
        }
        binding.divisionButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
        }
        binding.multiplyButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
        }
        binding.minusButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
        }
        binding.plusButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Operation(CalculatorOperation.Add))
        }
        binding.nineButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Number(9))
        }
        binding.eightButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Number(8))
        }
        binding.sevenButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Number(7))
        }
        binding.sixButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Number(6))
        }
        binding.fiveButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Number(5))
        }
        binding.fourButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Number(4))
        }
        binding.threeButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Number(3))
        }
        binding.twoButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Number(2))
        }
        binding.oneButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Number(1))
        }
        binding.zeroButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Number(0))
        }
        binding.equalButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Calculate)
        }
        binding.dotButton.setOnClickListener {
            viewModel.onAction(CalculatorAction.Decimal)
        }
    }
}