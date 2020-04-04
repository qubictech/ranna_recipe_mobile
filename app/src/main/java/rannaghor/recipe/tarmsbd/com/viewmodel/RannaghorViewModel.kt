package rannaghor.recipe.tarmsbd.com.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rannaghor.recipe.tarmsbd.com.model.Category
import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.repository.RannaghorRepository
import rannaghor.recipe.tarmsbd.com.service.RannaghorRetrofitService
import rannaghor.recipe.tarmsbd.com.service.RetrofitClient
import java.util.logging.Logger

const val TAG = "RannaghorViewModel"

class RannaghorViewModel : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val retrofit = RetrofitClient.instance
    private val rannaghorRetrofitService = retrofit.create(RannaghorRetrofitService::class.java)

    fun getCategories(): LiveData<List<Category>> = RannaghorRepository.getAllCategories()
    fun getAllRecipes(): LiveData<List<Recipe>> = RannaghorRepository.getAllRecipes()
    fun getRecipeListByCategory(): LiveData<List<Recipe>> =
        RannaghorRepository.getRecipeByCategory()

    fun getRecipeCategoryAndRecipeListFromNetwork() {
        compositeDisposable.add(
            rannaghorRetrofitService.recipe
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { recipes ->
                        RannaghorRepository.setAllRecipes(recipes)
                    }, this::handleError
                )
        )

        compositeDisposable.add(
            rannaghorRetrofitService.category
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { category ->
                        RannaghorRepository.setAllCategory(category)
                    }, this::handleError
                )
        )
    }

    fun createNetworkRequestForRecipeListByCategory(category: String) {
        compositeDisposable.add(
            rannaghorRetrofitService.recipeByCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Logger.getLogger("Response size: ").warning("${it.size}")
                        RannaghorRepository.setRecipesByCategory(it)
                    }, this::handleError
                )
        )
    }

    private fun handleError(error: Throwable) {
        Logger.getLogger(TAG).warning("   Error: ${error.localizedMessage}")
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}