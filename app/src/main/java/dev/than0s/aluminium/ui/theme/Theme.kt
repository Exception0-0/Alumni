package dev.than0s.aluminium.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import dev.burnoo.compose.rememberpreference.rememberStringPreference
import dev.than0s.aluminium.core.presentation.ui.COLOR_THEME
import dev.than0s.aluminium.core.presentation.ui.ColorTheme
import dev.than0s.aluminium.ui.Elevation
import dev.than0s.aluminium.ui.LocalElevation
import dev.than0s.aluminium.ui.LocalRoundCorners
import dev.than0s.aluminium.ui.LocalSize
import dev.than0s.aluminium.ui.LocalSpacing
import dev.than0s.aluminium.ui.LocalTextSize
import dev.than0s.aluminium.ui.RoundCorners
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.Spacing
import dev.than0s.aluminium.ui.TextSize

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

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
fun AluminiumTheme(
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val darkTheme = when (getCurrentColorTheme()) {
        ColorTheme.System -> isSystemInDarkTheme()
        ColorTheme.Dark -> true
        ColorTheme.Light -> false
    }

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalElevation provides Elevation(),
        LocalTextSize provides TextSize(),
        LocalRoundCorners provides RoundCorners(),
        LocalSize provides Size()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

@Composable
private fun getCurrentColorTheme(): ColorTheme {
    return rememberStringPreference(
        keyName = COLOR_THEME,
        initialValue = ColorTheme.System.name,
        defaultValue = ColorTheme.System.name
    ).value.let {
        ColorTheme.valueOf(it)
    }
}