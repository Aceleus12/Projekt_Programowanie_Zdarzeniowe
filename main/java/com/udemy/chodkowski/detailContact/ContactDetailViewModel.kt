package com.udemy.chodkowski.detailContact

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.udemy.chodkowski.BookRepository
import com.udemy.chodkowski.Contact
import java.util.*

class ContactDetailViewModel() : ViewModel() {

    private val bookRepository = BookRepository.get()
    private val contactIdLiveData = MutableLiveData<UUID>()

    var contactLiveData: LiveData<Contact?> =
        Transformations.switchMap(contactIdLiveData) { contactId ->
            bookRepository.getContact(contactId)
        }

    fun loadContact(contactId: UUID) {
        contactIdLiveData.value = contactId
    }

    fun saveContact(contact: Contact) {
        bookRepository.updateContact(contact)
    }

    fun removeContact(contact: Contact){
        bookRepository.removeContact(contact)
    }

}