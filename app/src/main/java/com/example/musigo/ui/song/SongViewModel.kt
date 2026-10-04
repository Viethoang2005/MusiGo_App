package com.example.musigo.ui.song

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musigo.data.model.Artist
import com.example.musigo.data.model.SongUiModel
import com.example.musigo.data.repository.SongRepository
import com.example.musigo.player.MusicPlayerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SongViewModel @Inject constructor(
    private val musicPlayerManager: MusicPlayerManager
): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _currentPlayingSongId = MutableStateFlow<String?>(null)
    val currentPlayingSongId: StateFlow<String?> = _currentPlayingSongId.asStateFlow()

    private val _currentPlayingSong = MutableStateFlow<SongUiModel?>(null)
    val currentPlayingSong: StateFlow<SongUiModel?> = _currentPlayingSong.asStateFlow()

    private val _currentPositon = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPositon.asStateFlow()

    private val _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long> = _duration.asStateFlow()
    init {
        viewModelScope.launch {
            while (true) {
                if(_currentPlayingSong.value != null) {
                    _currentPositon.value = musicPlayerManager.currentPosition
                    _duration.value = musicPlayerManager.duration.coerceAtLeast(0L)

                    _isPlaying.value = musicPlayerManager.isPlaying
                }
                delay(500L.milliseconds)
            }
        }
    }

    fun seekTo(position: Long) {
        musicPlayerManager.seekTo(position)
        _currentPositon.value = position
    }

    fun playSong(song: SongUiModel) {
        _currentPlayingSongId.value = song.song.id
        _currentPlayingSong.value = song
        musicPlayerManager.play(song.song.audioUrl)
        _isPlaying.value = true
        _duration.value = musicPlayerManager.duration.coerceAtLeast(0L)
    }

    fun togglePlayPause() {
        musicPlayerManager.togglePlayPause()
        _isPlaying.value = musicPlayerManager.isPlaying
    }

    @SuppressLint("DefaultLocale")
    fun formatDuration(ms: Long) : String {
        if(ms < 0) return "0:00"
        val totalSeconds = ms/1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        return String.format("%d:%02d", minutes, seconds)
    }
}