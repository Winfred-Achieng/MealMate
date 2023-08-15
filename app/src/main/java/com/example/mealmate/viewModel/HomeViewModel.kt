package com.example.mealmate.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mealmate.db.MealDatabase
import com.example.mealmate.pojo.*
import com.example.mealmate.retrofit.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDatabase: MealDatabase
):ViewModel() {

    private var randomMealLiveData = MutableLiveData<Meal>()
    private var popularItemsLiveData = MutableLiveData<List<MealsByCategory>>()
    private var categoriesLiveData = MutableLiveData<List<Category>>()
    private var favoritesMealsLiveData = mealDatabase.MealDao().getAllMeal()


    fun getRandomMeal(){
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealListJson> {
            override fun onResponse(call: Call<MealListJson>, response: Response<MealListJson>) {
                if (response.body()!= null){
                    val randomMeal: Meal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal

                    // Log.d("RAND","Meal id ${randomMeal.idMeal} name ${randomMeal.strMeal} ")
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealListJson>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetrofitInstance.api.getPopularItems("Seafood").enqueue(object :Callback<MealsByCategoryJson>{
            override fun onResponse(
                call: Call<MealsByCategoryJson>,
                response: Response<MealsByCategoryJson>
            ) {
                if(response.body()!=null){
                   popularItemsLiveData.value = response.body()!!.meals
                }
            }

            override fun onFailure(call: Call<MealsByCategoryJson>, t: Throwable) {
                Log.d("HomeFragment",t.message.toString())
            }
        })
    }

    fun getCategories(){
        RetrofitInstance.api.getCategories().enqueue(object: Callback<CategoryJson>{
            override fun onResponse(call: Call<CategoryJson>, response: Response<CategoryJson>) {
                if(response.body()!=null){
                    categoriesLiveData.value = response.body()!!.categories
                }else
                    return
            }

            override fun onFailure(call: Call<CategoryJson>, t: Throwable) {
                Log.d("HomeViewModel",t.message.toString())
            }
        })
    }

    fun deleteMeal(meal: Meal){
        viewModelScope.launch {
            mealDatabase.MealDao().deleteMeal(meal)
        }
    }
    fun insertMeal(meal:Meal){
        viewModelScope.launch {
            mealDatabase.MealDao().upsertMeal(meal)

        }
    }

    fun observeRandomMealLiveData():LiveData<Meal>{
        return randomMealLiveData
    }

    fun observePopularItemsLiveData(): LiveData<List<MealsByCategory>> {
        return popularItemsLiveData
    }

    fun observeCategoriesLiveData(): LiveData<List<Category>>{
        return categoriesLiveData
    }

    fun observeFavoritesMealsLiveData():LiveData<List<Meal>>{
        return favoritesMealsLiveData
    }
}