package com.example.mealmate.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mealmate.pojo.Meal

@Dao
interface MealDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertMeal(meal:Meal)

    @Delete
    suspend fun deleteMeal(meal: Meal)

    @Query("SELECT * FROM MealInformation")
    fun getAllMeal():LiveData<List<Meal>>
}