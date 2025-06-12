package com.lithium.truepost.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.lithium.truepost.data.local.CourseProgressDao
import com.lithium.truepost.data.local.CourseProgressEntity
import com.lithium.truepost.data.local.UserDao
import com.lithium.truepost.data.local.UserEntity

@Database(entities = [CourseProgressEntity::class, UserEntity::class], version = 2)
abstract class TruePostDatabase : RoomDatabase() {
    abstract fun courseProgressDao(): CourseProgressDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: TruePostDatabase? = null

        fun getDatabase(context: Context): TruePostDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TruePostDatabase::class.java,
                    "true_post_database"
                ).addMigrations(MIGRATION_1_2).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Agrega el nuevo campo con un valor por defecto
        database.execSQL("ALTER TABLE user ADD COLUMN bestScore INTEGER NOT NULL DEFAULT 0")
    }
}
