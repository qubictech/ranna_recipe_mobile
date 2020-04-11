package rannaghor.recipe.tarmsbd.com.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.MobileAds
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.database.network.RannaghorRetrofitService
import rannaghor.recipe.tarmsbd.com.database.network.RetrofitClient
import rannaghor.recipe.tarmsbd.com.database.roompersistance.viewmodel.RannaghorViewModel
import java.util.logging.Logger

class MainActivity : AppCompatActivity(R.layout.activity_main) {

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
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation_view)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_explore -> supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    ExploreRecipeFragment()
                ).commit()
                R.id.menu_favorite -> supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container,
                    FavoriteRecipeFragment()
                ).commit()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }
}
