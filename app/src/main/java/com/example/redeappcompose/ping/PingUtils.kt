package com.example.redeappcompose

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.InetAddress
import kotlin.math.pow
import kotlin.math.sqrt

//arquivo para colocar a lógica do Ping

suspend fun ping(host: String, count: Int, output: MutableState<List<String>>) {
    output.value = emptyList()
    output.value += "PING $host (${InetAddress.getByName(host).hostAddress}): 56(84) bytes of data."
    var sent = 0
    var received = 0
    val rttList = mutableListOf<Double>()
    try {
        while (sent < count) {
            val process = ProcessBuilder("ping", "-c", "1", host).start()
            val input = BufferedReader(InputStreamReader(process.inputStream))
            var line: String?
            while (input.readLine().also { line = it } != null) {
                if (line!!.startsWith("64 bytes from") || line!!.startsWith("rtt")) {
                    output.value += "$line\n"
                }
            }
            input.close()
            if (line != null && line!!.contains("64 bytes from")) {
                received++
                val matchResult = Regex("time=(\\d+\\.\\d+)").find(line!!)
                matchResult?.groupValues?.get(1)?.toDoubleOrNull()?.let { rttList.add(it) }
            }
            sent++
            delay(2000L)
        }
        output.value += "--- $host ping statistics ---"
        output.value += "$sent packets transmitted, $received received, ${((sent - received) / sent.toFloat() * 100).toInt()}% packet loss, time ${sent * 2000}ms"
        if (rttList.isNotEmpty()) {
            val minRtt = rttList.minOrNull()
            val avgRtt = rttList.average()
            val maxRtt = rttList.maxOrNull()
            val mdevRtt = rttList.map { rtt -> (rtt - avgRtt).pow(2) }.average().let { sqrt(it) }
            output.value += "rtt min/avg/max/mdev = ${"%.3f".format(minRtt)}/${"%.3f".format(avgRtt)}/${"%.3f".format(maxRtt)}/${"%.3f".format(mdevRtt)} ms"
        }
    } catch (ex: Exception) {
        output.value += "Falha ao pingar $host: ${ex.message}"
    }
}