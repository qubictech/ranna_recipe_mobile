package rannaghor.recipe.tarmsbd.com.ui.profile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.ui.authentication.LoginActivity
import rannaghor.recipe.tarmsbd.com.utility.SharedPrefUtil

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentUserData(view)
    }

    @SuppressLint("SetTextI18n")
    private fun getCurrentUserData(view: View) {
        val logout = view.findViewById<MaterialButton>(R.id.sign_out)
        val login = view.findViewById<MaterialButton>(R.id.sign_in)

        val username = view.findViewById<TextView>(R.id.username)
        val email = view.findViewById<TextView>(R.id.greetings_name)

        if (SharedPrefUtil(context!!).isUserLoggedIn()) {
            login.visibility = View.GONE
            logout.visibility = View.VISIBLE
            username.text = SharedPrefUtil(context!!).getUserLoggedInUserData()?.name
            email.text = SharedPrefUtil(context!!).getUserLoggedInUserData()?.email
        } else {
            login.visibility = View.VISIBLE
            logout.visibility = View.GONE
            username.text = SharedPrefUtil(context!!).getUserLoggedInUserData()?.name
            email.text = "You are logged out! Login again."
        }

        logout.setOnClickListener {
            SharedPrefUtil(context!!).clearUserCredential()
            getCurrentUserData(view)
        }

        login.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }
}
