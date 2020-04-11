package rannaghor.recipe.tarmsbd.com.ui.recipebycategory

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.NativeAdOptions
import com.google.android.gms.ads.formats.UnifiedNativeAd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.adapter.AllRecipeAdapter
import rannaghor.recipe.tarmsbd.com.database.roompersistance.viewmodel.RannaghorViewModel
import rannaghor.recipe.tarmsbd.com.ui.main.ExploreRecipeFragment
import rannaghor.recipe.tarmsbd.com.ui.main.MAX_NUMBER_OF_ADS
import java.util.logging.Logger
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class RecipeListActivity : AppCompatActivity(R.layout.activity_recipe_list) {

    private val recipeAdapterList = mutableListOf<Any>()
    private val nativeAd = mutableListOf<UnifiedNativeAd>()
    private lateinit var adLoader: AdLoader

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val category = intent.getStringExtra(ExploreRecipeFragment.CATEGORY_NAME)

        supportActionBar?.title = category
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rannaghorViewModel = ViewModelProvider(this).get(RannaghorViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.recipe_item_list)

        rannaghorViewModel.getRecipesByCategory(category = category).observe(this, Observer {

            recipeAdapterList.clear()
            recipeAdapterList.addAll(it)

            Logger.getLogger("recipe category : $category")
            val recipeAdapter = AllRecipeAdapter(this, recipeAdapterList)

            CoroutineScope(EmptyCoroutineContext).launch {
                loadNativeAd(recipeAdapter)
            }

            recyclerView.apply {
                hasFixedSize()
                layoutManager = LinearLayoutManager(context)
                adapter = recipeAdapter
            }
        })


    }

    private fun loadNativeAd(recipeAdapter: AllRecipeAdapter) {
        adLoader = AdLoader.Builder(this, AllRecipeAdapter.AD_UNIT_ID)
            .forUnifiedNativeAd { ad ->

                nativeAd.add(ad)

                if (!adLoader.isLoading) {
                    if (nativeAd.size > 0) {
                        var index = 0
                        val offset = (recipeAdapterList.size / nativeAd.size) + 1
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

        if (recipeAdapterList.size / 5 < 5) {
            adLoader.loadAds(AdRequest.Builder().build(), recipeAdapterList.size / 5)
        } else {
            adLoader.loadAds(AdRequest.Builder().build(), MAX_NUMBER_OF_ADS)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
