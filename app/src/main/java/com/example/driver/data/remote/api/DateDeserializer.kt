package com.example.driver.data.remote.api

import com.example.driver.common.parseToApiDateTime
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateDeserializer @Inject constructor(): JsonDeserializer<Date> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Date? {
        return json?.asString?.parseToApiDateTime()
    }
}