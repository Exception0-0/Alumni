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
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import dev.than0s.aluminium.core.composable.AluminiumAlertDialog
import dev.than0s.aluminium.core.composable.AluminiumClickableText
import dev.than0s.aluminium.core.composable.AluminiumElevatedButton
import dev.than0s.aluminium.core.composable.AluminiumLoadingElevatedButton
import dev.than0s.aluminium.core.composable.AluminiumTitleText
import dev.than0s.aluminium.features.registration.domain.data_class.RegistrationForm
import dev.than0s.aluminium.ui.roundCorners
import dev.than0s.aluminium.ui.spacing
import dev.than0s.aluminium.ui.textSize

@Composable
fun RegistrationRequestsScreen(
    viewModel: RequestViewModel = hiltViewModel()
) {
    RegistrationRequestsContent(
        requestsList = viewModel.requestsList,
        onAcceptedClick = viewModel::onAcceptClick,
        onRejectedClick = viewModel::onRejectedClick
    )
}

@Composable
private fun RegistrationRequestsContent(
    requestsList: List<RegistrationForm>,
    onAcceptedClick: (RegistrationForm, () -> Unit) -> Unit,
    onRejectedClick: (RegistrationForm, () -> Unit) -> Unit
) {
    LazyColumn {
        items(
            items = requestsList,
            key = { it.id }
        ) { request ->
            RegistrationRequestItem(
                request = request,
                onAcceptedClick = { callBack ->
                    onAcceptedClick(request, callBack)
                },
                onRejectedClick = { callBack ->
                    onRejectedClick(request, callBack)
                }
            )
        }
    }
}

@Composable
private fun RegistrationRequestItem(
    request: RegistrationForm,
    onAcceptedClick: (() -> Unit) -> Unit,
    onRejectedClick: (() -> Unit) -> Unit
) {
    var showAcceptAlertDialog by rememberSaveable { mutableStateOf(false) }
    var showRejectAlertDialog by rememberSaveable { mutableStateOf(false) }
    var showIdCardImage by rememberSaveable { mutableStateOf(false) }
    var circularProgressIndicatorState by rememberSaveable { mutableStateOf(false) }

    if (showAcceptAlertDialog) {
        AluminiumAlertDialog(
            title = accepted_title,
            description = accepted_text,
            circularProgressIndicatorState = circularProgressIndicatorState,
            onDismissRequest = {
                showAcceptAlertDialog = false
            },
            onConfirmation = {
                circularProgressIndicatorState = true
                onAcceptedClick {
                    circularProgressIndicatorState = false
                    showAcceptAlertDialog = false
                }
            }
        )
    }

    if (showRejectAlertDialog) {
        AluminiumAlertDialog(
            title = rejected_title,
            description = rejected_text,
            circularProgressIndicatorState = circularProgressIndicatorState,
            onDismissRequest = {
                showRejectAlertDialog = false
            },
            onConfirmation = {
                circularProgressIndicatorState = true
                onRejectedClick {
                    circularProgressIndicatorState = false
                    showRejectAlertDialog = false
                }
            }
        )
    }

    if (showIdCardImage) {
        Dialog(
            onDismissRequest = {
                showIdCardImage = false
            },
            content = {
                AsyncImage(
                    model = request.idCardImage,
                    contentDescription = "Id card image",
                    modifier = Modifier
                        .clip(shape = RoundedCornerShape(MaterialTheme.roundCorners.default))
                )
            }
        )
    }

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.spacing.small)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            modifier = Modifier.padding(MaterialTheme.spacing.medium)
        ) {
            AluminiumTitleText(
                title = request.category,
                fontSize = MaterialTheme.textSize.huge
            )
            AluminiumTitleText(
                title = "${request.batchFrom} - ${request.batchTo}",
                fontSize = MaterialTheme.textSize.medium
            )
            AluminiumTitleText(
                title = request.rollNo,
                fontSize = MaterialTheme.textSize.medium
            )
            AluminiumTitleText(
                title = "${request.lastName} ${request.firstName} ${request.middleName}",
                fontSize = MaterialTheme.textSize.medium
            )
            AluminiumTitleText(
                title = request.email,
                fontSize = MaterialTheme.textSize.medium
            )

            if (request.mobile != null) {
                AluminiumTitleText(
                    title = request.mobile,
                    fontSize = MaterialTheme.textSize.medium
                )
            }

            if (request.idCardImage != null) {
                AluminiumClickableText(
                    title = "show Id card image",
                    onClick = {
                        showIdCardImage = true
                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.small)
            ) {
                TextButton(
                    onClick = {
                        showAcceptAlertDialog = true
                    },
                    content = {
                        Text("Accept")
                    }
                )

                Spacer(modifier = Modifier.padding(MaterialTheme.spacing.small))

                TextButton(
                    onClick = {
                        showRejectAlertDialog = true
                    },
                    content = {
                        Text("Cancel")
                    }
                )
            }
        }
    }
}


const val accepted_title = "Do you really want to accept this request?"
const val accepted_text = "you can't undo this action, so be careful with your choices."

const val rejected_title = "Do you really want to reject this request?"
const val rejected_text = "You can't undo this action, so be careful with your choices."

@Preview(showSystemUi = true)
@Composable
private fun RegistrationRequestPreview() {
    RegistrationRequestsContent(
        listOf(
            RegistrationForm(
                id = "1",
                category = "Student",
                rollNo = "123456",
                firstName = "Himanshu",
                middleName = "Vasantrao",
                lastName = "Patil",
                mobile = "7030502354",
                email = "himanshupatil45h@gmail.com",
                batchFrom = "2023",
                batchTo = "2025"
            ),
            RegistrationForm(
                id = "2",
                category = "Student",
                rollNo = "123456",
                firstName = "Himanshu",
                middleName = "Vasantrao",
                lastName = "Patil",
                mobile = "7030502354",
                email = "himanshupatil45h@gmail.com",
                batchFrom = "2023",
                batchTo = "2025",
                idCardImage = Uri.EMPTY
            ),
            RegistrationForm(
                id = "3",
                category = "Student",
                rollNo = "123456",
                firstName = "Himanshu",
                middleName = "Vasantrao",
                lastName = "Patil",
                mobile = "7030502354",
                email = "himanshupatil45h@gmail.com",
                batchFrom = "2023",
                batchTo = "2025"
            )
        ),
        { _, _ -> }, { _, _ -> },
    )
}