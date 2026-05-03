package com.aero.habittracker.ui.habitDetail.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aero.habittracker.domain.Habit

@Composable
fun HabitDetailState(
    habit: Habit,
    onToggleCompletion: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.small
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = habit.isCompletedToday,
            onCheckedChange = { onToggleCompletion() }
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Estado de hoy",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = if (habit.isCompletedToday) "Completado" else "Pendiente",
                style = MaterialTheme.typography.bodyMedium,
                color = if (habit.isCompletedToday)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    }
}

