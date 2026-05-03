package com.aero.habittracker.ui.habitDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        HabitDetailHeader(onNavigateBack = onNavigateBack)

        Spacer(modifier = Modifier.height(24.dp))

        when {
            isLoading -> {
                HabitDetailLoading()
            }

            error != null -> {
                HabitDetailError(errorMessage = error)
            }

            habit != null -> {
                HabitDetailContent(
                    habit = habit!!,
                    onToggleCompletion = { viewModel.toggleCompletionStatus() },
                    onDelete = {
                        viewModel.deleteHabit()
                        onNavigateBack()
                    }
                )
            }

            else -> {
                HabitDetailError(errorMessage = null)
            }
        }
    }
}








