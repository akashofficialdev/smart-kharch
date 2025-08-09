package com.aug.smartkharch.ui.expenseentry.state

sealed class ExpenseEntryUiState {
    object Idle : ExpenseEntryUiState()
    object Saving : ExpenseEntryUiState()
    object Saved : ExpenseEntryUiState()
    data class Error(val error: ExpenseEntryError) : ExpenseEntryUiState()
}
