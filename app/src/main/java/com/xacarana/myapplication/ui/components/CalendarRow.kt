package com.xacarana.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun CalendarRow(
    days: List<LocalDate>,
    selected: LocalDate,
    onSelect: (LocalDate) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        days.forEach { day ->
            val isSelected = day == selected
            val bg = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent
            val fg = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

            Box(
                modifier = Modifier
                    .width(44.dp)
                    .height(54.dp)
                    .background(bg, RoundedCornerShape(12.dp))
                    .clickable { onSelect(day) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.format(DateTimeFormatter.ofPattern("E\ndd")),
                    color = fg,
                    style = MaterialTheme.typography.labelLarge
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
        }
    }
}
