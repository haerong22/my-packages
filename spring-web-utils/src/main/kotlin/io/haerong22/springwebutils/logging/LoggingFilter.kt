package io.haerong22.springwebutils.logging

import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.filter.AbstractRequestLoggingFilter
import java.util.UUID

class LoggingFilter(
    private val ignorePaths: Set<String> = emptySet(),
    private val includeHeaders: Set<String> = LoggingOptions.defaultHeaders(),
    loggingOptions: LoggingOptions = LoggingOptions(),
) : AbstractRequestLoggingFilter() {

    init {
        setBeforeMessagePrefix("")
        setBeforeMessageSuffix("")
        setAfterMessagePrefix("")
        setAfterMessageSuffix("")
        isIncludeQueryString = loggingOptions.queryString
        isIncludeHeaders = loggingOptions.header
        isIncludePayload = loggingOptions.payload
        maxPayloadLength = loggingOptions.maxPayloadLength
    }

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun shouldLog(request: HttpServletRequest): Boolean {
        return !ignorePaths.contains(request.requestURI)
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        val requestId = UUID.randomUUID().toString().replace("-", "")
        MDC.put("requestId", requestId)
        request.setAttribute("requested_at", System.currentTimeMillis())
        request.inputStream.readBytes()
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        log.info(message)
    }

    override fun createMessage(request: HttpServletRequest, prefix: String, suffix: String): String {
        val msg = StringBuilder()
        msg.append("\n")

        msg.append(prefix)
        msg.append("${request.method} ${request.requestURI}")

        if (this.isIncludeQueryString) {
            val queryString = request.queryString
            if (queryString != null) {
                msg.append("?${queryString}")
            }
        }

        msg.append("\n")

        if (this.isIncludeHeaders) {
            msg.append("headers=[")
            if (this.includeHeaders.isNotEmpty()) {
                val headers = request.headerNames.toList()
                    .filter { headerName -> this.includeHeaders.any { it.lowercase() == headerName } }
                    .joinToString(", ") { "$it: ${request.getHeader(it)}" }

                msg.append(headers)
            }
            msg.append("]")
        }

        msg.append("\n")

        if (this.isIncludePayload) {
            val payload = this.getMessagePayload(request)
            if (payload != null) {
                msg.append(payload)
                msg.append("\n")
            }
        }

        msg.append(request.remoteAddr)
        request.getAttribute("requested_at")?.let {
            val responseTime = System.currentTimeMillis() - it as Long
            msg.append(" (${responseTime}ms)")
        }

        msg.append(suffix)
        return msg.toString()
    }
}