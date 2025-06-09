package com.lithium.truepost.data.model

import androidx.annotation.DrawableRes

data class FacebookPostModel(
    val displayName: String,
    val content: String,
    val timestamp: String,
    val datestamp: String? = null,
    val reactionsCount: Int,
    val commentsCount: Int,
    val sharesCount: Int,
    @DrawableRes val avatarResId: Int,
    @DrawableRes val imagesResId: List<Int> = emptyList(),
)