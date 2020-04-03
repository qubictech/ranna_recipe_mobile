package rannaghor.recipe.tarmsbd.com.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import rannaghor.recipe.tarmsbd.com.model.Category
import rannaghor.recipe.tarmsbd.com.model.Recipe

object RannaghorRepository {
    private val categories = mutableListOf(
        Category("", "Breakfast"),
        Category("", "Juice"),
        Category("", "Sup"),
        Category("", "Biriyani"),
        Category("", "Kabab"),
        Category("", "Kacci"),
        Category("", "Payesh"),
        Category("", "Beef")
    )

    private val recipes = mutableListOf(
        Recipe("Chicken Curry", 100, 100),
        Recipe("Chicken Curry", 100, 100),
        Recipe("Chicken Curry", 100, 100),
        Recipe("Chicken Curry", 100, 100),
        Recipe("Chicken Curry", 100, 100),
        Recipe("Chicken Curry", 100, 100),
        Recipe("Chicken Curry", 100, 100),
        Recipe("Chicken Curry", 100, 100)
    )
    private val category = MutableLiveData<List<Category>>()
    private val recipe = MutableLiveData<List<Recipe>>()

    init {
        category.value = categories
        recipe.value = recipes
    }

    fun getAllCategories(): LiveData<List<Category>> = category

    fun getAllRecipes(): LiveData<List<Recipe>> = recipe
}