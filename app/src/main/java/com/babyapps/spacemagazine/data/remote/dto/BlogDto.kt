package com.babyapps.spacemagazine.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "blogs")
data class BlogDto(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val url: String,
    val imageUrl: String,
    val newsSite: String,
    val summary: String,
    val publishedAt: String
):Serializable