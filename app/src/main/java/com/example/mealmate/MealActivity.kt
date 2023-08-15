package com.example.mealmate

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.mealmate.databinding.ActivityMealBinding
import com.example.mealmate.db.MealDatabase
import com.example.mealmate.pojo.Meal
import com.example.mealmate.pojo.MealListJson
import com.example.mealmate.viewModel.HomeViewModel
import com.example.mealmate.viewModel.MealViewModel
import com.example.mealmate.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var binding: ActivityMealBinding
    private lateinit var  mealMvvm: MealViewModel
    private lateinit var youtubeLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        mealMvvm = ViewModelProvider(this).get(MealViewModel::class.java)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this,viewModelFactory).get(MealViewModel::class.java)

        getMealInformationFromIntent()

        setInformationInViews()

        loadingCase()

        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()

        onYoutubeImgClick()
        onFavoriteClick()
    }

    private fun onFavoriteClick() {
        binding.favFloatingBtn.setOnClickListener{
         mealToSave?.let {
             mealMvvm.insertMeal(it)
              Toast.makeText(this,"Meal Saved",Toast.LENGTH_SHORT).show()
         }
        }
    }

    private fun onYoutubeImgClick() {
        binding.imgYoutube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private var mealToSave:Meal? = null

    private fun observeMealDetailsLiveData() {
        mealMvvm.observeMealDetailsLiveData().observe(this,object : Observer<Meal>{
            override fun onChanged(t: Meal?) {
                onResponseCase()
              val meal = t
                mealToSave =meal

                binding.tvMealCategory.text = "Category : ${meal!!.strCategory}"
                binding.tvArea.text = "Area : ${meal.strArea}"
                binding.tvDetInstruction.text = meal.strInstructions

                youtubeLink = meal.strYoutube.toString()
            }
        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolBar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getMealInformationFromIntent() {
         val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.favFloatingBtn.visibility = View.INVISIBLE
        binding.tvArea.visibility = View.INVISIBLE
        binding.tvMealCategory.visibility = View.INVISIBLE
        binding.tvInstruction.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.favFloatingBtn.visibility = View.VISIBLE
        binding.tvArea.visibility = View.VISIBLE
        binding.tvMealCategory.visibility = View.VISIBLE
        binding.tvInstruction.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

}