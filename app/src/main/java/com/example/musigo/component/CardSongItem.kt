package com.example.musigo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
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
fun CardSongItem(
    songUiModel: SongUiModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val textColor = if(isSelected) colorResource(R.color.purple_200)
                else colorResource(R.color.textPrimary)
    Box(
        Modifier.fillMaxWidth().clickable {
            onClick()
        }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier.size(50.dp).clip(CircleShape),
                model = songUiModel.song.imageUrl,
                contentDescription = "Song Image"
            )
            Spacer(Modifier.width(10.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = songUiModel.song.title,
                    fontSize = 22.sp,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.SemiBold,
                    color = textColor
                )

                Text(
                    text = songUiModel.artistName,
                    fontSize = 15.sp,
                    color = colorResource(R.color.textSecondary)
                )
            }

            IconButton(
                onClick = {},
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = "More"
                )
            }
        }
    }

}


@Preview
@Composable
fun CardSongItemPreview() {
    CardSongItem(
        SongUiModel(
            Song("1", "Te that, anh nho em", "","","", 255, ""),
            "Thanh Hung"
        ),
        false,
        onClick = {}
    )
}