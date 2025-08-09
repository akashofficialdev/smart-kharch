package com.aug.smartkharch.ui.expensereport

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aug.smartkharch.domain.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class ExpenseReportViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ExpenseReportUiState>(ExpenseReportUiState.Loading)
    val uiState: StateFlow<ExpenseReportUiState> = _uiState

    init {
        loadReport()
    }

    private fun loadReport() {
        viewModelScope.launch {
            // Mock last 7 days data
            val data = mutableListOf<Pair<String, Double>>()
            val cal = Calendar.getInstance()
            for (i in 6 downTo 0) {
                cal.timeInMillis = System.currentTimeMillis() - i * 24 * 60 * 60 * 1000
                val dayLabel = "${cal.get(Calendar.DAY_OF_MONTH)}/${cal.get(Calendar.MONTH) + 1}"
                data.add(dayLabel to (50..500).random().toDouble()) // random for mock
            }

            val categoryTotals = mapOf(
                "Food" to (200..1000).random().toDouble(),
                "Travel" to (200..1000).random().toDouble(),
                "Staff" to (200..1000).random().toDouble(),
                "Utility" to (200..1000).random().toDouble()
            )

            _uiState.value = ExpenseReportUiState.Success(data, categoryTotals)
        }
    }
}
