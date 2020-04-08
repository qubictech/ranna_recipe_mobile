package rannaghor.recipe.tarmsbd.com.service

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import rannaghor.recipe.tarmsbd.com.model.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = false)
abstract class RannaghorDatabase : RoomDatabase() {
    abstract fun rannaghorDao(): RannaghorDao

    companion object {
        @Volatile
        private var INSTANCE: RannaghorDatabase? = null

        fun getDatabase(context: Context): RannaghorDatabase {
            val temp = INSTANCE
            if (temp != null) return temp

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RannaghorDatabase::class.java,
                    "rannaghor_database"
                ).build()
                INSTANCE = instance

                return instance
            }
        }
    }
}