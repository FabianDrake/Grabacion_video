package com.example.practica06_22110092

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.nio.ByteBuffer

class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

    private fun ByteBuffer.toByteArray(): ByteArray {
        rewind() // Reposicionar el buffer para leer desde el inicio
        val data = ByteArray(remaining())
        get(data)
        return data
    }

    override fun analyze(image: ImageProxy) {
        val buffer = image.planes[0].buffer
        val data = buffer.toByteArray()
        var lumaSum = 0.0
        for (byte in data) {
            lumaSum += (byte.toInt() and 0xFF)
        }
        val luma = lumaSum / data.size
        listener(luma)
        image.close()
    }
}
