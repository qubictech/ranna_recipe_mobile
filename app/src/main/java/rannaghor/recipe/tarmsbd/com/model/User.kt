package rannaghor.recipe.tarmsbd.com.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User(
    var name: String?,
    @PrimaryKey
    var email: String,
    var password: String?,
    @SerializedName("phone")
    var number: String?,
    var token: String?
) {
    constructor() : this("", "", "", "", "")
}