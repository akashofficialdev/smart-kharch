package com.aug.smartkharch.data.repository

import com.aug.smartkharch.data.local.dao.ExpenseDao
import com.aug.smartkharch.data.local.entity.Expense
import com.aug.smartkharch.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.ZoneId

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
) : ExpenseRepository {

    override suspend fun addExpense(expense: Expense) = dao.insertExpense(expense)

    override fun getAllExpenses(): Flow<List<Expense>> = flow {
        emit(dao.getAllExpenses())
    }

    override fun getExpensesForDateRange(start: Long, end: Long): Flow<List<Expense>> = flow {
        emit(dao.getExpensesForDateRange(start, end))
    }

    override fun getExpensesByDate(date: LocalDate): Flow<List<Expense>> {
        val startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1
        return dao.getExpensesByDate(startOfDay, endOfDay)
    }

    override fun getTotalSpentOnDate(date: LocalDate): Flow<Double> {
        val startOfDay = date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        val endOfDay = date.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli() - 1
        return dao.getTotalSpentOnDate(startOfDay, endOfDay)
            .map { it ?: 0.0 }
    }

    override suspend fun deleteExpense(expense: Expense) = dao.deleteExpense(expense)
}
