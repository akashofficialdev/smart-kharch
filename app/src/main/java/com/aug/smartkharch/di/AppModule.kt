package com.aug.smartkharch.di

import android.content.Context
import androidx.room.Room
import com.aug.smartkharch.data.local.dao.ExpenseDao
import com.aug.smartkharch.data.local.db.ExpenseDatabase
import com.aug.smartkharch.data.repository.ExpenseRepositoryImpl
import com.aug.smartkharch.domain.repository.ExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ExpenseDatabase =
        Room.databaseBuilder(context, ExpenseDatabase::class.java, "expense_db").build()

    @Provides
    fun provideExpenseDao(db: ExpenseDatabase): ExpenseDao = db.expenseDao()

    @Provides
    fun provideExpenseRepository(dao: ExpenseDao): ExpenseRepository =
        ExpenseRepositoryImpl(dao)
}
