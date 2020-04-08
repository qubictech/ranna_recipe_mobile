package rannaghor.recipe.tarmsbd.com.database.roompersistance.repository

import androidx.lifecycle.LiveData
import rannaghor.recipe.tarmsbd.com.database.roompersistance.dao.RannaghorDao
import rannaghor.recipe.tarmsbd.com.model.Recipe

class RannaghorRepo(private val rannaghorDao: RannaghorDao) {
    val allRecipes = rannaghorDao.getAllRecipes()
    val allCategories = rannaghorDao.getAllCategories()

    suspend fun addRecipes(recipes: List<Recipe>) {
        for (recipe in recipes) {
            rannaghorDao.addRecipes(recipe)
        }
    }

    suspend fun updateRecipe(recipe: Recipe) {
        rannaghorDao.updateRecipe(recipe)
    }

    fun searchRecipeByName(query: String): LiveData<List<Recipe>> {
        val mQuery = "%$query%"
        return rannaghorDao.searchRecipe(mQuery)
    }

    fun searchSavedRecipeByName(query: String): LiveData<List<Recipe>> {
        val mQuery = "%$query%"
        return rannaghorDao.searchSavedRecipe(mQuery)
    }

    fun getFavoriteRecipes(): LiveData<List<Recipe>> {
        return rannaghorDao.getFavoriteRecipeList()
    }

    fun getRecipeByCategory(category: String): LiveData<List<Recipe>> {
        return rannaghorDao.getRecipesByCategories(category)
    }

}