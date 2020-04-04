package rannaghor.recipe.tarmsbd.com.service

import io.reactivex.Flowable
import io.reactivex.Observable
import rannaghor.recipe.tarmsbd.com.model.Category
import rannaghor.recipe.tarmsbd.com.model.Recipe
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RannaghorRetrofitService {
    @get:GET("/api/recipe.php")
    val recipe: Observable<List<Recipe>>

    @POST("/api/recipe.php")
    fun recipeByCategory(
        @Query("type") category: String
    ): Flowable<List<Recipe>>

    @get:GET("/api/category.php")
    val category: Observable<List<Category>>
}