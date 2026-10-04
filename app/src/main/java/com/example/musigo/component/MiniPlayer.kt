package com.example.musigo.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.musigo.R
import com.example.musigo.data.model.Song
import com.example.musigo.data.model.SongUiModel

@Composable
fun MiniPlayer(
    song: SongUiModel?,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onNext: () -> Unit,
    onNavigateToPlayerScreen: () -> Unit
) {
    val iconPlay = if(isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow
    if(song == null) return
    Card(
        modifier = Modifier.fillMaxWidth().padding(10.dp).clickable{
            onNavigateToPlayerScreen()
        },
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.primaryDark)
        ),
        border = BorderStroke(1.dp, Color.Gray),
        elevation = CardDefaults.elevatedCardElevation(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = song.song.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(50.dp).clip(RoundedCornerShape(10.dp))
            )
            Spacer(Modifier.width(10.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.height(70.dp).weight(1f)
            ) {
                Text(
                    text = song.song.title,
                    fontSize = 13.sp,
                    color = colorResource(R.color.textPrimary),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    lineHeight = 14.sp
                )
                Text(
                    text = song.artistName,
                    fontSize = 11.sp,
                    color = colorResource(R.color.textSecondary),
                    maxLines = 1
                )
            }
            IconButton(
                onClick = onPlayPauseClick,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = iconPlay,
                    contentDescription = "Pause"
                )
            }

            IconButton(
                onClick = onNext,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "next"
                )
            }
        }


    }
}

@Preview
@Composable
fun MiniPlayerPreview() {
    val song = Song("1", "Te that, anh nho em", "Thanh Hung", "6:00", "",225, "")
    val songModel = SongUiModel(song, "Thanh Hưng")
    MiniPlayer(songModel, true, onNavigateToPlayerScreen = {}, onNext = {}, onPlayPauseClick = {})
}