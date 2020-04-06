package rannaghor.recipe.tarmsbd.com.utility

import android.content.Context
import androidx.preference.PreferenceManager
import rannaghor.recipe.tarmsbd.com.model.User
import java.util.logging.Logger

class SharedPrefUtil(private val context: Context) {
    companion object {
        const val USER_CREDENTIAL = "rannaghor.recipe.tarmsbd.com.user_credential"
        const val USER_NAME = "rannaghor.recipe.tarmsbd.com.user_name"
        const val USER_UID = "rannaghor.recipe.tarmsbd.com.user_uid"
        const val USER_EMAIL = "rannaghor.recipe.tarmsbd.com.user_email"
    }

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun isUserLoggedIn(): Boolean {
        val email = sharedPreferences.getString(USER_EMAIL, null);
        return !email.isNullOrEmpty()
    }

    fun storeUserCredentialIntoSharedPref(user: User) {
        Logger.getLogger("SharedPrefUtil").warning("$user")
        val editor = sharedPreferences.edit()

        editor.putString(USER_NAME, user.name)
        editor.putString(USER_EMAIL, user.email)
        editor.putString(USER_UID, user.token)

        editor.apply()
        editor.commit()
    }

    fun clearUserCredential() {
        val editor = sharedPreferences.edit()

        editor.remove(USER_NAME)
        editor.remove(USER_EMAIL)
        editor.remove(USER_UID)

        editor.apply()
        editor.commit()
    }


}