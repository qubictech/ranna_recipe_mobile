package rannaghor.recipe.tarmsbd.com.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import rannaghor.recipe.tarmsbd.com.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        StartFragment(LoginFragment())

    }


    public fun StartFragment(fragment :Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }

}
