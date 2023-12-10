package com.example.betterfit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class DiscoverWorkoutsActivity : AppCompatActivity() {

    private lateinit var recommendedWorkoutTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discover_workouts)

        // Get user information from MainActivity
        val age = intent.getIntExtra("age", 0)
        val sex = intent.getStringExtra("sex") ?: "Unknown"
        val heightFeet = intent.getDoubleExtra("heightFeet", 0.0)
        val heightInches = intent.getDoubleExtra("heightInches", 0.0)
        val weight = intent.getDoubleExtra("weight", 0.0)

        // Get user information from CounterActivity
        val consumedCalories = intent.getIntExtra("consumedCalories", 0)
        val burnedCalories = intent.getIntExtra("burnedCalories", 0)
        val remainingCalories = intent.getIntExtra("remainingCalories", 0)
        val bmi = intent.getDoubleExtra("bmi", 0.0)

        // Initialize UI components
        recommendedWorkoutTextView = findViewById(R.id.recommendedWorkoutTextView)

        // Add logic to recommend workouts based on user input
        val recommendedWorkout = recommendWorkoutBasedOnInput(age, sex, heightFeet, heightInches, weight, remainingCalories, bmi)
        recommendedWorkoutTextView.text = recommendedWorkout
    }

    private fun recommendWorkoutBasedOnInput(
        age: Int,
        sex: String,
        heightFeet: Double,
        heightInches: Double,
        weight: Double,
        remainingCalories: Int,
        bmi: Double
    ): String {
        val totalHeightInInches = heightFeet * 12 + heightInches

        // Adjust workout recommendations based on age, sex, and goal
        val ageGroup = getAgeGroup(age)

        val ageSpecificRecommendation = when (ageGroup) {
            "Teenager" -> "Include age-appropriate workouts with a focus on overall fitness."
            "Young Adult" -> "Explore a variety of exercises to find what you enjoy."
            "Adult" -> "Incorporate a mix of cardio and strength training for overall health."
            else -> ""
        }

        val goalSpecificRecommendation = when (remainingCalories) {
            in Int.MIN_VALUE..500 -> "Focus on calorie-burning workouts and cardio exercises."
            in 501..1000 -> "Consider a mix of cardio and strength training for balanced results."
            else -> "Incorporate strength training and muscle-building exercises."
        }

        val bmiSpecificRecommendation = when {
            bmi < 18.5 -> "Consider workouts that focus on building muscle mass and overall strength."
            bmi >= 25 -> "Include high-intensity cardio workouts to aid in weight management."
            else -> "Maintain a balanced workout routine for overall health."
        }

        return "$goalSpecificRecommendation\n$ageSpecificRecommendation\n$bmiSpecificRecommendation"
    }

    private fun getAgeGroup(age: Int): String {
        return when {
            age <= 18 -> "Teenager"
            age <= 30 -> "Young Adult"
            else -> "Adult"
        }
    }
}
