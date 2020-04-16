package rannaghor.recipe.tarmsbd.com.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.adapter.AllRecipeAdapter
import rannaghor.recipe.tarmsbd.com.adapter.RecipeCategoryAdapter
import rannaghor.recipe.tarmsbd.com.database.roompersistance.viewmodel.RannaghorViewModel
import rannaghor.recipe.tarmsbd.com.ui.profile.ProfileFragment
import rannaghor.recipe.tarmsbd.com.utility.SharedPrefUtil
import java.util.*
import java.util.logging.Logger

const val MAX_NUMBER_OF_ADS = 5

class ExploreRecipeFragment : Fragment(R.layout.fragment_explore_recipe) {
    private lateinit var recyclerViewExploreByCategory: RecyclerView
    private lateinit var recyclerViewPopularRecipe: RecyclerView
    private lateinit var rannaghorViewModel: RannaghorViewModel

    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var editTextSearchView: EditText

    private val recipeAdapterList = mutableListOf<Any>()
    private val nativeAd = mutableListOf<UnifiedNativeAd>()
    private lateinit var adLoader: AdLoader

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefreshLayout = view.findViewById(R.id.swipe_to_refresh_layout)
        swipeRefreshLayout.isRefreshing = true

        recyclerViewExploreByCategory = view.findViewById(R.id.recipe_categories)
        recyclerViewPopularRecipe = view.findViewById(R.id.popular_recipe)
        editTextSearchView = view.findViewById(R.id.search_recipe)
        editTextSearchView.clearFocus()

        rannaghorViewModel = ViewModelProvider(this).get(RannaghorViewModel::class.java)
        rannaghorViewModel.loadRecipeFromNetwork()

        editTextSearchView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                getSearchResult(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        view.findViewById<TextView>(R.id.username).apply {
            text = SharedPrefUtil(context).getUserLoggedInUserData()?.name
        }

        view.findViewById<ImageView>(R.id.avatar).setOnClickListener {
            val profile = ProfileFragment()
            profile.show(parentFragmentManager, "")
        }

        val greetings = view.findViewById<TextView>(R.id.greetings_name)
        val c = Calendar.getInstance()
        c.time = Date()

        when (c.get(Calendar.HOUR_OF_DAY)) {
            in 1..11 -> {
                greetings.text = "Good Morning"
            }
            12 -> {
                greetings.text = "Good Noon"
            }
            in 12..16 -> {
                greetings.text = "Good Afternoon"
            }
            else -> greetings.text = "Good Night"
        }

        getAllCategories()
        getAllRecipes()

        swipeRefreshLayout.setOnRefreshListener {
            getAllCategories()
            getAllRecipes()
        }

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
        rannaghorViewModel.getRecipes().observe(viewLifecycleOwner, Observer {
            recipeAdapterList.clear()
            recipeAdapterList.addAll(it)

            if (it.isNotEmpty()) swipeRefreshLayout.isRefreshing = false

            val recipeAdapter = context?.let { it1 -> AllRecipeAdapter(it1, recipeAdapterList) }

            CoroutineScope(Dispatchers.Main).launch {
                if (it.isNotEmpty()) {
                    loadNativeAd(MAX_NUMBER_OF_ADS, recipeAdapter!!)
                }
            }

            recyclerViewPopularRecipe.apply {
                hasFixedSize()
                layoutManager = LinearLayoutManager(context)
                adapter = recipeAdapter
            }
        })
    }

    private suspend fun loadNativeAd(adCounter: Int, recipeAdapter: AllRecipeAdapter) {
        adLoader = AdLoader.Builder(context, AllRecipeAdapter.AD_UNIT_ID)
            .forUnifiedNativeAd { ad ->

                nativeAd.add(ad)

                if (!adLoader.isLoading) {
                    if (nativeAd.size > 0) {
                        val offset = (recipeAdapterList.size / nativeAd.size) + 1
                        var index = 0

                        Logger.getLogger("NumberOfAds").warning("Offset: $offset")

                        for (loadAd: UnifiedNativeAd in nativeAd) {
                            recipeAdapterList.add(index, loadAd)
                            index += offset
                        }
                        recipeAdapter.notifyDataSetChanged()
                    } else {
                        Logger.getLogger("UnifiedNativeAd")
                            .warning("No ad loaded..........! -> ${nativeAd.size}")
                    }
                } else {
                    Logger.getLogger("UnifiedNativeAd")
                        .warning("Loading.............! -> ${nativeAd.size}")
                }

            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(p0: Int) {
                    super.onAdFailedToLoad(p0)

                    Logger.getLogger("NativeAdLoader")
                        .warning("Failed to load ad with error code $p0")
                }
            }).withNativeAdOptions(NativeAdOptions.Builder().build()).build()

        withContext(Dispatchers.Main) {
            adLoader.loadAd(AdRequest.Builder().build())
        }

    }

    private fun getSearchResult(query: String) {
        rannaghorViewModel.searchRecipeByName(query).observe(viewLifecycleOwner, Observer {
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
    }
}
