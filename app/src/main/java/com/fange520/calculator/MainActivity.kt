package com.fange520.calculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.*

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var previousDisplay: TextView

    private var currentNumber = "0"
    private var previousNumber = ""
    private var currentOperator: String? = null
    private var shouldResetScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        previousDisplay = findViewById(R.id.previousDisplay)

        // Number buttons
        val numberButtons = listOf(
            R.id.btn0 to "0", R.id.btn1 to "1", R.id.btn2 to "2",
            R.id.btn3 to "3", R.id.btn4 to "4", R.id.btn5 to "5",
            R.id.btn6 to "6", R.id.btn7 to "7", R.id.btn8 to "8",
            R.id.btn9 to "9", R.id.btnDot to "."
        )

        numberButtons.forEach { (id, num) ->
            findViewById<Button>(id)?.setOnClickListener { appendNumber(num) }
        }

        // Operator buttons
        findViewById<Button>(R.id.btnAdd)?.setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.btnSubtract)?.setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.btnMultiply)?.setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.btnDivide)?.setOnClickListener { setOperator("/") }
        findViewById<Button>(R.id.btnPercent)?.setOnClickListener { setOperator("%") }
        findViewById<Button>(R.id.btnPower)?.setOnClickListener { setOperator("^") }

        // Function buttons
        findViewById<Button>(R.id.btnSin)?.setOnClickListener { trigFunction("sin") }
        findViewById<Button>(R.id.btnCos)?.setOnClickListener { trigFunction("cos") }
        findViewById<Button>(R.id.btnTan)?.setOnClickListener { trigFunction("tan") }
        findViewById<Button>(R.id.btnLog)?.setOnClickListener { logFunction("log") }
        findViewById<Button>(R.id.btnLn)?.setOnClickListener { logFunction("ln") }
        findViewById<Button>(R.id.btnSqrt)?.setOnClickListener { sqrtFunction() }
        findViewById<Button>(R.id.btnSquare)?.setOnClickListener { squareFunction() }
        findViewById<Button>(R.id.btnPi)?.setOnClickListener { constant("pi") }
        findViewById<Button>(R.id.btnE)?.setOnClickListener { constant("e") }

        // Control buttons
        findViewById<Button>(R.id.btnClear)?.setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnDelete)?.setOnClickListener { deleteDigit() }
        findViewById<Button>(R.id.btnEquals)?.setOnClickListener { calculate() }

        updateDisplay()
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
            "pi" -> Math.PI.toString()
            "e" -> Math.E.toString()
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
        return if (value == value.toLong().toDouble()) {
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
        display.text = currentNumber
        previousDisplay.text = previousNumber + (if (currentOperator != null) " $currentOperator" else "")
    }
}
