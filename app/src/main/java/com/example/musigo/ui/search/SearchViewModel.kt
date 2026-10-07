package com.example.musigo.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musigo.data.model.Artist
import com.example.musigo.data.model.Song
import com.example.musigo.data.model.SongUiModel
import com.example.musigo.data.repository.SongRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val songRepository: SongRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _songResults = MutableStateFlow<List<SongUiModel>>(emptyList())
    val songResults: StateFlow<List<SongUiModel>> = _songResults.asStateFlow()

    private val _artistResults = MutableStateFlow<List<Artist>>(emptyList())
    val artistResults : StateFlow<List<Artist>> = _artistResults.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
        if(newQuery.isBlank()) {
            _songResults.value = emptyList()
            _artistResults.value = emptyList()
        }
        performSearch(newQuery)
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _songResults.value = songRepository.searchSongs(query)
                _artistResults.value = songRepository.searchArtists(query)
            }catch (e: Exception) {
                e.printStackTrace()
            }finally {
                _isLoading.value = false
            }
        }
    }


}