package com.udemy.chodkowski.bookList

import androidx.lifecycle.ViewModel
import com.udemy.chodkowski.BookRepository
import com.udemy.chodkowski.Contact

class BookListViewModel : ViewModel(){

    private val bookRepository = BookRepository.get()
    val bookListLiveData = bookRepository.getContacts()

    fun addContact(contact: Contact) {
        bookRepository.addContact(contact)
    }



}