package com.example.betterfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.*
import android.content.Intent

class MainActivity : ComponentActivity() {
    private lateinit var ageEditText: EditText
    private lateinit var sexSpinner: Spinner
    private lateinit var heightFeetEditText: EditText
    private lateinit var heightInchesEditText: EditText
    private lateinit var weightEditText: EditText
    private lateinit var goalRadioGroup: RadioGroup
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ageEditText = findViewById(R.id.ageEditText)
        sexSpinner = findViewById(R.id.sexSpinner)
        heightFeetEditText = findViewById(R.id.heightFeetEditText)
        heightInchesEditText = findViewById(R.id.heightInchesEditText)
        weightEditText = findViewById(R.id.weightEditText)
        goalRadioGroup = findViewById(R.id.goalRadioGroup)

        // Set up the Spinner for sex selection
        val sexOptions = arrayOf("Male", "Female")
        val sexAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sexOptions)
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sexSpinner.adapter = sexAdapter

        val calculateButton: Button = findViewById(R.id.calculateButton)
        calculateButton.setOnClickListener {
            calculateCaloricGoal()
        }
    }

    private fun calculateCaloricGoal() {
        val age = ageEditText.text.toString().toInt()
        val sex = sexSpinner.selectedItem.toString()
        val heightFeet = heightFeetEditText.text.toString().toDouble()
        val heightInches = heightInchesEditText.text.toString().toDouble()
        val weight = weightEditText.text.toString().toDouble()

        val bmr = if (sex == "Male") {
            66.47 + (6.24 * weight) + (12.7 * (heightFeet * 12 + heightInches)) - (6.75 * age)
        } else {
            655.1 + (4.35 * weight) + (4.7 * (heightFeet * 12 + heightInches)) - (4.7 * age)
        }

        // Set up spinner for Weight Goal
        val goalSpinner: Spinner = findViewById(R.id.goalSpinner)

        // Move the code for obtaining the selected item here
        val selectedGoal = goalSpinner.selectedItem.toString()

        val adjustment = when (selectedGoal) {
            "Weight Loss (1 pound per week)" -> -500
            "Weight Gain (1 pound per week)" -> +500
            "Extreme Weight Loss (2 pounds per week)" -> -1000
            "Extreme Weight Gain (2 pounds per week)" -> +1000
            else -> 0
        }

        val caloricGoal = (bmr + adjustment).toInt()

        val intent = Intent(this, CounterActivity::class.java)
        intent.putExtra("caloricGoal", caloricGoal)
        startActivity(intent)
    }
}