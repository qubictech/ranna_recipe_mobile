package rannaghor.recipe.tarmsbd.com.database.roompersistance.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.delay
import rannaghor.recipe.tarmsbd.com.database.roompersistance.dao.RannaghorDao
import rannaghor.recipe.tarmsbd.com.model.Recipe

class RannaghorRepo(private val rannaghorDao: RannaghorDao) {
    fun allRecipes(): LiveData<List<Recipe>> = rannaghorDao.getAllRecipes()

    fun allCategories(): LiveData<List<String>> = rannaghorDao.getAllCategories()

    suspend fun addRecipes(recipes: List<Recipe>) {
        for (recipe in recipes) {
            rannaghorDao.addRecipes(recipe)
        }
    }

    suspend fun updateRecipe(recipe: Recipe) {
        rannaghorDao.updateRecipe(recipe)
    }

    fun searchRecipeByName(query: String): LiveData<List<Recipe>> =
        rannaghorDao.searchRecipe("%$query%")

    fun searchSavedRecipeByName(query: String): LiveData<List<Recipe>> =
        rannaghorDao.searchSavedRecipe("%$query%")

    fun getFavoriteRecipes(): LiveData<List<Recipe>> = rannaghorDao.getFavoriteRecipeList()

    fun getRecipeByCategory(category: String): LiveData<List<Recipe>> =
        rannaghorDao.getRecipesByCategories("%$category%")

}