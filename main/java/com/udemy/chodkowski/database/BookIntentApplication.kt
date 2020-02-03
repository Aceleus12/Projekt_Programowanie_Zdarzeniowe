package com.udemy.chodkowski.database

import android.app.Application
import com.udemy.chodkowski.BookRepository

class BookIntentApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        BookRepository.initialize(this)
    }
}