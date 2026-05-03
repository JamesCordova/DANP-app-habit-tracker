package com.aero.habittracker.ui.habitDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aero.habittracker.domain.Habit

@Composable
fun HabitDetailContent(
    habit: Habit,
    onToggleCompletion: () -> Unit,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
    ) {
        HabitDetailInfo(habit = habit)

        Spacer(modifier = Modifier.height(24.dp))

        HabitDetailState(
            habit = habit,
            onToggleCompletion = onToggleCompletion
        )

        Spacer(modifier = Modifier.height(24.dp))

        HabitDetailActions(
            habit = habit,
            onToggleCompletion = onToggleCompletion,
            onDelete = onDelete
        )
    }
}

