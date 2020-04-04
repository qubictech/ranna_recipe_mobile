package rannaghor.recipe.tarmsbd.com.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.model.Category

class RecipeCategoryAdapter(private var context: Context, private var category: List<Category>) :
    RecyclerView.Adapter<RecipeCategoryAdapter.CategoryHolder>() {

    class CategoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var icon = itemView.findViewById<ImageView>(R.id.recipe_category_icon)
        private var name = itemView.findViewById<TextView>(R.id.recipe_category_name)

        fun bind(context: Context, category: Category) {
            name.text = category.name.trim()
            Glide.with(context).load(category.images).into(icon)
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
    }
}