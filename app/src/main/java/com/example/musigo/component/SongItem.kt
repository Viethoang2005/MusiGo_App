package com.example.musigo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.musigo.R
import com.example.musigo.data.model.Song
import com.example.musigo.data.model.SongUiModel


@Composable
fun SongItem(
    song: SongUiModel,
    isSelected: Boolean,
    onPlay: () -> Unit
) {
    val textColor = if(isSelected) colorResource(R.color.purple_200)
                    else colorResource(R.color.textPrimary)
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.primaryDark)),
        modifier = Modifier.width(200.dp).height(250.dp).clickable {
            onPlay()
        }
    ) {
        Column(
            modifier = Modifier.padding(8.dp).height(230.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                model = song.song.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(170.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = Modifier.padding(2.dp),
                text = song.song.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp,
                color = textColor
            )
            Spacer(Modifier.height(1.dp))
            Text(
                modifier = Modifier.padding(2.dp),
                text = song.artistName,
                color = colorResource(R.color.textSecondary),
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 14.sp
            )

        }
    }
}
@Preview
@Composable
fun SongItemPreview() {
    val song = Song("1", "Te that, anh nho em", "Thanh Hung", "6:00", "",225, "")
    val songModel = SongUiModel(song, "kenn")
    SongItem(songModel, false, onPlay = {})
}

