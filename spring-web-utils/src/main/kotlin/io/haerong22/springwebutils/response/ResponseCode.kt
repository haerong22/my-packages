package io.haerong22.springwebutils.response

import org.springframework.http.HttpStatus

interface ResponseCode {
    val httpStatus: HttpStatus
    val code: Int
    val message: String
}