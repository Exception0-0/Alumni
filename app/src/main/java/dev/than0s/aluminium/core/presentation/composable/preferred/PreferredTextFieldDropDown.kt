package dev.than0s.aluminium.core.presentation.composable.preferred

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreferredTextFieldDropDown(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    expanded: Boolean,
    dropList: List<String>,
    onStateChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: @Composable (() -> Unit)? = null,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            onStateChanged(it)
        },
        modifier = modifier,
    ) {
        PreferredTextField(
            value = value,
            onValueChange = {},
            placeholder = placeholder,
            readOnly = true,
            leadingIcon = leadingIcon,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryEditable, enabled)
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onStateChanged(false)
            },
        ) {
            dropList.forEach {
                DropdownMenuItem(
                    text = {
                        Text(it)
                    },
                    onClick = {
                        onValueChange(it)
                        onStateChanged(false)
                    }
                )
            }
        }
    }
}