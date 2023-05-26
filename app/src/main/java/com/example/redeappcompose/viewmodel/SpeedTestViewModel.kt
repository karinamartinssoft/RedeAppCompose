package com.example.redeappcompose.viewmodel

import SpeedTestTask
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.bmartel.speedtest.SpeedTestReport
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class SpeedTestViewModel : ViewModel() {
    private val speedTestTask = SpeedTestTask() // Declaração da variável speedTestTask

    fun testarVelocidade(
        coroutineScope: CoroutineScope,
        onCompletion: (Double) -> Unit,
        onError: (String) -> Unit
    ) {
        coroutineScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    startSpeedTest(onCompletion, onError)
                }
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
            }
        }
    }

    private fun startSpeedTest(
        onCompletion: (Double) -> Unit,
        onError: (String) -> Unit
    ) {
        val listener = object : SpeedTestTask.ISpeedTestListener {
            override fun onCompletion(report: SpeedTestReport) {
                val velocidadeString: BigDecimal? = report.transferRateBit
                val velocidadeMbps = if (velocidadeString != null) {
                    try {
                        velocidadeString.toDouble() / 1_000_000
                    } catch (e: NumberFormatException) {
                        0.0
                    }
                } else {
                    0.0
                }
                onCompletion(velocidadeMbps)
            }

            override fun onError(
                speedTestError: SpeedTestTask.SpeedTestError,
                errorMessage: String
            ) {
                onError(errorMessage ?: "Erro desconhecido")
            }

            override fun onProgress(percent: Float, report: SpeedTestReport) {
                // Não utilizado neste exemplo
            }
        }

        viewModelScope.launch {
            try {
                speedTestTask.execute(listener)
            } catch (e: Exception) {
                onError(e.message ?: "Erro desconhecido")
            }
        }
    }
}