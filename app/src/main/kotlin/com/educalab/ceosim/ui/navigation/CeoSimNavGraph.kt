package com.educalab.ceosim.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.educalab.ceosim.ui.CeoSimViewModel
import com.educalab.ceosim.ui.screens.badges.BadgesScreen
import com.educalab.ceosim.ui.screens.cashbox.CashboxScreen
import com.educalab.ceosim.ui.screens.challenges.ChallengesScreen
import com.educalab.ceosim.ui.screens.counter.CounterScreen
import com.educalab.ceosim.ui.screens.inventory.InventoryScreen
import com.educalab.ceosim.ui.screens.onboarding.OnboardingScreen
import com.educalab.ceosim.ui.screens.profile.ProfileScreen
import com.educalab.ceosim.ui.screens.store.StoreHomeScreen
import com.educalab.ceosim.ui.screens.upgrades.UpgradesScreen
import com.educalab.ceosim.ui.screens.warehouse.WarehouseScreen

object Routes {
    const val ONBOARDING = "onboarding"
    const val HOME = "home"
    const val WAREHOUSE = "warehouse"
    const val INVENTORY = "inventory"
    const val COUNTER = "counter"
    const val CASHBOX = "cashbox"
    const val UPGRADES = "upgrades"
    const val CHALLENGES = "challenges"
    const val BADGES = "badges"
    const val PROFILE = "profile"
}

@Composable
fun CeoSimNavGraph(viewModel: CeoSimViewModel, startAtOnboarding: Boolean) {
    val navController: NavHostController = rememberNavController()
    val startDestination = if (startAtOnboarding) Routes.ONBOARDING else Routes.HOME

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinish = {
                    viewModel.completeOnboarding()
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.HOME) {
            StoreHomeScreen(viewModel = viewModel, navController = navController)
        }
        composable(Routes.WAREHOUSE) {
            WarehouseScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.INVENTORY) {
            InventoryScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.COUNTER) {
            CounterScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.CASHBOX) {
            CashboxScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.UPGRADES) {
            UpgradesScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.CHALLENGES) {
            ChallengesScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.BADGES) {
            BadgesScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.PROFILE) {
            ProfileScreen(viewModel = viewModel, onBack = { navController.popBackStack() })
        }
    }
}
