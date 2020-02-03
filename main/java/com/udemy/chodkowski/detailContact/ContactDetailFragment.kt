package com.udemy.chodkowski.detailContact

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.Observer
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.udemy.chodkowski.Contact
import com.udemy.chodkowski.R
import java.util.*

private const val ARG_CONTACT_ID = "contact_id"

class ContactDetailFragment: Fragment(){

    interface Callbacks {
        fun backToList()
    }

    private var callbacks: Callbacks? = null
    private lateinit var contact: Contact
    private lateinit var nameField: EditText
    private lateinit var surnameField: EditText
    private lateinit var cityField: EditText
    private lateinit var streetField: EditText
    private lateinit var numberField: EditText



    private val contactDetailViewModel: ContactDetailViewModel by lazy {
        ViewModelProviders.of(this).get(ContactDetailViewModel::class.java)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callbacks = context as Callbacks
    }

    override fun onDetach() {
        super.onDetach()
        callbacks=null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contact = Contact()
        val contactId: UUID = arguments?.getSerializable(ARG_CONTACT_ID) as UUID
        contactDetailViewModel.loadContact(contactId)

        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_contact -> {
                contactDetailViewModel.removeContact(contact)
                callbacks?.backToList()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_contact_detail, container, false)

        nameField = view.findViewById(R.id.contact_name) as EditText
        surnameField = view.findViewById(R.id.contact_surname) as EditText
        numberField = view.findViewById(R.id.contact_number) as EditText
        cityField = view.findViewById(R.id.contact_city) as EditText
        streetField = view.findViewById(R.id.contact_street) as EditText


        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contactId = arguments?.getSerializable(ARG_CONTACT_ID) as UUID
        contactDetailViewModel.loadContact(contactId)
        contactDetailViewModel.contactLiveData.observe(
            viewLifecycleOwner,
            Observer { contact ->
                contact?.let {
                    this.contact = contact
                    updateUI()
                }
            })
    }

    private fun updateUI() {
        nameField.setText(contact.name)
        surnameField.setText(contact.surname)
        numberField.setText(contact.number)
        cityField.setText(contact.city)
        streetField.setText(contact.street)
    }

    override fun onStop() {
        super.onStop()

        contactDetailViewModel.saveContact(contact)
    }

    override fun onStart() {
        super.onStart()

        val nameWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                contact.name = sequence.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        nameField.addTextChangedListener(nameWatcher)

        val surnameWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                contact.surname = sequence.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        surnameField.addTextChangedListener(surnameWatcher)

        val numberWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                contact.number = sequence.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        numberField.addTextChangedListener(numberWatcher)

        val cityWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                contact.city = sequence.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        cityField.addTextChangedListener(cityWatcher)

        val streetWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(sequence: CharSequence?, start: Int, before: Int, count: Int) {
                contact.street = sequence.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }
        }
        streetField.addTextChangedListener(streetWatcher)

    }

    companion object {

        fun newInstance(contactId: UUID): ContactDetailFragment {
            val args = Bundle().apply {
                putSerializable(ARG_CONTACT_ID, contactId)
            }
            return ContactDetailFragment().apply {
                arguments = args
            }
        }
    }
}