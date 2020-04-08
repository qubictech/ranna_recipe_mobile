package rannaghor.recipe.tarmsbd.com.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(@PrimaryKey var id: String, var images: String, var name: String)