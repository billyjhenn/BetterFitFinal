// CounterActivity.kt
package com.example.betterfit

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CounterActivity : AppCompatActivity() {
    private lateinit var breakfastCaloriesEditText: EditText
    private lateinit var lunchCaloriesEditText: EditText
    private lateinit var dinnerCaloriesEditText: EditText
    private lateinit var snacksCaloriesEditText: EditText
    private lateinit var burnedCaloriesEditText: EditText
    private lateinit var consumedCaloriesTextView: TextView
    private lateinit var caloriesGoalTextView: TextView
    private lateinit var burnedCaloriesTextView: TextView
    private lateinit var remainingCaloriesTextView: TextView

    private var caloricGoal: Int = 0
    private var consumedCalories: Int = 0
    private var burnedCalories: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_counter)

        caloricGoal = intent.getIntExtra("caloricGoal", 0)
        val age = intent.getIntExtra("age", 0)
        val sex = intent.getStringExtra("sex") ?: "Unknown"
        val heightFeet = intent.getDoubleExtra("heightFeet", 0.0)
        val heightInches = intent.getDoubleExtra("heightInches", 0.0)
        val weight = intent.getDoubleExtra("weight", 0.0)
        val selectedGoal = intent.getStringExtra("selectedGoal") ?: "Maintain"

        // Initialize UI components
        breakfastCaloriesEditText = findViewById(R.id.BreakfastCaloriesEditText)
        lunchCaloriesEditText = findViewById(R.id.LunchCaloriesEditText)
        dinnerCaloriesEditText = findViewById(R.id.DinnerCaloriesEditText)
        snacksCaloriesEditText = findViewById(R.id.SnacksCaloriesEditText)
        burnedCaloriesEditText = findViewById(R.id.burnedCaloriesEditText)
        consumedCaloriesTextView = findViewById(R.id.consumedCaloriesTextView)
        caloriesGoalTextView = findViewById(R.id.caloriesGoalTextView)
        burnedCaloriesTextView = findViewById(R.id.burnedCaloriesTextView)
        remainingCaloriesTextView = findViewById(R.id.remainingCaloriesTextView)

        // Set initial caloric goal
        caloriesGoalTextView.text = caloricGoal.toString()

        // Set up buttons
        val addBurnedCaloriesButton: Button = findViewById(R.id.caloriesBurnedButton)
        val addCaloriesEatenButton: Button = findViewById(R.id.caloriesEatenButton)

        // Set up buttons for navigation
        val searchFoodButton: Button = findViewById(R.id.SearchFoodButton)
        val discoverMealsButton: Button = findViewById(R.id.discoverMealsButton)
        val discoverWorkoutsButton: Button = findViewById(R.id.discoverWorkoutsButton)

        addBurnedCaloriesButton.setOnClickListener {
            addBurnedCalories()
        }

        addCaloriesEatenButton.setOnClickListener {
            addCaloriesEaten()
        }

        // Set up listeners for navigation buttons
        searchFoodButton.setOnClickListener {
            // Navigate to the Search Food screen
            val intent = Intent(baseContext, SearchFoodActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        discoverMealsButton.setOnClickListener {
            // Pass relevant information to DiscoverMealsActivity
            val intent = Intent(this, DiscoverMealsActivity::class.java)
            intent.putExtra("consumedCalories", consumedCalories)
            intent.putExtra("burnedCalories", burnedCalories)
            intent.putExtra("caloricGoal", caloricGoal)
            intent.putExtra("age", age)
            intent.putExtra("sex", sex)
            intent.putExtra("heightFeet", heightFeet)
            intent.putExtra("heightInches", heightInches)
            intent.putExtra("weight", weight)
            intent.putExtra("selectedGoal", selectedGoal)
            startActivity(intent)
        }

        discoverWorkoutsButton.setOnClickListener {
            // Navigate to the Discover Workouts screen
            startActivity(Intent(this, DiscoverWorkoutsActivity::class.java))
        }
    }

    private fun addBurnedCalories() {
        val burnedCaloriesInput = burnedCaloriesEditText.text.toString()
        if (burnedCaloriesInput.isNotEmpty()) {
            val burnedCaloriesValue = burnedCaloriesInput.toInt()
            burnedCalories += burnedCaloriesValue
            updateCalories()
        }
    }

    private fun addCaloriesEaten() {
        // Default values to zero if the input is empty
        val breakfastCalories = breakfastCaloriesEditText.text.toString().toIntOrNull() ?: 0
        val lunchCalories = lunchCaloriesEditText.text.toString().toIntOrNull() ?: 0
        val dinnerCalories = dinnerCaloriesEditText.text.toString().toIntOrNull() ?: 0
        val snacksCalories = snacksCaloriesEditText.text.toString().toIntOrNull() ?: 0

        val consumedCaloriesValue = breakfastCalories + lunchCalories + dinnerCalories + snacksCalories
        consumedCalories += consumedCaloriesValue
        updateCalories()
    }

    private fun updateCalories() {
        val remainingCalories = caloricGoal + burnedCalories - consumedCalories
        consumedCaloriesTextView.text = consumedCalories.toString()
        burnedCaloriesTextView.text = burnedCalories.toString()

        remainingCaloriesTextView.text = remainingCalories.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // Save data to outState before the activity is destroyed
        outState.putInt("caloricGoal", caloricGoal)
        super.onSaveInstanceState(outState)
    }
}
