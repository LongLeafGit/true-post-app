package com.lithium.truepost.ui.quiz.component.x

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lithium.truepost.data.model.XTweetModel
import com.lithium.truepost.data.raw.AllXTweets
import com.lithium.truepost.ui.quiz.component.HighlightLinkText
import com.lithium.truepost.ui.quiz.component.toUsername
import com.lithium.truepost.ui.theme.TruePostTheme
import androidx.compose.ui.graphics.Color
import com.lithium.truepost.ui.quiz.component.IconCount

@Composable
fun XTweetCompact(
    tweet: XTweetModel,
    modifier: Modifier = Modifier,
) {
    Card {
        Row(
            modifier = modifier.padding(8.dp),
        ) {
            Image(
                painter = painterResource(tweet.avatarResId),
                contentDescription = tweet.displayName,
                modifier = Modifier.size(32.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                XTweetHeader(tweet)
                Spacer(modifier = Modifier.height(8.dp))
                HighlightLinkText(text = tweet.content)
                Spacer(modifier = Modifier.height(8.dp))
                ImageGrid(tweet.imagesResId)
                Spacer(modifier = Modifier.height(8.dp))
                XTweetFooter(tweet)
            }
        }
    }
}

@Composable
private fun XTweetHeader(
    tweet: XTweetModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Text(
            text = tweet.displayName,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(8.dp))
        if (tweet.isVerified) {
            Icon(
                imageVector = Icons.Filled.Verified,
                contentDescription = "Verificado",
                tint = Color(0xFF1877F2),
                modifier = Modifier.size(24.dp),
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
        Text(
            text = toUsername(tweet.username)
                    + " · ${tweet.timestamp}",
        )
        Spacer(modifier = Modifier.weight(1F))
        Icon(
            imageVector = Icons.Filled.MoreHoriz,
            contentDescription = "Más",
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
private fun XTweetFooter(
    tweet: XTweetModel,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        IconCount(
            count = tweet.repliesCount,
            imageVector = Icons.Outlined.ModeComment,
            contentDescription = "Respuestas",
        )
        IconCount(
            count = tweet.retweetsCount,
            imageVector = Icons.Outlined.Repeat,
            contentDescription = "Retuits",
        )
        IconCount(
            count = tweet.likesCount,
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = "Me gusta",
        )
        IconCount(
            count = tweet.viewsCount,
            imageVector = Icons.Outlined.BarChart,
            contentDescription = "Visualizaciones",
        )
        Row {
            IconCount(
                count = 0, // Nunca aparece el texto
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = "Guardar",
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconCount(
                count = 0, // Nunca aparece el texto
                imageVector = Icons.Outlined.Share,
                contentDescription = "Compartir",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun XTweetCompactPreview() {
    var tweet = AllXTweets.first()
    tweet = tweet.copy(
        datestamp = null,
    )
    TruePostTheme {
        XTweetCompact(
            tweet = tweet,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}