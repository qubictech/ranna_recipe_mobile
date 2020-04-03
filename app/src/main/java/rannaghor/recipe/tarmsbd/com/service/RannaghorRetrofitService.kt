package rannaghor.recipe.tarmsbd.com.service

import io.reactivex.Observable
import rannaghor.recipe.tarmsbd.com.model.Category
import rannaghor.recipe.tarmsbd.com.model.Recipe
import retrofit2.http.GET

interface RannaghorRetrofitService {
    @get:GET("/api/recipe.php")
    val recipe: Observable<List<Recipe>>

    @get:GET("/api/category.php")
    val category: Observable<List<Category>>
}