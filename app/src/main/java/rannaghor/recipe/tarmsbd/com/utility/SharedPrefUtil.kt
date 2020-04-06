package rannaghor.recipe.tarmsbd.com.utility

import android.content.Context
import androidx.preference.PreferenceManager
import rannaghor.recipe.tarmsbd.com.model.Recipe
import rannaghor.recipe.tarmsbd.com.model.User
import java.util.logging.Logger

class SharedPrefUtil(private val context: Context) {
    companion object {
        const val USER_CREDENTIAL = "rannaghor.recipe.tarmsbd.com.user_credential"
        const val USER_NAME = "rannaghor.recipe.tarmsbd.com.user_name"
        const val USER_UID = "rannaghor.recipe.tarmsbd.com.user_uid"
        const val USER_EMAIL = "rannaghor.recipe.tarmsbd.com.user_email"

        const val SAVED_RECIPE_LIST = "rannaghor.recipe.tarmsbd.com.saved_list"
    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val editor = sharedPreferences.edit()

    fun isUserLoggedIn(): Boolean {
        val email = sharedPreferences.getString(USER_EMAIL, null);
        return !email.isNullOrEmpty()
    }

    fun saveNewRecipe(recipe: Recipe) {
        val recipes = getSavedRecipeList()
        recipe.id?.let { recipes?.add(it) }
        editor.putString(SAVED_RECIPE_LIST, recipes?.toString())
    }

    fun getSavedRecipeList(): MutableSet<String>? = sharedPreferences.getStringSet(
        SAVED_RECIPE_LIST,
        mutableSetOf()
    )

    fun storeUserCredentialIntoSharedPref(user: User) {
        Logger.getLogger("SharedPrefUtil").warning("$user")

        editor.putString(USER_NAME, user.name)
        editor.putString(USER_EMAIL, user.email)
        editor.putString(USER_UID, user.token)

        editor.apply()
        editor.commit()
    }

    fun getUserLoggedInUserData(): String? {
        return sharedPreferences.getString(USER_NAME, "Smith") + "!"
    }

    fun clearUserCredential() {

        editor.remove(USER_NAME)
        editor.remove(USER_EMAIL)
        editor.remove(USER_UID)

        editor.apply()
        editor.commit()
    }


}