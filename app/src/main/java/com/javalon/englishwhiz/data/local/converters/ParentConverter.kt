package com.javalon.englishwhiz.data.local.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.javalon.englishwhiz.data.local.entity.Meaning
import com.javalon.englishwhiz.data.local.entity.Parent

class ParentConverter {

    @TypeConverter
    fun toJson(parent: Parent): String {
        return Gson().toJson(parent)
    }

    @TypeConverter
    fun fromJson(jsonString: String): Parent {
        return Gson().fromJson(jsonString, object: TypeToken<Parent>() {}.type)
    }
}