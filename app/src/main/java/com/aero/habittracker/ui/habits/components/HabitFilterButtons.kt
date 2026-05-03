package com.aero.habittracker.ui.habits.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.aero.habittracker.ui.habits.HabitFilter

@Composable
fun HabitFilterButtons(
    currentFilter: HabitFilter,
    onFilterChange: (HabitFilter) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .clip(RoundedCornerShape(percent = 50))
            .background(color = MaterialTheme.colorScheme.secondaryContainer),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterButton(
            label = "Todos",
            isSelected = currentFilter == HabitFilter.ALL,
            onClick = { onFilterChange(HabitFilter.ALL) },
            modifier = Modifier.weight(1f)
        )

        FilterButton(
            label = "Completados",
            isSelected = currentFilter == HabitFilter.COMPLETED,
            onClick = { onFilterChange(HabitFilter.COMPLETED) },
            modifier = Modifier.weight(1f)
        )

        FilterButton(
            label = "Pendientes",
            isSelected = currentFilter == HabitFilter.PENDING,
            onClick = { onFilterChange(HabitFilter.PENDING) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FilterButton(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxHeight(),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.secondaryContainer,
            contentColor = if (isSelected)
                MaterialTheme.colorScheme.onPrimary
            else
                MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Text(label)
    }
}

@Preview(showBackground = true)
@Composable
fun HabitFilterButtonsPreview() {
    HabitFilterButtons(
        currentFilter = HabitFilter.ALL,
        onFilterChange = {}
    )
}
