package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Declare UI components
    private lateinit var editTextA: EditText
    private lateinit var editTextB: EditText
    private lateinit var editTextC: EditText
    private lateinit var editTextD: EditText
    private lateinit var editTextE: EditText
    private lateinit var editTextF: EditText
    private lateinit var editTextG: EditText
    private lateinit var buttonCalculate: Button
    private lateinit var textViewResultDry: TextView
    private lateinit var textViewResultBurn: TextView
    private lateinit var textViewKDry: TextView
    private lateinit var textViewKBurn: TextView
    private lateinit var textViewLowerBurningTemp: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Set the content view to the updated layout
        setContentView(R.layout.activity_main)

        // Initialize UI components
        editTextA = findViewById(R.id.editTextA)
        editTextB = findViewById(R.id.editTextB)
        editTextC = findViewById(R.id.editTextC)
        editTextD = findViewById(R.id.editTextD)
        editTextE = findViewById(R.id.editTextE)
        editTextF = findViewById(R.id.editTextF)
        editTextG = findViewById(R.id.editTextG)
        buttonCalculate = findViewById(R.id.buttonCalculate)
        textViewResultDry = findViewById(R.id.textViewResultDry)
        textViewResultBurn = findViewById(R.id.textViewResultBurn)
        textViewKDry = findViewById(R.id.textViewKDry)
        textViewKBurn = findViewById(R.id.textViewKBurn)
        textViewLowerBurningTemp = findViewById(R.id.textViewLowerBurningTemp)

        // Set click listener for the calculate button
        buttonCalculate.setOnClickListener {
            calculateResult()
        }
    }

    private fun calculateKDry(w: Double): Double {
        return 100 / (100 - w)
    }

    private fun calculateKBurn(w: Double, a_burn: Double): Double {
        return 100 / (100 - w - a_burn)
    }

    private fun calculateLowerBurningTemp(a:Double, b:Double, c:Double, d:Double, e:Double): Double {
        return (339*a + 1030*b - 108*(c-d) - 25*e )*0.001
    }

    private fun calculateWarmDry(q:Double, w: Double): Double {
        return (q + 0.025*w) * (100/(100-w))
    }

    private fun calculateWarmBurn(q:Double, w: Double, a:Double): Double {
        return (q + 0.025*w) * (100/(100-w-a))
    }

    // Function to perform the calculation
    private fun calculateResult() {
        // Retrieve and validate input values
        val aText = editTextA.text.toString()
        val bText = editTextB.text.toString()
        val cText = editTextC.text.toString()
        val dText = editTextD.text.toString()
        val eText = editTextE.text.toString()
        val fText = editTextF.text.toString()
        val gText = editTextG.text.toString()

        // Convert input strings to Double
        val a = aText.toDouble()
        val b = bText.toDouble()
        val c = cText.toDouble()
        val d = dText.toDouble()
        val e = eText.toDouble()
        val f = fText.toDouble()
        val g = gText.toDouble()

        val k_dry = calculateKDry(f)
        val k_burn = calculateKBurn(f, g)

        val h_dry = k_dry * a
        val c_dry = k_dry * b
        val s_dry = k_dry * c
        val n_dry = k_dry * d
        val o_dry = k_dry * e
        val a_dry = k_dry * g

        val formatted_h_dry = String.format("%.2f", h_dry)
        val formatted_c_dry = String.format("%.2f", c_dry)
        val formatted_s_dry = String.format("%.2f", s_dry)
        val formatted_n_dry = String.format("%.2f", n_dry)
        val formatted_o_dry = String.format("%.2f", o_dry)
        val formatted_a_dry = String.format("%.2f", a_dry)

        val h_burn = k_burn * a
        val c_burn = k_burn * b
        val s_burn = k_burn * c
        val n_burn = k_burn * d
        val o_burn = k_burn * e

        val formatted_h_burn = String.format("%.2f", h_burn)
        val formatted_c_burn = String.format("%.2f", c_burn)
        val formatted_s_burn = String.format("%.2f", s_burn)
        val formatted_n_burn = String.format("%.2f", n_burn)
        val formatted_o_burn = String.format("%.2f", o_burn)

        val lower_burning_temp = calculateLowerBurningTemp(b, a, e, c, f)

        val lower_burning_temp_formated = String.format("%.2f", lower_burning_temp)

        val warm_dry = calculateWarmDry(lower_burning_temp, f)
        val warm_burn = calculateWarmBurn(lower_burning_temp, f, g)

        // Display the result
        textViewKDry.text = "Склад сухої маси палива: H - $formatted_h_dry%, C - $formatted_c_dry%, S - $formatted_s_dry%, N - $formatted_n_dry%, O -$formatted_o_dry%, A - $formatted_a_dry%"
        textViewKBurn.text = "Склад горючої маси палива: H - $formatted_h_burn%, C - $formatted_c_burn%, S - $formatted_s_burn%, N - $formatted_n_burn%, O -$formatted_o_burn%"
        textViewLowerBurningTemp.text = "Нижча теплота згоряння: $lower_burning_temp МДж/кг"
        textViewResultDry.text = "Суха маса: $warm_dry МДж/кг"
        textViewResultBurn.text = "Горюча маса: $warm_burn МДж/кг"
    }
}