package rannaghor.recipe.tarmsbd.com.ui.authentication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Schedulers.io
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.service.RannaghorRetrofitService
import rannaghor.recipe.tarmsbd.com.service.RetrofitClient
import rannaghor.recipe.tarmsbd.com.ui.main.MainActivity
import rannaghor.recipe.tarmsbd.com.viewmodel.TAG
import java.util.logging.Logger

/**
 * A simple [Fragment] subclass.
 */
class SingUpFragment : Fragment(R.layout.fragment_sing_up) {

    private lateinit var mName: EditText
    private lateinit var mEmail: EditText
    private lateinit var mNumber: EditText
    private lateinit var mPass: EditText


    private val compositeDisposable = CompositeDisposable()
    private val retrofit = RetrofitClient.instance
    private val rannaghorRetrofitService = retrofit.create(RannaghorRetrofitService::class.java)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mName = view.findViewById(R.id.editTextFnameSignup)
        mEmail = view.findViewById(R.id.editTextEmailsignup)
        mNumber = view.findViewById(R.id.editTextNumbersignup)
        mPass = view.findViewById(R.id.editTextPasswordsignup)

        view.findViewById<TextView>(R.id.texViewLogin)
            .setOnClickListener {
                val loginActivity: LoginActivity = activity as LoginActivity
                loginActivity.startFragment(LoginFragment())
            }

        view.findViewById<MaterialButton>(R.id.buttonSignUp)
            .setOnClickListener {
                val name = mName.text.toString()
                val email = mEmail.text.toString()
                val number = mNumber.text.toString()
                val pass = mPass.text.toString()
                if (name.isEmpty()) {
                    mName.setError("required...")
                    return@setOnClickListener
                } else if (email.isEmpty()) {
                    mEmail.setError("required...")
                    return@setOnClickListener
                } else if (number.isEmpty()) {
                    mNumber.setError("required...")
                    return@setOnClickListener
                } else if (pass.isEmpty()) {
                    mPass.setError("required...")
                    return@setOnClickListener
                } else if (pass.length < 6) {
                    mPass.setError("at least 6 character")
                    return@setOnClickListener
                }

                compositeDisposable.add(
                    rannaghorRetrofitService.userRegistration(name, email, pass, number)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                startActivity(Intent(context, MainActivity::class.java))
                            }, this::handleError
                        )
                )


            }
    }

    private fun handleError(error: Throwable) {
        Logger.getLogger(TAG).warning("   Error: ${error.localizedMessage}")
    }
}
