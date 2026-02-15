package com.mycaruae.app.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "brands")
data class BrandEntity(
    @PrimaryKey val id: String,
    val name: String,
    val popularityRank: Int,
)
