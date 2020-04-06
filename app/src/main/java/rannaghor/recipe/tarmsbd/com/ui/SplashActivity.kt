package rannaghor.recipe.tarmsbd.com.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.ui.authentication.LoginActivity
import rannaghor.recipe.tarmsbd.com.ui.main.MainActivity
import rannaghor.recipe.tarmsbd.com.utility.SharedPrefUtil

class SplashActivity : AppCompatActivity(R.layout.activity_splash) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler().postDelayed({
            if (SharedPrefUtil(applicationContext!!).isUserLoggedIn()) {
                startActivity(Intent(applicationContext, MainActivity::class.java))
            } else {
                startActivity(Intent(applicationContext, LoginActivity::class.java))
            }
            finish()
        }, 4000)
    }
}
