package com.aug.smartkharch.domain.repository

import com.aug.smartkharch.data.local.entity.Expense
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface ExpenseRepository {
    suspend fun addExpense(expense: Expense)
    fun getAllExpenses(): Flow<List<Expense>>
    suspend fun getExpensesByDate(date: LocalDate): Flow<List<Expense>>
    fun getTotalSpentOnDate(date: LocalDate): Flow<Double>
    suspend fun deleteExpense(expense: Expense)
}
