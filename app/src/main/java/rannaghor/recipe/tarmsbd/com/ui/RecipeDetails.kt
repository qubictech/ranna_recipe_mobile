package rannaghor.recipe.tarmsbd.com.ui

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import rannaghor.recipe.tarmsbd.com.R
import rannaghor.recipe.tarmsbd.com.model.Recipe


class RecipeDetails : AppCompatActivity(R.layout.activity_recipe_details) {

    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            val recipe = intent.getParcelableExtra<Recipe>(RECIPE_DETAIL)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
        val bottomSheet: View = findViewById(R.id.bottom_sheet)

        mBottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheet)
        val comment: RelativeLayout = findViewById(R.id.relative_lt)
        comment.setOnClickListener() {
            BottomSheetBehavior.from(bottomSheet).state = STATE_EXPANDED
        }

    }

    companion object {
        const val RECIPE_DETAIL = "rannaghor.recipe.tarmsbd.com.ui.main.recipe_detail"
    }

}
