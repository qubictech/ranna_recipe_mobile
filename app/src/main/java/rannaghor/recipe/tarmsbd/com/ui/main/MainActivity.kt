package rannaghor.recipe.tarmsbd.com.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import rannaghor.recipe.tarmsbd.com.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(
                R.id.fragment_container,
                ExploreRecipeFragment()
            ).commit()
        }

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_explore -> supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    ExploreRecipeFragment()
                ).commit()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
