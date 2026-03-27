package com.fange520.calculator.ui.calculator

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fange520.calculator.databinding.FragmentCalculatorBinding
import kotlin.math.*

class CalculatorFragment : Fragment(R.layout.fragment_calculator) {

    private var _binding: FragmentCalculatorBinding? = null
    private val binding get() = _binding!!

    private var currentNumber = "0"
    private var previousNumber = ""
    private var currentOperator: String? = null
    private var shouldResetScreen = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCalculatorBinding.bind(view)

        // Number buttons
        val numberButtons = mapOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8",
            R.id.btn9 to "9", R.id.btnDot to "."
        )

        numberButtons.forEach { (id, num) ->
            binding.root.findViewById<android.widget.Button>(id)?.setOnClickListener { appendNumber(num) }
        }

        // Operator buttons
        binding.btnAdd.setOnClickListener { setOperator("+") }
        binding.btnSubtract.setOnClickListener { setOperator("-") }
        binding.btnMultiply.setOnClickListener { setOperator("*") }
        binding.btnDivide.setOnClickListener { setOperator("/") }
        binding.btnPercent.setOnClickListener { setOperator("%") }
        binding.btnPower.setOnClickListener { setOperator("^") }

        // Function buttons
        binding.btnSin.setOnClickListener { trigFunction("sin") }
        binding.btnCos.setOnClickListener { trigFunction("cos") }
        binding.btnTan.setOnClickListener { trigFunction("tan") }
        binding.btnLog.setOnClickListener { logFunction("log") }
        binding.btnLn.setOnClickListener { logFunction("ln") }
        binding.btnSqrt.setOnClickListener { sqrtFunction() }
        binding.btnSquare.setOnClickListener { squareFunction() }
        binding.btnPi.setOnClickListener { constant("pi") }
        binding.btnE.setOnClickListener { constant("e") }

        // Control buttons
        binding.btnClear.setOnClickListener { clearAll() }
        binding.btnDelete.setOnClickListener { deleteDigit() }
        binding.btnEquals.setOnClickListener { calculate() }

        updateDisplay()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun appendNumber(num: String) {
        if (shouldResetScreen) {
            currentNumber = ""
            shouldResetScreen = false
        }
        if (num == "." && currentNumber.contains(".")) return
        if (currentNumber == "0" && num != ".") {
            currentNumber = num
        } else {
            currentNumber += num
        }
        updateDisplay()
    }

    private fun setOperator(op: String) {
        if (currentOperator != null && !shouldResetScreen) {
            calculate()
        }
        previousNumber = currentNumber
        currentOperator = op
        shouldResetScreen = true
        updateDisplay()
    }

    private fun trigFunction(func: String) {
        val value = currentNumber.toDoubleOrNull() ?: return
        val result = when (func) {
            "sin" -> sin(Math.toRadians(value))
            "cos" -> cos(Math.toRadians(value))
            "tan" -> tan(Math.toRadians(value))
            else -> return
        }
        currentNumber = formatResult(result)
        shouldResetScreen = true
        updateDisplay()
    }

    private fun logFunction(func: String) {
        val value = currentNumber.toDoubleOrNull() ?: return
        val result = when (func) {
            "log" -> log10(value)
            "ln" -> ln(value)
            else -> return
        }
        currentNumber = formatResult(result)
        shouldResetScreen = true
        updateDisplay()
    }

    private fun sqrtFunction() {
        val value = currentNumber.toDoubleOrNull() ?: return
        currentNumber = formatResult(sqrt(value))
        shouldResetScreen = true
        updateDisplay()
    }

    private fun squareFunction() {
        val value = currentNumber.toDoubleOrNull() ?: return
        currentNumber = formatResult(value * value)
        shouldResetScreen = true
        updateDisplay()
    }

    private fun constant(const: String) {
        currentNumber = when (const) {
            "pi" -> PI.toString()
            "e" -> E.toString()
            else -> return
        }
        updateDisplay()
    }

    private fun calculate() {
        if (currentOperator == null || shouldResetScreen) return

        val prev = previousNumber.toDoubleOrNull() ?: return
        val curr = currentNumber.toDoubleOrNull() ?: return

        val result = when (currentOperator) {
            "+" -> prev + curr
            "-" -> prev - curr
            "*" -> prev * curr
            "/" -> if (curr == 0.0) Double.NaN else prev / curr
            "%" -> prev % curr
            "^" -> prev.pow(curr)
            else -> return
        }

        currentNumber = formatResult(result)
        currentOperator = null
        previousNumber = ""
        shouldResetScreen = true
        updateDisplay()
    }

    private fun formatResult(value: Double): String {
        return if (abs(value - value.toLong()) < 1e-10 && abs(value) < 1e10) {
            value.toLong().toString()
        } else {
            value.toString()
        }
    }

    private fun clearAll() {
        currentNumber = "0"
        previousNumber = ""
        currentOperator = null
        shouldResetScreen = false
        updateDisplay()
    }

    private fun deleteDigit() {
        if (shouldResetScreen) return
        currentNumber = currentNumber.dropLast(1)
        if (currentNumber.isEmpty() || currentNumber == "-") {
            currentNumber = "0"
        }
        updateDisplay()
    }

    private fun updateDisplay() {
        binding.display.text = currentNumber
        binding.previousDisplay.text = previousNumber + (if (currentOperator != null) " $currentOperator" else "")
    }
}
