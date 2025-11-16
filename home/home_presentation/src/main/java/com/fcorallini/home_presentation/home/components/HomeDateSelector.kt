package com.fcorallini.home_presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.ZonedDateTime

@Composable
fun HomeDateSelector(
    selectedDay : ZonedDateTime,
    mainDate : ZonedDateTime,
    onDateClick : (ZonedDateTime) -> Unit,
    modifier: Modifier = Modifier,
    datesToShow : Int = 5
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for( i in datesToShow downTo 0) {
            val date = mainDate.minusDays(i.toLong())
            HomeDateItem(
                date = date,
                isSelectedDate = date.toLocalDate() == selectedDay.toLocalDate()
            ) { onDateClick(date) }
        }
    }
}