package rannaghor.recipe.tarmsbd.com.database.roompersistance.sqlite

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rannaghor.recipe.tarmsbd.com.database.roompersistance.dao.RannaghorDao
import rannaghor.recipe.tarmsbd.com.model.Recipe

@Database(entities = [Recipe::class], version = 4, exportSchema = false)
abstract class RannaghorDatabase : RoomDatabase() {
    abstract fun rannaghorDao(): RannaghorDao

    companion object {
        @Volatile
        private var INSTANCE: RannaghorDatabase? = null

        fun getDatabase(context: Context): RannaghorDatabase {
            val temp =
                INSTANCE
            if (temp != null) return temp

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RannaghorDatabase::class.java,
                    "rannaghor_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance

                return instance
            }
        }
    }
}