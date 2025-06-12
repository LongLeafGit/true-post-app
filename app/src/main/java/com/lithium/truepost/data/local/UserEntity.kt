package com.lithium.truepost.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val email: String,
    val firstname: String,
    val bestScore: Int,
)