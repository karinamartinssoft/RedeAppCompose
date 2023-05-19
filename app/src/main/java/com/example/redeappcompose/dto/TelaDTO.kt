package com.example.redeappcompose.dto

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Ping : Screen("ping")
    object SpeedTest : Screen("speedtest")
}