package rannaghor.recipe.tarmsbd.com.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rannaghor.recipe.tarmsbd.com.model.Category

object RannaghorRepository {
    private val categories = mutableListOf(Category("", ""))
    private val category = MutableLiveData<List<Category>>()

    init {
        category.value = categories
    }

    fun getAllCategories(): LiveData<List<Category>> {
        return category
    }
}