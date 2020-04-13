package rannaghor.recipe.tarmsbd.com.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.ui.main.ExploreRecipeFragment
import rannaghor.recipe.tarmsbd.com.ui.recipebycategory.RecipeListActivity

class RecipeCategoryAdapter(private var context: Context, private var category: List<String>) :
    RecyclerView.Adapter<RecipeCategoryAdapter.CategoryHolder>() {

    companion object {
        const val DRINKS = "পানীয়"
        const val BREAKFAST = "নাস্তা"
        const val VEGETABLE = "শাক-সবজী"
        const val MEAT = "মাংস"
        const val EGG = "ডিম"
        const val DAL = "ডাল"
        const val RICE = "খিচুড়ী,পোলাও,বিরিয়ান"
        const val FISH = "মাছ"
        const val CAKE = "কেক,বিস্কুট"
        const val PITHA = "পিঠা"
    }

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var icon = itemView.findViewById<ImageView>(R.id.recipe_category_icon)
        private var name = itemView.findViewById<TextView>(R.id.recipe_category_name)

        fun bind(context: Context, category: String) {
            name.text = category.trim()

            when {
                category.trim().contains(DRINKS) -> {
                    Glide.with(context).load(R.drawable.ic_juice).into(icon)
                }
                category.trim().contains(BREAKFAST) -> {
                    Glide.with(context).load(R.drawable.ic_nasta).into(icon)
                }
                category.trim().contains(VEGETABLE) -> {
                    Glide.with(context).load(R.drawable.ic_vegetable).into(icon)
                }
                category.trim().contains(MEAT) -> {
                    Glide.with(context).load(R.drawable.ic_meat).into(icon)
                }
                category.trim().contains(EGG) -> {
                    Glide.with(context).load(R.drawable.ic_egg).into(icon)
                }
                category.trim().contains(DAL) -> {
                    Glide.with(context).load(R.drawable.ic_dal).into(icon)
                }
                category.trim().contains(RICE) -> {
                    Glide.with(context).load(R.drawable.ic_rice).into(icon)
                }
                category.trim().contains(FISH) -> {
                    Glide.with(context).load(R.drawable.ic_fish).into(icon)
                }
                category.trim().contains(CAKE) -> {
                    Glide.with(context).load(R.drawable.ic_cake).into(icon)
                }
                category.trim().contains(PITHA) -> {
                    Glide.with(context).load(R.drawable.ic_soup).into(icon)
                }
                else -> Glide.with(context).load(R.mipmap.ic_launcher).into(icon)
            }

            icon.clipToOutline = true
        }

        fun onClickListener(context: Context, category: String) {
            val intent = Intent(context, RecipeListActivity::class.java)
            intent.putExtra(
                ExploreRecipeFragment.CATEGORY_NAME,
                category
            )

            val icon: View = itemView.findViewById<ImageView>(R.id.root_layout)
            val pairImage = Pair.create(icon, "animated_layout")
            val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(context as Activity, pairImage)

            context.startActivity(intent)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val holder = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_item, parent, false)

        return CategoryHolder(holder)
    }

    override fun getItemCount() = category.size

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.bind(category = category[position], context = context)

        holder.itemView.setOnClickListener {
            if (holder.adapterPosition != RecyclerView.NO_POSITION) {
                try {
                    holder.onClickListener(context, category = category[position])
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}