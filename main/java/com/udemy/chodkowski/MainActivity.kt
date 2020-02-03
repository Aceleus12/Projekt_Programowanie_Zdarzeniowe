package com.udemy.chodkowski

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udemy.chodkowski.bookList.BookListFragment
import com.udemy.chodkowski.detailContact.ContactDetailFragment
import java.util.*

class MainActivity: AppCompatActivity(), BookListFragment.Callbacks, ContactDetailFragment.Callbacks{

    override fun backToList() {
        val fragment = BookListFragment.newInstance()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment == null) {
            val fragment = BookListFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun onContactSelected(contactId: UUID) {
        val fragment = ContactDetailFragment.newInstance(contactId)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}