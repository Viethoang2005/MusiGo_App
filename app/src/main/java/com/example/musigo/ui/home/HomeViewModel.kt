package com.example.musigo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musigo.data.model.Artist
import com.example.musigo.data.model.SongUiModel
import com.example.musigo.data.repository.ArtistRepository
import com.example.musigo.data.repository.SongRepository
import com.example.musigo.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val songRepository: SongRepository,
    private val artistRepository: ArtistRepository
) : ViewModel() {
    private val _songs = MutableStateFlow<List<SongUiModel>>(emptyList())
    val songs = _songs.asStateFlow()
    private val _artists = MutableStateFlow<List<Artist>>(emptyList())
    val artists = _artists.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadSongs() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _songs.value = songRepository.getSongsWithArtist()
            }catch (e: Exception) {
                _error.value = e.message
            }finally {
                _isLoading.value = false
            }
        }

    }

    fun loadArtists() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _error.value = null
                _artists.value = artistRepository.getAllArtist()
            }catch (e: Exception) {
                _error.value = e.message
            }
            finally {
                _isLoading.value = false
            }
        }
    }
}