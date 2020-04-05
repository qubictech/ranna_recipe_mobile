package rannaghor.recipe.tarmsbd.com.ui.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.adapter.AllRecipeAdapter
import rannaghor.recipe.tarmsbd.com.service.OnClickEventListener
import rannaghor.recipe.tarmsbd.com.ui.RecipeDetails
import rannaghor.recipe.tarmsbd.com.viewmodel.RannaghorViewModel

/**
 * A simple [Fragment] subclass.
 */
class FavoriteRecipeFragment : Fragment(R.layout.fragment_favorite_recipe) {

    private lateinit var recyclerView: RecyclerView;
    private lateinit var rannaghorViewModel: RannaghorViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.favorite_recipe)
        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh_layout)
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed(
                {
                    swipeRefreshLayout.isRefreshing = false
                }, 200
            )
        }

        rannaghorViewModel = ViewModelProvider(this).get(RannaghorViewModel::class.java)

        rannaghorViewModel.getSavedRecipeList().observe(this, Observer { recipes ->
            val recipeListAdapter = context?.let { AllRecipeAdapter(it, recipes) }

            if (recipes.isEmpty()) {
                view.findViewById<ImageView>(R.id.empty_list_image).apply {
                    visibility = View.VISIBLE
                }
            } else {
                view.findViewById<ImageView>(R.id.empty_list_image).apply {
                    visibility = View.GONE
                }
            }

            recipeListAdapter?.setOnClickEventListener(object : OnClickEventListener {
                override fun onItemClickListener(position: Int) {
                    val intent = Intent(context, RecipeDetails::class.java)
                    intent.putExtra(RecipeDetails.RECIPE_DETAIL, recipes[position])
                    startActivity(intent)
                }
            })

            recyclerView.apply {
                hasFixedSize()
                layoutManager = LinearLayoutManager(context)
                adapter = recipeListAdapter
            }
        })

    }

}
