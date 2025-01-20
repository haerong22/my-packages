package io.haerong22.springwebutils.response

import org.springframework.http.HttpStatus

enum class CommonResponseCode(
    override val httpStatus: HttpStatus,
    override val code: Int,
    override val message: String
) : ResponseCode {

    SUCCESS(HttpStatus.OK, 0, "success"),
    FAIL(HttpStatus.OK, -1, "fail"),
    UNEXPECTED_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, -1, "unexpected"),
}