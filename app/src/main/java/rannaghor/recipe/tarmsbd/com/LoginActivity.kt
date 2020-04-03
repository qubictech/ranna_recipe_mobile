package rannaghor.recipe.tarmsbd.com

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        StartFragment(LoginFragment())

    }


    private fun StartFragment(fragment :Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
    }

}
