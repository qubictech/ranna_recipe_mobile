package rannaghor.recipe.tarmsbd.com.ui.authentication

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import rannaghor.recipe.tarmsbd.com.R

/**
 * A simple [Fragment] subclass.
 */
class SingUpFragment : Fragment(R.layout.fragment_sing_up) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.texViewLogin)
            .setOnClickListener {
                val loginActivity: LoginActivity = activity as LoginActivity
                loginActivity.startFragment(LoginFragment())
            }
    }
}
