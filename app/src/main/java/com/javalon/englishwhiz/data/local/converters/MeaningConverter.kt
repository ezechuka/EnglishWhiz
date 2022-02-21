package com.javalon.englishwhiz.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.javalon.englishwhiz.data.local.entity.Meaning

class MeaningConverter {

    @TypeConverter
    fun fromJson(meaning: List<Meaning>): String {
        return Gson().toJson(meaning)
    }

    @TypeConverter
    fun fromJson(jsonString: String): List<Meaning> {
        return Gson().fromJson(jsonString, object: TypeToken<List<Meaning>>() {}.type)
            ?: emptyList()
    }
}