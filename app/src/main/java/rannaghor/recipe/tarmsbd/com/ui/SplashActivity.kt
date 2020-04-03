package rannaghor.recipe.tarmsbd.com.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import rannaghor.recipe.tarmsbd.com.ui.main.MainActivity
import rannaghor.recipe.tarmsbd.com.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }, 7000)
    }
}
