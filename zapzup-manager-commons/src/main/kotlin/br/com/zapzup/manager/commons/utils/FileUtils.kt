package br.com.zapzup.manager.commons.utils

import java.io.ByteArrayOutputStream
import java.util.zip.Deflater
import java.util.zip.Inflater

object FileUtils {

    fun compressBytes(data: ByteArray): ByteArray {
        val deflate = Deflater()
        deflate.setInput(data)
        deflate.finish()

        val outputStream = ByteArrayOutputStream(data.size)
        val buffer = ByteArray(size = 1024)

        while (!deflate.finished()) {
            val count = deflate.deflate(buffer)
            outputStream.write(buffer, 0, count)
        }

        outputStream.close()

        return outputStream.toByteArray()
    }

    fun decompressBytes(data: ByteArray?): ByteArray? {
        data ?: return null

        val inflater = Inflater()
        inflater.setInput(data)
        val outputStream = ByteArrayOutputStream(data.size)
        val buffer = ByteArray(1024)

        while (!inflater.finished()) {
            val count = inflater.inflate(buffer)
            outputStream.write(buffer, 0, count)
        }

        outputStream.close()

        return outputStream.toByteArray()
    }
}