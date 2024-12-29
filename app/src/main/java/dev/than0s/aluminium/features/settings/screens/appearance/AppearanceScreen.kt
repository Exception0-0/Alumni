package dev.than0s.aluminium.features.settings.screens.appearance

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.than0s.aluminium.core.presentation.composable.AluminiumSurface
import dev.than0s.aluminium.core.presentation.ui.ColorTheme
import dev.than0s.aluminium.core.presentation.ui.getCurrentColorTheme
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
    val context = LocalContext.current
    val currentColorTheme by
    getCurrentColorTheme(context).collectAsStateWithLifecycle(ColorTheme.System)

    ColorThemeDialog(
        currentColorTheme = currentColorTheme,
        screenState = screenState,
        context = context,
        onEvent = onEvent
    )

    Column {
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
                Text(currentColorTheme.name)
            },
            modifier = Modifier.clickable {
                onEvent(AppearanceScreenEvents.OnColorThemeDialogShowRequest)
            }
        )
    }
}

@Composable
private fun ColorThemeDialog(
    currentColorTheme: ColorTheme,
    screenState: AppearanceScreenState,
    context: Context,
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
                                    onEvent(
                                        AppearanceScreenEvents.OnColorThemeChanged(
                                            context = context,
                                            theme = it
                                        )
                                    )
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