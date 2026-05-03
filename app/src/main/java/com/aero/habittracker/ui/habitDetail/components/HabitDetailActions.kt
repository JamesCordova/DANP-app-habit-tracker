package com.aero.habittracker.ui.habitDetail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aero.habittracker.domain.Habit

@Composable
fun HabitDetailActions(
    habit: Habit,
    onToggleCompletion: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = { onToggleCompletion() },
            modifier = Modifier.weight(1f)
        ) {
            Text(if (habit.isCompletedToday) "Marcar como pendiente" else "Marcar como completo")
        }

        Button(
            onClick = onDelete,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text("Eliminar")
        }
    }
}

