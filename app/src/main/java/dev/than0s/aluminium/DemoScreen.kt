package dev.than0s.aluminium

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.than0s.mydiary.ui.textSize

@Preview(showSystemUi = true)
@Composable
fun Content() {
    Text(
        text = "Hello world",
        fontSize = MaterialTheme.textSize.gigantic
    )
}