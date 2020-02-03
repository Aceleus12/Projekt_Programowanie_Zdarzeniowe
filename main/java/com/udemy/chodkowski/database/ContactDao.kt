package com.udemy.chodkowski.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udemy.chodkowski.Contact
import java.util.*

@Dao
interface ContactDao {

    @Query("SELECT * FROM contact")
    fun getContacts(): LiveData<List<Contact>>

    @Query("SELECT * FROM contact WHERE id=(:id)")
    fun getContact(id: UUID): LiveData<Contact?>

    @Update
    fun updateContact(contact: Contact)

    @Insert
    fun addContact(contact: Contact)

    //@Query("DELETE FROM contact WHERE id =(:id)")
    @Delete
    fun removeContact(contact: Contact)
}