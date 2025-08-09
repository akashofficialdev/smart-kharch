package com.aug.smartkharch.ui.expenselist.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.aug.smartkharch.data.ExpenseCategory
import com.aug.smartkharch.data.local.entity.Expense
import com.aug.smartkharch.ui.expenselist.components.CategoryFilterChips
import com.aug.smartkharch.ui.expenselist.components.EmptyExpensesView
import com.aug.smartkharch.ui.expenselist.components.ExpenseItem
import com.aug.smartkharch.ui.expenselist.components.ExpenseSummaryBar

@Composable
fun ExpenseListContent(
    expenses: List<Expense>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val filteredExpenses = selectedCategory?.let { cat ->
        expenses.filter { it.category == cat }
    } ?: expenses

    val totalCount = filteredExpenses.size
    val totalAmount = filteredExpenses.sumOf { it.amount }

    Column(modifier = modifier) {
        ExpenseSummaryBar(
            totalCount = totalCount,
            totalAmount = totalAmount,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        CategoryFilterChips(
            categories = ExpenseCategory.List,
            selectedCategory = selectedCategory,
            onCategorySelected = onCategorySelected
        )
        if (filteredExpenses.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                EmptyExpensesView()
            }
        } else {
            LazyColumn {
                items(filteredExpenses) { expense ->
                    ExpenseItem(expense)
                }
            }
        }
    }
}
