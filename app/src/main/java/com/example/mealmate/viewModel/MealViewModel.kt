package com.example.mealmate.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.db.MealDatabase
import com.example.mealmate.pojo.Meal
import com.example.mealmate.pojo.MealListJson
import com.example.mealmate.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealViewModel(
   val mealDatabase:MealDatabase
) :ViewModel(){
    private var mealDetailsLiveData = MutableLiveData<Meal>()

    fun getMealDetail(id:String) {
        RetrofitInstance.api.getMealDetail(id).enqueue(object : Callback<MealListJson>{
            override fun onResponse(call: Call<MealListJson>, response: Response<MealListJson>) {
                if(response.body()!=null){
                    mealDetailsLiveData.value = response.body()!!.meals[0]
                }
                else {
                    return
                }
            }

            override fun onFailure(call: Call<MealListJson>, t: Throwable) {
                Log.d("Meal Activity",t.message.toString())
            }
        })
    }

    fun observeMealDetailsLiveData() : LiveData<Meal>{
        return mealDetailsLiveData
    }

    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.MealDao().upsertMeal(meal)

        }

    }
}