package com.example.oportunia.presentation.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.geometry.Offset
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp



fun Modifier.gradientBackgroundBlue(
    gradientColors: List<Color>,
    shape: Shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
): Modifier {
    return this.background(
        brush = Brush.linearGradient(
            colors = gradientColors,
            start = Offset(0f, 0f),
            end = Offset(1000f, 1000f)
        ),
        shape = shape
    )
}
