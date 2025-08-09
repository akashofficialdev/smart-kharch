package com.aug.smartkharch.ui.expensereport.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.aug.smartkharch.R
import com.aug.smartkharch.data.model.BarChartData

@Composable
fun CategoryTotalSection(categoryChartData: List<BarChartData>) {
    Text(text = stringResource(R.string.category_totals), style = MaterialTheme.typography.titleMedium)
    SimpleBarChart(data = categoryChartData)
}