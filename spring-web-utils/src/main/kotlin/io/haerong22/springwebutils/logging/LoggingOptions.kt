package io.haerong22.springwebutils.logging

class LoggingOptions(
    val queryString: Boolean = true,
    val header: Boolean = true,
    val payload: Boolean = true,
    val maxPayloadLength: Int = 10000,
) {
    companion object {
        fun defaultHeaders(): Set<String> {
            return setOf(
                "User-Agent",
                "Authorization",
            )
        }
    }
}