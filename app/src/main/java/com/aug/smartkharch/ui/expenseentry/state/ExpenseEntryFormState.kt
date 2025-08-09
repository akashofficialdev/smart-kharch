package com.aug.smartkharch.ui.expenseentry.state

import android.net.Uri

data class ExpenseEntryFormState(
    val title: String = "",
    val amount: String = "",
    val category: String = "Food",
    val notes: String = "",
    val receiptImageUri: Uri? = null
)
