package com.fcorallini.habits.home.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fcorallini.habits.core.presentation.HabitCheckBox
import com.fcorallini.habits.home.domain.model.Habit
import com.fcorallini.habits.ui.theme.HabitsTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

@Composable
fun HomeHabit(
    habit : Habit,
    selectedDate : LocalDate,
    onCheckedChange : () -> Unit,
    onHabitClick : () -> Unit,
    modifier: Modifier = Modifier
) {
    val checked : Boolean = habit.completedDates.contains(selectedDate)
    Row(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp))
            .background(Color.White)
            .clickable { onHabitClick() }
            .padding(14.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = habit.name)
        HabitCheckBox(
            isChecked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHabit() {
    HabitsTheme {
        HomeHabit(
            habit = Habit(
                id = "1",
                name = "habit",
                frequency = emptyList(),
                reminder = LocalTime.now(),
                completedDates = emptyList(),
                startDate = ZonedDateTime.now()
            ),
            selectedDate = LocalDate.now().minusDays(1),
            {},
            {},
        )
    }
}