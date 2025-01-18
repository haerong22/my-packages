package io.haerong22.springwebutils.logging

import io.haerong22.springwebutils.utils.HttpRequestUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.MDC
import org.springframework.web.filter.AbstractRequestLoggingFilter
import java.util.UUID

class LoggingFilter(
    private val ignorePaths: Set<String> = emptySet(),
) : AbstractRequestLoggingFilter() {

    private val log: Logger = LoggerFactory.getLogger(javaClass)

    override fun shouldLog(request: HttpServletRequest): Boolean {
        return !ignorePaths.contains(request.requestURI)
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        super.doFilterInternal(ReadableRequestWrapper(request), response, filterChain)
    }

    override fun beforeRequest(request: HttpServletRequest, message: String) {
        val requestId = UUID.randomUUID().toString().replace("-", "")
        MDC.put("requestId", requestId)
        request.setAttribute("requested_at", System.currentTimeMillis())
    }

    override fun afterRequest(request: HttpServletRequest, message: String) {
        log.info(buildLogMessage(request))
    }

    private fun buildLogMessage(request: HttpServletRequest): String {
        val requestUtils = HttpRequestUtils(request)

        val message = StringBuilder()
        message.append("${requestUtils.getRequestUri()}\n")
        message.append("${requestUtils.getBody()}\n")
        message.append("${requestUtils.getUserAgent()} [ ${requestUtils.getRemoteIp()}(${requestUtils.getResponseTime()}ms) ]")
        return message.toString()
    }
}