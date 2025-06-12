package com.lithium.truepost.ui.quiz.component.x

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lithium.truepost.R
import com.lithium.truepost.ui.theme.TruePostTheme
import kotlin.math.min

@Composable
fun ImageGrid(
    images: List<Int>,
    modifier: Modifier = Modifier,
    columns: Int = 2,
    rows: Int = 2,
) {
    val maxColumns = min(columns, images.size)
    val maxRows = min(rows, (images.size + columns - 1) / columns)
    val maxImages = columns * rows
    val displayImages = images.take(maxImages)
    val remaining = images.size - maxImages

    Column(modifier = modifier) {
        for (row in 0 until maxRows) {
            Row {
                for (col in 0 until maxColumns) {
                    val index = row * maxColumns + col
                    if (index < displayImages.size) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .padding(1.dp)
                        ) {
                            Image(
                                painter = painterResource(displayImages[index]),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop,
                            )
                            // Overlay +remaining en la última imagen visible
                            if (index == displayImages.lastIndex && remaining > 0) {
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(Color(0x88000000)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "+$remaining",
                                        color = Color.White,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    } else {
                        Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageGridPreview() {
    val images = listOf(
        R.drawable.disinformation,
        R.drawable.posts,
        R.drawable.fact_check,
        R.drawable.bot,
        R.drawable.facebook,
        R.drawable.x,
    )
    TruePostTheme {
        ImageGrid(
            images = images,
            columns = 2,
            rows = 2,
            modifier = Modifier.fillMaxWidth()
        )
    }
}