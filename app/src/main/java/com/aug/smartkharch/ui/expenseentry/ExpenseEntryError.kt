package com.aug.smartkharch.ui.expenseentry

sealed class ExpenseEntryError {
    object EmptyTitle : ExpenseEntryError()
    object InvalidAmount : ExpenseEntryError()
}
