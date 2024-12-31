package dev.than0s.aluminium.features.settings.screens.appearance

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contrast
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import dev.burnoo.compose.rememberpreference.rememberBooleanPreference
import dev.burnoo.compose.rememberpreference.rememberStringPreference
import dev.than0s.aluminium.core.presentation.composable.AluminiumGroupTitle
import dev.than0s.aluminium.core.presentation.composable.AluminiumSurface
import dev.than0s.aluminium.core.presentation.composable.AluminiumSwitch
import dev.than0s.aluminium.core.presentation.ui.COLOR_THEME
import dev.than0s.aluminium.core.presentation.ui.ColorTheme
import dev.than0s.aluminium.core.presentation.ui.DYNAMIC_THEME
import dev.than0s.aluminium.core.presentation.ui.PURE_BLACK
import dev.than0s.aluminium.ui.spacing

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

    ColorThemeDialog(
        currentColorTheme = ColorTheme.valueOf(storeColorTheme),
        screenState = screenState,
        onEvent = onEvent,
        onColorThemeChanged = { theme: ColorTheme ->
            storeColorTheme = theme.name
        },
    )

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
        onEvent = onEvent
    )
}

@Composable
private fun ThemeColumn(
    currentColorTheme: String,
    isPureBlack: Boolean,
    isDynamicTheme: Boolean,
    onPureBlackChange: (Boolean) -> Unit,
    onDynamicThemeChange: (Boolean) -> Unit,
    onEvent: (AppearanceScreenEvents) -> Unit
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        AluminiumGroupTitle(
            title = "theme"
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
                AluminiumSwitch(
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
                AluminiumSwitch(
                    checked = isPureBlack,
                    onCheckedChange = {
                        onPureBlackChange(it)
                    },
                )
            },
        )
    }
}

@Composable
private fun ColorThemeDialog(
    currentColorTheme: ColorTheme,
    screenState: AppearanceScreenState,
    onColorThemeChanged: (ColorTheme) -> Unit,
    onEvent: (AppearanceScreenEvents) -> Unit
) {
    if (screenState.colorThemeDialog) {
        Dialog(
            onDismissRequest = {
                onEvent(AppearanceScreenEvents.OnColorThemeDialogDismissRequest)
            },
            content = {
                AluminiumSurface {
                    Column(
                        modifier = Modifier.padding(MaterialTheme.spacing.medium)
                    ) {
                        ColorTheme.entries.forEach {
                            val isSelected = currentColorTheme == it
                            ColorThemeRadioButton(
                                colorTheme = it,
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
}

@Composable
private fun ColorThemeRadioButton(
    colorTheme: ColorTheme,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(56.dp)
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null
        )
        Text(
            text = colorTheme.name,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AppearanceScreenPreview() {
    AppearanceScreenContent(
        screenState = AppearanceScreenState(),
        onEvent = {}
    )
}