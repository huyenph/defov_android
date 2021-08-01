package com.defov.defovandroid.data.local.storage

interface Storage {
    fun setString(key: String, value: String)
    fun getString(key: String): String
    fun setInt(key: String, value: Int)
    fun getInt(key: String): Int
    fun setFloat(key: String, value: Float)
    fun getFloat(key: String): Float
    fun setLong(key: String, value: Long)
    fun getLong(key: String): Long
    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String): Boolean
}