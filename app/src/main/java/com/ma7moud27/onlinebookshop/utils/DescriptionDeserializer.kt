package com.ma7moud27.onlinebookshop.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.ma7moud27.onlinebookshop.model.work.Description
import java.lang.reflect.Type

class DescriptionDeserializer : JsonDeserializer<Description> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): Description {
        return if (json.isJsonObject) {
            val jsonObject = json.asJsonObject
            val key = jsonObject.keySet().lastOrNull()
            val value = jsonObject.get(key).asString
            Description(value)
        } else {
            val value = json.asString
            Description(value)
        }
    }
}
