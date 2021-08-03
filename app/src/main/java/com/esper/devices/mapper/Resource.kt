package com.esper.devices.mapper

/**
 * A generic class that describes data with a status
 */
data class Resource(val status: Status, val message: String?) {
    companion object {
        fun success(): Resource = Resource(status = Status.SUCCESS, message = "Loading successful!")

        fun error(message: String): Resource =
            Resource(status = Status.ERROR, message = message)

        fun loading(): Resource = Resource(status = Status.LOADING, message = "Loading data... Please Wait")
    }
}

enum class Status {
    SUCCESS, ERROR, LOADING
}