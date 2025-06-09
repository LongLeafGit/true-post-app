package com.lithium.truepost.ui.quiz.component.x

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.sp
import com.lithium.truepost.ui.theme.TruePostTheme
import kotlin.math.min
import com.lithium.truepost.R

@Composable
fun ImageGrid(
    @DrawableRes images: List<Int>,
    modifier: Modifier = Modifier,
    maxColumns: Int = 2,
    maxRows: Int = 2,
    cornerRadius: Int = 8,
) {
    if (images.isEmpty()) return

    val maxImages = maxColumns * maxRows
    val displayImages = images.take(maxImages)
    val moreCount = images.size - maxImages

    LazyVerticalGrid(
        columns = GridCells.Fixed(maxColumns),
        modifier = modifier,
    ) {
        itemsIndexed(displayImages) { index, imageResId ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(cornerRadius.dp))
            ) {
                Image(
                    painter = painterResource(imageResId),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Si es la última imagen visible y hay más, muestra overlay
                if (index == displayImages.lastIndex && moreCount > 0) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(Color(0x88000000))
                            .clip(RoundedCornerShape(cornerRadius.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+$moreCount",
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
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
            maxColumns = 2,
            maxRows = 2,
            modifier = Modifier.fillMaxSize()
        )
    }
}