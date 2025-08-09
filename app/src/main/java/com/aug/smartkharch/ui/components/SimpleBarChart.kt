package com.aug.smartkharch.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aug.smartkharch.data.model.BarChartData

@Composable
fun SimpleBarChart(
    data: List<BarChartData>,
    modifier: Modifier = Modifier,
    barColor: Color = MaterialTheme.colorScheme.primary
) {
    if (data.isEmpty()) {
        Text("No data")
        return
    }

    val maxValue = data.maxOf { it.value }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        data.forEach { item ->
            val barHeightRatio = if (maxValue == 0.0) 0f else (item.value / maxValue).toFloat()
            val barHeight = 150.dp * barHeightRatio
            Column(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.weight(weight = maxOf(0.0001f, 1f - barHeightRatio)))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(barHeight)
                        .background(barColor)
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Labels
                Text(
                    text = item.label,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
                Text(
                    text = "₹${"%.2f".format(item.value)}",
                    style = MaterialTheme.typography.labelSmall
                )

            }
        }
    }
}
