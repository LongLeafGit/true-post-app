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
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.ModeComment
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lithium.truepost.data.model.XTweetModel
import com.lithium.truepost.data.raw.AllXTweets
import com.lithium.truepost.ui.quiz.component.HighlightLinkText
import com.lithium.truepost.ui.quiz.component.IconCount
import com.lithium.truepost.ui.quiz.component.shortCount
import com.lithium.truepost.ui.quiz.component.toUsername
import com.lithium.truepost.ui.theme.TruePostTheme

@Composable
fun XTweetFull(
    tweet: XTweetModel,
    modifier: Modifier = Modifier,
) {
    Card {
        Column(
            modifier = modifier.padding(8.dp),
        ) {
            XTweetHeader(tweet)
            Spacer(modifier = Modifier.height(8.dp))
            HighlightLinkText(text = tweet.content)
            Spacer(modifier = Modifier.height(8.dp))
            ImageGrid(tweet.imagesResId)
            if (tweet.imagesResId.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
            XTweetFooter(tweet)
        }
    }
}

@Composable
private fun XTweetHeader(
    tweet: XTweetModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Image(
            painter = painterResource(tweet.avatarResId),
            contentDescription = tweet.displayName,
            modifier = Modifier.size(48.dp).clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Row {
                Text(
                    text = tweet.displayName,
                    fontWeight = FontWeight.Bold,
                )
                if (tweet.isVerified) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Filled.Verified,
                        contentDescription = "Verificado",
                        tint = Color(0xFF1877F2),
                        modifier = Modifier.size(24.dp),
                    )
                }
            }
            Text(
                text = toUsername(tweet.username),
                style = MaterialTheme.typography.bodyMedium)
        }
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
    Column(
        modifier = modifier,
    ) {
        TimestampWithBoldViews(tweet)
        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
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
                count = 0, // Nunca aparece el texto
                imageVector = Icons.Outlined.BookmarkBorder,
                contentDescription = "Guardar",
            )
            IconCount(
                count = 0, // Nunca aparece el texto
                imageVector = Icons.Outlined.Share,
                contentDescription = "Compartir",
            )
        }
    }
}

@Composable
fun TimestampWithBoldViews(
    tweet: XTweetModel,
    modifier: Modifier = Modifier,
) {
    val viewsStr = shortCount(tweet.viewsCount)
    val baseText = tweet.timestamp +
            (if (tweet.datestamp != null) " · ${tweet.datestamp}" else "") +
            " · "
    val annotatedString = buildAnnotatedString {
        append(baseText)
        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        append(viewsStr)
        pop()
        if (tweet.viewsCount == 1) {
            append(" Visualización")
        } else {
            append(" Visualizaciones")
        }
    }
    Text(
        text = annotatedString,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun XTweetFullPreview() {
    var tweet = AllXTweets.first()
    tweet = tweet.copy(
        datestamp = tweet.datestamp ?: "1 jun. 2025"
    )
    TruePostTheme {
        XTweetFull(
            tweet = tweet,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}