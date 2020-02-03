package com.udemy.chodkowski

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.udemy.chodkowski.database.BookDatabase
import java.lang.IllegalStateException
import java.util.*
import java.util.concurrent.Executors

private const val DATABASE_NAME = "book-database"

class BookRepository private constructor(context: Context) {

    private val database: BookDatabase = Room.databaseBuilder(
        context.applicationContext,
        BookDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val contactDao = database.contactDao()
    private val executor = Executors.newSingleThreadExecutor()

    fun getContacts(): LiveData<List<Contact>> = contactDao.getContacts()

    fun getContact(id: UUID): LiveData<Contact?> = contactDao.getContact(id)

    fun updateContact(contact: Contact) {
        executor.execute {
            contactDao.updateContact(contact)
        }
    }

    fun removeContact(contact: Contact){
        executor.execute{
            contactDao.removeContact(contact)
        }
    }

    fun addContact(contact: Contact) {
        executor.execute {
            contactDao.addContact(contact)
        }
    }

    companion object {

        private var INSTANCE: BookRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = BookRepository(context)
            }
        }

        fun get(): BookRepository {
            return INSTANCE ?: throw IllegalStateException("BookRepository must be initialized")
        }
    }
}