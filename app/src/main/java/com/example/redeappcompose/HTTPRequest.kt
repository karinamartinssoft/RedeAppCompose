package com.example.redeappcompose

import okhttp3.*
import java.io.IOException

class HTTPRequest(private val url: String) {
    private val client = OkHttpClient()

    fun executeRequest(onSuccess: (String) -> Unit, onError: (Exception) -> Unit) {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                onError(e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    onSuccess(responseBody ?: "")
                } else {
                    onError(IOException("Unexpected response code: ${response.code}"))
                }
            }
        })
    }
}

fun main() {
    val url = "https://dallas10.testmy.net/b/dl-24185&nfw=1"
    val request = HTTPRequest(url)

    request.executeRequest(
        onSuccess = { response ->
            println("Success! Response: $response")
        },
        onError = { exception ->
            println("Error: ${exception.message}")
        }
    )
}