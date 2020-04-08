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
    private lateinit var onClickEventListener: OnClickEventListener

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var icon = itemView.findViewById<ImageView>(R.id.recipe_category_icon)
        private var name = itemView.findViewById<TextView>(R.id.recipe_category_name)

        fun bind(context: Context, category: String) {
            name.text = category.trim()
            if (adapterPosition % 2 == 0)
                Glide.with(context).load(R.drawable.ic_juice).into(icon)
            else Glide.with(context).load(R.drawable.ic_nasta).into(icon)

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

            context.startActivity(intent, options.toBundle())
        }

    }

    public fun setOnClickEventListener(onClickEventListener: OnClickEventListener) {
        this.onClickEventListener = onClickEventListener
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
                holder.onClickListener(context, category = category[position])
            }
        }
    }
}