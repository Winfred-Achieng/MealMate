package com.example.mealmate.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealmate.databinding.PopularItemsBinding
import com.example.mealmate.pojo.MealsByCategory

class MostPopularAdapter: RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>(){
    private var mealsList = ArrayList<MealsByCategory>()
    lateinit var onItemsClick:((MealsByCategory) -> Unit)
    class PopularMealViewHolder( val binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root)



    fun setMeals (mealsList: ArrayList<MealsByCategory>){
        this.mealsList =mealsList
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener{
            onItemsClick.invoke(mealsList[position])
        }
    }
}