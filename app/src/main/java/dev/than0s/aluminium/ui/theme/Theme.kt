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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import dev.than0s.aluminium.ui.CoverHeight
import dev.than0s.aluminium.ui.LocalCoverSize
import dev.than0s.aluminium.ui.LocalPostHeight
import dev.than0s.aluminium.ui.LocalProfileSize
import dev.than0s.aluminium.ui.LocalRoundCorners
import dev.than0s.aluminium.ui.LocalSpacing
import dev.than0s.aluminium.ui.LocalTextSize
import dev.than0s.aluminium.ui.Padding
import dev.than0s.aluminium.ui.PostHeight
import dev.than0s.aluminium.ui.ProfileSize
import dev.than0s.aluminium.ui.RoundedCorners
import dev.than0s.aluminium.ui.TextSize

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    secondary = SecondaryDark,
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    secondary = SecondaryLight,
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
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    pureBlack: Boolean = false,
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }.let {
        if (darkTheme && pureBlack) {
            it.copy(background = Color.Black, surface = Color.Black)
        } else it
    }

    CompositionLocalProvider(
        LocalSpacing provides Padding(),
        LocalTextSize provides TextSize(),
        LocalRoundCorners provides RoundedCorners(),
        LocalProfileSize provides ProfileSize(),
        LocalCoverSize provides CoverHeight(),
        LocalPostHeight provides PostHeight()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}