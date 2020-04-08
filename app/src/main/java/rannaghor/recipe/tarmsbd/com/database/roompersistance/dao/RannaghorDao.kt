package rannaghor.recipe.tarmsbd.com.database.roompersistance.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import rannaghor.recipe.tarmsbd.com.model.Recipe

@Dao
interface RannaghorDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addRecipes(recipe: Recipe)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecipe(recipe: Recipe)

    @Query("SELECT * FROM Recipe ORDER BY likes DESC")
    fun getAllRecipes(): LiveData<List<Recipe>>

    @Query("SELECT * FROM Recipe WHERE type=:category")
    fun getRecipesByCategories(category: String): LiveData<List<Recipe>>

    @Query("SELECT DISTINCT type FROM Recipe")
    fun getAllCategories(): LiveData<List<String>>

    @Query("SELECT * FROM Recipe WHERE liked = 1")
    fun getFavoriteRecipeList(): LiveData<List<Recipe>>

    @Query("SELECT * FROM Recipe WHERE  name LIKE :query")
    fun searchRecipe(query: String): LiveData<List<Recipe>>

    @Query("SELECT * FROM Recipe WHERE  name LIKE :query AND liked = 1")
    fun searchSavedRecipe(query: String): LiveData<List<Recipe>>
}