package com.example.redeappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.redeappcompose.ui.theme.RedeAppComposeTheme
import kotlinx.coroutines.*




class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            RedeAppComposeTheme(isDarkTheme = isDarkTheme, onToggleTheme = {isDarkTheme = !isDarkTheme}) {

                App(isDarkTheme = isDarkTheme, onToggleTheme = {isDarkTheme = !isDarkTheme})

               /* IpAddressScreen(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme }*/



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
                    modifier = Modifier.padding(start = 8.dp, end = 16.dp)
                )
                Text(
                    text = "Home",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
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
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
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
                    text = "Speed",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
            }
        }
        Box(modifier = Modifier
            .align(Alignment.Start)
            .padding(16.dp)) {
            Button(onClick = { onToggleTheme() }) {
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
                        title = { Text("Rede App") },
                        navigationIcon = {
                            IconButton(
                                onClick = { scope.launch { drawerState.open() } }
                            ) {
                                Icon(Icons.Filled.Menu, contentDescription = "Open Drawer")
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
            .background(color = Color(0xFF6200EE))
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

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Ping : Screen("ping")
    object SpeedTest : Screen("speedtest")
}


/*fun IpAddressScreen(isDarkTheme: Boolean, onToggleTheme: () -> Unit){

    Box(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Button(modifier = Modifier.align(alignment = Alignment.Start),
                onClick = { onToggleTheme() }) {
                Text(text = if (isDarkTheme) "Tema Escuro" else "Tema Claro")
            }
        }
    }
}*/

@Composable
fun HomeScreen(){

}

@Composable
fun PingScreen(){

}

@Composable
fun SpeedTestScreen() {

}






/*@Composable
fun IpAddressScreen(isDarkTheme: Boolean, onToggleTheme: () -> Unit) {

    Box(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Button(modifier = Modifier.align(alignment = Alignment.Start),
                onClick = { onToggleTheme() }) {
                Text(text = if (isDarkTheme) "Tema Escuro" else "Tema Claro")} }}}*/
    /*var host by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }
    var isPinging by remember { mutableStateOf(false) }
    val outputState = remember { mutableStateOf(output) }
    val pingJob = remember { mutableStateOf<Job?>(null) }




    Box(modifier = Modifier.padding(16.dp)) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Button(modifier = Modifier.align(alignment = Alignment.Start),
                onClick = { onToggleTheme() }) {
                Text(text = if (isDarkTheme) "Tema Escuro" else "Tema Claro")
            }
            Image(
                painter = painterResource(id = R.drawable.technology),
                contentDescription = "Wifi Ip Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(50.dp)
            )


            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                OutlinedTextField(
                    value = host, onValueChange = { host = it },
                    label = { Text("Host or IP Address") },
                    modifier = Modifier.weight(1f)

                )

                Button(
                    onClick = {
                        isPinging = !isPinging
                        if (isPinging) {
                            isPinging = true
                            pingJob.value = CoroutineScope(Dispatchers.IO).launch {
                                pingContinuously(host, outputState)


                            }

                        } else {
                            pingJob.value?.cancel()
                            isPinging = false
                            outputState.value = "Ping interrompido.\n"
                        }
                    }, modifier = Modifier
                        .width(IntrinsicSize.Min)
                        .padding(16.dp)
                ) {
                    Text(if (isPinging) "Parar Ping" else "Iniciar Ping")
                }
            }
            Text(
                outputState.value, modifier = Modifier
                    .height(400.dp)
                    .verticalScroll(rememberScrollState())
            )


        }
    }
*/



/*suspend fun pingContinuously(
    host: String,
    output: MutableState<String>
) {
    output.value = "Disparando $host:\n"
    var sent = 0
    var received = 0
    try {
        while (true) {
            val process = ProcessBuilder("ping", "-c", "1", host).start()
            val input = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (input.readLine().also { line = it } != null) {
                output.value += "$line\n"
            }
            input.close()
            if (output.value.endsWith("64 bytes from $host")) {
                received++
            }
            sent++
            delay(1000L)
        }
    } catch (ex: Exception) {
        output.value += "Failed to ping $host: ${ex.message}\n"
    }
}*/



// pingar 4 vezes e mostrar o resultado dos 4 pings na tela
/*suspend fun ping(host: String, count: Int) = flow {
    val output = mutableListOf<String>()
    var sent = 0
    var received = 0
    try {
        emit("Disparando $host: ")
        for (i in 1..count) {
            val process = ProcessBuilder("ping", "-c", "4", host).start()
            val input = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (input.readLine().also { line = it } != null) {
                output.add(line!!)
            }
            input.close()
            if (output.last().startsWith("64 bytes from")) {
                received++
            }
            sent++



            output.forEach {
                emit(it)
            }


        }
    } catch (ex: Exception) {
        emit("Failed to ping $host: ${ex.message}\n")
    }

}*/

//função responsavel por recuperar o endereço IP da rede wifi à qual o dispositivo está conectado.

/*fun getWifiIpAddress(wifiManager: WifiManager): String? {  // a função utiliza a classe WifiManager da API do Android, que permite obter informações sobre a conexão atual
    val ipAddress = wifiManager.connectionInfo.ipAddress
    return if (ipAddress == 0) {  // validação que verifica se o wifi está habilitado ou não, retornando null caso não estiver
        null
    } else {
        val ip = InetAddress.getByAddress(
            byteArrayOf( //utilizado o Array de bytes a partir de 4 valores de bytes que representam o endereço de IP em formato hexadecimal. Esses valores são obtidos atraves do WIFIINFO do dispositivo Android, em seguida utilizado o método InetAddress para criar um objeto a partir do array de bytes representando o endereço IP
                (ipAddress and 0xff).toByte(),
                (ipAddress shr 8 and 0xff).toByte(),
                (ipAddress shr 16 and 0xff).toByte(),
                (ipAddress shr 24 and 0xff).toByte()
            )
        )
        ip.hostAddress
    }
}*/

/*fun getPingTime(ipAddress: String?): String {
    if (ipAddress == null) return ""
    val process = Runtime.getRuntime().exec("/system/bin/ping -c 1 $ipAddress")
    val input = BufferedReader(InputStreamReader(process.inputStream))
    val output = StringBuffer()
    var line: String? = ""

    while ((input.readLine().also { line = it }) != null) {
        output.append(line)
    }

    val matcher: Matcher = Pattern.compile("time=(\\d+\\.\\d+|\\d+)").matcher(output.toString())
    return if (matcher.find()) {
        "Ping Time: ${matcher.group(1)} ms"
    } else {
        "Ping Failed"
    }
}*/


//função compose para criar uma navigation bar
/*bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route
                            val items = listOf(
                                Screen.Home,
                                Screen.Tela1,
                                Screen.Tela2
                            )
                            items.forEach { screen ->
                                BottomNavigationItem(
                                    icon = { Icon(screen.icon, contentDescription = null) },
                                    label = { Text(screen.title) },
                                    selected = currentRoute == screen.route,
                                    onClick = {
                                        navController.navigate(screen.route) {

                                            popUpTo(navController.graph.startDestinationRoute!!) {
                                                saveState = true
                                            }

                                            launchSingleTop = true
                                        }
                                    }
                                )
                            }
                        }
                    },
                    content = { paddingValues ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues)
                        ) {
                            NavGraph(startDestination = Screen.Home)
                        }
                    }
                )*/


















