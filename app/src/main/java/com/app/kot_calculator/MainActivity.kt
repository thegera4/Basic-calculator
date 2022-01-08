package com.app.kot_calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.app.kot_calculator.databinding.ActivityMainBinding

import kotlin.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var lastNumeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigit(view: View) {
        //para obtener lo que tenga de texto el boton y pasarlo al textview
        binding.tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onDecimalPoint(view: View) {
        if (lastNumeric && !lastDot) {
            binding.tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View){
        if(lastNumeric && !isOperatorAdded(binding.tvInput.text.toString())){
            binding.tvInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    private fun isOperatorAdded(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/") || value.contains("-") || value.contains("+") || value.contains("*")
        }
    }

    fun onClear(view: View) {
        binding.tvInput.text = ""
        lastNumeric = false
        lastDot = false
    }

    fun onEqual(view: View) {
        if(lastNumeric){
            var tvValue = binding.tvInput.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                when {

                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()){
                            one = prefix + one
                        }
                        binding.tvInput.text = remoZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }

                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        binding.tvInput.text = remoZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }

                    tvValue.contains("*") -> {
                        val splitValue = tvValue.split("*")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        binding.tvInput.text = remoZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                    }

                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        binding.tvInput.text = remoZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }
                }

            }  catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

    private fun remoZeroAfterDot(result: String) : String{
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }
}


