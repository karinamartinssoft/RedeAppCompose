package com.example.redeappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.redeappcompose.ui.theme.RedeAppComposeTheme
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }

            RedeAppComposeTheme(isDarkTheme = isDarkTheme,
                onToggleTheme = { isDarkTheme = !isDarkTheme }) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IpAddressScreen(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = { isDarkTheme = !isDarkTheme })
                }
            }
        }
    }
}

@Composable
fun IpAddressScreen(isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    var host by remember { mutableStateOf("") }
    var output by remember { mutableStateOf("") }


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

                Button(onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        val pingResult = ping(host, 1)
                        pingResult.collect { line ->
                            withContext(Dispatchers.Main) {
                                output += "$line\n"
                            }
                            delay(1000L)


                        }

                    }
                }, modifier = Modifier.width(IntrinsicSize.Min).padding(16.dp)) {
                    Text("Ping")
                }
            }
            Text(
                output, modifier = Modifier
                    .height(400.dp)
                    .verticalScroll(rememberScrollState())
            )


        }
    }
}

//fazer uma lista para pingar sequencialmente
suspend fun ping(host: String, count: Int) = flow {
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

}


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




















