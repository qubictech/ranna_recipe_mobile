package rannaghor.recipe.tarmsbd.com.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import rannaghor.recipe.tarmsbd.com.R

class MainActivity : AppCompatActivity() {

    private var bottomNavigationView: BottomNavigationView =
        findViewById(R.id.bottom_navigation_view)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    init {
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
