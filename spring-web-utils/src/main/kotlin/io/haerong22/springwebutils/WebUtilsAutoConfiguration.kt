package io.haerong22.springwebutils

import io.haerong22.springwebutils.logging.LoggingFilter
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean

@AutoConfiguration
class WebUtilsAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    fun loggingFilter(): LoggingFilter = LoggingFilter()
}