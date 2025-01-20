package io.haerong22.springwebutils.exception

import io.haerong22.springwebutils.response.ResponseCode

class CustomException(
    val responseCode: ResponseCode
): RuntimeException(responseCode.message)