package rannaghor.recipe.tarmsbd.com.ui.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.ui.main.MainActivity

class LoginFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.buttonLogin)
            .setOnClickListener{
                startActivity(Intent(view.context,MainActivity::class.java))
            }


        view.findViewById<TextView>(R.id.texViewLogin)
            .setOnClickListener {
                val loginActivity: LoginActivity =activity as LoginActivity
                loginActivity.StartFragment(SingUpFragment())


            }
    }

}
