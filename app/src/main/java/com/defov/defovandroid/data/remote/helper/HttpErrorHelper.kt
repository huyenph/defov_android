package com.defov.defovandroid.data.remote.helper

import com.defov.defovandroid.domain.model.BaseModel
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.Response

class HttpErrorHelper : BaseModel() {
    @SerializedName("message")
    var message: String? = null
        get() = if (field.isNullOrEmpty()) "fail" else field

    @SerializedName("status_code")
    var statusCode: Int = 0

    companion object {
        fun parseData(json: String): HttpErrorHelper {
            var httpError: HttpErrorHelper
            try {
                httpError = Gson().fromJson(json, HttpErrorHelper::class.java)
                if (httpError != null) {
                    return httpError
                }
            } catch (e: Exception) {
                httpError = HttpErrorHelper()
                httpError.message = json
            }
            return httpError
        }

        fun getErrorString(response: Response): String {
            val httpError = HttpErrorHelper()
            httpError.statusCode = response.code
            if (response.body != null) {
                val body = response.body.toString()
                if (!body.startsWith("<!DOCTYPE HTML>")) {
                    httpError.message = body
                } else {
                    httpError.message = ""
                }
            }
            return httpError.toString()
        }

        override fun toString(): String = Gson().toJson(this)
    }
}