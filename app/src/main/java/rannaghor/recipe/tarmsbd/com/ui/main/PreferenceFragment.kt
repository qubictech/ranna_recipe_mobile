package rannaghor.recipe.tarmsbd.com.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
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
}
