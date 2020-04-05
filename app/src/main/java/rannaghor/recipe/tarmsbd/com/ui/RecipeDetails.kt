package rannaghor.recipe.tarmsbd.com.ui

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import rannaghor.recipe.tarmsbd.com.R


class RecipeDetails : AppCompatActivity() {

    private var mBottomSheetBehavior: BottomSheetBehavior<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_details)


        val bottomSheet: View = findViewById(R.id.bottom_sheet)

        mBottomSheetBehavior = BottomSheetBehavior.from<View>(bottomSheet)
        val comment:RelativeLayout=findViewById(R.id.relative_lt)
        comment.setOnClickListener(){
            BottomSheetBehavior.from(bottomSheet).state=STATE_EXPANDED
        }

    }

}
