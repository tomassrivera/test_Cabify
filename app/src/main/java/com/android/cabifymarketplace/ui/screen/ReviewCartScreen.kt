package com.android.cabifymarketplace.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.cabifymarketplace.R
import com.android.cabifymarketplace.ui.utils.Formats
import com.android.cabifymarketplace.ui.viewmodel.ProductViewModel


@Composable
fun ReviewCartScreen(modifier: Modifier = Modifier, viewModel: ProductViewModel, onCancelButtonClicked: () -> Unit) {
    val orderInfo = viewModel.order.observeAsState()
    val discountList = viewModel.getDiscountsSummary()

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {

        LazyColumn() {
            items(items = orderInfo.value!!) { item ->
                summaryRow(item.name + " (" + item.quantity + ")", Formats.currencyFormat(item.getFinalPrice()))
            }
            items(items = discountList) { item ->
                summaryRow(item.first, Formats.currencyFormat(item.second))
            }
        }
        Divider(color = MaterialTheme.colors.primary, thickness = 1.dp)
        summaryRow(stringResource(R.string.total_row_review_cart), Formats.currencyFormat(viewModel.getTotalPriceToPay()))
        Spacer(modifier = Modifier.height(16.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onCancelButtonClicked
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
