package dev.than0s.aluminium.core.composable

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import dev.than0s.aluminium.ui.textSize

@Composable
fun AluminiumDescriptionText(
    description:String,
){
    Text(
        text = description,
        fontWeight = FontWeight.W300,
        fontSize = MaterialTheme.textSize.small
    )
}

@Preview(showBackground = true)
@Composable
private fun AluminiumDescriptionTextPreview(){
    AluminiumDescriptionText(
        description = "Hello there I want to tell you something I never tell any one"
    )
}