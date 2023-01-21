package com.android.cabifymarketplace.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.cabifymarketplace.model.OrderInfo
import com.android.cabifymarketplace.ui.utils.Formats
import com.android.cabifymarketplace.ui.viewmodel.ProductViewModel


@Composable
fun ReviewCartScreen(modifier: Modifier = Modifier, viewModel: ProductViewModel) {
    val orderInfo = viewModel.order
    val discountList = viewModel.getDiscountsSummary()

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {

        LazyColumn() {
            items(items = orderInfo.productsCodeSelected.toList()) { item ->
                summaryRow(item.name + " (" + item.quantity + ")", Formats.currencyFormat(item.getFinalPrice()))
            }
            items(items = discountList) { item ->
                summaryRow(item.first, Formats.currencyFormat(item.second))
            }
        }
    }
}

@Composable
fun summaryRow(description: String, total: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = description)
        }
        Column(horizontalAlignment = Alignment.End) {
            Text(text = total)
        }
    }
}

/*
@Preview
@Composable
fun ReviewCartScreenPreview() {
    ReviewCartScreen(OrderInfo(mutableMapOf("TSHIRT" to 3, "MUG" to 1)))
}*/
