package com.example.musigo.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.musigo.R
import com.example.musigo.component.ArtistItem
import com.example.musigo.component.CardSongItem
import com.example.musigo.data.model.Artist
import com.example.musigo.data.model.SongUiModel
import com.example.musigo.theme.MusiGoTheme
import com.example.musigo.ui.song.SongViewModel

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    songViewModel: SongViewModel
) {
    val inputSearch by searchViewModel.searchQuery.collectAsState()
    val songs by searchViewModel.songResults.collectAsState()
    val artists by searchViewModel.artistResults.collectAsState()
    val isLoading by searchViewModel.isLoading.collectAsState()

    val playerState by songViewModel.playerState.collectAsState()
    val currentSongId = playerState.currentSongId
    SearchScreenContent(
        inputSearch = inputSearch,
        onQueryChanged = { searchViewModel.onQueryChanged(it)},
        currentSongId = currentSongId ?: "",
        songs = songs,
        artists = artists,
        isLoading = isLoading,
        onSongClick = { index ->
            songViewModel.setPlaylist(songs, index)
        },
        onArtistClick =  {}
    )
}

@Composable
fun SearchScreenContent(
    inputSearch: String,
    onQueryChanged: (String) -> Unit,
    currentSongId: String,
    songs: List<SongUiModel>,
    artists: List<Artist>,
    isLoading: Boolean,
    onSongClick: (Int) -> Unit,
    onArtistClick: (Int) -> Unit
) {


    Box(
        Modifier
            .fillMaxSize()
            .background(colorResource(R.color.primaryDark))
            .padding(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Search",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.textPrimary)
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(15.dp)),
                value = inputSearch,
                onValueChange = onQueryChanged,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    unfocusedSupportingTextColor = Color.Gray
                ),
                maxLines = 1,
                placeholder = {
                    Text(
                        text = "Artists or songs",
                        color = colorResource(R.color.textSecondary)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                }
            )
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                when{
                    isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center),
                            color = colorResource(R.color.textPrimary)
                        )
                    }
                    inputSearch.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Nhap ten bai hat hoac ca si de tim kiem",
                                color = colorResource(R.color.textSecondary),
                                fontSize = 15.sp
                            )
                        }
                    }
                    songs.isEmpty() && artists.isEmpty() -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Khong tim thay ket qua phu hop",
                                color = colorResource(R.color.textSecondary),
                                fontSize = 15.sp
                            )
                        }
                    }
                    else -> {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                                .padding(horizontal = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if(artists.isNotEmpty()) {
                                item {
                                    Text(
                                        text = "Artist",
                                        color = colorResource(R.color.textPrimary),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                                itemsIndexed(artists) {index, artist ->
                                    ArtistItem(artist)
                                }
                            }
                            if(songs.isNotEmpty()) {
                                item {
                                    Text(
                                        text = "Tracks",
                                        color = colorResource(R.color.textPrimary),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                itemsIndexed(songs) { index, song ->
                                    val isSelected = currentSongId == song.song.id
                                    CardSongItem(
                                        songUiModel = song,
                                        isSelected = isSelected,
                                        onClick = { onSongClick(index) }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }


    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchScreenPreview() {
    MusiGoTheme {
        SearchScreenContent(
            inputSearch = "inputSearch",
            onQueryChanged = { },
            currentSongId = "",
            songs = emptyList(),
            artists = emptyList(),
            isLoading = false,
            onSongClick = {},
            onArtistClick = {}
        )
    }
}