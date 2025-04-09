package dev.than0s.aluminium.features.notification.presentation.push_notifications

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredAnimatedVisibility
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredColumn
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilledButton
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredFilterChip
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredGroupTitle
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredOutlinedTextField
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredRow
import dev.than0s.aluminium.core.presentation.composable.preferred.PreferredTextField

@Composable
fun ScreenPushNotification(
    viewModel: ViewModelPushNotification = hiltViewModel()
) {
    Content(
        state = viewModel.state,
        onEvent = viewModel::onEvent
    )
}

@Composable
private fun Content(
    state: StatePushNotification,
    onEvent: (EventsPushNotification) -> Unit,
) {
    PreferredColumn {
        PreferredTextField(
            value = state.content.title,
            placeholder = "Title",
            onValueChange = {
                onEvent(EventsPushNotification.ChangeTitle(it))
            },
        )
        PreferredTextField(
            value = state.content.content,
            placeholder = "Content",
            onValueChange = {
                onEvent(EventsPushNotification.ChangeContent(it))
            }
        )
        PreferredRow {
            PreferredFilterChip(
                label = "Student",
                selected = state.student,
                onClick = {
                    onEvent(EventsPushNotification.ChangeStudentFilter)
                }
            )
            PreferredFilterChip(
                label = "Alumni",
                selected = state.alumni,
                onClick = {
                    onEvent(EventsPushNotification.ChangeAlumniFilter)
                }
            )
            PreferredFilterChip(
                label = "Staff",
                selected = state.staff,
                onClick = {
                    onEvent(EventsPushNotification.ChangeStaffFilter)
                }
            )
        }
        PreferredAnimatedVisibility(
            visible = state.student,
            content = {
                PreferredColumn {
                    PreferredGroupTitle(
                        text = "Student"
                    )
                    PreferredRow {
                        PreferredFilterChip(
                            label = "MCA",
                            selected = state.studentFilter.mca,
                            onClick = {
                                onEvent(EventsPushNotification.ChangeStudentMcaFilter)
                            }
                        )
                        PreferredFilterChip(
                            label = "MBA",
                            selected = state.studentFilter.mba,
                            onClick = {
                                onEvent(EventsPushNotification.ChangeStudentMbaFilter)
                            }
                        )
                    }

                    PreferredFilterChip(
                        label = "Batch",
                        selected = state.isStudentBatch,
                        onClick = {
                            onEvent(EventsPushNotification.ChangeStudentBatchFilter)
                        }
                    )

                    PreferredAnimatedVisibility(
                        visible = state.isStudentBatch,
                    ) {
                        PreferredRow {
                            PreferredOutlinedTextField(
                                value = state.studentBatch.from,
                                placeholder = "From",
                                onValueChange = {
                                    onEvent(EventsPushNotification.ChangeStudentBatchFrom(it))
                                },
                                modifier = Modifier.weight(0.5f)
                            )
                            PreferredOutlinedTextField(
                                value = state.studentBatch.to,
                                placeholder = "To",
                                onValueChange = {
                                    onEvent(EventsPushNotification.ChangeStudentBatchTo(it))
                                },
                                modifier = Modifier.weight(0.5f)
                            )
                        }
                    }
                }
            }
        )
        PreferredAnimatedVisibility(
            visible = state.alumni,
            content = {
                PreferredColumn {
                    PreferredGroupTitle(
                        text = "Alumni"
                    )

                    PreferredRow {
                        PreferredFilterChip(
                            label = "MCA",
                            selected = state.alumniFilter.mca,
                            onClick = {
                                onEvent(EventsPushNotification.ChangeAlumniMcaFilter)
                            }
                        )
                        PreferredFilterChip(
                            label = "MBA",
                            selected = state.alumniFilter.mba,
                            onClick = {
                                onEvent(EventsPushNotification.ChangeAlumniMbaFilter)
                            }
                        )
                    }

                    PreferredFilterChip(
                        label = "Batch",
                        selected = state.isAlumniBatch,
                        onClick = {
                            onEvent(EventsPushNotification.ChangeAlumniBatchFilter)
                        }
                    )

                    PreferredAnimatedVisibility(
                        visible = state.isAlumniBatch
                    ) {
                        PreferredRow {
                            PreferredOutlinedTextField(
                                value = state.alumniBatch.from,
                                placeholder = "From",
                                onValueChange = {
                                    onEvent(EventsPushNotification.ChangeAlumniBatchFrom(it))
                                },
                                modifier = Modifier.weight(0.5f)
                            )
                            PreferredOutlinedTextField(
                                value = state.alumniBatch.to,
                                placeholder = "To",
                                onValueChange = {
                                    onEvent(EventsPushNotification.ChangeAlumniBatchTo(it))
                                },
                                modifier = Modifier.weight(0.5f)
                            )
                        }
                    }
                }
            }
        )
        PreferredFilledButton(
            onClick = {
                onEvent(EventsPushNotification.PushNotification)
            },
            content = {
                Text("Push")
            }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Preview() {
    Content(
        state = StatePushNotification(),
        onEvent = {}
    )
}