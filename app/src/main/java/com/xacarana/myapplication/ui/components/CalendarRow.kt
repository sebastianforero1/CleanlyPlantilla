package com.xacarana.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ChevronLeft
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.xacarana.myapplication.util.DateUtils
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * Fila de calendario con paginación por semanas.
 * - Flechas para ir a semana anterior / siguiente.
 * - Mantiene seleccionado el día recibido por props.
 * - Si 'selected' cambia a una semana distinta, se centra esa semana automáticamente.
 */
@Composable
fun CalendarRow(
    days: List<LocalDate>,          // puedes seguir pasándolo como hasta ahora
    selected: LocalDate,
    onSelect: (LocalDate) -> Unit
) {
    // Estado de la semana mostrada (domingo de esa semana)
    var weekStart by remember(selected) {
        mutableStateOf(DateUtils.startOfWeek(selected))
    }

    // Si desde fuera seleccionan un día fuera de la semana mostrada, mover la semana
    LaunchedEffect(selected) {
        if (!DateUtils.isInWeek(selected, weekStart)) {
            weekStart = DateUtils.startOfWeek(selected)
        }
    }

    val visibleDays = remember(weekStart) { DateUtils.weekRangeFromStart(weekStart) }

    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { weekStart = DateUtils.previousWeekStart(weekStart) }) {
            Icon(Icons.Outlined.ChevronLeft, contentDescription = "Semana anterior")
        }

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            visibleDays.forEach { day ->
                val isSelected = day == selected

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(vertical = 6.dp)
                        .widthIn(min = 36.dp)
                        .clickable { onSelect(day) }
                ) {
                    // Nombre corto del día (SUN, MON, ...)
                    Text(
                        text = day.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.height(4.dp))
                    // Día del mes en "pill"
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surfaceVariant
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day.dayOfMonth.toString(),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            ),
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        IconButton(onClick = { weekStart = DateUtils.nextWeekStart(weekStart) }) {
            Icon(Icons.Outlined.ChevronRight, contentDescription = "Semana siguiente")
        }
    }
}
