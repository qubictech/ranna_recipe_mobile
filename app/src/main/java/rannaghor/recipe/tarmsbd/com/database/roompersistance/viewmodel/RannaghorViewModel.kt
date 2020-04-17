package rannaghor.recipe.tarmsbd.com.database.roompersistance.viewmodel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rannaghor.recipe.tarmsbd.com.database.network.RannaghorRetrofitService
import rannaghor.recipe.tarmsbd.com.database.network.RetrofitClient
import rannaghor.recipe.tarmsbd.com.database.roompersistance.repository.RannaghorRepo
import rannaghor.recipe.tarmsbd.com.database.roompersistance.sqlite.RannaghorDatabase
import rannaghor.recipe.tarmsbd.com.model.Recipe
import java.util.logging.Logger

class RannaghorViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RannaghorRepo
    private val compositeDisposable = CompositeDisposable()
    private val context: Context

    init {
        val rannaghorDao = RannaghorDatabase.getDatabase(application).rannaghorDao()
        repository = RannaghorRepo(rannaghorDao)
        context = application.applicationContext
    }

    fun loadRecipeFromNetwork() {
        viewModelScope.launch {
            val retrofit = RetrofitClient.INSTANCE
            val rannaghorRetrofitService = retrofit.create(RannaghorRetrofitService::class.java)

            println("RannaghorViewModel -> ${compositeDisposable.size()}")

            compositeDisposable.add(
                rannaghorRetrofitService.recipe
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        { result ->
                            println("Response ->"+result.code())

                            if (result.isSuccessful) {

                                Toast.makeText(context, result.message(), Toast.LENGTH_SHORT).show()
                                result.body()?.let {
                                    insertRecipes(it)
                                }
                            } else {
                                Toast.makeText(context, result.message(), Toast.LENGTH_SHORT).show()
                            }
                        }, {
                            Logger.getLogger("MainActivity")
                                .warning("   Error: ${it.localizedMessage}")
                            Toast.makeText(context, it.localizedMessage.toString(), Toast.LENGTH_SHORT).show()
                        }
                    )
            )
        }
    }

    fun getRecipes(): LiveData<List<Recipe>> = repository.allRecipes()

    fun searchRecipeByName(query: String) = repository.searchRecipeByName(query)

    fun getCategories(): LiveData<List<String>> = repository.allCategories()

    fun getFavoriteRecipes() = repository.getFavoriteRecipes()

    fun searchSavedRecipe(query: String) = repository.searchSavedRecipeByName(query)

    fun getRecipesByCategory(category: String) = repository.getRecipeByCategory(category)

    private fun insertRecipes(recipes: List<Recipe>) = viewModelScope.launch(Dispatchers.IO) {
        repository.addRecipes(recipes)
    }

    fun updateRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipe)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}