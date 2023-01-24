package com.android.cabifymarketplace.presentation.products

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.cabifymarketplace.R
import com.android.cabifymarketplace.core.Resource
import com.android.cabifymarketplace.domain.model.Product
import com.android.cabifymarketplace.domain.model.Products
import com.android.cabifymarketplace.presentation.utils.Formats.currencyFormat

@Composable
fun ProductsScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    onNextButtonClicked: () -> Unit,
    onUpdateCartClicked: (Int, Product) -> Unit
) {

    val productList by viewModel.productList

    Box(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        when (productList) {
            is Resource.Success<Products> -> {
                productList.data?.products?.let { productList ->
                    ProductList(productList, onUpdateCartClicked, viewModel)
                }
                Box(modifier = Modifier.fillMaxSize()) {
                    FloatingActionButton(
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .align(alignment = Alignment.BottomEnd),
                        onClick = onNextButtonClicked
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ic_cart), contentDescription = "Add")
                    }
                }
            }
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is Resource.Error -> {
                Text(
                    text = productList.message ?: "",
                    color = MaterialTheme.colors.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun ProductList(
    productList: List<Product>,
    onUpdateCartClicked: (Int, Product) -> Unit,
    viewModel: ProductViewModel
) {
    LazyColumn() {
        items(items = productList) { item ->
            Card(
                backgroundColor = MaterialTheme.colors.primary,
                modifier = Modifier.padding(vertical = 8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                CardContent(item, onUpdateCartClicked, viewModel)
            }
        }
    }
}

@Composable
private fun CardContent(
    product: Product,
    onUpdateCartClicked: (Int, Product) -> Unit,
    viewModel: ProductViewModel
) {

    val cartProducts by viewModel.order.observeAsState()

    Column(
        modifier = Modifier
            .padding(vertical = 2.dp, horizontal = 8.dp),
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = product.name, style = MaterialTheme.typography.h6)
            }
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = currencyFormat(product.price),
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp),

            horizontalArrangement = Arrangement.End
        ) {
            if (cartProducts?.find { it.code == product.code } != null) {
                IconButton(
                    onClick = {
                        onUpdateCartClicked(-1, product)
                    },
                    Modifier.size(30.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_remove), contentDescription = "", Modifier.padding(end = 10.dp))
                }
                Text(text = cartProducts!!.find { it.code == product.code }!!.quantity.toString(), fontSize = 22.sp)
            }
            IconButton(
                onClick = {
                    onUpdateCartClicked(1, product)
                },
                Modifier.size(30.dp)
            ) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "", Modifier.padding(start = 10.dp))
            }
        }
    }
}

/*@Preview
@Composable
fun StartOrderPreview() {
    ProductList(
        listOf(
            Product("TSHIRT", "Cabify T-Shirt", BigDecimal(23.45)),
            Product("MUG", "Cabify Coffee Mug", BigDecimal(7.5))
        ),
        { i: Int, s: Product -> },
        cartProducts,
        viewModel
    )
}*/
