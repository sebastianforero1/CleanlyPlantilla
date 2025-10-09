package com.xacarana.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

/**
 * Estadísticas del día para pintar indicadores en el calendario.
 * @param total cantidad total de tareas del día
 * @param done  cantidad de tareas completadas del día
 */
data class DayStats(val total: Int = 0, val done: Int = 0)

/**
 * Fila de calendario semanal con indicadores por día.
 *
 * - Muestra el número de día, abreviatura (Lun, Mar...), un badge done/total
 *   y una barra de progreso muy compacta (done/total).
 * - statsProvider(date) debe devolver DayStats para ese día.
 *
 * Comentarios (ES):
 * - No hay dependencias nuevas. Material 3.
 * - Si no quieres stats, deja statsProvider por defecto (todo a 0) y no se dibujan badges.
 */
@Composable
fun CalendarRow(
    days: List<LocalDate>,
    selected: LocalDate,
    onSelect: (LocalDate) -> Unit,
    statsProvider: (LocalDate) -> DayStats = { DayStats() }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        days.forEach { date ->
            val isSelected = date == selected
            val stats = statsProvider(date)
            DayCell(
                date = date,
                selected = isSelected,
                stats = stats,
                onClick = { onSelect(date) }
            )
        }
    }
}

@Composable
private fun DayCell(
    date: LocalDate,
    selected: Boolean,
    stats: DayStats,
    onClick: () -> Unit
) {
    val dayNumber = date.dayOfMonth.toString()
    val dayName = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
        .replace(".", "") // limpio abreviaturas tipo "lun."
    val selectedColor = MaterialTheme.colorScheme.primary
    val onSelected = MaterialTheme.colorScheme.onPrimary
    val outline = MaterialTheme.colorScheme.outlineVariant
    val onSurface = MaterialTheme.colorScheme.onSurfaceVariant

    Column(
        modifier = Modifier
            .width(48.dp)
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Abreviatura del día (Lun, Mar, ...)
        Text(
            text = dayName.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = onSurface
        )

        Spacer(Modifier.height(4.dp))

        // Bolita con el número de día
        Surface(
            color = if (selected) selectedColor else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (selected) onSelected else MaterialTheme.colorScheme.onSurface,
            shape = CircleShape,
            tonalElevation = if (selected) 2.dp else 0.dp
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = dayNumber,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium
                    )
                )
            }
        }

        // Badge done/total (solo si hay tareas)
        if (stats.total > 0) {
            Spacer(Modifier.height(6.dp))
            BadgeCounter(done = stats.done, total = stats.total)

            // Mini barra de progreso (hecha con dos cajas)
            Spacer(Modifier.height(4.dp))
            ProgressMini(done = stats.done, total = stats.total, outline = outline)
        }
    }
}

@Composable
private fun BadgeCounter(done: Int, total: Int) {
    val bg = MaterialTheme.colorScheme.secondaryContainer
    val fg = MaterialTheme.colorScheme.onSecondaryContainer
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bg)
            .padding(horizontal = 6.dp, vertical = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$done/$total",
            style = MaterialTheme.typography.labelSmall,
            color = fg
        )
    }
}

@Composable
private fun ProgressMini(done: Int, total: Int, outline: androidx.compose.ui.graphics.Color) {
    val pct = if (total <= 0) 0f else (done.toFloat() / total.toFloat()).coerceIn(0f, 1f)
    val track = outline.copy(alpha = 0.45f)
    val fill = MaterialTheme.colorScheme.primary

    Box(
        modifier = Modifier
            .height(4.dp)
            .width(40.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(track)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(pct)
                .background(fill)
        )
    }
}
