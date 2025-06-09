package com.lithium.truepost.data.repository

import com.lithium.truepost.data.model.FacebookPostModel
import com.lithium.truepost.data.raw.AllFacebookPosts

class FacebookRepository() {
    private val basePosts = AllFacebookPosts

    suspend fun getPosts(): List<FacebookPostModel> {
        return basePosts
    }
}