package com.android.cabifymarketplace.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.android.cabifymarketplace.R
import com.android.cabifymarketplace.model.Product
import com.android.cabifymarketplace.model.Products
import com.android.cabifymarketplace.ui.common.Resource
import com.android.cabifymarketplace.ui.utils.Formats.currencyFormat
import com.android.cabifymarketplace.ui.viewmodel.ProductViewModel
import java.math.BigDecimal

@Composable
fun ProductsScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    onNextButtonClicked: () -> Unit,
    onUpdateCartClicked: (Int, Product) -> Unit,
    modifier: Modifier = Modifier
) {

    val productList by viewModel.getProducts().observeAsState()

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        if (productList is Resource.Success) {
            ProductList((productList as Resource.Success<Products>).data.products, onUpdateCartClicked)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        FloatingActionButton(
            modifier = Modifier
                .padding(all = 16.dp)
                .align(alignment = Alignment.BottomEnd),
            onClick = onNextButtonClicked) {
            Icon(painter = painterResource(id = R.drawable.ic_cart), contentDescription = "Add")
        }
    }
}

@Composable
private fun ProductList(
    productList: List<Product>,
    onUpdateCartClicked: (Int, Product) -> Unit
) {
        LazyColumn() {
            items(items = productList) { item ->
                Card(
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    CardContent(item, onUpdateCartClicked)
                }
            }
    }
}

@Composable
private fun CardContent(
    product: Product,
    onUpdateCartClicked: (Int, Product) -> Unit
) {
    var itemsQuantity by rememberSaveable { mutableStateOf(0) }
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
                    text = currencyFormat(product.price), style = MaterialTheme.typography.h5.copy(
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
            if (itemsQuantity > 0) {
                IconButton(onClick = {
                    itemsQuantity--
                    onUpdateCartClicked(-1, product)
                                     }, Modifier.size(30.dp)) {
                    Icon(painter = painterResource(id = R.drawable.ic_remove), contentDescription = "",  Modifier.padding(end = 10.dp))
                }
                Text(text = itemsQuantity.toString(), fontSize = 22.sp)
            }
            IconButton(onClick = {
                itemsQuantity++
                onUpdateCartClicked(1, product)
                                 }, Modifier.size(30.dp)) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = "", Modifier.padding(start = 10.dp))
            }
        }
    }
}

@Preview
@Composable
fun StartOrderPreview() {
    ProductList(
        listOf(
            Product("TSHIRT", "Cabify T-Shirt", BigDecimal(23.45)),
            Product("MUG", "Cabify Coffee Mug", BigDecimal(7.5))
        )
    ) { i: Int, s: Product -> }
}
