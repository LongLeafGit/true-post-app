package com.lithium.truepost.data.model

import androidx.annotation.DrawableRes

data class XTweetModel(
    val displayName: String,
    val username: String = "@${displayName}",
    val isVerified: Boolean,
    val content: String,
    val timestamp: String,
    val datestamp: String? = null,
    val repliesCount: Int = 0,
    val retweetsCount: Int = 0,
    val likesCount: Int = 0,
    val viewsCount: Int = 0,
    @DrawableRes val avatarResId: Int,
    @DrawableRes val imagesResId: List<Int> = emptyList(),
)