package com.javalon.englishwhiz.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.javalon.englishwhiz.data.local.entity.Meaning

class SynonymConverter {

    @TypeConverter
    fun toJson(synonym: List<String>): String {
        return Gson().toJson(synonym)
    }

    @TypeConverter
    fun fromJson(jsonString: String): List<String> {
        return Gson().fromJson(jsonString, object: TypeToken<List<String>>() {}.type)
    }
}