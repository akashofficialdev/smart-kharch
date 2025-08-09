package com.aug.smartkharch.ui.expensereport

sealed class ExpenseReportUiState {
    object Loading : ExpenseReportUiState()
    data class Success(
        val dailyTotals: List<Pair<String, Double>>,
        val categoryTotals: Map<String, Double>
    ) : ExpenseReportUiState()
    data class Error(val message: String) : ExpenseReportUiState()
}
