package rannaghor.recipe.tarmsbd.com.database.roompersistance.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.database.roompersistance.repository.RannaghorRepo
import rannaghor.recipe.tarmsbd.com.database.roompersistance.sqlite.RannaghorDatabase

class RannaghorViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RannaghorRepo

    init {
        val rannaghorDao = RannaghorDatabase.getDatabase(application).rannaghorDao()
        repository = RannaghorRepo(rannaghorDao)
    }

    fun getRecipes(): LiveData<List<Recipe>> = repository.allRecipes

    fun getCategories(): LiveData<List<String>> = repository.allCategories

    fun getFavoriteRecipes() = repository.getFavoriteRecipes()

    fun getRecipesByCategory(category: String) = repository.getRecipeByCategory(category)

    fun insertRecipes(recipes: List<Recipe>) = viewModelScope.launch(Dispatchers.IO) {
        repository.addRecipes(recipes)
    }

    fun insertFavoriteRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.addFavoriteRecipe(recipe)
    }

    fun removeFavoriteRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.removeFavoriteRecipe(recipe)
    }

}