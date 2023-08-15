package com.example.mealmate



import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mealmate.adapters.CategoriesAdapter
import com.example.mealmate.adapters.MostPopularAdapter
import com.example.mealmate.databinding.FragmentHomeBinding
import com.example.mealmate.pojo.MealsByCategory
import com.example.mealmate.pojo.Meal
import com.example.mealmate.viewModel.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel:HomeViewModel
    private lateinit var randomMeal: Meal
    private lateinit var popularItemsAdapter:MostPopularAdapter
    private lateinit var categoriesAdapter:CategoriesAdapter

    companion object{
        const val MEAL_ID = "com.example.mealmate.idMeal"
        const val MEAL_NAME ="com.example.mealmate.nameMeal"
        const val MEAL_THUMB ="com.example.mealmate.thumbMeal"
        const val CATEGORY_NAME="com.example.mealmate.categoryName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //homeMvvm = ViewModelProvider(this).get(HomeViewModel::class.java)....moved to main activity

        viewModel = (activity as MainActivity).viewModel

        popularItemsAdapter = MostPopularAdapter()
        categoriesAdapter =CategoriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()

        viewModel.getRandomMeal()
        observeRandomMeal()
        onRandomMealClick()

        viewModel.getPopularItems()
        observePopularItemsLiveData()
        onPopularItemClicked()

        prepareCategoriesRecyclerView()

        viewModel.getCategories()
        observeCategoriesLiveData()
        onCategoryClick()


    }

    private fun onCategoryClick() {
        categoriesAdapter.onItemClick ={category ->  
           val intent = Intent(activity,CategoryMealsActivity::class.java)
            intent.putExtra(CATEGORY_NAME,category.strCategory)
            startActivity(intent)
        }
    }

    private fun prepareCategoriesRecyclerView() {
        binding.recViewCategories.apply {
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
            adapter =categoriesAdapter
        }
    }

    private fun observeCategoriesLiveData() {
        viewModel.observeCategoriesLiveData().observe(viewLifecycleOwner
        ) { categories->

                categoriesAdapter.setCategoriesList(categories)

            }

    }

    private fun onPopularItemClicked() {
        popularItemsAdapter.onItemsClick ={ meal ->
            val intent =Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewPopularItems.apply {
            layoutManager =LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
       viewModel.observePopularItemsLiveData().observe(viewLifecycleOwner
       ) { mealList->
           popularItemsAdapter.setMeals(mealsList = mealList as ArrayList<MealsByCategory>)
       }
    }

    private fun onRandomMealClick() {
        binding.cardRandomMeal.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra(MEAL_ID,randomMeal.idMeal)
            intent.putExtra(MEAL_NAME,randomMeal.strMeal)
            intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
            startActivity(intent)


        }
    }

    private fun observeRandomMeal() {
        viewModel.observeRandomMealLiveData().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)
            this.randomMeal=meal
        }
    }


}