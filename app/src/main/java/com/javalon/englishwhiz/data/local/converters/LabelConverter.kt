package com.javalon.englishwhiz.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.javalon.englishwhiz.data.local.entity.Label
import com.javalon.englishwhiz.data.local.entity.Meaning

class LabelConverter {

    @TypeConverter
    fun toJson(labels: List<Label>): String {
        return Gson().toJson(labels)
    }

    @TypeConverter
    fun fromJson(jsonString: String): List<Label> {
        return Gson().fromJson(jsonString, object: TypeToken<List<Label>>() {}.type)
    }
}