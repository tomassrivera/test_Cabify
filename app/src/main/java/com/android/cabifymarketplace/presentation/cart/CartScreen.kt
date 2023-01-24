package com.android.cabifymarketplace.presentation.cart

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.android.cabifymarketplace.R
import com.android.cabifymarketplace.core.Resource
import com.android.cabifymarketplace.presentation.CabifyMarketplaceScreen
import com.android.cabifymarketplace.presentation.ui.theme.positiveDiscount

@Composable
fun ReviewCartScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel()
) {

    val summaryCart by viewModel.discounts

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        when (summaryCart) {
            is Resource.Success<List<DescriptionCartSummary>> -> {
                LazyColumn() {
                    summaryCart.data?.let {
                        items(items = it) { item ->
                            summaryRow(item)
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.resetOrder()
                            navController.popBackStack(
                                CabifyMarketplaceScreen.Products.name,
                                inclusive = false
                            )
                        }
                    ) {
                        Text(stringResource(R.string.cancel_button))
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { }
                    ) {
                        Text(stringResource(R.string.pay_button))
                    }
                }
            }
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is Resource.Error -> {
                Text(
                    text = summaryCart.message ?: "",
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun summaryRow(
    descriptionCartSummary: DescriptionCartSummary

) {
    var textColor: Color = Color.Unspecified
    var fontWeight: FontWeight? = null
    when (descriptionCartSummary.type) {
        "positive" -> { textColor = MaterialTheme.colors.positiveDiscount }
        "total" -> { fontWeight = FontWeight.Bold }
    }
    if (descriptionCartSummary.type == "total") {
        Divider(color = MaterialTheme.colors.primary, thickness = 1.dp)
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {

            Text(text = descriptionCartSummary.description, fontSize = 16.sp, fontWeight = fontWeight)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = descriptionCartSummary.price, fontSize = 16.sp, color = textColor, fontWeight = fontWeight)
        }
    }
}

/*
@Preview
@Composable
fun ReviewCartScreenPreview() {
    ReviewCartScreen(OrderInfo(mutableMapOf("TSHIRT" to 3, "MUG" to 1)))
}*/
