package com.mycaruae.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    // Primary — Deep Navy
    primary = Navy40,
    onPrimary = Color.White,
    primaryContainer = Navy90,
    onPrimaryContainer = Navy10,

    // Secondary — Warm Amber
    secondary = Amber40,
    onSecondary = Color.White,
    secondaryContainer = Amber90,
    onSecondaryContainer = Amber20,

    // Tertiary — Slate accent
    tertiary = Slate600,
    onTertiary = Color.White,
    tertiaryContainer = Slate200,
    onTertiaryContainer = Slate800,

    // Error
    error = StatusRed,
    onError = Color.White,
    errorContainer = StatusRedLight,
    onErrorContainer = Color(0xFF410002),

    // Background & Surface
    background = Navy99,
    onBackground = Slate900,
    surface = Color.White,
    onSurface = Slate900,
    surfaceVariant = Slate100,
    onSurfaceVariant = Slate500,
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = Navy99,
    surfaceContainer = Slate50,
    surfaceContainerHigh = Slate100,
    surfaceContainerHighest = Slate200,

    // Outline
    outline = Slate300,
    outlineVariant = Slate200,

    // Inverse
    inverseSurface = Slate800,
    inverseOnSurface = Slate100,
    inversePrimary = Navy80,

    // Scrim
    scrim = Color(0xFF000000),
)

private val DarkColorScheme = darkColorScheme(
    // Primary — Lighter Navy
    primary = Navy80,
    onPrimary = Navy20,
    primaryContainer = Navy30,
    onPrimaryContainer = Navy90,

    // Secondary — Warm Amber glow
    secondary = Amber80,
    onSecondary = Amber20,
    secondaryContainer = Amber30,
    onSecondaryContainer = Amber90,

    // Tertiary
    tertiary = Slate400,
    onTertiary = Slate800,
    tertiaryContainer = Slate700,
    onTertiaryContainer = Slate200,

    // Error
    error = StatusRedDark,
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = StatusRedLight,

    // Background & Surface
    background = Color(0xFF0D1117),
    onBackground = Slate200,
    surface = Color(0xFF131920),
    onSurface = Slate200,
    surfaceVariant = Color(0xFF1E2530),
    onSurfaceVariant = Slate400,
    surfaceContainerLowest = Color(0xFF0A0E14),
    surfaceContainerLow = Color(0xFF111820),
    surfaceContainer = Color(0xFF171E28),
    surfaceContainerHigh = Color(0xFF1E2530),
    surfaceContainerHighest = Color(0xFF272F3A),

    // Outline
    outline = Slate600,
    outlineVariant = Slate700,

    // Inverse
    inverseSurface = Slate200,
    inverseOnSurface = Slate800,
    inversePrimary = Navy40,

    // Scrim
    scrim = Color(0xFF000000),
)

@Composable
fun MyCarUaeTheme(
    themePreference: String = "system",
    content: @Composable () -> Unit,
) {
    val darkTheme = when (themePreference) {
        "light" -> false
        "dark" -> true
        else -> isSystemInDarkTheme()
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MyCarUaeTypography,
        content = content,
    )
}