package rannaghor.recipe.tarmsbd.com.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.formats.UnifiedNativeAd
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.viewholder.RecipeHolder

class AllRecipeAdapter(private val context: Context, private val recipes: List<Any>) :
    RecyclerView.Adapter<RecipeHolder>() {

    companion object {
        const val RECIPE_VIEW_TYPE = 0
        const val UNIFIED_NATIVE_AD_VIEW_TYPE = 1
        const val AD_UNIT_ID = "ca-app-pub-3436342122495555/6779138259"
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
                holder.bindAdView(recipes[position] as UnifiedNativeAd)
            }
            RECIPE_VIEW_TYPE -> {
                holder.bind(context, recipe = recipes[position] as Recipe)
                holder.itemView.setOnClickListener {
                    if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                        try {
                            holder.onClickListener(context, recipes[position] as Recipe)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (recipes[position] is Recipe) RECIPE_VIEW_TYPE else UNIFIED_NATIVE_AD_VIEW_TYPE
    }
}