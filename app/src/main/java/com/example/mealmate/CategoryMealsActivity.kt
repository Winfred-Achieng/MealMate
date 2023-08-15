package com.example.mealmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mealmate.adapters.CategoryMealAdapter
import com.example.mealmate.databinding.ActivityCategoryMealsBinding
import com.example.mealmate.databinding.CategoryItemBinding
import com.example.mealmate.pojo.MealsByCategory
import com.example.mealmate.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryMealsBinding
    private lateinit var categoryMealsMvvm:CategoryMealsViewModel
    lateinit var categoryMealsAdapter:CategoryMealAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsMvvm = ViewModelProvider(this).get(CategoryMealsViewModel::class.java)

        categoryMealsMvvm.getMealsByCategory(intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!)

        categoryMealsMvvm.observeMealsLiveData().observe(this) { mealsList ->
            binding.tvCategoryCount.text = mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        }


    }

    private fun prepareRecyclerView() {
        categoryMealsAdapter = CategoryMealAdapter()
        binding.recViewMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }
}