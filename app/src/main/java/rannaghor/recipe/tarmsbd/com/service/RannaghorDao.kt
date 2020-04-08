package rannaghor.recipe.tarmsbd.com.service

import androidx.lifecycle.LiveData
import androidx.room.*
import rannaghor.recipe.tarmsbd.com.model.Recipe

@Dao
interface RannaghorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipes(recipe: Recipe)

    @Query("SELECT * FROM Recipe")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM Recipe WHERE type=:category")
    fun getRecipesByCategories(category: String): LiveData<List<Recipe>>

    @Query("SELECT DISTINCT type FROM Recipe")
    fun getAllCategories(): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavoriteRecipe(recipe: Recipe)

    @Delete
    suspend fun removeFavoriteRecipe(recipe: Recipe)

    @Query("SELECT * FROM Recipe WHERE likes>0")
    fun getFavoriteRecipeList(): LiveData<List<Recipe>>
}