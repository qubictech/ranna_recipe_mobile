package rannaghor.recipe.tarmsbd.com.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.formats.NativeAdOptions
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.viewholder.RecipeHolder
import java.util.logging.Logger

class AllRecipeAdapter(private val context: Context, private val recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeHolder>() {

    companion object {
        const val RECIPE_VIEW_TYPE = 0
        const val UNIFIED_NATIVE_AD_VIEW_TYPE = 1
        const val AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {

        val holder = when (viewType) {
            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.ad_unified, parent, false)
            }
            else -> {
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recipe_item, parent, false)
            }
        }
        return RecipeHolder(holder)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        when (getItemViewType(position)) {
            UNIFIED_NATIVE_AD_VIEW_TYPE -> {
                val adLoader = AdLoader.Builder(context, AD_UNIT_ID)
                    .forUnifiedNativeAd {
                        holder.bindAdView(it)
                    }
                    .withAdListener(object : AdListener() {
                        override fun onAdFailedToLoad(p0: Int) {
                            super.onAdFailedToLoad(p0)

                            Logger.getLogger("NativeAdLoader")
                                .warning("Failed to load ad with error code $p0")
                        }
                    })
                    .withNativeAdOptions(NativeAdOptions.Builder().build())

                adLoader.build().loadAd(AdRequest.Builder().build())
            }
            RECIPE_VIEW_TYPE -> {
                holder.bind(context, recipe = recipes[position])
                holder.itemView.setOnClickListener {
                    if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                        try {
                            holder.onClickListener(context, recipes[position])
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 5 == 0) UNIFIED_NATIVE_AD_VIEW_TYPE else RECIPE_VIEW_TYPE
    }
}