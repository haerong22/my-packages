package io.haerong22.springwebutils.logging

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.InputStreamReader

class ReadableRequestWrapper(request: HttpServletRequest?) : HttpServletRequestWrapper(request) {
    private var cachedBody: ByteArray = request?.inputStream?.readBytes()!!

    override fun getInputStream(): ServletInputStream {
        return ByteArrayInputStream(cachedBody).let { byteArrayInputStream ->
            object : ServletInputStream() {
                override fun read(): Int = byteArrayInputStream.read()
                override fun isFinished(): Boolean = false
                override fun isReady(): Boolean = false
                override fun setReadListener(p0: ReadListener?) {}
            }
        }
    }

    override fun getReader(): BufferedReader {
        return BufferedReader(InputStreamReader(inputStream))
    }

    fun getRequestBody(): String {
        return String(cachedBody, Charsets.UTF_8)
    }
}