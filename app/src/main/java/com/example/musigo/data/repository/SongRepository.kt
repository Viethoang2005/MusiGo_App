package com.example.musigo.data.repository


import android.util.Log
import com.example.musigo.data.model.Artist
import com.example.musigo.data.model.Song
import com.example.musigo.data.model.SongUiModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SongRepository @Inject constructor(){
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun getSongs() : List<Song> {
        val snapshot = firestore
            .collection("songs")
            .get()
            .await()
        return snapshot.documents.map { document ->
            Song(
                id = document.id,
                title = document.getString("title") ?: "",
                artistId = document.getString("artistId") ?: "",
                albumId = document.getString("albumId") ?: "",
                audioUrl = document.getString("audioUrl") ?: "",
                imageUrl = document.getString("imageUrl") ?: "",
                duration = document.getLong("duration") ?: 0
            )
        }
    }

    suspend fun getArtists() : Map<String, String> {
        val snapshot = firestore
            .collection("artists")
            .get()
            .await()
        return snapshot.documents.associate { document ->
            document.id to (
                document.getString("name") ?: "Unknown Artist"
            )
        }
    }



    // lay tat ca bai hat trong db kem theo ten artist cua tung bai
    suspend fun getSongsWithArtist() : List<SongUiModel> {
        val songs = getSongs()
        val artists = getArtists()
        return songs.map { song ->
            SongUiModel(
                song = song,
                artistName = artists[song.artistId] ?: "Unknown Artist"
            )
        }
    }

    suspend fun searchSongs(query: String) : List<SongUiModel> {
        return try {
            if(query.isBlank()) return emptyList()
            val queryLower = query.lowercase()
            val artistMap = getArtists()

            val snapshot = firestore
                .collection("songs")
                .orderBy("title_lowercase")
                .startAt(queryLower)
                .endAt(queryLower + "\uf8ff")
                .get()
                .await()

            Log.d("SearchDebug", "So luong document tim thay: ${snapshot.documents.size}")
            snapshot.documents.mapNotNull { doc ->
                val song = Song(
                    id = doc.id,
                    title = doc.getString("title") ?: "",
                    artistId = doc.getString("artistId") ?: "",
                    albumId = doc.getString("albumId") ?: "",
                    audioUrl = doc.getString("audioUrl") ?: "",
                    imageUrl = doc.getString("imageUrl") ?: "",
                    duration = doc.getLong("duration") ?: 0
                )
                SongUiModel(
                    song = song,
                    artistName = artistMap[song.artistId] ?: "Unknown Artist"
                )
            }
        }catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun searchArtists(query: String) : List<Artist> {
        return try {
            if(query.isBlank()) return emptyList()
            val queryLower = query.lowercase()
            val snapshot = firestore
                .collection("artists")
                .orderBy("name_lowercase")
                .startAt(queryLower)
                .endAt(queryLower + "\uf8ff")
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(Artist::class.java)
            }
        }catch (e: Exception) {
            emptyList()
        }
    }

    // lay tat ca cac bai hat thuoc ve mot artist cu the
    suspend fun getSongsByArtist(artistId: String) : List<SongUiModel> {
        return try {

            val snapshot = firestore
                .collection("songs")
                .whereEqualTo("artistId", artistId)
                .get()
                .await()

            snapshot.documents.mapNotNull { doc ->
                doc.toObject(SongUiModel::class.java)
            }
        }catch (e: Exception) {
            emptyList()
        }
    }

}