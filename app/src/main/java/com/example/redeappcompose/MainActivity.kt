package com.example.redeappcompose

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.redeappcompose.dto.Screen
import com.example.redeappcompose.ui.theme.RedeAppComposeTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            RedeAppComposeTheme(
                isDarkTheme = isDarkTheme,
                onToggleTheme = { isDarkTheme = !isDarkTheme }) {

                App(isDarkTheme = isDarkTheme, onToggleTheme = { isDarkTheme = !isDarkTheme })


            }
        }
    }
}

@Composable
fun DrawerContent(
    navController: NavController,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = { navController.navigate(Screen.Home.route) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {

                Image(
                    painter = painterResource(R.drawable.ic_home_24), "Home",
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = "Home",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = Color(0xFF2196F3)
                )
            }
        }
        TextButton(
            onClick = { navController.navigate(Screen.Ping.route) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_network_ping_24), "Ping",
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Ping",

                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = Color(0xFF2196F3)
                )
            }
        }
        TextButton(
            onClick = { navController.navigate(Screen.SpeedTest.route) }

        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_speed_24), "Speed",
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Speed Test",
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = Color(0xFF2196F3)
                )
            }
        }

        TextButton(
            onClick = { navController.navigate(Screen.LocalAreaNetwork.route) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_device_hub_24), "Area Network",
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Local Area Network",

                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = Color(0xFF2196F3)
                )
            }
        }

        TextButton(
            onClick = { navController.navigate(Screen.GeoPing.route) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_geoping_24), "GeoPing",
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "GeoPing",

                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = Color(0xFF2196F3)
                )
            }
        }
        TextButton(
            onClick = { navController.navigate(Screen.TracerRoute.route) }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_route_24), "TracerRoute",
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tracer Route",

                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = Color(0xFF2196F3)
                )
            }
        }
        TextButton(
            onClick = { navController.navigate(Screen.ScannerWifi.route) }


        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_wifi_24), "Wifi",
                    modifier = Modifier.padding(start = 8.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Scanner Wifi",

                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Start,
                    color = Color(0xFF2196F3)
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(16.dp)
        ) {
            Button(
                onClick = { onToggleTheme() }, colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF2196F3)
                )
            ) {
                Text(text = if (isDarkTheme) "Tema Escuro" else "Tema Claro")
            }
        }
    }
}

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(55.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colors.primary
        )

    ) {
        content()
    }
}


@Composable
fun App(isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    ModalDrawer(
        modifier = Modifier.fillMaxSize(),
        drawerState = drawerState,
        scrimColor = Color.Transparent,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            Column {
                CustomToolbar(title = "Rede App")
                DrawerContent(navController, isDarkTheme, onToggleTheme)
            }
        },
        content = {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Rede App", color = Color.White) },
                        backgroundColor = Color(0xFF2196F3),
                        navigationIcon = {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } }
                            ) {
                                Icon(
                                    Icons.Filled.Menu,
                                    contentDescription = "Open Drawer",
                                    tint = Color.White
                                )
                            }
                        }
                    )
                },
                content = { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.Home.route
                        ) {
                            composable(Screen.Home.route) { HomeScreen() }
                            composable(Screen.Ping.route) { PingScreen() }
                            composable(Screen.SpeedTest.route) { SpeedTestScreen() }
                            composable(Screen.LocalAreaNetwork.route) { LocalAreaNetworkScreen() }
                            composable(Screen.GeoPing.route) { GeoPingScreen() }
                            composable(Screen.TracerRoute.route) { TracerRouteScreen() }
                            composable(Screen.ScannerWifi.route) { ScannerWifiScreen() }

                        }
                    }
                }
            )
        }
    )
}

@Composable
fun CustomToolbar(
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            // Definindo cantos arredondados
            .background(color = Color(0xFF2196F3))
        // Cor roxa personalizada
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .align(Alignment.Center)

        ) {
            Text(
                text = title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,


                )
        }
    }
}


@Composable
fun HomeScreen() {

}

@Composable
fun PingScreen() {
    var host by remember { mutableStateOf("") }
    val count by remember { mutableStateOf(4) }
    var isPinging by remember { mutableStateOf(false) }
    val outputState = remember { mutableStateOf<List<String>>(emptyList()) }
    val pingJob = remember { mutableStateOf<Job?>(null) }

    val receivedScaffoldState = rememberScaffoldState()

    LaunchedEffect(receivedScaffoldState) {
        receivedScaffoldState.drawerState.close()
    }

    Column(modifier = Modifier.padding(1.dp)) {
        TextField(
            value = host,
            onValueChange = { host = it },
            label = { Text("Host or IP Address", fontSize = 12.sp) },
            textStyle = TextStyle(fontSize = 20.sp),
            modifier = Modifier
                .fillMaxWidth()
        )

        Box(
            modifier = Modifier
                .height(450.dp)
                .width(500.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                items(outputState.value) { line ->
                    Text(line, fontSize = 14.sp)

                }
            }
        }
        Row(
            modifier = Modifier
                .padding(50.dp)
                .align(Alignment.CenterHorizontally)

        ) {
            Button(
                onClick = {
                    isPinging = !isPinging
                    if (isPinging) {
                        pingJob.value = CoroutineScope(Dispatchers.IO).launch {
                            ping(host, count, outputState)
                        }
                    } else {
                        pingJob.value?.cancel()
                    }
                },
                modifier = Modifier
                    .padding(5.dp)
                    .width(200.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF2196F3)
                )
            ) {

                Text("Ping")
            }
        }

    }
}


@SuppressLint("SetJavaScriptEnabled")
@Composable
fun SpeedTestScreen() {
    val mUrl = "https://karinamartinssoft.github.io/TesteVelocidade/"

    val webViewRef = remember { mutableStateOf<WebView?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    settings.javaScriptEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView?, url: String?) {
                            super.onPageFinished(view, url)
                            isLoading.value = false
                        }

                        override fun shouldOverrideUrlLoading(
                            view: WebView?,
                            request: WebResourceRequest?
                        ): Boolean {
                            return true // Retorna true para ignorar o clique no link
                        }
                    }
                    loadUrl(mUrl)
                    webViewRef.value = this
                }
            }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .size(50.dp)
                .padding(bottom = 100.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(color = Color(0xFF2196F3))
            } else {
                Button(
                    onClick = {
                        webViewRef.value?.reload()
                    }, colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF2196F3)
                    )
                ) {
                    Text("Recarregar")
                }
            }
        }
    }
}

@Composable
fun LocalAreaNetworkScreen() {

}

@Composable
fun GeoPingScreen() {

}

@Composable
fun TracerRouteScreen() {

}

@Composable
fun ScannerWifiScreen() {

}



















