package com.lithium.truepost.ui.menu.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircleOutline
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lithium.truepost.R
import com.lithium.truepost.ui.theme.TruePostTheme

data class TempCourse(
    @DrawableRes val imageResId: Int,
    val title: String,
    val description: String,
    val completed: Boolean = false,
)

@Composable
fun CourseSelector(
    course: TempCourse,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(16.dp),
        ) {
            if (course.completed) {
                Image(
                    imageVector = Icons.Filled.CheckCircleOutline,
                    contentDescription = "Completado",
                    colorFilter = ColorFilter.tint(color = Color.Blue),
                    modifier = Modifier.size(100.dp),
                )
            } else {
                Image(
                    painter = painterResource(course.imageResId),
                    contentDescription = course.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp)
                        .clip(RoundedCornerShape(16.dp)),
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = course.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
                Text(
                    text = course.description,
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CourseSelectorPreview() {
    TruePostTheme {
        CourseSelector(
            course = TempCourse(
                imageResId = R.drawable.bot,
                title = "¿Qué son los bots sociales?",
                description = "Aprende que son los bots y por que son sociales.",
            ),
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CourseSelectorPreviewCompleted() {
    TruePostTheme {
        CourseSelector(
            course = TempCourse(
                imageResId = R.drawable.bot,
                title = "¿Qué son los bots sociales?",
                description = "Aprende que son los bots y por que son sociales.",
                completed = true,
            ),
            onClick = {},
        )
    }
}