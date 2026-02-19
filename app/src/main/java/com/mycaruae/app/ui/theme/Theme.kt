package com.mycaruae.app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Navy40,
    onPrimary = Color.White,
    primaryContainer = Navy90,
    onPrimaryContainer = Navy10,
    secondary = Amber40,
    onSecondary = Color.White,
    secondaryContainer = Amber90,
    onSecondaryContainer = Amber10,
    tertiary = StatusGreen,
    onTertiary = Color.White,
    tertiaryContainer = StatusGreenLight,
    onTertiaryContainer = Color(0xFF002106),
    error = StatusRed,
    onError = Color.White,
    errorContainer = StatusRedLight,
    onErrorContainer = Color(0xFF410002),
    background = Slate50,
    onBackground = Slate900,
    surface = Color.White,
    onSurface = Slate900,
    surfaceVariant = Slate100,
    onSurfaceVariant = Slate600,
    outline = Slate300,
    outlineVariant = Slate200,
    inverseSurface = Slate800,
    inverseOnSurface = Slate50,
)

private val DarkColorScheme = darkColorScheme(
    primary = Navy80,
    onPrimary = Navy20,
    primaryContainer = Navy30,
    onPrimaryContainer = Navy90,
    secondary = Amber80,
    onSecondary = Amber20,
    secondaryContainer = Amber30,
    onSecondaryContainer = Amber90,
    tertiary = StatusGreenDark,
    onTertiary = Color(0xFF003910),
    tertiaryContainer = Color(0xFF005320),
    onTertiaryContainer = StatusGreenDark,
    error = StatusRedDark,
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = StatusRedDark,
    background = DarkSurface,
    onBackground = Slate200,
    surface = DarkSurface,
    onSurface = Slate200,
    surfaceVariant = DarkSurfaceHigh,
    onSurfaceVariant = Slate400,
    outline = Slate600,
    outlineVariant = Slate700,
    inverseSurface = Slate200,
    inverseOnSurface = Slate800,
)

@Composable
fun MyCarUaeTheme(
    themePreference: String = "system",
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themePreference) {
        "dark" -> true
        "light" -> false
        else -> isSystemInDarkTheme()
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content,
    )
}