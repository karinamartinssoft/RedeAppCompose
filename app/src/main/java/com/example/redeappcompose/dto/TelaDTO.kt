package com.example.redeappcompose.dto

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Ping : Screen("ping")
    object SpeedTest : Screen("speed_test")

    object LocalAreaNetwork : Screen("local_area_network")

    object GeoPing : Screen("geo_ping")
    object TracerRoute  : Screen("tracer_route")

    object ScannerWifi : Screen("scanner_wifi")
}