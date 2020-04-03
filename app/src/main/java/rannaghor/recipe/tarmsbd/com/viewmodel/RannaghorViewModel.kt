package rannaghor.recipe.tarmsbd.com.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import rannaghor.recipe.tarmsbd.com.model.Category
import rannaghor.recipe.tarmsbd.com.repository.RannaghorRepository
import java.util.*

class RannaghorViewModel : ViewModel() {
    fun getCategories(): LiveData<List<Category>> {
        return RannaghorRepository.getAllCategories()
    }
}