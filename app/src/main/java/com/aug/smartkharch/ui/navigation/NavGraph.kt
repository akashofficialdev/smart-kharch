package com.aug.smartkharch.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.aug.smartkharch.ui.expenseentry.ExpenseEntryScreen
import com.aug.smartkharch.ui.expenselist.ExpenseListScreen
import com.aug.smartkharch.ui.expensereport.ExpenseReportScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController) {
    AnimatedNavHost(
        navController = navController,
        startDestination = NavRoutes.EXPENSE_LIST,
        enterTransition = {
            slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(600)
            )
        },
        exitTransition = {
            slideOutVertically(
                targetOffsetY = { -it },
                animationSpec = tween(600)
            )
        },
        popEnterTransition = {
            slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(600)
            )
        },
        popExitTransition = {
            slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(600)
            )
        }

    ){
        composable(NavRoutes.EXPENSE_LIST) {
            ExpenseListScreen(
                onAddExpenseClick = { navController.navigate(NavRoutes.EXPENSE_ENTRY) },
                onReportClick = { navController.navigate(NavRoutes.EXPENSE_REPORT) }
            )
        }
        composable(NavRoutes.EXPENSE_ENTRY) {
            ExpenseEntryScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(NavRoutes.EXPENSE_REPORT) {
            ExpenseReportScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }

}
