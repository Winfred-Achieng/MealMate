package com.example.mealmate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealmate.databinding.CategoryItemBinding
import com.example.mealmate.databinding.MealItemBinding
import com.example.mealmate.pojo.MealsByCategory

class CategoryMealAdapter():RecyclerView.Adapter<CategoryMealAdapter.CategoryMealViewHolder>() {

    private  var mealsList = ArrayList<MealsByCategory>()

    fun setMealsList(mealsList: List<MealsByCategory>){
        this.mealsList = mealsList as ArrayList<MealsByCategory>
        notifyDataSetChanged()
    }

    inner class CategoryMealViewHolder(val binding: MealItemBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMealViewHolder {
       return CategoryMealViewHolder(
           MealItemBinding.inflate(
               LayoutInflater.from(parent.context)
           ))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: CategoryMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgMeal)
        holder.binding.tvMeal.text = mealsList[position].strMeal
    }
}