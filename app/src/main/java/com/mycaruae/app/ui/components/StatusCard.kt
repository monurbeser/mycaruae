package com.mycaruae.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mycaruae.app.ui.theme.CocIcons
import com.mycaruae.app.ui.theme.StatusExpired
import com.mycaruae.app.ui.theme.StatusExpiredLight
import com.mycaruae.app.ui.theme.StatusGreen
import com.mycaruae.app.ui.theme.StatusGreenLight
import com.mycaruae.app.ui.theme.StatusOrange
import com.mycaruae.app.ui.theme.StatusOrangeLight
import com.mycaruae.app.ui.theme.StatusRed
import com.mycaruae.app.ui.theme.StatusRedLight
import com.mycaruae.app.ui.theme.StatusYellow
import com.mycaruae.app.ui.theme.StatusYellowLight

enum class ExpiryLevel {
    GREEN, YELLOW, ORANGE, RED, EXPIRED
}

fun daysToExpiryLevel(days: Long): ExpiryLevel = when {
    days < 0 -> ExpiryLevel.EXPIRED
    days <= 7 -> ExpiryLevel.RED
    days <= 14 -> ExpiryLevel.ORANGE
    days <= 30 -> ExpiryLevel.YELLOW
    else -> ExpiryLevel.GREEN
}

fun ExpiryLevel.containerColor(): Color = when (this) {
    ExpiryLevel.GREEN -> StatusGreenLight
    ExpiryLevel.YELLOW -> StatusYellowLight
    ExpiryLevel.ORANGE -> StatusOrangeLight
    ExpiryLevel.RED -> StatusRedLight
    ExpiryLevel.EXPIRED -> StatusExpiredLight
}

fun ExpiryLevel.contentColor(): Color = when (this) {
    ExpiryLevel.GREEN -> StatusGreen
    ExpiryLevel.YELLOW -> StatusYellow
    ExpiryLevel.ORANGE -> StatusOrange
    ExpiryLevel.RED -> StatusRed
    ExpiryLevel.EXPIRED -> StatusExpired
}

fun ExpiryLevel.icon(): ImageVector = when (this) {
    ExpiryLevel.GREEN -> CocIcons.CheckCircle
    ExpiryLevel.YELLOW -> CocIcons.Info
    ExpiryLevel.ORANGE -> CocIcons.Warning
    ExpiryLevel.RED -> CocIcons.Error
    ExpiryLevel.EXPIRED -> CocIcons.Error
}

@Composable
fun StatusCard(
    title: String,
    daysRemaining: Long,
    expiryDateFormatted: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    val level = daysToExpiryLevel(daysRemaining)

    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = level.containerColor(),
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = level.icon(),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = level.contentColor(),
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = level.contentColor(),
                )
                Text(
                    text = when {
                        daysRemaining < 0 -> "Expired ${-daysRemaining} days ago"
                        daysRemaining == 0L -> "Expires TODAY"
                        daysRemaining == 1L -> "Expires tomorrow"
                        else -> "$daysRemaining days remaining"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = level.contentColor(),
                )
                Text(
                    text = expiryDateFormatted,
                    style = MaterialTheme.typography.bodySmall,
                    color = level.contentColor().copy(alpha = 0.7f),
                )
            }
            Icon(
                imageVector = CocIcons.ChevronRight,
                contentDescription = null,
                tint = level.contentColor().copy(alpha = 0.5f),
            )
        }
    }
}