package com.example.musigo.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import com.example.musigo.data.model.Artist

@Composable
fun ArtistItem(
    artist: Artist
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(R.color.primaryDark)),
        modifier = Modifier.width(200.dp).height(220.dp).clickable {

        }
    ) {
        Column(
            modifier = Modifier.padding(8.dp).height(230.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            AsyncImage(
                model = artist.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(170.dp)
                    .clip(CircleShape)
            )
            Spacer(Modifier.height(4.dp))
            Text(
                modifier = Modifier.padding(2.dp),
                text = artist.name,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = colorResource(R.color.textPrimary)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ArtistItemPreview() {
    ArtistItem(Artist("1","Kenn", "https://res.cloudinary.com/cmatwqd9/image/upload/v1791035558/fishy_avt.jpg"))
}