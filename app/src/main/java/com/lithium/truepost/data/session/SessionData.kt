package com.lithium.truepost.data.session

data class SessionData(
    val email: String,
    val name: String,
    val accessToken: String,
    val refreshToken: String
)