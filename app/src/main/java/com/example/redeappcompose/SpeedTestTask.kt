import fr.bmartel.speedtest.SpeedTestReport
import fr.bmartel.speedtest.model.SpeedTestMode
import java.math.BigDecimal

class SpeedTestTask() {
    interface ISpeedTestListener {
        fun onCompletion(report: SpeedTestReport)
        fun onError(speedTestError: SpeedTestError, errorMessage: String)
        fun onProgress(percent: Float, report: SpeedTestReport)
    }

    enum class SpeedTestError {
        CUSTOM_ERROR
    }

    fun execute(listener: ISpeedTestListener) {
        // Simulando o teste de velocidade com um atraso de 3 segundos
        Thread.sleep(3000)

        // Supondo que o teste de velocidade tenha sido concluído com sucesso
        val velocidadeMbps = 10.0 // Valor de exemplo

        val transferRateOctet = BigDecimal.valueOf(10.0) // Velocidade de transferência em octetos
        val transferRateBit = transferRateOctet.multiply(BigDecimal.valueOf(8)) // Velocidade de transferência em bits

        val report = SpeedTestReport(
            SpeedTestMode.DOWNLOAD, // Modo do teste de velocidade (DOWNLOAD, UPLOAD ou PING)
            100f, // Percentual de progresso do teste (0 a 100)
            System.currentTimeMillis(), // Tempo de início do teste (timestamp)
            System.currentTimeMillis(), // Tempo de criação do relatório (timestamp)
            0L, // Tamanho temporário do pacote
            0L, // Tamanho total do pacote
            transferRateOctet, // Taxa de transferência em octetos
            transferRateBit, // Taxa de transferência em bits
            0 // Número de solicitações
        )
    }
}