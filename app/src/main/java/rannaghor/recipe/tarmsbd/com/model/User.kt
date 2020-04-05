package rannaghor.recipe.tarmsbd.com.model

import com.google.gson.annotations.SerializedName

data class User(
    var name: String,
    var email: String,
    var password: String,
    @SerializedName("phone")
    var number: String
)