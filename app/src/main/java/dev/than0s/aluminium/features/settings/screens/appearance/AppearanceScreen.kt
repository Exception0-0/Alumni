package dev.than0s.aluminium.features.settings.screens.appearance

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.RoundedCorner
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import dev.burnoo.compose.rememberpreference.rememberBooleanPreference
import dev.burnoo.compose.rememberpreference.rememberFloatPreference
import dev.burnoo.compose.rememberpreference.rememberStringPreference
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredGroupTitle
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRadioButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredSurface
import dev.than0s.aluminium.core.presentation.ui.COLOR_THEME
import dev.than0s.aluminium.core.presentation.ui.ColorTheme
import dev.than0s.aluminium.core.presentation.ui.DYNAMIC_THEME
import dev.than0s.aluminium.core.presentation.ui.PURE_BLACK
import dev.than0s.aluminium.core.presentation.ui.ROUNDED_CORNERS
import dev.than0s.aluminium.ui.RoundedCorners
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.roundedCorners

@Composable
fun AppearanceScreen(
    viewModel: AppearanceScreenViewModel = hiltViewModel()
) {
    AppearanceScreenContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun AppearanceScreenContent(
    screenState: AppearanceScreenState,
    onEvent: (AppearanceScreenEvents) -> Unit
) {
    var storeColorTheme by rememberStringPreference(
        keyName = COLOR_THEME,
        initialValue = ColorTheme.System.name,
        defaultValue = ColorTheme.System.name
    )
    var isPureBlack by rememberBooleanPreference(
        keyName = PURE_BLACK,
        initialValue = false,
        defaultValue = false,
    )
    var isDynamicTheme by rememberBooleanPreference(
        keyName = DYNAMIC_THEME,
        initialValue = false,
        defaultValue = false,
    )
    var roundedCorners by rememberFloatPreference(
        keyName = ROUNDED_CORNERS,
        initialValue = roundedCorners.default.value,
        defaultValue = roundedCorners.default.value,
    )

    if (screenState.colorThemeDialog) {
        ColorThemeDialog(
            currentColorTheme = ColorTheme.valueOf(storeColorTheme),
            onEvent = onEvent,
            onColorThemeChanged = { theme: ColorTheme ->
                storeColorTheme = theme.name
            },
        )
    }

    if (screenState.roundedCornersDialog) {
        RoundedCornersDialog(
            roundedCorners = roundedCorners,
            onRoundedCornersChanged = {
                roundedCorners = it
            },
            onEvent = onEvent
        )
    }

    ThemeColumn(
        currentColorTheme = storeColorTheme,
        isPureBlack = isPureBlack,
        isDynamicTheme = isDynamicTheme,
        onPureBlackChange = { value ->
            isPureBlack = value
        },
        onDynamicThemeChange = { value ->
            isDynamicTheme = value
        },
        onRoundedCornersChanged = {
            roundedCorners = it
        },
        roundedCorners = roundedCorners.toInt().toString(),
        onEvent = onEvent
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ThemeColumn(
    currentColorTheme: String,
    isPureBlack: Boolean,
    isDynamicTheme: Boolean,
    roundedCorners: String,
    onPureBlackChange: (Boolean) -> Unit,
    onDynamicThemeChange: (Boolean) -> Unit,
    onRoundedCornersChanged: (Float) -> Unit,
    onEvent: (AppearanceScreenEvents) -> Unit,
) {
    PreferredColumn(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        PreferredGroupTitle(
            text = "theme",
            modifier = Modifier.fillMaxWidth()
        )
        ListItem(
            headlineContent = {
                Text(text = "Enable dynamic theme")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Palette,
                    contentDescription = "Dynamic theme"
                )
            },
            trailingContent = {
                Switch(
                    checked = isDynamicTheme,
                    onCheckedChange = {
                        onDynamicThemeChange(it)
                    },
                )
            },
        )
        ListItem(
            headlineContent = {
                Text(text = "Color Theme")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.DarkMode,
                    contentDescription = "Color Theme"
                )
            },
            supportingContent = {
                Text(text = currentColorTheme)
            },
            modifier = Modifier.clickable {
                onEvent(AppearanceScreenEvents.OnColorThemeDialogShowRequest)
            }
        )
        ListItem(
            headlineContent = {
                Text(text = "Pure black")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.Contrast,
                    contentDescription = "Pure Black"
                )
            },
            trailingContent = {
                Switch(
                    checked = isPureBlack,
                    onCheckedChange = {
                        onPureBlackChange(it)
                    },
                )
            },
        )
        PreferredGroupTitle(
            text = "shape",
            modifier = Modifier.fillMaxWidth()
        )
        ListItem(
            headlineContent = {
                Text(text = "Rounded Corners")
            },
            leadingContent = {
                Icon(
                    imageVector = Icons.Default.RoundedCorner,
                    contentDescription = "rounded corners"
                )
            },
            supportingContent = {
                Text(
                    text = "Hold it to set reset"
                )
            },
            trailingContent = {
                Text(
                    text = roundedCorners
                )
            },
            modifier = Modifier.combinedClickable(
                onClick = {
                    onEvent(AppearanceScreenEvents.ShowRoundedCornersDialog)
                },
                onLongClick = {
                    onRoundedCornersChanged(RoundedCorners().default.value)
                }
            )
        )
    }
}

@Composable
private fun ColorThemeDialog(
    currentColorTheme: ColorTheme,
    onColorThemeChanged: (ColorTheme) -> Unit,
    onEvent: (AppearanceScreenEvents) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onEvent(AppearanceScreenEvents.OnColorThemeDialogDismissRequest)
        },
        content = {
            PreferredSurface {
                PreferredColumn(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier.padding(MaterialTheme.padding.medium)
                ) {
                    ColorTheme.entries.forEach {
                        val isSelected = currentColorTheme == it
                        PreferredRadioButton(
                            text = it.name,
                            isSelected = isSelected,
                            onClick = {
                                onColorThemeChanged(it)
                                onEvent(AppearanceScreenEvents.OnColorThemeDialogDismissRequest)
                            }
                        )
                    }
                }
            }
        }
    )
}

@Composable
private fun RoundedCornersDialog(
    roundedCorners: Float,
    onRoundedCornersChanged: (Float) -> Unit,
    onEvent: (AppearanceScreenEvents) -> Unit
) {
    Dialog(
        onDismissRequest = {
            onEvent(AppearanceScreenEvents.DismissRoundedCornersDialog)
        },
        content = {
            PreferredSurface {
                PreferredColumn(
                    modifier = Modifier.padding(MaterialTheme.padding.medium)
                ) {
                    Slider(
                        value = roundedCorners,
                        onValueChange = onRoundedCornersChanged,
                        steps = 6,
                        valueRange = RoundedCorners().none.value..RoundedCorners().veryLarge.value
                    )
                    Text(
                        text = roundedCorners.toInt().toString()
                    )
                }
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
private fun AppearanceScreenPreview() {
    AppearanceScreenContent(
        screenState = AppearanceScreenState(),
        onEvent = {}
    )
}