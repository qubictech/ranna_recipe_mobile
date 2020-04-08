package rannaghor.recipe.tarmsbd.com.repository

import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.service.RannaghorDao

class RannaghorRepo(private val rannaghorDao: RannaghorDao) {
    val allRecipes = rannaghorDao.getAllRecipes()

    suspend fun addRecipes(recipes: List<Recipe>){
        for(recipe in recipes){
            rannaghorDao.addRecipes(recipe)
        }
    }
}