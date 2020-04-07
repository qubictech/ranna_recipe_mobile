package rannaghor.recipe.tarmsbd.com.ui.recipebycategory

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.adapter.AllRecipeAdapter
import rannaghor.recipe.tarmsbd.com.service.OnClickEventListener
import rannaghor.recipe.tarmsbd.com.ui.RecipeDetails
import rannaghor.recipe.tarmsbd.com.ui.main.ExploreRecipeFragment
import rannaghor.recipe.tarmsbd.com.viewmodel.RannaghorViewModel
import java.util.logging.Logger

class RecipeListActivity : AppCompatActivity(R.layout.activity_recipe_list) {

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

        rannaghorViewModel.createNetworkRequestForRecipeListByCategory(
            category.toLowerCase()
        )

        rannaghorViewModel.getRecipeListByCategory(category.toLowerCase()).observe(this, Observer {
            Logger.getLogger("recipe category : $category")
            val recipeAdapter = AllRecipeAdapter(this, it)

            recyclerView.apply {
                hasFixedSize()
                layoutManager = LinearLayoutManager(context)
                adapter = recipeAdapter
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }
}
