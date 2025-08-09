package com.aug.smartkharch.ui.expenselist.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aug.smartkharch.R

@Composable
fun CategoryFilterChips(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    LazyRow(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        item {
            AssistChip(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { onCategorySelected(null) },
                label = { Text(
                    stringResource(R.string.all),
                    color = if (selectedCategory == null) Color.White else MaterialTheme.colorScheme.onSurface
                ) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (selectedCategory == null) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            )
        }

        items(categories) { category ->
            AssistChip(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { onCategorySelected(category) },
                label = { Text(category,
                    color = if (selectedCategory == category) Color.White else MaterialTheme.colorScheme.onSurface
                ) },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}
