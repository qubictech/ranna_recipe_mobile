package rannaghor.recipe.tarmsbd.com.service

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import rannaghor.recipe.tarmsbd.com.model.Category
import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.model.User
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("/api/users.php")
    fun userRegistration(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("mobile") number: String
    ): Single<User>

    @POST("/api/users.php")
    fun userLogin(
        @Query("email") email: String,
        @Query("password") password: String
    ): Single<User>

}