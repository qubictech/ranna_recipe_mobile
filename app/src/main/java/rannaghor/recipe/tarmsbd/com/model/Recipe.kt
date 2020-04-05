package rannaghor.recipe.tarmsbd.com.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    var id: String,
    var name: String,
    var materials: String,
    var recipe: String,
    var images: String,
    var videos: String,
    var type: String
) : Parcelable