package rannaghor.recipe.tarmsbd.com.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    @SerializedName("id")
    var id: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("materials")
    var materials: String?,
    @SerializedName("recipe")
    var recipe: String?,
    @SerializedName("images")
    var image: String?,
    @SerializedName("videos")
    var videos: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("likes")
    var likes: Int?
) : Parcelable {
    constructor() : this("", "", "", "", "", "", "", 0)
}