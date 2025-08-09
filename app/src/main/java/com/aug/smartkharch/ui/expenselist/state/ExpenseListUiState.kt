package com.aug.smartkharch.ui.expenselist.state

import com.aug.smartkharch.data.local.entity.Expense

sealed class ExpenseListUiState {
    object Loading : ExpenseListUiState()
    data class Success(val expenses: List<Expense>) : ExpenseListUiState()
    object Empty : ExpenseListUiState()
    data class Error(val message: String) : ExpenseListUiState()
}
