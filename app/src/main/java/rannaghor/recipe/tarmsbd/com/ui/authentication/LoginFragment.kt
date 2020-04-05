package rannaghor.recipe.tarmsbd.com.ui.authentication

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.service.RannaghorRetrofitService
import rannaghor.recipe.tarmsbd.com.service.RetrofitClient
import rannaghor.recipe.tarmsbd.com.ui.main.MainActivity
import rannaghor.recipe.tarmsbd.com.viewmodel.TAG
import java.util.logging.Logger
import kotlin.math.log


class LoginFragment : Fragment(R.layout.fragment_login) {





    private lateinit var mEmail: EditText
    private lateinit var mPass: EditText


    private val compositeDisposable = CompositeDisposable()
    private val retrofit = RetrofitClient.instance
    private val rannaghorRetrofitService = retrofit.create(RannaghorRetrofitService::class.java)

    override fun onStart() {
        super.onStart()
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        prefs.apply {
            val isLogin = prefs.getString("login", "no")

            if (isLogin=="yes"){
                startActivity(Intent(context, MainActivity::class.java))
            }

        }



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mEmail = view.findViewById(R.id.editTextEmailLogin)
        mPass = view.findViewById(R.id.editTextPasswordLogin)

        view.findViewById<Button>(R.id.buttonSignUp)
            .setOnClickListener {
                val email = mEmail.text.toString()
                val pass = mPass.text.toString()

                if (email.isEmpty()) {
                    mEmail.error = ("required...")
                    return@setOnClickListener
                } else if (pass.isEmpty()) {
                    mPass.error = ("required...")
                    return@setOnClickListener
                } else if (pass.length < 6) {
                    mPass.error = ("at least 6 character")
                    return@setOnClickListener
                }

                compositeDisposable.add(
                    rannaghorRetrofitService.userLogin(email, pass, "login")
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                            {
                                val size=it.size
                                if (size>0) {
                                    startActivity(Intent(context, MainActivity::class.java))

                                    val prefs =
                                        PreferenceManager.getDefaultSharedPreferences(context)
                                    val editor = prefs.edit()
                                    editor.putString("login", "yes").apply()
                                }else{
                                    Toast.makeText(context,"Login Failed",Toast.LENGTH_LONG).show()
                                }

                            }, this::handleError
                        )
                )
            }

        view.findViewById<TextView>(R.id.texViewLogin)
            .setOnClickListener {
                val loginActivity: LoginActivity = activity as LoginActivity
                loginActivity.startFragment(SingUpFragment())
            }
    }

    private fun handleError(error: Throwable) {
        Logger.getLogger(TAG).warning("   Error: ${error.localizedMessage}")
    }

}
