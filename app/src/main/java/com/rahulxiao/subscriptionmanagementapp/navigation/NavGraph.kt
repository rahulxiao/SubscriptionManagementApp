package com.rahulxiao.subscriptionmanagementapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.rahulxiao.subscriptionmanagementapp.data.local.AppDatabase
import com.rahulxiao.subscriptionmanagementapp.data.repository.SubscriptionRepository
import com.rahulxiao.subscriptionmanagementapp.ui.screens.*
import com.rahulxiao.subscriptionmanagementapp.viewmodel.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class Screen(val route: String) {
    object Onboarding : Screen("onboarding")
    object Dashboard : Screen("dashboard")
    object Add : Screen("add")
    object Details : Screen("details/{subscriptionId}") {
        fun createRoute(subscriptionId: Long) = "details/$subscriptionId"
    }
    object Analytics : Screen("analytics")
    object Notes : Screen("notes")
    object Settings : Screen("settings")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Onboarding.route
) {
    val context = LocalContext.current
    val database = remember { AppDatabase.getDatabase(context) }
    val repository = remember { SubscriptionRepository(database.subscriptionDao()) }
    val viewModelFactory = remember { ViewModelFactory(repository) }
    
    // Seed mock data on first launch
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            repository.seedMockData()
        }
    }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Onboarding
        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                onComplete = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }
        
        // Dashboard
        composable(Screen.Dashboard.route) {
            val viewModel: DashboardViewModel = viewModel(factory = viewModelFactory)
            DashboardScreen(
                viewModel = viewModel,
                onNavigateToDetails = { subscriptionId ->
                    navController.navigate(Screen.Details.createRoute(subscriptionId))
                },
                onNavigateToAdd = {
                    navController.navigate(Screen.Add.route)
                },
                onNavigateToAnalytics = {
                    navController.navigate(Screen.Analytics.route)
                },
                onNavigateToNotes = {
                    navController.navigate(Screen.Notes.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                currentRoute = Screen.Dashboard.route
            )
        }
        
        // Add Subscription
        composable(Screen.Add.route) {
            val viewModel: AddSubscriptionViewModel = viewModel(factory = viewModelFactory)
            AddSubscriptionScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Subscription Details
        composable(
            route = Screen.Details.route,
            arguments = listOf(
                navArgument("subscriptionId") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val subscriptionId = backStackEntry.arguments?.getLong("subscriptionId") ?: 0L
            val detailsFactory = remember {
                DetailsViewModelFactory(repository, subscriptionId)
            }
            val viewModel: DetailsViewModel = viewModel(factory = detailsFactory)
            
            SubscriptionDetailsScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        // Analytics
        composable(Screen.Analytics.route) {
            val viewModel: AnalyticsViewModel = viewModel(factory = viewModelFactory)
            AnalyticsScreen(
                viewModel = viewModel,
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onNavigateToAdd = {
                    navController.navigate(Screen.Add.route)
                },
                onNavigateToNotes = {
                    navController.navigate(Screen.Notes.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                currentRoute = Screen.Analytics.route
            )
        }
        
        // Notes
        composable(Screen.Notes.route) {
            NotesScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onNavigateToAdd = {
                    navController.navigate(Screen.Add.route)
                },
                onNavigateToAnalytics = {
                    navController.navigate(Screen.Analytics.route)
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                currentRoute = Screen.Notes.route
            )
        }
        
        // Settings
        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateToDashboard = {
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Dashboard.route) { inclusive = true }
                    }
                },
                onNavigateToAdd = {
                    navController.navigate(Screen.Add.route)
                },
                onNavigateToAnalytics = {
                    navController.navigate(Screen.Analytics.route)
                },
                onNavigateToNotes = {
                    navController.navigate(Screen.Notes.route)
                },
                currentRoute = Screen.Settings.route
            )
        }
    }
}

