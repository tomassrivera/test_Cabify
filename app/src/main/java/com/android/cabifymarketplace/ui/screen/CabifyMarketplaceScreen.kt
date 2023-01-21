package com.android.cabifymarketplace.ui.screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.android.cabifymarketplace.R
import com.android.cabifymarketplace.model.Product
import com.android.cabifymarketplace.ui.viewmodel.ProductViewModel


enum class CabifyMarketplaceScreen(@StringRes val title: Int) {
    Products(title = R.string.products_screen),
    ReviewCart(title = R.string.review_cart_screen)
}

@Composable
fun CabifyMarketplaceAppBar(
    currentScreen: CabifyMarketplaceScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navigateCart: () -> Unit,
    modifier: Modifier = Modifier,
    cartQuantity: Int
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
        },
        actions = {
            Column(
                Modifier
                    .padding(end = 15.dp)
            ) {
                IconButton(
                    onClick = navigateCart
                ) {
                    BadgedBox(badge = { Badge { Text(cartQuantity.toString()) } }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = "Favorite"
                        )
                    }

                }
            }

        }
    )
}

@Composable
fun CabifyMarketplaceApp(
    modifier: Modifier = Modifier,
    viewModel: ProductViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CabifyMarketplaceScreen.valueOf(
        backStackEntry?.destination?.route ?: CabifyMarketplaceScreen.Products.name
    )

    var cartQuantity by rememberSaveable { mutableStateOf(0) }

    Scaffold(
        topBar = {
            CabifyMarketplaceAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                navigateCart = { navController.navigate(CabifyMarketplaceScreen.ReviewCart.name)},
                cartQuantity = cartQuantity
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
                    onNextButtonClicked = { navController.navigate(CabifyMarketplaceScreen.ReviewCart.name) },
                    onUpdateCartClicked = { quantity: Int, product: Product ->
                        cartQuantity += quantity
                        viewModel.changeItemQuantity(product, quantity)
                    }
                )
            }
            composable(route = CabifyMarketplaceScreen.ReviewCart.name) {
                ReviewCartScreen(viewModel = viewModel)
            }
        }
    }
}