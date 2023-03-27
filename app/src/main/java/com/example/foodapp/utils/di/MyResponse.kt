package com.example.foodapp.utils.di

class MyResponse<out T>(val status: Status, val data: T? = null, val message: String? = null) {

    enum class Status { LOADING, SUCCESS, ERROR }

    companion object {
        fun <T> loading(): MyResponse<T> {
            return MyResponse(Status.LOADING)
        }

        fun <T> success(data: T?): MyResponse<T> {
            return MyResponse(Status.SUCCESS, data)
        }

        fun <T> error(error: String): MyResponse<T> {
            return MyResponse(Status.ERROR, message = error)
        }
    }
}