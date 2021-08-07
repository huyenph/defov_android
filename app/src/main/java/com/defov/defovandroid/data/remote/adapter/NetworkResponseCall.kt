package com.defov.defovandroid.data.remote.adapter

import okhttp3.Request
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response
import java.io.IOException
import java.lang.UnsupportedOperationException

class NetworkResponseCall<S : Any, E : Any>(
    private val _delegate: Call<S>,
    private val _errorConverter: Converter<ResponseBody, E>
) : Call<NetworkResponse<S, E>> {

    override fun isExecuted(): Boolean = _delegate.isExecuted

    override fun cancel() = _delegate.cancel()

    override fun isCanceled(): Boolean = _delegate.isCanceled

    override fun request(): Request = _delegate.request()

    override fun enqueue(callback: Callback<NetworkResponse<S, E>>) =
        _delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()
                if (response.isSuccessful) {
                    if (body != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Success(code, body))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.UnknownError(code))
                        )
                    }
                } else {
                    val errorBody = when {
                        error == null -> null
                        error.contentLength() == 0L -> null
                        else -> try {
                            _errorConverter.convert(error)
                        } catch (e: Exception) {
                            null
                        }
                    }
                    if (errorBody != null) {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.Failure(code, errorBody))
                        )
                    } else {
                        callback.onResponse(
                            this@NetworkResponseCall,
                            Response.success(NetworkResponse.UnknownError(code))
                        )
                    }
                }
            }

            override fun onFailure(call: Call<S>, t: Throwable) {
                val networkResponse = when (t) {
                    is IOException -> NetworkResponse.NetworkError(500, t)
                    else -> NetworkResponse.UnknownError(400, t)
                }
                callback.onResponse(
                    this@NetworkResponseCall,
                    Response.success(networkResponse)
                )
            }
        })

    override fun clone(): Call<NetworkResponse<S, E>> =
        NetworkResponseCall(_delegate.clone(), _errorConverter)

    override fun execute(): Response<NetworkResponse<S, E>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }
}