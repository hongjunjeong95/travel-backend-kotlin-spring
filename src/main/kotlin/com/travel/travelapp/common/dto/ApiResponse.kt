package com.travel.travelapp.common.dto


data class ApiResponse<Data>(
    val status: ApiStatus,
    val message: String?,
    val data: Data?
) {
    companion object {
        fun <Data>success(data: Data?): ApiResponse<Data> {
            return ApiResponse(ApiStatus.SUCCESS, null, data)
        }

        fun <Data>error(message: String?): ApiResponse<Data> {
            return ApiResponse(ApiStatus.ERROR, message, null)
        }
    }
}

enum class ApiStatus {
    SUCCESS,
    ERROR
}