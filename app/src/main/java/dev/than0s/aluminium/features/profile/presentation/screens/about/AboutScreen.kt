package dev.than0s.aluminium.features.profile.presentation.screens.about

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.core.presentation.composable.AluminumLoading
import dev.than0s.aluminium.core.presentation.composable.CardInfoFormat
import dev.than0s.aluminium.core.presentation.composable.ShimmerBackground
import dev.than0s.aluminium.features.profile.domain.data_class.AboutInfo
import dev.than0s.aluminium.ui.Size
import dev.than0s.aluminium.ui.spacing

@Composable
fun AboutScreen(
    viewModel: AboutViewModel = hiltViewModel(),
    userId: String
) {
    // TODO
    LaunchedEffect(key1 = Unit) {
        viewModel.userId = userId
        viewModel.onEvent(AboutEvents.LoadAboutInfo)
    }

    AboutContent(
        aboutInfo = viewModel.aboutInfo,
        screenState = viewModel.screenState,
        onEvents = viewModel::onEvent
    )
}

@Composable
fun AboutContent(
    aboutInfo: AboutInfo,
    screenState: AboutState,
    onEvents: (AboutEvents) -> Unit,
) {
    if (screenState.isLoading) {
        AluminumLoading()
    } else {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            CardInfoFormat(
                title = "Role",
                info = aboutInfo.role.name,
                icon = Icons.Outlined.Email,
            )
            aboutInfo.course?.let {
                CardInfoFormat(
                    title = "Course",
                    info = it.name,
                    icon = Icons.Outlined.Phone,
                )
            }
            aboutInfo.batchFrom?.let {
                CardInfoFormat(
                    title = "Batch",
                    info = "${aboutInfo.batchFrom} - ${aboutInfo.batchTo}",
                    icon = Icons.Outlined.Star,
                )
            }
        }
    }
}

//@Composable
//private fun ShimmerAbout() {
//    Column(
//        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.large),
//        modifier = Modifier
//            .verticalScroll(rememberScrollState())
//            .shimmer()
//    ) {
//        ShimmerBackground(
//            modifier = Modifier
//                .height(MaterialTheme.Size.small)
//                .fillMaxWidth()
//        )
//        ShimmerBackground(
//            modifier = Modifier
//                .height(MaterialTheme.Size.small)
//                .fillMaxWidth()
//        )
//        ShimmerBackground(
//            modifier = Modifier
//                .height(MaterialTheme.Size.small)
//                .fillMaxWidth()
//        )
//    }
//}