package com.aug.smartkharch.ui.expenseentry.state

sealed class ExpenseEntryError {
    object EmptyTitle : ExpenseEntryError()
    object InvalidAmount : ExpenseEntryError()
}
