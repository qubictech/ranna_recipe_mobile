package rannaghor.recipe.tarmsbd.com.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.service.RannaghorRetrofitService
import rannaghor.recipe.tarmsbd.com.service.RetrofitClient
import rannaghor.recipe.tarmsbd.com.viewmodel.RannaghorViewModel
import java.util.logging.Logger

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val compositeDisposable = CompositeDisposable()
    private val retrofit = RetrofitClient.instance
    private val rannaghorRetrofitService = retrofit.create(RannaghorRetrofitService::class.java)

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
        val rannaghorRoomViewModel = ViewModelProvider(this).get(RannaghorViewModel::class.java)

        compositeDisposable.add(
            rannaghorRetrofitService.recipe
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { recipes ->
                        rannaghorRoomViewModel.insertRecipes(recipes)
                    }, this::handleError
                )
        )
    }

    private fun handleError(error: Throwable) {
        Logger.getLogger("MainActivity")
            .warning("   Error: ${error.localizedMessage}")
    }

    private fun setupBottomNavigationView() {
        bottomNavigationView = findViewById(R.id.bottom_navigation_view)

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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
