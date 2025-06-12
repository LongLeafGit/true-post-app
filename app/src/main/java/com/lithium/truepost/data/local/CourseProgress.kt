package com.lithium.truepost.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_progress")
data class CourseProgressEntity(
    @PrimaryKey val courseId: Int,
    val completed: Boolean = false,
)
