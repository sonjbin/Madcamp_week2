package com.example.tabapplication.ui.main.fragment

import android.Manifest.permission.READ_CONTACTS
import android.Manifest.permission.WRITE_CONTACTS
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.adapter.NumberAdapter
import com.example.tabapplication.ui.main.adapter.SwipeToDeleteCallback
import com.example.tabapplication.ui.main.helper.LoginCallback
import com.example.tabapplication.ui.main.helper.contactTask
import com.example.tabapplication.ui.main.helper.userTask
import com.facebook.login.LoginManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_phonebookdisplay.*
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

/**
 * A simple [Fragment] subclass.
 */
class NumberFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private  var viewAdapter: RecyclerView.Adapter<*>? = null
    private var readPermission = false
    //private var writePermission = true

    private lateinit var numberAdapter: NumberAdapter

    companion object{
        private const val READ_CONTACTS_PERMISSIONS_REQUEST = 1
        private const val WRITE_CONTACTS_PERMISSIONS_REQUEST = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getPermissionToReadUserContacts()
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
            if(viewAdapter != null) {
                adapter = viewAdapter
            }else{
                Toast.makeText(context,"No contact in server",Toast.LENGTH_SHORT).show()
            }
        }
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = viewAdapter as NumberAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        addButtonAnimation(view, R.id.plusLayout_contact, R.id.add_address_Layout, R.id.add_server_Layout_contact, R.id.plusFab_contact,R.id.addFab_address, R.id.addFab_server_contact)

        return view
    }

    data class Contact(val name: String, val phoneNumber: String)

    private fun Context.fetchAllContacts(): MutableList<Contact> {
        contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
            .use { cursor ->
                val builder = ArrayList<Contact>()
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        val name =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                                ?: "N/A"
                        val phoneNumber =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                                ?: "N/A"
                        builder.add(Contact(name, phoneNumber))
                    }
                }
                val contactObjects: ArrayList<JSONObject> = ArrayList<JSONObject>()
                var contact_name = ""
                var contact_number = ""
                for(i in 0 until builder.size){
                    contact_name = builder[i].name
                    contact_number = builder[i].phoneNumber
                    val cObject: JSONObject = JSONObject()
                    cObject.put("name", "$contact_name")
                    cObject.put("number","$contact_number")
                    contactObjects.add(cObject)
                }


                val send = contactTask(contactObjects)
                send.execute()

                return builder
            }
    }

    private fun getPermissionToReadUserContacts() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(READ_CONTACTS),
                READ_CONTACTS_PERMISSIONS_REQUEST
            )
        }
        else {
            readPermission = true
        }
    }

//    private fun getPermissionToWriteUserContacts() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                WRITE_CONTACTS
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestPermissions(
//                arrayOf(WRITE_CONTACTS),
//                WRITE_CONTACTS_PERMISSIONS_REQUEST
//            )
//        } else {
//            layContactsList()
//        }
//    }

    private fun layContactsList() {
        viewAdapter = NumberAdapter(requireContext().fetchAllContacts()) {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:${it.phoneNumber}")
            startActivity(intent)
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

        if (requestCode == NumberFragment.WRITE_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Write Contacts permission granted", Toast.LENGTH_SHORT).show()
                layContactsList()
            } else {
                Toast.makeText(context, "Write Contacts permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun addButtonAnimation(view:View, layout1: Int, layout2: Int,layout3: Int, fab1: Int, fab2: Int, fab3: Int){
        val fab1: FloatingActionButton = view.findViewById(fab1)
        val fab2: FloatingActionButton = view.findViewById(fab2)
        val fab3: FloatingActionButton = view.findViewById(fab3)
        val layout1: LinearLayout = view.findViewById(layout2)
        val layout2: LinearLayout = view.findViewById(layout3)
        val showButtonAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.show_button)
        val hideButtonAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.hide_button)
        val showLayoutAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.show_layout)
        val hideLayoutAnim: Animation = AnimationUtils.loadAnimation(context,R.anim.hide_layout)

        fab1.setOnClickListener{
            if(layout1.visibility == View.VISIBLE && layout2.visibility == View.VISIBLE ){
                layout1.visibility = View.GONE
                layout2.visibility = View.GONE
                fab2.isClickable = false
                fab3.isClickable = false
                fab1.startAnimation(hideButtonAnim)
                layout1.startAnimation(hideLayoutAnim)
                layout2.startAnimation(hideLayoutAnim)
            }
            else{
                layout1.visibility = View.VISIBLE
                layout2.visibility = View.VISIBLE
                fab2.isClickable = true
                fab3.isClickable = true
                fab1.startAnimation(showButtonAnim)
                layout1.startAnimation(showLayoutAnim)
                layout2.startAnimation(showLayoutAnim)
            }
        }

        fab2.setOnClickListener{
            Toast.makeText(context, "$readPermission", Toast.LENGTH_SHORT).show()
            if(readPermission ){
                layContactsList()
            }
        }

        fab3.setOnClickListener{

        }
    }


}