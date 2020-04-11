package rannaghor.recipe.tarmsbd.com.database.roompersistance.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rannaghor.recipe.tarmsbd.com.database.network.RannaghorRetrofitService
import rannaghor.recipe.tarmsbd.com.database.network.RetrofitClient
import rannaghor.recipe.tarmsbd.com.database.roompersistance.repository.RannaghorRepo
import rannaghor.recipe.tarmsbd.com.database.roompersistance.sqlite.RannaghorDatabase
import rannaghor.recipe.tarmsbd.com.model.Recipe
import java.util.logging.Logger
import kotlin.coroutines.EmptyCoroutineContext

class RannaghorViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RannaghorRepo
    private val compositeDisposable = CompositeDisposable()

    init {
        val rannaghorDao = RannaghorDatabase.getDatabase(application).rannaghorDao()
        repository = RannaghorRepo(rannaghorDao)

        CoroutineScope(EmptyCoroutineContext).launch {
            loadRecipeFromNetwork {
                insertRecipes(it)
            }
        }
    }

    private fun loadRecipeFromNetwork(onComplete: (List<Recipe>) -> Unit) {
        val retrofit = RetrofitClient.INSTANCE
        val rannaghorRetrofitService = retrofit.create(RannaghorRetrofitService::class.java)

        compositeDisposable.add(
            rannaghorRetrofitService.recipe
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { recipes ->
                        try {
                            return@subscribe onComplete(recipes)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }, this::handleError
                )
        )
    }

    fun getRecipes(): LiveData<List<Recipe>> = repository.allRecipes

    fun searchRecipeByName(query: String) = repository.searchRecipeByName(query)

    fun getCategories(): LiveData<List<String>> = repository.allCategories

    fun getFavoriteRecipes() = repository.getFavoriteRecipes()

    fun searchSavedRecipe(query: String) = repository.searchSavedRecipeByName(query)

    fun getRecipesByCategory(category: String) = repository.getRecipeByCategory(category)

    fun insertRecipes(recipes: List<Recipe>) = viewModelScope.launch(Dispatchers.IO) {
        repository.addRecipes(recipes)
    }

    fun updateRecipe(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipe)
    }

    private fun handleError(error: Throwable) {
        Logger.getLogger("MainActivity")
            .warning("   Error: ${error.localizedMessage}")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

}