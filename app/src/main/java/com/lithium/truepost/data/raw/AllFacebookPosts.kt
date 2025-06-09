package com.lithium.truepost.data.raw

import com.lithium.truepost.data.model.FacebookPostModel
import com.lithium.truepost.R

val AllFacebookPosts = listOf(
    FacebookPostModel(
        displayName = "Luis Mario",
        content = "He encontrado una aplicación para aprender a diferenciar publicaciones manipuladas de legítimas.",
        timestamp = "2 h",
        reactionsCount = 127,
        commentsCount = 18,
        sharesCount = 7,
        avatarResId = R.drawable.facebook,
        imagesResId = listOf(R.drawable.posts, R.drawable.disinformation)
    )
)