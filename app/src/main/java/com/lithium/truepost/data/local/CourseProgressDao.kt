package com.lithium.truepost.data.local

import androidx.room.*

@Dao
interface CourseProgressDao {

    @Query("SELECT * FROM course_progress")
    suspend fun getAllProgress(): List<CourseProgressEntity>

    @Query("SELECT * FROM course_progress WHERE courseId = :id LIMIT 1")
    suspend fun getProgress(id: Int): CourseProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: CourseProgressEntity)
}
