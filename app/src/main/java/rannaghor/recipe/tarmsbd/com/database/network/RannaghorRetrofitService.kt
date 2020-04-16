package rannaghor.recipe.tarmsbd.com.database.network

import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import rannaghor.recipe.tarmsbd.com.model.Category
import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface RannaghorRetrofitService {
    @get:GET("/api/recipe.php")
    val recipe: Observable<Response<List<Recipe>>>

    @get:GET("/api/category.php")
    val category: Observable<List<Category>>

    @POST("/api/users.php")
    fun userRegistration(
        @Query("name") name: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("phone") number: String
    ): Single<List<User>>

    @POST("/api/users.php")
    fun userLogin(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("from") login: String
    ): Single<List<User>>

    @POST("/api/recipe.php")
    fun incrementLikes(
        @Query("liked") liked: String = "true",
        @Query("id") id: String
    ): Flowable<List<Recipe>>
}