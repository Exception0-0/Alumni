package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferredFullScreen(
    contentDescription: String? = null,
    onDismissRequest: () -> Unit,
    content: @Composable (Modifier) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = rememberModalBottomSheetState(true) { false },
        dragHandle = null,
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            contentDescription?.let {
                                Text(it)
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = onDismissRequest) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "back button"
                                )
                            }
                        },
                    )
                }
            ) { contentPadding ->
                content(
                    Modifier.padding(contentPadding)
                )
            }
        }
    )
}