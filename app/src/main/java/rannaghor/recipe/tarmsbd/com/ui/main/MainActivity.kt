package rannaghor.recipe.tarmsbd.com.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
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
    private val compositeDisposable = CompositeDisposable()

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

        val retrofit = RetrofitClient.INSTANCE
        val rannaghorRetrofitService = retrofit.create(RannaghorRetrofitService::class.java)

        compositeDisposable.add(
            rannaghorRetrofitService.recipe
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { recipes ->
                        try {
                            rannaghorRoomViewModel.insertRecipes(recipes)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }, this::handleError
                )
        )
    }

    private fun handleError(error: Throwable) {
        Logger.getLogger("MainActivity")
            .warning("   Error: ${error.localizedMessage}")
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

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}
