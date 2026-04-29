package com.aero.habittracker.ui.habits.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun InputNewHabit(
    input: String,
    onInputChange: (String) -> Unit,
    onAddHabit: () -> Unit
) {
    Row {
        TextField(
            value = input,
            onValueChange = onInputChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Nuevo hábito") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Button(onClick = onAddHabit) {
            Text("Agregar")
        }
    }
}