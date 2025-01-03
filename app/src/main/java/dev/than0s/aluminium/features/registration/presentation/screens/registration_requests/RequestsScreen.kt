package dev.than0s.aluminium.features.registration.presentation.screens.registration_requests

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.R
import dev.than0s.aluminium.core.Role
import dev.than0s.aluminium.core.presentation.composable.AluminiumAlertDialog
import dev.than0s.aluminium.core.presentation.composable.AluminiumClickableText
import dev.than0s.aluminium.core.presentation.composable.AluminiumElevatedCard
import dev.than0s.aluminium.core.presentation.composable.AluminiumTitleText
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.ui.roundedCorners
import dev.than0s.aluminium.ui.padding
import dev.than0s.aluminium.ui.textSize

@Composable
fun RegistrationRequestsScreen(
    viewModel: RequestViewModel = hiltViewModel()
) {
    RegistrationRequestsContent(
        screenState = viewModel.screenState,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun RegistrationRequestsContent(
    screenState: RequestScreenState,
    onEvent: (RequestScreenEvents) -> Unit,
) {
    WarningDialogLogic(
        screenState = screenState,
        onEvent = onEvent
    )
    IdCardImageLogic(
        screenState = screenState,
        onEvent = onEvent
    )
    LazyColumn {
        items(
            items = screenState.requestsList,
        ) { request ->
            RegistrationRequestItem(
                request = request,
                onEvent = onEvent
            )
        }
    }
}

@Composable
private fun IdCardImageLogic(
    screenState: RequestScreenState,
    onEvent: (RequestScreenEvents) -> Unit
) {
    if (screenState.idCardSelection != null) {
        Dialog(
            onDismissRequest = {
                onEvent(RequestScreenEvents.OnHideIdCard)
            },
            content = {
                AsyncImage(
                    model = screenState.requestsList.first {
                        it.id == screenState.idCardSelection
                    }.idCardImage,
                    contentDescription = "Id card image",
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(MaterialTheme.roundedCorners.default))
                )
            }
        )
    }
}

@Composable
private fun RegistrationRequestItem(
    request: RegistrationForm,
    onEvent: (RequestScreenEvents) -> Unit,
) {
    AluminiumElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.padding.small)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.padding.small),
            modifier = Modifier.padding(MaterialTheme.padding.medium)
        ) {
            AluminiumTitleText(
                title = request.role.name,
                fontSize = MaterialTheme.textSize.huge
            )
            if (request.batchFrom != null) {
                AluminiumTitleText(
                    title = "${request.batchFrom} - ${request.batchTo}",
                    fontSize = MaterialTheme.textSize.medium
                )
            }
            if (request.collegeId != null) {
                AluminiumTitleText(
                    title = request.collegeId,
                    fontSize = MaterialTheme.textSize.medium
                )
            }
            AluminiumTitleText(
                title = "${request.lastName} ${request.firstName} ${request.middleName}",
                fontSize = MaterialTheme.textSize.medium
            )
            AluminiumTitleText(
                title = request.email,
                fontSize = MaterialTheme.textSize.medium
            )

            if (request.idCardImage != null) {
                AluminiumClickableText(
                    title = "show Id card image",
                    onClick = {
                        onEvent(RequestScreenEvents.OnShowIdCard(request.id))
                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.padding.small)
            ) {
                TextButton(
                    onClick = {
                        onEvent(RequestScreenEvents.OnShowDialog(request.id, true))
                    },
                    content = {
                        Text("Accept")
                    }
                )

                Spacer(modifier = Modifier.padding(MaterialTheme.padding.small))

                TextButton(
                    onClick = {
                        onEvent(RequestScreenEvents.OnShowDialog(request.id, false))
                    },
                    content = {
                        Text("Cancel")
                    }
                )
            }
        }
    }
}

@Composable
private fun WarningDialogLogic(
    screenState: RequestScreenState,
    onEvent: (RequestScreenEvents) -> Unit
) {
    if (screenState.requestSelection != null) {
        screenState.requestSelection.second.let { dialogMode ->
            AluminiumAlertDialog(
                title = if (dialogMode) stringResource(R.string.accept_request_title)
                else stringResource(R.string.reject_request_title),
                description = stringResource(R.string.request_desc),
                circularProgressIndicatorState = screenState.isDialogLoading,
                onDismissRequest = {
                    onEvent(RequestScreenEvents.OnDismissDialog)
                },
                onConfirmation = {
                    screenState.requestsList.first { it.id == screenState.requestSelection.first }
                        .run {
                            onEvent(
                                if (dialogMode)
                                    RequestScreenEvents.OnAcceptClick(this)
                                else
                                    RequestScreenEvents.OnRejectClick(this)
                            )
                        }
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun RegistrationRequestPreview() {
    RegistrationRequestsContent(
        screenState = RequestScreenState(
            listOf(
                RegistrationForm(
                    id = "1",
                    role = Role.Student,
                    collegeId = "123456",
                    firstName = "Himanshu",
                    middleName = "Vasantrao",
                    lastName = "Patil",
                    email = "himanshupatil45h@gmail.com",
                    batchFrom = "2023",
                    batchTo = "2025"
                ),
                RegistrationForm(
                    id = "2",
                    firstName = "Himanshu",
                    middleName = "Vasantrao",
                    lastName = "Patil",
                    email = "himanshupatil45h@gmail.com",
                    batchFrom = "2023",
                    batchTo = "2025",
                    idCardImage = Uri.EMPTY
                ),
                RegistrationForm(
                    id = "3",
                    firstName = "Himanshu",
                    middleName = "Vasantrao",
                    lastName = "Patil",
                    email = "himanshupatil45h@gmail.com",
                    batchFrom = "2023",
                    batchTo = "2025"
                )
            )
        ),
        onEvent = {}
    )
}