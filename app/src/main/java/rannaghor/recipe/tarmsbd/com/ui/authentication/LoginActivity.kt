package rannaghor.recipe.tarmsbd.com.ui.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import rannaghor.recipe.tarmsbd.com.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState==null){

            StartFragment(LoginFragment())
        }

    }


    public fun StartFragment(fragment :Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }

}
