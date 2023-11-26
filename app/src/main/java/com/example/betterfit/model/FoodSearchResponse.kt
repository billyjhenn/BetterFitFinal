package com.example.betterfit.model
import com.google.gson.annotations.SerializedName

data class FoodSearchResponse(
    val foods: List<FoodItem>
)

data class FoodItem(
    val fdcId: Int,
    val description: String,
    val brandOwner: String?,
    val ingredients: String?,
    val foodNutrients: List<FoodNutrient>?,
    @SerializedName("servingSize")
    val servingSize: Double?,
    @SerializedName("servingSizeUnit")
    val servingSizeUnit: String?
)

data class FoodNutrient(
    val nutrientId: Int,
    val nutrientName: String,
    val unitName: String,
    val value: Double
)