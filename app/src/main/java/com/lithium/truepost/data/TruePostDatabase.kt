package com.lithium.truepost.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lithium.truepost.data.local.CourseProgressDao
import com.lithium.truepost.data.local.CourseProgressEntity

@Database(entities = [CourseProgressEntity::class], version = 1)
abstract class TruePostDatabase : RoomDatabase() {
    abstract fun courseProgressDao(): CourseProgressDao
}
