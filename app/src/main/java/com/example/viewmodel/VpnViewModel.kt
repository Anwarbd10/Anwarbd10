package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Server
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class VpnState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    DISCONNECTING
}

class VpnViewModel : ViewModel() {

    private val _vpnState = MutableStateFlow(VpnState.DISCONNECTED)
    val vpnState: StateFlow<VpnState> = _vpnState.asStateFlow()

    val servers = listOf(
        Server(
            id = "bd-1",
            name = "Bangladesh Optimized",
            country = "Bangladesh",
            flag = "🇧🇩",
            latencyMs = 18,
            locationsCount = 4,
            isBangladesh = true,
            description = "Supports Alap, bKash, Brilliant, Dialers & Banking"
        ),
        Server(
            id = "uk-1",
            name = "United Kingdom",
            country = "London / Manchester",
            flag = "🇬🇧",
            latencyMs = 45,
            locationsCount = 14,
            description = "High speed streaming and gaming"
        ),
        Server(
            id = "us-1",
            name = "United States",
            country = "Los Angeles, CA",
            flag = "🇺🇸",
            latencyMs = 72,
            locationsCount = 12,
            description = "Ultra-fast servers with zero throttling"
        ),
        Server(
            id = "au-1",
            name = "Australia",
            country = "Sydney",
            flag = "🇦🇺",
            latencyMs = 95,
            locationsCount = 11,
            description = "Secure Oceania connection"
        ),
        Server(
            id = "de-1",
            name = "Germany",
            country = "Frankfurt",
            flag = "🇩🇪",
            latencyMs = 52,
            locationsCount = 8,
            description = "Strict privacy and high encryption"
        ),
        Server(
            id = "fr-1",
            name = "France",
            country = "Paris",
            flag = "🇫🇷",
            latencyMs = 58,
            locationsCount = 6,
            description = "European lightning node"
        ),
        Server(
            id = "sg-1",
            name = "Singapore",
            country = "Singapore",
            flag = "🇸🇬",
            latencyMs = 32,
            locationsCount = 5,
            isPremium = true,
            description = "Lightning low ping for Asia"
        ),
        Server(
            id = "jp-1",
            name = "Japan",
            country = "Tokyo",
            flag = "🇯🇵",
            latencyMs = 40,
            locationsCount = 7,
            isPremium = true,
            description = "Dedicated gaming and streaming VIP node"
        )
    )

    private val _selectedServer = MutableStateFlow(servers[0]) // Default to Bangladesh Optimized!
    val selectedServer: StateFlow<Server> = _selectedServer.asStateFlow()

    private val _connectionTimeSeconds = MutableStateFlow(0L)
    val connectionTimeSeconds: StateFlow<Long> = _connectionTimeSeconds.asStateFlow()

    private val _downloadSpeed = MutableStateFlow(0.0)
    val downloadSpeed: StateFlow<Double> = _downloadSpeed.asStateFlow()

    private val _uploadSpeed = MutableStateFlow(0.0)
    val uploadSpeed: StateFlow<Double> = _uploadSpeed.asStateFlow()

    private val _currentTab = MutableStateFlow(0) // 0: Home, 1: Servers, 2: SpeedTest, 3: Settings
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()

    private val _isDarkMode = MutableStateFlow(true)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _killSwitchEnabled = MutableStateFlow(true)
    val killSwitchEnabled: StateFlow<Boolean> = _killSwitchEnabled.asStateFlow()

    private val _autoConnect = MutableStateFlow(false)
    val autoConnect: StateFlow<Boolean> = _autoConnect.asStateFlow()

    private var timerJob: Job? = null
    private var speedJob: Job? = null

    fun setTab(tab: Int) {
        _currentTab.value = tab
    }

    fun selectServer(server: Server) {
        _selectedServer.value = server
        if (_vpnState.value == VpnState.CONNECTED) {
            // Reconnect with new server
            disconnect()
            connect()
        }
    }

    fun toggleVpn() {
        when (_vpnState.value) {
            VpnState.DISCONNECTED -> connect()
            VpnState.CONNECTED -> disconnect()
            else -> {}
        }
    }

    fun connect() {
        if (_vpnState.value == VpnState.CONNECTED || _vpnState.value == VpnState.CONNECTING) return
        _vpnState.value = VpnState.CONNECTING
        viewModelScope.launch {
            delay(1800) // simulation delay
            _vpnState.value = VpnState.CONNECTED
            startTimer()
            startSpeedSimulation()
        }
    }

    fun disconnect() {
        if (_vpnState.value == VpnState.DISCONNECTED || _vpnState.value == VpnState.DISCONNECTING) return
        _vpnState.value = VpnState.DISCONNECTING
        viewModelScope.launch {
            delay(1000)
            _vpnState.value = VpnState.DISCONNECTED
            stopTimer()
            stopSpeedSimulation()
        }
    }

    private fun startTimer() {
        _connectionTimeSeconds.value = 0L
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000L)
                _connectionTimeSeconds.update { it + 1 }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        _connectionTimeSeconds.value = 0L
    }

    private fun startSpeedSimulation() {
        speedJob?.cancel()
        speedJob = viewModelScope.launch {
            while (true) {
                // simulate realistic speeds around 45-85 mb/s down, 20-40 mb/s up
                _downloadSpeed.value = 40.0 + (Math.random() * 35.0)
                _uploadSpeed.value = 18.0 + (Math.random() * 18.0)
                delay(3000L)
            }
        }
    }

    private fun stopSpeedSimulation() {
        speedJob?.cancel()
        _downloadSpeed.value = 0.0
        _uploadSpeed.value = 0.0
    }

    fun toggleDarkMode() {
        _isDarkMode.update { !it }
    }

    fun toggleKillSwitch() {
        _killSwitchEnabled.update { !it }
    }

    fun toggleAutoConnect() {
        _autoConnect.update { !it }
    }
}
