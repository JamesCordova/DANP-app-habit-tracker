package com.aero.habittracker.ui.habits

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.aero.habittracker.ui.habits.components.InputNewHabit
import com.aero.habittracker.ui.habits.components.ListHabits
import com.aero.habittracker.ui.habits.components.ProgressBarHabits
import com.aero.habittracker.ui.habits.components.TituloApp

@Composable
fun HabitTrackerScreen(
    viewModel: HabitTrackerViewModel = viewModel()
) {
    val input = viewModel.input
    val habits = viewModel.habits
    val progress = viewModel.progress

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
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

        ListHabits(
            habits = habits,
            onToggleHabit = { habit, checked ->
                viewModel.toggleHabit(habit, checked)
            },
            onDeleteHabit = { habit ->
                viewModel.deleteHabit(habit)
            }
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HabitTrackerScreenPreview() {
    HabitTrackerScreen()
}