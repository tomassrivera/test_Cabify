package com.android.cabifymarketplace.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.android.cabifymarketplace.presentation.ui.theme.CabifyMarketplaceTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CabifyMarketplaceTheme {
                CabifyMarketplaceApp()
            }
        }
    }
}
