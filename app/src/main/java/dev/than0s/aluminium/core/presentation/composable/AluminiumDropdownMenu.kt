package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.enums.EnumEntries

@Composable
fun <T : Enum<T>> AluminiumDropdownMenu(
    value: String,
    placeHolder: String,
    dropdownList: List<T>,
    onSelect: (T) -> Unit,
) {
    var dropdownState by remember { mutableStateOf(false) }
    Column {
        AluminiumTextField(
            value = value,
            onValueChange = {},
            placeholder = placeHolder,
            enable = false,
            modifier = Modifier.clickable {
                dropdownState = true
            }
        )
        DropdownMenu(
            expanded = dropdownState,
            onDismissRequest = {
                dropdownState = false
            },
        ) {
            dropdownList.forEach {
                DropdownMenuItem(
                    text = {
                        Text(text = it.name)
                    },
                    onClick = {
                        onSelect(it)
                        dropdownState = false
                    },
                    modifier = Modifier.width(128.dp)
                )
            }
        }
    }
}