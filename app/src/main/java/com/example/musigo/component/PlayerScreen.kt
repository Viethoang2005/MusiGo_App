package com.example.musigo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.musigo.R
import com.example.musigo.data.model.Song
import com.example.musigo.data.model.SongUiModel
import com.example.musigo.ui.song.SongViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerScreen(
    song: SongUiModel?,
    songViewModel: SongViewModel,
    onBackClick: () -> Unit
) {
    val currentPosition by songViewModel.currentPosition.collectAsState()
    val songDuration by songViewModel.duration.collectAsState()
    val isPlaying by songViewModel.isPlaying.collectAsState()

    val slidePosition = if(songDuration > 0) {
        currentPosition.toFloat() / songDuration.toFloat()
    } else 0f

    Scaffold(
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(0, 0, 0, 0),
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "MusiGo",
                            fontSize = 24.sp,
                            textAlign = TextAlign.Center,
                            color = colorResource(R.color.textPrimary)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = Color.White
                        ),
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "back"
                        )
                    }
                },
                actions =  {
                    Box(Modifier.size(48.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.primaryDark)
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colorResource(R.color.primaryDark))
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(20.dp)
                        .clip(RoundedCornerShape(40.dp)),
                    model = song?.song?.imageUrl,
                    contentDescription = null
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = song?.song?.title ?: "Unknown Title",
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    color = colorResource(R.color.textPrimary)
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = song?.artistName ?: "Unknown Artist",
                    fontSize = 20.sp,
                    color = colorResource(R.color.textSecondary)
                )

                Spacer(Modifier.height(40.dp))
                Column(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
                ) {
                    Slider(
                        value = slidePosition,
                        onValueChange = {newValue ->
                            val targetPosition  = (newValue * songDuration).toLong()
                            songViewModel.seekTo(targetPosition)
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = colorResource(R.color.textPrimary),
                            activeTrackColor = colorResource(R.color.textPrimary),
                            inactiveTrackColor = Color.Gray.copy(alpha = 0.4f)
                        ),
                        track = { sliderState ->
                            val fraction = (sliderState.value - sliderState.valueRange.start) /
                                    (sliderState.valueRange.endInclusive - sliderState.valueRange.start)

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(3.dp) // Độ dày của thanh slider (bạn có thể chỉnh 2.dp hoặc 4.dp tùy ý)
                                    .clip(RoundedCornerShape(1.5.dp))
                                    .background(Color.Gray.copy(alpha = 0.3f))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(fraction)
                                        .height(3.dp)
                                        .clip(RoundedCornerShape(1.5.dp))
                                        .background(colorResource(R.color.textPrimary))
                                )
                            }
                        },
                        thumb = {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .clip(CircleShape)
                                    .background(colorResource(R.color.textPrimary))

                            )
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = songViewModel.formatDuration(currentPosition),
                            fontSize = 13.sp,
                            color = colorResource(R.color.textSecondary)
                        )
                        Text(
                            text = songViewModel.formatDuration(songDuration),
                            fontSize = 13.sp,
                            color = colorResource(R.color.textSecondary)
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colorResource(R.color.textPrimary)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shuffle,
                            contentDescription = "Shuffle"
                        )
                    }
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colorResource(R.color.textPrimary)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipPrevious,
                            contentDescription = "Previous"
                        )
                    }
                    FloatingActionButton(
                        onClick = { songViewModel.togglePlayPause() },
                        containerColor = colorResource(R.color.textPrimary),
                        contentColor = colorResource(R.color.primaryDark),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(
                            imageVector = if(isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if(isPlaying) "Pause" else "Play",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colorResource(R.color.textPrimary)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.SkipNext,
                            contentDescription = "Next"
                        )
                    }
                    IconButton(
                        onClick = {},
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colorResource(R.color.textPrimary)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Repeat,
                            contentDescription = "Repeat"
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PLayerScreenPreview() {
    val song = Song("1", "Te that, anh nho em", "Thanh Hung", "", "",225, "")
    val songModel = SongUiModel(song, "Thanh Hưng")
    PlayerScreen(songModel, songViewModel = hiltViewModel(), onBackClick = {})
}