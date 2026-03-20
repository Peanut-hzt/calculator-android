package com.fange520.calculator.ui.converter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.fange520.calculator.R

class ConverterFragment : Fragment(R.layout.fragment_converter) {

    private lateinit var inputValue: EditText
    private lateinit var outputValue: TextView
    private lateinit var fromSpinner: Spinner
    private lateinit var toSpinner: Spinner

    private var currentCategory = "length"
    private var isUpdating = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputValue = view.findViewById(R.id.inputValue)
        outputValue = view.findViewById(R.id.outputValue)
        fromSpinner = view.findViewById(R.id.fromSpinner)
        toSpinner = view.findViewById(R.id.toSpinner)

        val categorySpinner: Spinner = view.findViewById(R.id.categorySpinner)
        val categories = listOf("长度", "面积", "重量", "温度", "汇率")
        categorySpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, categories)
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                currentCategory = when (position) {
                    0 -> "length"
                    1 -> "area"
                    2 -> "weight"
                    3 -> "temperature"
                    4 -> "currency"
                    else -> "length"
                }
                updateSpinners()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        inputValue.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) convert()
        }

        updateSpinners()
    }

    private fun updateSpinners() {
        val (fromUnits, toUnits) = getUnitsForCategory(currentCategory)

        fromSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, fromUnits.map { it.second })
        toSpinner.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, toUnits.map { it.second })

        fromSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { convert() }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        toSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) { convert() }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun convert() {
        if (isUpdating) return
        isUpdating = true

        val input = inputValue.text.toString().toDoubleOrNull() ?: 0.0
        val fromUnit = getUnitsForCategory(currentCategory).first[fromSpinner.selectedItemPosition].first
        val toUnit = getUnitsForCategory(currentCategory).first[toSpinner.selectedItemPosition].first

        val result = when (currentCategory) {
            "length" -> convertLength(input, fromUnit, toUnit)
            "area" -> convertArea(input, fromUnit, toUnit)
            "weight" -> convertWeight(input, fromUnit, toUnit)
            "temperature" -> convertTemperature(input, fromUnit, toUnit)
            "currency" -> convertCurrency(input, fromUnit, toUnit)
            else -> 0.0
        }

        outputValue.text = formatResult(result)
        isUpdating = false
    }

    private fun convertLength(value: Double, from: String, to: String): Double {
        val meters = value * when (from) {
            "mm" -> 0.001; "cm" -> 0.01; "m" -> 1.0; "km" -> 1000.0
            "in" -> 0.0254; "ft" -> 0.3048; "yd" -> 0.9144; "mi" -> 1609.344
            else -> 1.0
        }
        return meters / when (to) {
            "mm" -> 0.001; "cm" -> 0.01; "m" -> 1.0; "km" -> 1000.0
            "in" -> 0.0254; "ft" -> 0.3048; "yd" -> 0.9144; "mi" -> 1609.344
            else -> 1.0
        }
    }

    private fun convertArea(value: Double, from: String, to: String): Double {
        val sqMeters = value * when (from) {
            "mm²" -> 0.000001; "cm²" -> 0.0001; "m²" -> 1.0; "km²" -> 1000000.0
            "in²" -> 0.00064516; "ft²" -> 0.092903; "yd²" -> 0.836127; "acre" -> 4046.8564; "ha" -> 10000.0
            else -> 1.0
        }
        return sqMeters / when (to) {
            "mm²" -> 0.000001; "cm²" -> 0.0001; "m²" -> 1.0; "km²" -> 1000000.0
            "in²" -> 0.00064516; "ft²" -> 0.092903; "yd²" -> 0.836127; "acre" -> 4046.8564; "ha" -> 10000.0
            else -> 1.0
        }
    }

    private fun convertWeight(value: Double, from: String, to: String): Double {
        val kg = value * when (from) {
            "mg" -> 0.000001; "g" -> 0.001; "kg" -> 1.0; "t" -> 1000.0
            "oz" -> 0.0283495; "lb" -> 0.453592; "st" -> 6.35029
            else -> 1.0
        }
        return kg / when (to) {
            "mg" -> 0.000001; "g" -> 0.001; "kg" -> 1.0; "t" -> 1000.0
            "oz" -> 0.0283495; "lb" -> 0.453592; "st" -> 6.35029
            else -> 1.0
        }
    }

    private fun convertTemperature(value: Double, from: String, to: String): Double {
        val celsius = when (from) {
            "°C" -> value
            "°F" -> (value - 32) * 5 / 9
            "K" -> value - 273.15
            else -> value
        }
        return when (to) {
            "°C" -> celsius
            "°F" -> celsius * 9 / 5 + 32
            "K" -> celsius + 273.15
            else -> celsius
        }
    }

    private fun convertCurrency(value: Double, from: String, to: String): Double {
        val rates = mapOf("CNY" to 1.0, "USD" to 7.24, "EUR" to 7.85, "JPY" to 0.048, "GBP" to 9.12, "HKD" to 0.93, "KRW" to 0.0054)
        val inCNY = value * (rates[from] ?: 1.0)
        return inCNY / (rates[to] ?: 1.0)
    }

    private fun getUnitsForCategory(category: String): Pair<List<Pair<String, String>>, List<Pair<String, String>>> {
        return when (category) {
            "length" -> Pair(
                listOf("mm" to "毫米", "cm" to "厘米", "m" to "米", "km" to "千米", "in" to "英寸", "ft" to "英尺", "yd" to "码", "mi" to "英里"),
                listOf("mm" to "毫米", "cm" to "厘米", "m" to "米", "km" to "千米", "in" to "英寸", "ft" to "英尺", "yd" to "码", "mi" to "英里")
            )
            "area" -> Pair(
                listOf("mm²" to "平方毫米", "cm²" to "平方厘米", "m²" to "平方米", "km²" to "平方千米", "in²" to "平方英寸", "ft²" to "平方英尺", "yd²" to "平方码", "acre" to "英亩", "ha" to "公顷"),
                listOf("mm²" to "平方毫米", "cm²" to "平方厘米", "m²" to "平方米", "km²" to "平方千米", "in²" to "平方英寸", "ft²" to "平方英尺", "yd²" to "平方码", "acre" to "英亩", "ha" to "公顷")
            )
            "weight" -> Pair(
                listOf("mg" to "毫克", "g" to "克", "kg" to "千克", "t" to "吨", "oz" to "盎司", "lb" to "磅", "st" to "英石"),
                listOf("mg" to "毫克", "g" to "克", "kg" to "千克", "t" to "吨", "oz" to "盎司", "lb" to "磅", "st" to "英石")
            )
            "temperature" -> Pair(
                listOf("°C" to "摄氏度", "°F" to "华氏度", "K" to "开尔文"),
                listOf("°C" to "摄氏度", "°F" to "华氏度", "K" to "开尔文")
            )
            "currency" -> Pair(
                listOf("CNY" to "人民币", "USD" to "美元", "EUR" to "欧元", "JPY" to "日元", "GBP" to "英镑", "HKD" to "港币", "KRW" to "韩元"),
                listOf("CNY" to "人民币", "USD" to "美元", "EUR" to "欧元", "JPY" to "日元", "GBP" to "英镑", "HKD" to "港币", "KRW" to "韩元")
            )
            else -> Pair(listOf("m" to "米"), listOf("m" to "米"))
        }
    }

    private fun formatResult(value: Double): String {
        return if (value == value.toLong().toDouble() && value < 1e10) {
            value.toLong().toString()
        } else {
            String.format("%.6f", value).trimEnd('0').trimEnd('.')
        }
    }
}
