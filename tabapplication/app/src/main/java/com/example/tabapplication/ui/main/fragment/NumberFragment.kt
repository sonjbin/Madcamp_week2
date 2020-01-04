package com.example.tabapplication.ui.main.fragment



import android.Manifest.permission.READ_CONTACTS
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.adapter.GalleryImageAdapter
import com.example.tabapplication.ui.main.adapter.NumberAdapter
import kotlinx.android.synthetic.main.fragment_number.view.*

/**
 * A simple [Fragment] subclass.
 */
class NumberFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
//    private lateinit var viewManager: RecyclerView.LayoutManager



    private lateinit var numberAdapter: NumberAdapter

    companion object{
        private const val READ_CONTACTS_PERMISSIONS_REQUEST = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermissionToReadUserContacts();

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_number, container, false)
        recyclerView = view.findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            var viewManager : RecyclerView.LayoutManager = LinearLayoutManager(context)

            layoutManager = viewManager
            adapter = viewAdapter
        }
        return view
    }

    data class Contact(val name: String, val phoneNumber: String)

    fun Context.fetchAllContacts(): List<Contact> {
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
            .use { cursor ->
                if (cursor == null) return emptyList()
                val builder = ArrayList<Contact>()
                while (cursor.moveToNext()) {
                    val name =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                            ?: "N/A"
                    val phoneNumber =
                        cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            ?: "N/A"
                    builder.add(Contact(name, phoneNumber))
                }
                return builder
            }
    }

    fun getPermissionToReadUserContacts() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermissions(
                arrayOf(READ_CONTACTS),
                READ_CONTACTS_PERMISSIONS_REQUEST
            )
        } else {
            layContactsList()
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == NumberFragment.READ_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Read Contacts permission granted", Toast.LENGTH_SHORT).show()
                layContactsList()
            } else {
                Toast.makeText(context, "Read Contacts permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    fun layContactsList() {
        viewAdapter = NumberAdapter(requireContext().fetchAllContacts()) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${it.phoneNumber}")
            startActivity(intent)
        }
    }




}
