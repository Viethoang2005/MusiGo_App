package com.example.musigo.data.model

import com.google.gson.annotations.SerializedName

data class Song(
    val id: String = "",
    val title: String = "",
    val artistId: String = "",
    val albumId : String = "",
    val audioUrl: String = "",
    val duration: Long = 0,
    val imageUrl: String = ""
)
