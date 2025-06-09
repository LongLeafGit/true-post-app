package com.lithium.truepost.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_progress")
data class CourseProgressEntity(
    @PrimaryKey val courseId: String,
    val completed: Boolean = false,
)
