package dev.than0s.aluminium.features.registration.presentation.screens.requests

import android.net.Uri
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.valentinilk.shimmer.shimmer
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.presentation.composable.lottie_animation.AnimationNoData
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAsyncImage
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredClickableText
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilterChip
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFullScreen
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredGroupTitle
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredOutlinedTextField
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredPinchZoom
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredWarningDialog
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerText
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerTextHeight
import dev.than0s.aluminium.core.presentation.composable.shimmer.ShimmerTextWidth
import dev.than0s.aluminium.core.presentation.utils.PrettyTimeUtils
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.textSize

@Composable
fun RegistrationRequestsScreen(
    viewModel: RequestsScreenViewModel = hiltViewModel()
) {
    RegistrationRequestsContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegistrationRequestsContent(
    screenState: RequestScreenState,
    onEvent: (RequestScreenEvents) -> Unit,
) {
    screenState.requestSelection?.let {
        PreferredWarningDialog(
            title = if (it.second) stringResource(R.string.accept_request_title)
            else stringResource(R.string.reject_request_title),
            description = stringResource(R.string.alert_message),
            isLoading = screenState.isUpdating,
            onConfirmation = {
                if (it.second) {
                    onEvent(RequestScreenEvents.OnAcceptClick(requestId = it.first))
                } else {
                    onEvent(RequestScreenEvents.OnRejectClick(requestId = it.first))
                }
            },
            onDismissRequest = {
                onEvent(RequestScreenEvents.DismissWarningDialog)
            }
        )
    }
    screenState.selectedIdCard?.let {
        PreferredFullScreen(
            contentDescription = "ID Card",
            onDismissRequest = {
                onEvent(RequestScreenEvents.DismissIdCard)
            },
            content = {
                PreferredPinchZoom {
                    PreferredAsyncImage(
                        model = it,
                    )
                }
            }
        )
    }
    if (screenState.isBottomSheetVisible) {
        BottomSheet(
            screenState = screenState,
            onEvent = onEvent
        )
    }
    PullToRefreshBox(
        isRefreshing = screenState.isLoading,
        onRefresh = {
            onEvent(RequestScreenEvents.LoadRequest)
        }
    ) {
        if (screenState.isLoading) {
            ShimmerList()
        } else {
            PreferredColumn(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                PreferredOutlinedTextField(
                    placeholder = "Search...",
                    value = screenState.searchText,
                    onValueChange = {
                        onEvent(RequestScreenEvents.OnSearchTextChanged(it))
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                onEvent(RequestScreenEvents.ShowBottomSheet)
                            },
                            content = {
                                Icon(
                                    imageVector = Icons.Filled.FilterList,
                                    contentDescription = "filter list"
                                )
                            }
                        )
                    },
                    modifier = Modifier
                        .padding(MaterialTheme.padding.small)
                        .fillMaxWidth()
                )
                if (screenState.filteredList.isEmpty()) {
                    AnimationNoData(
                        text = "No request found"
                    )
                } else {
                    LazyColumn {
                        items(items = screenState.filteredList) { request ->
                            RequestItem(
                                request = request,
                                onIdCardClick = { uri ->
                                    onEvent(RequestScreenEvents.ShowIdCard(uri))
                                },
                                onAcceptClick = {
                                    onEvent(
                                        RequestScreenEvents.ShowWarningDialog(
                                            formId = request.id,
                                            accepted = true
                                        )
                                    )
                                },
                                onRejectClick = {
                                    onEvent(
                                        RequestScreenEvents.ShowWarningDialog(
                                            formId = request.id,
                                            accepted = false,
                                        )
                                    )
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    screenState: RequestScreenState,
    onEvent: (RequestScreenEvents) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            onEvent(RequestScreenEvents.DismissBottomSheet)
        },
    ) {
        PreferredGroupTitle(
            text = "Approval Status"
        )
        FilterRow(
            screenState = screenState,
            onEvent = onEvent
        )
    }
}

@Composable
fun FilterRow(
    screenState: RequestScreenState,
    onEvent: (RequestScreenEvents) -> Unit
) {
    PreferredRow(
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
        modifier = Modifier
            .padding(horizontal = MaterialTheme.padding.small)
            .horizontalScroll(rememberScrollState())
    ) {
        PreferredFilterChip(
            label = "All",
            selected = screenState.allFilter,
            onClick = {
                onEvent(RequestScreenEvents.OnAllFilterClick)
            },
        )
        PreferredFilterChip(
            label = "Pending",
            selected = screenState.pendingFilter,
            onClick = {
                onEvent(RequestScreenEvents.OnPendingFilterClick)
            },
        )
        PreferredFilterChip(
            label = "Approved",
            selected = screenState.approvedFilter,
            onClick = {
                onEvent(RequestScreenEvents.OnApprovedFilterClick)
            },
        )
        PreferredFilterChip(
            label = "Rejected",
            selected = screenState.rejectedFilter,
            onClick = {
                onEvent(RequestScreenEvents.OnRejectedFilterClick)
            },
        )
    }
}

@Composable
private fun RequestItem(
    request: RegistrationForm,
    onIdCardClick: (Uri) -> Unit,
    onAcceptClick: () -> Unit,
    onRejectClick: () -> Unit,
) {
    PreferredColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.none),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(MaterialTheme.padding.small)
            .fillMaxWidth()
    ) {
        Text(
            text = "${request.firstName} ${request.middleName} ${request.lastName}",
            fontSize = MaterialTheme.textSize.large,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = request.role.name,
            fontSize = MaterialTheme.textSize.medium,
        )
        Text(
            text = request.collegeId,
            fontSize = MaterialTheme.textSize.medium,
        )
        request.course?.name?.let {
            Text(
                text = it,
                fontSize = MaterialTheme.textSize.medium
            )
        }
        request.batchFrom?.let {
            Text(
                text = "${request.batchFrom} - ${request.batchTo}",
                fontSize = MaterialTheme.textSize.medium
            )
        }
        Text(
            text = request.email,
            fontSize = MaterialTheme.textSize.medium
        )
        Text(
            text = PrettyTimeUtils.getFormatedDateAndTime(request.timestamp),
            fontSize = MaterialTheme.textSize.medium,
        )
        request.idCardImage?.let {
            PreferredClickableText(
                text = stringResource(R.string.show_id_card),
                onClick = {
                    onIdCardClick(it)
                }
            )
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            PreferredRow(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(vertical = MaterialTheme.padding.small)
            ) {
                when (request.status.approvalStatus) {
                    null -> {
                        PreferredRow {
                            PreferredTextButton(
                                text = "Reject",
                                onClick = onRejectClick
                            )
                            PreferredFilledButton(
                                onClick = onAcceptClick,
                                content = {
                                    Text("Accept")
                                }
                            )
                        }
                    }

                    true -> {
                        Text(
                            text = "Approved",
                            fontSize = MaterialTheme.textSize.large,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    false -> {
                        Text(
                            text = "Rejected",
                            fontSize = MaterialTheme.textSize.large,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ShimmerList() {
    PreferredColumn(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .shimmer()
    ) {
        for (i in 1..10) {
            ShimmerRequestItem()
        }
    }
}

@Composable
private fun ShimmerRequestItem() {
    PreferredColumn(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(MaterialTheme.padding.small)
            .fillMaxWidth()
    ) {
        ShimmerText(width = ShimmerTextWidth.high)
        ShimmerText(
            height = ShimmerTextHeight.small,
            width = ShimmerTextWidth.high
        )
        ShimmerText(
            height = ShimmerTextHeight.small,
            width = ShimmerTextWidth.medium
        )
        ShimmerText(
            height = ShimmerTextHeight.small,
            width = ShimmerTextWidth.medium
        )
        ShimmerText(
            height = ShimmerTextHeight.small,
            width = ShimmerTextWidth.small
        )
        ShimmerText(
            height = ShimmerTextHeight.small,
            width = ShimmerTextWidth.small
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            PreferredRow(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(vertical = MaterialTheme.padding.small)
            ) {
                ShimmerText(
                    height = ShimmerTextHeight.high,
                    width = ShimmerTextWidth.medium
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegistrationRequestPreview() {
//    RegistrationRequestsContent(
//        screenState = RequestScreenState(
//            listOf(
//                RegistrationForm(
//                    id = "1",
//                    role = Role.Student,
//                    collegeId = "123456",
//                    course = Course.MCA,
//                    firstName = "Himanshu",
//                    middleName = "Vasantrao",
//                    lastName = "Patil",
//                    email = "himanshupatil45h@gmail.com",
//                    batchFrom = "2023",
//                    batchTo = "2025",
//                    idCardImage = Uri.EMPTY,
//                    status = RegistrationRequestStatus(approvalStatus = true)
//                ),
//            )
//        ),
//        onEvent = {}
//    )
//    ShimmerRequestItem()
}