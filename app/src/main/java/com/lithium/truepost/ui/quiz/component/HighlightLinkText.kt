package com.lithium.truepost.ui.quiz.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun HighlightLinkText(
    text: String,
    modifier: Modifier = Modifier,
) {
    val urlRegex = "(https?://[\\w-]+(\\.[\\w-]+)+\\S*)".toRegex()
    val hashtagRegex = "#\\w+".toRegex()

    // Encuentra todos los matches, y los mezcla y ordena
    val allMatches = (urlRegex.findAll(text).map { it to "url" } +
            hashtagRegex.findAll(text).map { it to "hashtag" })
        .sortedBy { it.first.range.first }

    val annotatedString = buildAnnotatedString {
        var lastIndex = 0
        for ((match, type) in allMatches) {
            val start = match.range.first
            val end = match.range.last + 1
            if (start > lastIndex) {
                append(text.substring(lastIndex, start))
            }
            when (type) {
                "url" -> pushStyle(
                    SpanStyle(
                        color = Color(0xFF1B95E0),
                        textDecoration = TextDecoration.Underline
                    )
                )
                "hashtag" -> pushStyle(
                    SpanStyle(
                        color = Color(0xFF1877F2) // Otro azul, o el mismo si prefieres
                    )
                )
            }
            append(text.substring(start, end))
            pop()
            lastIndex = end
        }
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }

    Text(
        text = annotatedString,
        modifier = modifier,
    )
}