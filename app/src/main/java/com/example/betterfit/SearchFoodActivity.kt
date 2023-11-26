package com.example.betterfit

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.betterfit.model.FoodSearchResponse
import com.example.betterfit.model.RetrofitClient
import com.example.betterfit.model.FoodApiService

class SearchFoodActivity : AppCompatActivity() {
    private lateinit var foodSearchEditText: EditText
    private lateinit var searchButton: Button
    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_food)

        // Initialize UI components
        foodSearchEditText = findViewById(R.id.foodSearchEditText)
        searchButton = findViewById(R.id.searchButton)
        resultTextView = findViewById(R.id.resultTextView)

        // Example of using Retrofit to search for food
        val foodApiService = RetrofitClient.instance

        searchButton.setOnClickListener {
            val query = foodSearchEditText.text.toString().trim()
            if (query.isNotEmpty()) {
                makeApiCall(foodApiService, query)
            } else {
                // Show a message or handle empty query
            }
        }
    }

    private fun makeApiCall(foodApiService: FoodApiService, query: String) {
        // Add your actual API key here
        val apiKey = "bdGchKSs5C5D5P4V4FS2w5wMi6oGbG9Us3PJkILP"

        // Make a sample API call
        val call = foodApiService.searchFood(apiKey, query)
        call.enqueue(object : Callback<FoodSearchResponse> {
            override fun onResponse(
                call: Call<FoodSearchResponse>,
                response: Response<FoodSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val foodList = response.body()?.foods
                    // Process the foodList
                    // For simplicity, let's concatenate results into a single string
                    val resultText = StringBuilder()
                    foodList?.forEach { foodItem ->
                        // Check if brandOwner is available and append it to the resultText
                        foodItem.brandOwner?.let { brandOwner ->
                            resultText.append("Brand: $brandOwner\n")
                        }
                        // Append food description to the resultText
                        resultText.append("Food: ${foodItem.description}\n")
                        // Check if foodNutrients is available
                        foodItem.foodNutrients?.forEach { nutrient ->
                            // Check if the nutrient is "Energy" and append calories to the resultText
                            if (nutrient.nutrientName.equals("Energy", ignoreCase = true)) {
                                val caloriesText =
                                    "Calories per Serving: ${nutrient.value} ${nutrient.unitName}\n"
                                resultText.append(caloriesText)
                            }
                        }
                        // Check if servingSize is available and append it to the resultText
                        foodItem.servingSize?.let { servingSize ->
                            resultText.append("Serving Size: ")
                            when (servingSize) {
                                is Double -> {
                                    // Handle the case where servingSize is a number
                                    resultText.append("$servingSize")
                                    // Check if unitName is not null or empty before appending
                                    if (!foodItem.servingSizeUnit.isNullOrBlank()) {
                                        resultText.append(" ${foodItem.servingSizeUnit}\n")
                                    } else {
                                        resultText.append("\n")
                                    }
                                }

                                else -> {
                                    // Handle other cases if needed
                                    resultText.append("$servingSize\n")
                                }
                            }
                        }

                        // Add a separator line between food items
                        resultText.append("-----\n")
                    }

                    // Display the result in TextView
                    resultTextView.text = resultText.toString()
                } else {
                    // Handle error
                    Log.e("SearchFoodActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FoodSearchResponse>, t: Throwable) {
                // Handle failure
                Log.e("SearchFoodActivity", "Failure: ${t.message}")
            }
        })
    }
}