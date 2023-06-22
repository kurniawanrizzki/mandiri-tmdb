package com.mandiri.tmdb.data.common

import android.annotation.SuppressLint
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

@SuppressLint("SimpleDateFormat")
class UTCAdapter : JsonSerializer<Date>, JsonDeserializer<Date> {
    private val formatter by lazy {
        SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        ).also { it.timeZone = TimeZone.getTimeZone("UTC") }
    }

    override fun serialize(
        src: Date?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement = JsonPrimitive(formatter.format(src ?: Date()))

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ) = try {
        val text = json?.asString ?: ""
        formatter.parse(text) ?: Date()
    } catch (e: ParseException) {
        throw JsonParseException(e)
    }
}