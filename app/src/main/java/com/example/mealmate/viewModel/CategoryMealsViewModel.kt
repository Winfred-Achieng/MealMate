package com.example.mealmate.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mealmate.pojo.Category
import com.example.mealmate.pojo.MealsByCategory
import com.example.mealmate.pojo.MealsByCategoryJson
import com.example.mealmate.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel(): ViewModel() {
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()


    fun getMealsByCategory(CategoryName:String){
        RetrofitInstance.api.getMealsByCategory(CategoryName).enqueue(object :Callback<MealsByCategoryJson>{
            override fun onResponse(
                call: Call<MealsByCategoryJson>,
                response: Response<MealsByCategoryJson>
            ) {
                if(response.body()!=null){
                    mealsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryJson>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    fun observeMealsLiveData():LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}