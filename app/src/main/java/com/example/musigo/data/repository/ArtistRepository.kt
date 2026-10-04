package com.example.musigo.data.repository

import com.example.musigo.data.model.Artist
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class ArtistRepository @Inject constructor() {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getAllArtist() : List<Artist> {
        val snapshot = firestore
            .collection("artists")
            .get()
            .await()

        return snapshot.documents.map { document ->
            Artist(
                id = document.id,
                name = document.getString("name") ?: "",
                imageUrl = document.getString("imageUrl") ?: ""
            )
        }
    }
}