package com.example.musigo.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musigo.R
import com.example.musigo.component.ArtistItem
import com.example.musigo.component.SongItem
import com.example.musigo.component.TopBar
import com.example.musigo.theme.MusiGoTheme
import com.example.musigo.ui.song.SongViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    songViewModel: SongViewModel = hiltViewModel()
) {
    HomeScreenContent(homeViewModel, songViewModel)
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    homeViewModel: HomeViewModel,
    songViewModel: SongViewModel
) {
    val songs by homeViewModel.songs.collectAsState()
    val artists by homeViewModel.artists.collectAsState()

    val isLoading by homeViewModel.isLoading.collectAsState()
    val error by homeViewModel.error.collectAsState()

    val currentPlayingId by songViewModel.currentPlayingSongId.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.loadSongs()
        homeViewModel.loadArtists()
    }
    Scaffold(
        topBar = { TopBar() }
    ) { innerPadding ->
        if(isLoading) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }else if(error != null) {
            Box(
                modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: $error")
            }
        }
        else {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(colorResource(R.color.primaryDark))
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(16.dp)
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                        ) {
                            Text(
                                "Recent Played",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.textPrimary)
                            )
                            Spacer(Modifier.height(12.dp))
                            LazyRow {
                                items(songs) { song ->
                                    val isSelected = currentPlayingId == song.song.id
                                    SongItem(
                                        song = song,
                                        isSelected,
                                        onPlay = {
                                            songViewModel.playSong(song)
                                        }
                                    )
                                }
                            }
                        }

                    }

                    // trending songs
                    item {
                        Spacer(Modifier.height(40.dp))
                    }
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                        ) {
                            Text(
                                "Trending Songs",
                                fontSize = 22.sp,

                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.textPrimary)
                            )
                            Spacer(Modifier.height(12.dp))
                            LazyRow {
                                items(songs) { song ->
                                    val isSelected = currentPlayingId == song.song.id
                                    SongItem(
                                        song = song,
                                        isSelected,
                                        onPlay = {
                                            songViewModel.playSong(song)
                                        }
                                    )
                                }
                            }
                        }

                    }

                    // artists
                    item {
                        Spacer(Modifier.height(40.dp))
                    }
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                        ) {
                            Text(
                                "Artists",
                                fontSize = 22.sp,

                                fontWeight = FontWeight.Bold,
                                color = colorResource(R.color.textPrimary)
                            )
                            Spacer(Modifier.height(12.dp))
                            LazyRow {
                                items(artists) { artist ->
                                    ArtistItem(
                                        artist = artist
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
fun HomeScreenPreview() {
    MusiGoTheme {
        HomeScreenContent(viewModel(), viewModel())
    }
}