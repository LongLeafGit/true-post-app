package com.lithium.truepost.ui.quiz.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lithium.truepost.data.model.FacebookPostModel
import com.lithium.truepost.data.raw.AllFacebookPosts
import com.lithium.truepost.ui.theme.TruePostTheme
import kotlin.math.min

@Composable
fun FacebookPost(
    post: FacebookPostModel,
    modifier: Modifier = Modifier,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            FaceBookPostHeader(post)
            HighlightLinkText(
                text = post.content,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            FacebookImageGrid(post.imagesResId)
            FaceBookPostFooter(post)
        }
    }
}

@Composable
private fun FaceBookPostHeader(
    post: FacebookPostModel,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(post.avatarResId),
            contentDescription = post.displayName,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = post.displayName, fontWeight = FontWeight.Bold)
            Text(
                text = if (post.datestamp != null) "${post.datestamp} a las " else "" +
                        post.timestamp,
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        Icon(
            imageVector = Icons.Filled.MoreHoriz,
            contentDescription = "Cerrar",
            modifier = Modifier.size(32.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Cerrar",
            modifier = Modifier.size(32.dp),
        )
    }
}

@Composable
fun FacebookImageGrid(
    @DrawableRes images: List<Int>,
    modifier: Modifier = Modifier
) {
    if (images.isEmpty()) return

    LazyVerticalGrid(
        columns = GridCells.Fixed(min(images.size, 3)),
        userScrollEnabled = false,
        modifier = modifier
            .fillMaxWidth()
    ) {
        items(images) { imageResId ->
            Image(
                painter = painterResource(imageResId),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(1.dp)
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
private fun FaceBookPostFooter(
    post: FacebookPostModel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1877F2)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ThumbUp,
                    contentDescription = "Like",
                    tint = Color.White,
                    modifier = Modifier.size(12.dp).clip(CircleShape)
                )
            }
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEA4335)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "Love",
                    tint = Color.White,
                    modifier = Modifier.size(12.dp)
                )
            }
            Spacer(Modifier.width(4.dp))
            post.reactionsCount.takeIf { it > 0 }?.let { r ->
                Text(text = "$r")
            }
            Spacer(modifier = Modifier.weight(1f))
            post.commentsCount.takeIf { it > 0 }?.let { c ->
                Text(text = "$c comentario${if (c == 1) "" else "s"}")
            }
            Spacer(modifier = Modifier.width(8.dp))
            post.sharesCount.takeIf { it > 0 }?.let { s ->
                Text(text = "$s ve${if (s == 1) "z" else "ces"} compartido")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FacebookPostPreview() {
    val post = AllFacebookPosts.first()
    TruePostTheme {
        FacebookPost(
            post = post,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}