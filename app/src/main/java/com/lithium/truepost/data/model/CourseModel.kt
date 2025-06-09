package com.lithium.truepost.data.model

import androidx.annotation.DrawableRes

data class CourseData(
    val id: String,
    @DrawableRes val imageResId: Int,
    val title: String,
    val description: String,
    val completed: Boolean = false,
    val content: List<CourseContent>,
)

data class CourseContent(
    val title: String,
    val imageResId: Int,
    val text: String,
)