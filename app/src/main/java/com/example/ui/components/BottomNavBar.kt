package com.example.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavBar(
    currentTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("VPN") },
            selected = currentTab == 0,
            onClick = { onTabSelected(0) },
            modifier = Modifier.testTag("nav_home")
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocationOn, contentDescription = "Servers") },
            label = { Text("Servers") },
            selected = currentTab == 1,
            onClick = { onTabSelected(1) },
            modifier = Modifier.testTag("nav_servers")
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Speed Test") },
            label = { Text("Speed Test") },
            selected = currentTab == 2,
            onClick = { onTabSelected(2) },
            modifier = Modifier.testTag("nav_speedtest")
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text("Settings") },
            selected = currentTab == 3,
            onClick = { onTabSelected(3) },
            modifier = Modifier.testTag("nav_settings")
        )
    }
}
