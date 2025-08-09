package com.aug.smartkharch.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aug.smartkharch.data.local.dao.ExpenseDao
import com.aug.smartkharch.data.local.entity.Expense

@Database(entities = [Expense::class], version = 1, exportSchema = false)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao(): ExpenseDao
}
