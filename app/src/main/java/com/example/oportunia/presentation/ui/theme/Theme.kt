package com.example.oportunia.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = com.example.oportunia.presentation.ui.theme.Purple80,
    secondary = com.example.oportunia.presentation.ui.theme.PurpleGrey80,
    tertiary = com.example.oportunia.presentation.ui.theme.Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = com.example.oportunia.presentation.ui.theme.Purple40,
    secondary = com.example.oportunia.presentation.ui.theme.PurpleGrey40,
    tertiary = com.example.oportunia.presentation.ui.theme.Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun OportunIATheme(
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // Forzamos siempre el esquema claro
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            // Si dynamicColor y Android 12+, tomamos el dynamicLight
            dynamicLightColorScheme(context)
        }
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
