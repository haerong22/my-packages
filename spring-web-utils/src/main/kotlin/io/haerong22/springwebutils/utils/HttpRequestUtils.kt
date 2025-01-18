package io.haerong22.springwebutils.utils

import io.haerong22.springwebutils.logging.ReadableRequestWrapper
import jakarta.servlet.http.HttpServletRequest
import java.net.URLDecoder
import java.nio.charset.StandardCharsets


class HttpRequestUtils(
    private val request: HttpServletRequest,
) {

    fun getRequestUri(): String {
        val queryString = request.queryString
            ?.let { "?${URLDecoder.decode(it, StandardCharsets.UTF_8.name())}" }
            ?: ""
        return "${request.method} ${request.requestURI} $queryString"
    }

    fun getRemoteIp(): String {
        val headers = listOf(
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR"
        )
        return headers.firstNotNullOfOrNull { request.getHeader(it) } ?: request.remoteAddr
    }

    fun getUserAgent(): String = request.getHeader("User-Agent")

    fun getResponseTime(): Long {
        val requestTime = request.getAttribute("requested_at")
        val responseTime = System.currentTimeMillis() - requestTime as Long
        return responseTime
    }

    fun getBody(): String {
        val body = request as ReadableRequestWrapper
        return body.getRequestBody()
    }
}