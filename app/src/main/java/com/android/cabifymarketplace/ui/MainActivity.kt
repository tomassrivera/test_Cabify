package com.android.cabifymarketplace.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.android.cabifymarketplace.ui.screen.CabifyMarketplaceApp
import com.android.cabifymarketplace.ui.theme.CabifyMarketplaceTheme
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
