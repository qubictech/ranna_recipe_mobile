package rannaghor.recipe.tarmsbd.com.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rannaghor.recipe.tarmsbd.com.model.Category
import rannaghor.recipe.tarmsbd.com.model.Recipe

object RannaghorRepository {
    private val category = MutableLiveData<List<Category>>()
    private val recipe = MutableLiveData<List<Recipe>>()
    private val recipeByCategory = MutableLiveData<List<Recipe>>()
    private val savedRecipe = MutableLiveData<List<Recipe>>()

    fun getAllCategories(): LiveData<List<Category>> = category

    fun getAllRecipes(): LiveData<List<Recipe>> = recipe

    fun getRecipeByCategory(): LiveData<List<Recipe>> = recipeByCategory

    fun getSavedRecipeList(): LiveData<List<Recipe>> = savedRecipe

    fun setAllRecipes(recipes: List<Recipe>) {
        recipe.value = recipes
    }

    fun setAllCategory(categories: List<Category>) {
        category.value = categories
    }

    fun setRecipesByCategory(recipes: List<Recipe>) {
        recipeByCategory.value = recipes
    }

    fun saveRecipeIntoLocalStorage(recipe: Recipe) {
        val savedList = savedRecipe.value
        val newList = mutableListOf<Recipe>()

        savedList?.let { newList.addAll(it) }
        newList.add(recipe)

        savedRecipe.value = newList
    }
}