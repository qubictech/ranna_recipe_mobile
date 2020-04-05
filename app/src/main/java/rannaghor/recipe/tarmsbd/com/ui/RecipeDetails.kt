package rannaghor.recipe.tarmsbd.com.ui

import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderView
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.adapter.ImageSliderAdapter
import rannaghor.recipe.tarmsbd.com.model.Recipe


class RecipeDetails : AppCompatActivity(R.layout.activity_recipe_details) {

    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null

    private lateinit var recipeName: TextView
    private lateinit var recipeDetail: TextView
    private lateinit var requiredMaterials: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recipeName = findViewById(R.id.recipe_name)
        recipeDetail = findViewById(R.id.textviewRecipe)
        requiredMaterials = findViewById(R.id.textviewMaterials)

        try {
            val recipe = intent.getParcelableExtra<Recipe>(RECIPE_DETAIL)

            supportActionBar?.title = recipe?.name?.trim()
            recipeName.text = recipe?.name?.trim()
            recipeDetail.text = recipe?.recipe?.trim()
            requiredMaterials.text = recipe?.materials?.trim()
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

//        val bottomSheet: View = findViewById(R.id.bottom_sheet)
//
//        mBottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheet)
//        val comment: RelativeLayout = findViewById(R.id.relative_lt)
//        comment.setOnClickListener() {
//            BottomSheetBehavior.from(bottomSheet).state = STATE_EXPANDED
//        }

        val sliderView = findViewById<SliderView>(R.id.imageSlider)
        val adapter = ImageSliderAdapter(applicationContext)

        val images = mutableListOf(
            R.drawable.ic_juice,
            R.drawable.ic_nasta,
            R.drawable.ic_burger_2,
            R.drawable.ic_burger
        )

        adapter.renewItems(images)

        sliderView.apply {
            setSliderAdapter(adapter)
            setIndicatorAnimation(IndicatorAnimations.WORM)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val RECIPE_DETAIL = "rannaghor.recipe.tarmsbd.com.ui.main.recipe_detail"
    }

}
