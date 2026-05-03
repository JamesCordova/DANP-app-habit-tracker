package com.aero.habittracker.ui.habits.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
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
            shape = RoundedCornerShape(50),
            placeholder = { Text("Nuevo hábito") }
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = onAddHabit,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Agregar hábito",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Preview()
@Composable
fun InputNewHabitPreview() {
    InputNewHabit(
        input = "",
        onInputChange = {},
        onAddHabit = {}
    )
}