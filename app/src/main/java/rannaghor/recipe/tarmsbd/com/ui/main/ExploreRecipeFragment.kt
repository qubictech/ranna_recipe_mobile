package rannaghor.recipe.tarmsbd.com.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.adapter.AllRecipeAdapter
import rannaghor.recipe.tarmsbd.com.adapter.RecipeCategoryAdapter
import rannaghor.recipe.tarmsbd.com.model.Category
import rannaghor.recipe.tarmsbd.com.service.OnClickEventListener
import rannaghor.recipe.tarmsbd.com.ui.recipebycategory.RecipeListActivity
import rannaghor.recipe.tarmsbd.com.viewmodel.RannaghorViewModel

class ExploreRecipeFragment : Fragment(R.layout.fragment_explore_recipe), OnClickEventListener {
    private lateinit var recyclerViewExploreByCategory: RecyclerView
    private lateinit var recyclerViewPopularRecipe: RecyclerView
    private lateinit var rannaghorViewModel: RannaghorViewModel

    private var categories = mutableListOf<Category>()

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh_layout)
        swipeRefreshLayout.isRefreshing = true

        recyclerViewExploreByCategory = view.findViewById(R.id.recipe_categories)
        recyclerViewPopularRecipe = view.findViewById(R.id.popular_recipe)
        rannaghorViewModel = ViewModelProvider(this).get(RannaghorViewModel::class.java)

        getAllCategories()
        getAllRecipes()

        swipeRefreshLayout.setOnRefreshListener {
            getAllCategories()
            getAllRecipes()
        }

        rannaghorViewModel.getRecipeCategoryAndRecipeListFromNetwork()
    }

    private fun getAllCategories() {
        rannaghorViewModel.getCategories().observe(viewLifecycleOwner, Observer {
            val categoryAdapter = context?.let { it1 -> RecipeCategoryAdapter(it1, it) }
            categories.addAll(it)
            categoryAdapter?.setOnClickEventListener(this)
            recyclerViewExploreByCategory.apply {
                hasFixedSize()
                layoutManager = GridLayoutManager(context, 4)
                adapter = categoryAdapter
            }
        })
    }

    private fun getAllRecipes() {
        rannaghorViewModel.getAllRecipes().observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) swipeRefreshLayout.isRefreshing = false

            val recipeAdapter = context?.let { it1 -> AllRecipeAdapter(it1, it) }
            recyclerViewPopularRecipe.apply {
                hasFixedSize()
                layoutManager = LinearLayoutManager(context)
                adapter = recipeAdapter
            }
        })
    }

    companion object {
        const val CATEGORY_NAME = "rannaghor.recipe.tarmsbd.com.ui.main.catrgory_name"
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ExploreRecipeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    @SuppressLint("DefaultLocale")
    override fun onItemClickListener(position: Int) {
        val intent = Intent(context, RecipeListActivity::class.java)
        intent.putExtra(
            CATEGORY_NAME,
            categories[position].name
        )
        startActivity(intent)
    }

}
