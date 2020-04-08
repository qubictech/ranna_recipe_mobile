package rannaghor.recipe.tarmsbd.com.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.repository.RannaghorRepo
import rannaghor.recipe.tarmsbd.com.service.RannaghorDatabase

class RannaghorRoomViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RannaghorRepo

    init {
        val rannaghorDao = RannaghorDatabase.getDatabase(application).rannaghorDao()
        repository = RannaghorRepo(rannaghorDao)
    }

    fun getRecipes() = repository.allRecipes

    fun insertRecipes(recipes: List<Recipe>) = viewModelScope.launch(Dispatchers.IO) {
        repository.addRecipes(recipes)
    }
}