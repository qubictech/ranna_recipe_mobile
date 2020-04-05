package rannaghor.recipe.tarmsbd.com.ui.main

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import rannaghor.recipe.tarmsbd.com.R

class PreferenceFragment : Fragment(R.layout.fragment_preference) {

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PreferenceFragment().apply {
                arguments = Bundle().apply {
                    //                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<MaterialButton>(R.id.buttonLogout).setOnClickListener(){
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            val  editor =prefs.edit()
            editor.putString("login","no").apply()
        }
    }
}
