package com.lithium.truepost.ui.quiz.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lithium.truepost.data.model.XTweetModel
import com.lithium.truepost.ui.quiz.component.x.XTweetCompact
import com.lithium.truepost.ui.quiz.component.x.XTweetFull

@Composable
fun XTweet(
    tweet: XTweetModel,
    modifier: Modifier = Modifier,
) {
    if (tweet.datestamp != null) {
        XTweetFull(tweet, modifier)
    } else {
        XTweetCompact(tweet, modifier)
    }
}

fun toUsername(username: String): String {
    return "@${username.trimStart('@')}"
}

fun shortCount(count: Int): String {
    return when {
        count < 1_000 -> "$count"
        count < 1_000_000 -> {
            val mil = count / 1_000
            "$mil mil"
        }
        count < 2_000_000 -> "1 millón"
        count < 1_000_000_000 -> {
            val millones = count / 1_000_000
            "$millones millones"
        }
        else -> "999+ millones"
    }
}
