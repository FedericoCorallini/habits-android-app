package com.fcorallini.home_presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fcorallini.home_domain.model.Habit
import java.time.LocalDate

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
        com.fcorallini.core_presentation.HabitCheckBox(
            isChecked = checked,
            onCheckedChange = onCheckedChange,
            modifier = Modifier.padding(end = 8.dp)
        )
    }
}
