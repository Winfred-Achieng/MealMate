package com.example.mealmate.retrofit

import com.example.mealmate.pojo.CategoryJson
import com.example.mealmate.pojo.MealsByCategoryJson
import com.example.mealmate.pojo.MealListJson
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal():Call<MealListJson>

    @GET("lookup.php?")
    fun getMealDetail(@Query("i")id:String):Call<MealListJson>

    @GET("filter.php?")
    fun getPopularItems(@Query("c")CategoryMeals:String): Call<MealsByCategoryJson>

    @GET("categories.php")
    fun getCategories(): Call<CategoryJson>

    @GET("filter.php?")
    fun getMealsByCategory(@Query("c")CategoryName:String): Call<MealsByCategoryJson>
}

