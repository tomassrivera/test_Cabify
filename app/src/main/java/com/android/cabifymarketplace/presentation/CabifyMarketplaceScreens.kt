package com.android.cabifymarketplace.presentation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.cabifymarketplace.R
import com.android.cabifymarketplace.domain.model.Product
import com.android.cabifymarketplace.presentation.cart.ReviewCartScreen
import com.android.cabifymarketplace.presentation.products.ProductViewModel
import com.android.cabifymarketplace.presentation.products.ProductsScreen
import com.android.cabifymarketplace.presentation.ui.theme.CabifyMarketplaceTheme
import com.android.cabifymarketplace.presentation.utils.PreviewUtil

enum class CabifyMarketplaceScreens(@StringRes val title: Int) {
    Products(title = R.string.products_screen),
    ReviewCart(title = R.string.review_cart_screen)
}

@Composable
fun CabifyMarketplaceTopAppBar(
    currentScreen: CabifyMarketplaceScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    navigateCart: () -> Unit,
    cartQuantity: Int
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button_content_description)
                    )
                }
            }
        },
        actions = {
            Column(Modifier.padding(end = 15.dp)) {
                IconButton(
                    onClick = navigateCart
                ) {
                    BadgedBox(badge = { Badge { Text(cartQuantity.toString()) } }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = stringResource(R.string.cart_button_content_description)
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
    viewModel: ProductViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CabifyMarketplaceScreens.valueOf(
        backStackEntry?.destination?.route ?: CabifyMarketplaceScreens.Products.name
    )

    val cartProducts by viewModel.order.observeAsState()

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            CabifyMarketplaceTopAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                navigateCart = { navController.navigate(CabifyMarketplaceScreens.ReviewCart.name) },
                cartQuantity = cartProducts?.sumOf { it.quantity } ?: 0
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CabifyMarketplaceScreens.Products.name,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = CabifyMarketplaceScreens.Products.name) {
                ProductsScreen(
                    onNextButtonClicked = { navController.navigate(CabifyMarketplaceScreens.ReviewCart.name) },
                    onUpdateCartClicked = { quantity: Int, product: Product ->
                        viewModel.changeItemQuantity(product, quantity)
                    }
                )
            }
            composable(route = CabifyMarketplaceScreens.ReviewCart.name) {
                ReviewCartScreen(navController)
            }
        }
    }
}

@Composable
fun ShowError(
    modifier: Modifier = Modifier,
    message: String?,
    onClickAction: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Text(
            text = stringResource(
                R.string.error_message,
                message ?: stringResource(R.string.unexpected_message_error)
            ),
            color = MaterialTheme.colors.error,
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = onClickAction,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.button_error_retry))
        }
    }
}

@Composable
fun ShowLoading(modifier: Modifier = Modifier) {
    CircularProgressIndicator(modifier = modifier)
}

@Preview
@Composable
fun PreviewCabifyMarketplaceTopAppBar() {
    CabifyMarketplaceTheme {
        CabifyMarketplaceApp(viewModel = PreviewUtil.getMockProductsViewModel())
    }
}

@Preview
@Composable
fun PreviewError() {
    CabifyMarketplaceTheme {
        ShowError(message = "Connection error") {}
    }
}

@Preview
@Composable
fun PreviewLoading() {
    CabifyMarketplaceTheme {
        ShowLoading()
    }
}
