package com.ma7moud27.shelfy.utils.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.ma7moud27.shelfy.model.book.Notes
import java.lang.reflect.Type

class NoteDeserializer : JsonDeserializer<Notes> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext,
    ): Notes {
        return if (json.isJsonObject) {
            val jsonObject = json.asJsonObject
            val key = jsonObject.keySet().lastOrNull()
            val value = jsonObject.get(key).asString
            Notes(value)
        } else {
            val value = json.asString
            Notes(value)
        }
    }
}
