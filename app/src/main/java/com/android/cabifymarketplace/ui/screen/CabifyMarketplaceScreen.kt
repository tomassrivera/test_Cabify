package com.android.cabifymarketplace.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.cabifymarketplace.R


enum class CabifyMarketplaceScreen(@StringRes val title: Int) {
    Products(title = R.string.products_screen)
}

@Composable
fun CabifyMarketplaceAppBar(
    currentScreen: CabifyMarketplaceScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CabifyMarketplaceApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CabifyMarketplaceScreen.valueOf(
        backStackEntry?.destination?.route ?: CabifyMarketplaceScreen.Products.name
    )

    Scaffold(
        topBar = {
            CabifyMarketplaceAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CabifyMarketplaceScreen.Products.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = CabifyMarketplaceScreen.Products.name) {
                ProductsScreen(
                    onNextButtonClicked = {
                    }
                )
            }
        }
    }
}