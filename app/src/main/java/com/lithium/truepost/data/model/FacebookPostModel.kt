package com.lithium.truepost.data.model

import androidx.annotation.DrawableRes
import java.util.UUID

data class FacebookPostModel(
    val id: String = UUID.randomUUID().toString(),
    val displayName: String,
    val content: String,
    val timestamp: String,
    val datestamp: String? = null,
    val reactionsCount: Int,
    val commentsCount: Int,
    val sharesCount: Int,
    @DrawableRes val avatarResId: Int,
    @DrawableRes val imagesResId: List<Int> = emptyList(),
    val isLegit: Boolean = false, // es hecha pr un humano real
)