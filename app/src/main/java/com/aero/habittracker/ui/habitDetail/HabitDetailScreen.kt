package com.aero.habittracker.ui.habitDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aero.habittracker.HabitTrackerApplication
import com.aero.habittracker.ui.habitDetail.components.HabitDetailContent
import com.aero.habittracker.ui.habitDetail.components.HabitDetailError
import com.aero.habittracker.ui.habitDetail.components.HabitDetailHeader
import com.aero.habittracker.ui.habitDetail.components.HabitDetailLoading
import com.aero.habittracker.ui.habitDetail.components.HabitLogsHistory

@Composable
fun HabitDetailScreen(
    habitId: Int,
    application: HabitTrackerApplication,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HabitDetailViewModel = viewModel(factory = habitDetailViewModelFactory(application, habitId))
) {
    val habit by viewModel.habit.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val logs by viewModel.habitLogs.collectAsState()

    val baseModifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .safeDrawingPadding()

    when {
        isLoading -> {
            Column(modifier = baseModifier) {
                HabitDetailLoading()
            }
        }

        error != null -> {
            Column(modifier = baseModifier) {
                HabitDetailHeader(onNavigateBack = onNavigateBack)
                HabitDetailError(errorMessage = error)
            }
        }

        habit != null -> {
            LazyColumn(modifier = baseModifier) {
                item {
                    HabitDetailHeader(onNavigateBack = onNavigateBack)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Column(modifier = Modifier.padding(16.dp)) {
                        HabitDetailContent(
                            habit = habit!!,
                            onToggleCompletion = { viewModel.toggleCompletionStatus() },
                            onDelete = {
                                viewModel.deleteHabit()
                                onNavigateBack()
                            }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                item {
                    HabitLogsHistory(logs = logs)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }

        else -> {
            Column(modifier = baseModifier) {
                HabitDetailHeader(onNavigateBack = onNavigateBack)
                HabitDetailError(errorMessage = null)
            }
        }
    }
}








