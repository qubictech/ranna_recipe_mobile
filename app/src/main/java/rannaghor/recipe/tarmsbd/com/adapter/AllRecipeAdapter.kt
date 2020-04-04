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
import rannaghor.recipe.tarmsbd.com.model.Recipe

class AllRecipeAdapter(private val context: Context, private val recipes: List<Recipe>) :
    RecyclerView.Adapter<AllRecipeAdapter.RecipeHolder>() {
    class RecipeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.comment_username)
        private val likes = itemView.findViewById<TextView>(R.id.recipe_likes_count)
        private val comments = itemView.findViewById<TextView>(R.id.recipe_comments_count)
        private val icon = itemView.findViewById<ImageView>(R.id.recipe_image)
        fun bind(context: Context, recipe: Recipe) {
            name.text = recipe.name.trim()
//            likes.text = "${recipe.likes} +"
//            comments.text = "${recipe.comments} +"
            if (adapterPosition % 2 == 0)
                Glide.with(context).load(R.drawable.ic_burger_2).into(icon)
            else Glide.with(context).load(R.drawable.ic_burger).into(icon)

            icon.clipToOutline = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeHolder {
        val holder =
            LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        return RecipeHolder(holder)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: RecipeHolder, position: Int) {
        holder.bind(context, recipe = recipes[position])
    }
}