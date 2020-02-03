package com.udemy.chodkowski.bookList


import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udemy.chodkowski.Contact
import com.udemy.chodkowski.R
import java.util.*

private const val TAG = "CrimeListFragment"

class BookListFragment : Fragment(){
    interface Callbacks {
        fun onContactSelected(contactId: UUID)
    }

    private var callbacks: Callbacks? = null

    private lateinit var bookRecyclerVew: RecyclerView
    private var adapter: ContactAdapter? = ContactAdapter(emptyList())
    private val bookListViewModel : BookListViewModel by lazy {
        ViewModelProviders.of(this).get(BookListViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onDetach() {
        super.onDetach()
        callbacks = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_contact_list, container, false)

        bookRecyclerVew = view.findViewById(R.id.contact_recycler_view) as RecyclerView
        bookRecyclerVew.layoutManager = LinearLayoutManager(context)
        bookRecyclerVew.adapter = adapter

        return view
    }

    override fun onStart() {
        super.onStart()
        bookListViewModel.bookListLiveData.observe(
            viewLifecycleOwner,
            Observer { contacts ->
                contacts?.let {
                    Log.i(TAG, "Got contactLiveData ${contacts.size}")
                    updateUI(contacts)
                }
            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_contact_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_contact -> {
                val contact = Contact()
                bookListViewModel.addContact(contact)
                callbacks?.onContactSelected(contact.id)
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    private fun updateUI(contacts: List<Contact>){
        adapter?.let {
            it.contacts = contacts
        } ?: run {
            adapter = ContactAdapter(contacts)
        }
        bookRecyclerVew.adapter = adapter
    }

    companion object {
        fun newInstance(): BookListFragment {
            return BookListFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private open inner class ContactHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener{

        protected lateinit var contact: Contact

        protected val nameTextView: TextView = itemView.findViewById(R.id.contact_name_text)
        protected val locationButton: Button = itemView.findViewById(R.id.localisation_button)
        protected val navigateButton: Button = itemView.findViewById(R.id.navigate_button)
        protected val callButton: Button = itemView.findViewById(R.id.call_button)



        init {
            itemView.setOnClickListener(this)

            locationButton.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=${contact.city} ${contact.street}"))
                startActivity(intent)
            }
            navigateButton.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("google.navigation:q=${contact.city} ${contact.street}"))
                startActivity(intent)
            }
            callButton.setOnClickListener {

                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${contact.number}"))
                startActivity(intent)
            }

        }

        open fun bind(contact: Contact){
            this.contact = contact
            nameTextView.text = "${contact.name} ${contact.surname}"

        }

        override fun onClick(v: View) {
            callbacks?.onContactSelected(contact.id)
        }
    }

    private inner class ContactAdapter(var contacts: List<Contact>)
        : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
            return ContactHolder(layoutInflater.inflate(R.layout.list_item_contact, parent, false))
        }

        override fun getItemCount() = contacts.size

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val contact = contacts[position]

            val contactHolder = holder as ContactHolder

            contactHolder.bind(contact)

        }

    }

}