package com.udemy.chodkowski.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.udemy.chodkowski.Contact

@Database(entities = [ Contact::class], version=1, exportSchema = false)
@TypeConverters(ContactTypeConverters::class)
abstract class BookDatabase : RoomDatabase(){

    abstract fun contactDao(): ContactDao
}