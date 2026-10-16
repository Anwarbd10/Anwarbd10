package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.ui.components.BottomNavBar
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.ServerListScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.SpeedTestScreen
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.VpnViewModel

class MainActivity : ComponentActivity() {

    private val vpnViewModel: VpnViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by vpnViewModel.isDarkMode.collectAsState()
            val currentTab by vpnViewModel.currentTab.collectAsState()

            MyApplicationTheme(darkTheme = isDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            currentTab = currentTab,
                            onTabSelected = { tab -> vpnViewModel.setTab(tab) }
                        )
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        when (currentTab) {
                            0 -> HomeScreen(
                                viewModel = vpnViewModel,
                                onNavigateServers = { vpnViewModel.setTab(1) },
                                onNavigateSettings = { vpnViewModel.setTab(3) }
                            )
                            1 -> ServerListScreen(
                                viewModel = vpnViewModel,
                                onBack = { vpnViewModel.setTab(0) }
                            )
                            2 -> SpeedTestScreen(
                                onBack = { vpnViewModel.setTab(0) }
                            )
                            3 -> SettingsScreen(
                                viewModel = vpnViewModel,
                                onBack = { vpnViewModel.setTab(0) }
                            )
                        }
                    }
                }
            }
        }
    }
}
