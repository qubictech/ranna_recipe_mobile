package rannaghor.recipe.tarmsbd.com.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable

import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.adapter.AllRecipeAdapter
import rannaghor.recipe.tarmsbd.com.adapter.RecipeCategoryAdapter
import rannaghor.recipe.tarmsbd.com.service.RannaghorRetrofitService
import rannaghor.recipe.tarmsbd.com.service.RetrofitClient
import rannaghor.recipe.tarmsbd.com.viewmodel.RannaghorViewModel

class ExploreRecipeFragment : Fragment(R.layout.fragment_explore_recipe) {
    private lateinit var recyclerViewExploreByCategory: RecyclerView
    private lateinit var recyclerViewPopularRecipe: RecyclerView
    private lateinit var rannaghorViewModel: RannaghorViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewExploreByCategory = view.findViewById(R.id.recipe_categories)
        recyclerViewPopularRecipe = view.findViewById(R.id.popular_recipe)
        rannaghorViewModel = ViewModelProvider(this).get(RannaghorViewModel::class.java)

        getAllCategories()
        getAllRecipes()

        rannaghorViewModel.getRecipeCategoryAndRecipeListFromNetwork()
    }

    private fun getAllCategories() {
        rannaghorViewModel.getCategories().observe(viewLifecycleOwner, Observer {
            val categoryAdapter = context?.let { it1 -> RecipeCategoryAdapter(it1, it) }
            recyclerViewExploreByCategory.apply {
                hasFixedSize()
                layoutManager = GridLayoutManager(context, 4)
                adapter = categoryAdapter
            }
        })
    }

    private fun getAllRecipes() {
        rannaghorViewModel.getAllRecipes().observe(viewLifecycleOwner, Observer {
            val recipeAdapter = context?.let { it1 -> AllRecipeAdapter(it1, it) }
            recyclerViewPopularRecipe.apply {
                hasFixedSize()
                layoutManager = LinearLayoutManager(context)
                adapter = recipeAdapter
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExploreRecipeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

}
