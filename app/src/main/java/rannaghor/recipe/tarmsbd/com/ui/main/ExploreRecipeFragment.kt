package rannaghor.recipe.tarmsbd.com.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.internal.disposables.ArrayCompositeDisposable
import io.reactivex.schedulers.Schedulers

import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.adapter.AllRecipeAdapter
import rannaghor.recipe.tarmsbd.com.adapter.RecipeCategoryAdapter
import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.repository.RannaghorRepository
import rannaghor.recipe.tarmsbd.com.service.RannaghorRetrofitService
import rannaghor.recipe.tarmsbd.com.service.RetrofitClient
import rannaghor.recipe.tarmsbd.com.viewmodel.RannaghorViewModel
import java.util.logging.Logger

class ExploreRecipeFragment : Fragment() {
    private lateinit var recyclerViewExploreByCategory: RecyclerView
    private lateinit var recyclerViewPopularRecipe: RecyclerView
    private lateinit var rannaghorViewModel: RannaghorViewModel

    lateinit var rannaghorRetrofitService: RannaghorRetrofitService
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_explore_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewExploreByCategory = view.findViewById(R.id.recipe_categories)
        recyclerViewPopularRecipe = view.findViewById(R.id.popular_recipe)
        rannaghorViewModel = ViewModelProvider(this).get(RannaghorViewModel::class.java)

        val retrofit = RetrofitClient.instance
        rannaghorRetrofitService = retrofit.create(RannaghorRetrofitService::class.java)
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(
            rannaghorRetrofitService.recipe
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    this::allRecipes, this::handleError
                )
        )

        getAllCategories()
        getAllRecipes()
    }

    private fun allRecipes(recipes: List<Recipe>) {
        RannaghorRepository.setAllRecipes(recipes)
    }

    private fun handleError(error: Throwable) {
        Logger.getLogger("ExploreRecipe").warning("   Error: $error.localizedMessage")
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
