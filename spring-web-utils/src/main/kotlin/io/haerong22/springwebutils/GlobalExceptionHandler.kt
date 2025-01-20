package io.haerong22.springwebutils

import io.haerong22.springwebutils.exception.CustomException
import io.haerong22.springwebutils.response.CommonResponse
import io.haerong22.springwebutils.response.CommonResponseCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ResponseEntity<CommonResponse<Unit>> {
        val error = e.responseCode
        return ResponseEntity.status(error.httpStatus).body(CommonResponse.fail(error))
    }

    @ExceptionHandler(Exception::class)
    fun handleUnexpectedException(e: Exception): ResponseEntity<CommonResponse<Unit>> {
        val error = CommonResponseCode.UNEXPECTED_ERROR
        return ResponseEntity.status(error.httpStatus).body(CommonResponse.fail(error))
    }
}