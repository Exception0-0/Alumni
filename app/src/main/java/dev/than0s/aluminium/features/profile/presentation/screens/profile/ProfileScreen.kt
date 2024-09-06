package dev.than0s.aluminium.features.profile.presentation.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.mydiary.ui.spacing
import dev.than0s.mydiary.ui.textSize

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(), restartApp: () -> Unit) {
    ProfileScreenContent(
        onSignOutClick = viewModel::onSignOutClick,
        restartApp = restartApp
    )
}

@Composable
private fun ProfileScreenContent(onSignOutClick: (() -> Unit) -> Unit, restartApp: () -> Unit) {
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
            modifier = Modifier
                .fillMaxWidth()

        ) {

            IconButton(
                onClick = {
                    onSignOutClick(restartApp)
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                    contentDescription = Icons.AutoMirrored.Filled.ExitToApp.name
                )
            }

            Image(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = Icons.Default.AccountCircle.name,
                modifier = Modifier.size(128.dp)
            )
            Text(
                text = "Than0s OP",
                fontSize = MaterialTheme.textSize.gigantic,
                fontWeight = FontWeight.W400
            )
            Text(
                text = "hey, I am a student, that never attend any lecture,I am using telegram btw",
                modifier = Modifier.width(256.dp),
                textAlign = TextAlign.Center
            )
            IconButton(
                onClick = {},
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = Icons.Default.Edit.name)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun ProfileScreenPreview() {
    ProfileScreenContent({}, {})
}