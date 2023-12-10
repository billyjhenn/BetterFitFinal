package com.example.betterfit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class DiscoverMealsActivity : AppCompatActivity() {

    private lateinit var recommendedMealTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_meals)

        // Get user information from MainActivity
        val age = intent.getIntExtra("age", 0)
        val sex = intent.getStringExtra("sex") ?: "Unknown"
        val heightFeet = intent.getDoubleExtra("heightFeet", 0.0)
        val heightInches = intent.getDoubleExtra("heightInches", 0.0)
        val weight = intent.getDoubleExtra("weight", 0.0)

        // Get user information from CounterActivity
        val consumedCalories = intent.getIntExtra("consumedCalories", 0)
        val burnedCalories = intent.getIntExtra("burnedCalories", 0)
        val caloricGoal = intent.getIntExtra("caloricGoal", 0)

        // Calculate remaining calories
        val remainingCalories = caloricGoal - consumedCalories + burnedCalories

        // Initialize UI components
        recommendedMealTextView = findViewById(R.id.recommendedMealTextView)

        // Add logic to recommend meals based on user input
        val recommendedMeal = recommendMealBasedOnInput(remainingCalories, age, sex, heightFeet, heightInches, weight)
        recommendedMealTextView.text = recommendedMeal
    }

    private fun recommendMealBasedOnInput(
        remainingCalories: Int,
        age: Int,
        sex: String,
        heightFeet: Double,
        heightInches: Double,
        weight: Double
    ): String {
        val bmi = calculateBMI(heightFeet, heightInches, weight)
        val ageGroup = getAgeGroup(age)

        // Check if the user is underweight, overweight, or obese
        val bmiStatus = getBMIStatus(bmi)

        return when {
            remainingCalories > 500 -> "Recommended Meal: Salmon with Quinoa and Vegetables suitable for $sex in the $ageGroup age group with BMI $bmi\n${
                getWarningMessage(
                    bmiStatus,
                    sex
                )
            }"
            remainingCalories > 200 -> "Recommended Meal: Grilled Chicken Salad suitable for $sex in the $ageGroup age group with BMI $bmi\n${
                getWarningMessage(
                    bmiStatus,
                    sex
                )
            }"
            remainingCalories > 0 -> "Recommended Meal: Vegetable Stir-Fry suitable for $sex in the $ageGroup age group with BMI $bmi\n${
                getWarningMessage(
                    bmiStatus,
                    sex
                )
            }"
            else -> "No specific meal recommendation. Consider a balanced meal with proper nutrients."
        }
    }

    private fun calculateBMI(heightFeet: Double, heightInches: Double, weight: Double): Double {
        val totalHeightInInches = heightFeet * 12 + heightInches
        return (weight / (totalHeightInInches * totalHeightInInches)) * 703
    }

    private fun getAgeGroup(age: Int): String {
        return when {
            age <= 18 -> "Teenager"
            age <= 30 -> "Young Adult"
            else -> "Adult"
        }
    }

    private fun getBMIStatus(bmi: Double): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi < 24.9 -> "Normal Weight"
            bmi < 29.9 -> "Overweight"
            else -> "Obese"
        }
    }

    private fun getWarningMessage(bmiStatus: String, sex: String): String {
        return when (bmiStatus) {
            "Underweight" -> "Warning: Underweight may have health risks. Consider consulting a healthcare professional."
            "Overweight" -> "Warning: Overweight may have health risks. Consider consulting a healthcare professional."
            "Obese" -> "Warning: Obesity may have health risks. Consider consulting a healthcare professional."
            else -> {
                // No warning for normal weight
                if (sex == "Female") {
                    "Note: Consult with a healthcare professional for personalized advice."
                } else {
                    "Note: Consider maintaining a healthy lifestyle for overall well-being."
                }
            }
        }
    }
}
