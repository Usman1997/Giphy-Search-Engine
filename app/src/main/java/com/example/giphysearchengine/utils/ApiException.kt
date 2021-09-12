package com.example.giphysearchengine.utils

import java.io.IOException

sealed class ApiException(message: String? = null) : IOException(message) {

    //These classes and errors are according to the the documentation of GIPHY
    //https://developers.giphy.com/docs/api#response-codes
    //You can create your own exception here and handle it accordingly as per
    // the requirements of your business

    data class ServerError(override val message: String?) : ApiException(message)

    data class ClientError(override val message: String?)  : ApiException(message)

    object Forbidden : ApiException()

    object BadRequest : ApiException()

    object NotFound: ApiException()

    object TooManyRequest: ApiException()

}