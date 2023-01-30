package com.android.cabifymarketplace.presentation.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.cabifymarketplace.R
import com.android.cabifymarketplace.core.Constants.DESCRIPTION_CART_SUMMARY_TYPE_DISCOUNT
import com.android.cabifymarketplace.core.Constants.DESCRIPTION_CART_SUMMARY_TYPE_NORMAL
import com.android.cabifymarketplace.core.Constants.DESCRIPTION_CART_SUMMARY_TYPE_TOTAL
import com.android.cabifymarketplace.core.Resource
import com.android.cabifymarketplace.presentation.CabifyMarketplaceScreens
import com.android.cabifymarketplace.presentation.ShowError
import com.android.cabifymarketplace.presentation.ShowLoading
import com.android.cabifymarketplace.presentation.ui.theme.CabifyMarketplaceTheme
import com.android.cabifymarketplace.presentation.ui.theme.positiveDiscount
import com.android.cabifymarketplace.presentation.utils.PreviewUtil

@Composable
fun ReviewCartScreen(
    navController: NavController,
    viewModel: CartViewModel = hiltViewModel()
) {
    val summaryCart by viewModel.discounts
    val resetOrderUnit = { viewModel.resetOrder() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        when (summaryCart) {
            is Resource.Success<List<DescriptionCartSummary>> -> {
                if ((summaryCart.data).isNullOrEmpty()) {
                    ShowEmptyCart(Modifier.align(Alignment.Center), navController)
                } else {
                    Column {
                        SummaryCart(summaryCart.data!!)
                        Spacer(modifier = Modifier.height(16.dp))
                        ButtonsArea(resetOrderUnit, navController)
                    }
                }
            }
            is Resource.Loading -> {
                ShowLoading(Modifier.align(Alignment.Center))
            }

            is Resource.Error -> {
                ShowError(Modifier.align(Alignment.Center), summaryCart.message) {
                    viewModel.getDiscountsSummary()
                }
            }
        }
    }
}

@Composable
fun SummaryCart(
    summaryCart: List<DescriptionCartSummary>
) {
    Column {
        LazyColumn {
            items(items = summaryCart) { item ->
                var textColor: Color = Color.Unspecified
                var fontWeight: FontWeight? = null
                var description: String = item.description
                when (item.type) {
                    DESCRIPTION_CART_SUMMARY_TYPE_NORMAL -> {
                        description = stringResource(
                            R.string.description_cart_summary_normal,
                            item.description,
                            item.quantity
                        )
                    }
                    DESCRIPTION_CART_SUMMARY_TYPE_DISCOUNT -> {
                        textColor = MaterialTheme.colors.positiveDiscount
                    }
                    DESCRIPTION_CART_SUMMARY_TYPE_TOTAL -> {
                        description = stringResource(R.string.description_cart_summary_total)
                        fontWeight = FontWeight.Bold
                    }
                }
                if (item.type == DESCRIPTION_CART_SUMMARY_TYPE_TOTAL) {
                    Divider(color = MaterialTheme.colors.primary, thickness = 1.dp)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = description, fontSize = 16.sp, fontWeight = fontWeight)
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = item.price,
                            fontSize = 16.sp,
                            color = textColor,
                            fontWeight = fontWeight
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ShowEmptyCart(modifier: Modifier, navController: NavController) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_cart),
            contentDescription = stringResource(
                R.string.empty_cart_image_content_description
            )
        )
        Text(
            text = stringResource(R.string.empty_cart_message),
            textAlign = TextAlign.Center,
        )
        Button(
            onClick = {
                navController.popBackStack(
                    CabifyMarketplaceScreens.Products.name,
                    inclusive = false
                )
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        ) {
            Text(stringResource(R.string.add_item_cart_button))
        }
    }
}

@Composable
fun ButtonsArea(resetOrderUnit: () -> Unit, navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedButton(
            modifier = Modifier.weight(1f),
            onClick = {
                resetOrderUnit.invoke()
                navController.popBackStack(
                    CabifyMarketplaceScreens.Products.name,
                    inclusive = false
                )
            }
        ) {
            Text(stringResource(R.string.cancel_button))
        }
        Button(modifier = Modifier.weight(1f), onClick = { }) {
            Text(stringResource(R.string.pay_button))
        }
    }
}

@Preview
@Composable
fun ReviewCartScreenPreview() {
    CabifyMarketplaceTheme {
        SummaryCart(
            PreviewUtil.getMockDescriptionCartSummary()
        )
    }
}

@Preview
@Composable
fun ShowEmptyCartPreview() {
    CabifyMarketplaceTheme {
        ShowEmptyCart(
            Modifier,
            NavController(LocalContext.current)
        )
    }
}
