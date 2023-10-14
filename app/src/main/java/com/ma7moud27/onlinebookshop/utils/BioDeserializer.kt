package com.ma7moud27.onlinebookshop.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.ma7moud27.onlinebookshop.model.author.Bio
import java.lang.reflect.Type

class BioDeserializer : JsonDeserializer<Bio> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): Bio {
        return if (json.isJsonObject) {
            val jsonObject = json.asJsonObject
            val key = jsonObject.keySet().lastOrNull()
            val value = jsonObject.get(key).asString
            Bio(value)
        } else {
            val value = json.asString
            Bio(value)
        }
    }
}
