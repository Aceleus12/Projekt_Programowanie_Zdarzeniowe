package com.udemy.chodkowski.database

import androidx.room.TypeConverter
import java.util.*


class ContactTypeConverters {


    @TypeConverter
    fun toUUID(uuid: String?): UUID? {
        return UUID.fromString(uuid)
    }

    @TypeConverter
    fun fromUUID(uuid: UUID?): String? {
        return uuid?.toString()
    }
}