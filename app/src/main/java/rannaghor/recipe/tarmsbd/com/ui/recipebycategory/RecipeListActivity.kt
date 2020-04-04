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
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.adapter.AllRecipeAdapter
import rannaghor.recipe.tarmsbd.com.ui.main.ExploreRecipeFragment
import rannaghor.recipe.tarmsbd.com.viewmodel.RannaghorViewModel

class RecipeListActivity : AppCompatActivity(R.layout.activity_recipe_list) {

    @SuppressLint("DefaultLocale")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.title = intent.getStringExtra(ExploreRecipeFragment.CATEGORY_NAME)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rannaghorViewModel = ViewModelProvider(this).get(RannaghorViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.recipe_item_list)

        rannaghorViewModel.createNetworkRequestForRecipeListByCategory(
            intent.getStringExtra(ExploreRecipeFragment.CATEGORY_NAME).toLowerCase()
        )

        rannaghorViewModel.getRecipeListByCategory().observe(this, Observer {
            val recipeAdapter = AllRecipeAdapter(applicationContext, it)
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
