package rannaghor.recipe.tarmsbd.com.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rannaghor.recipe.tarmsbd.com.model.Category
import rannaghor.recipe.tarmsbd.com.model.Recipe

object RannaghorRepository {
    private val category = MutableLiveData<List<Category>>()
    private val recipe = MutableLiveData<List<Recipe>>()

    fun getAllCategories(): LiveData<List<Category>> = category

    fun getAllRecipes(): LiveData<List<Recipe>> = recipe

    fun setAllRecipes(recipes: List<Recipe>) {
        recipe.value = recipes
    }

    fun setAllCategory(categories: List<Category>) {
        category.value = categories
    }
}