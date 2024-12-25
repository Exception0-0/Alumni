package dev.than0s.aluminium.core.presentation.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun AluminumCircularLoading() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun AluminiumLinearLoading() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun AluminiumLoadingPreview() {
    AluminiumLinearLoading()
}