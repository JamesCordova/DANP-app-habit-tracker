package com.aero.habittracker.ui.habits

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aero.habittracker.HabitTrackerApplication
import com.aero.habittracker.ui.habits.components.HabitFilterButtons
import com.aero.habittracker.ui.habits.components.InputNewHabit
import com.aero.habittracker.ui.habits.components.ListHabits
import com.aero.habittracker.ui.habits.components.ProgressBarHabits
import com.aero.habittracker.ui.habits.components.TituloApp

@Composable
fun HabitTrackerScreen(
    application: HabitTrackerApplication,
    onNavigateBack: () -> Unit = {},
    onNavigateToDetail: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HabitTrackerViewModel = viewModel(factory = habitTrackerViewModelFactory(application))
) {
    val input = viewModel.input
    val progress = viewModel.progress
    val currentFilter by viewModel.currentFilterFlow.collectAsState()
    val filteredHabits by viewModel.filteredHabits.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeDrawingPadding()
            .padding(16.dp)
    ) {
        TituloApp()

        Spacer(modifier = Modifier.height(16.dp))

        ProgressBarHabits(progress)

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text= "Progreso: ${(progress * 100).toInt()}%",
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        InputNewHabit(
            input = input,
            onInputChange = viewModel::onInputChange,
            onAddHabit = { viewModel.addHabit() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        HabitFilterButtons(
            currentFilter = currentFilter,
            onFilterChange = { filter -> viewModel.setFilter(filter) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        ListHabits(
            habits = filteredHabits,
            onToggleHabit = { habit, checked ->
                viewModel.toggleHabit(habit, checked)
            },
            onDeleteHabit = { habit ->
                viewModel.deleteHabit(habit)
            },
            onSelectHabit = { habit ->
                onNavigateToDetail(habit.id)
            }
        )
    }
}

