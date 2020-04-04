package rannaghor.recipe.tarmsbd.com.ui.authentications

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import rannaghor.recipe.tarmsbd.com.R

/**
 * A simple [Fragment] subclass.
 */
class SingUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sing_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.texViewLogin)
            .setOnClickListener {
                val loginActivity: LoginActivity =activity as LoginActivity
                loginActivity.StartFragment(LoginFragment())


            }



    }



}
