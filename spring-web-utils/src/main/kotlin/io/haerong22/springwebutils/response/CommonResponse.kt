package io.haerong22.springwebutils.response

class CommonResponse<T>(
    val code: Int,
    val message: String,
    val data: T?
) {

    companion object {
        fun <T> success(data: T?): CommonResponse<T> {
            return this.toResponse(CommonResponseCode.SUCCESS, data)
        }

        fun <T> success(responseCode: ResponseCode, data: T?): CommonResponse<T> {
            return this.toResponse(responseCode, data)
        }

        fun fail(): CommonResponse<Unit> {
            return this.fail(CommonResponseCode.FAIL)
        }

        fun fail(responseCode: ResponseCode): CommonResponse<Unit> {
            return this.toResponse(responseCode, null)
        }

        private fun <T> toResponse(responseCode: ResponseCode, data: T?): CommonResponse<T> {
            return CommonResponse(
                code = responseCode.code,
                message = responseCode.message,
                data = data
            )
        }
    }
}