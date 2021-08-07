package com.defov.defovandroid.data.remote.response

import com.defov.defovandroid.domain.model.BaseModel
import com.google.gson.annotations.SerializedName

// For general error response
data class ErrorResponse(
    @SerializedName("error_id") val errorId: Int = 0,
    @SerializedName("error_message") val errorMessage: String? = null,
    @SerializedName("error_name") val errorName: String? = null
) : BaseModel()
