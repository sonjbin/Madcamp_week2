package com.example.tabapplication.ui.main.fragment

import android.Manifest.permission.READ_CONTACTS
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.adapter.NumberAdapter
import com.example.tabapplication.ui.main.helper.SwipeToDeleteCallback
import com.example.tabapplication.ui.main.helper.delete_contactTask

import com.example.tabapplication.ui.main.helper.send_contactTask
import com.facebook.AccessToken
import kotlinx.android.synthetic.main.fragment_number.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
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
        var contactList: ArrayList<Contact> = ArrayList()
        var isFinish:Boolean = false
    }

   inner class load_contactTask(): AsyncTask<Void, Void, String>(){
        val accessToken = AccessToken.getCurrentAccessToken()
        val uid = accessToken.userId
        override fun doInBackground(vararg params: Void?): String {
            var response: String?
            try {
                var get = URL("http://192.249.19.254:6680/contacts/$uid").readText()
                val cList: JSONArray = JSONArray(get)
                val loaded: ArrayList<NumberFragment.Contact> = ArrayList()
                for(i in 0 until cList.length()){
                    val name = cList.getJSONObject(i).getString("name")
                    val number = cList.getJSONObject(i).getString("number")
                    loaded.add(NumberFragment.Contact(name,number))
                }
                contactList = loaded
                isFinish = true
                return get
            }
            catch (e: java.lang.Exception){
                response = null
                Log.d("noResponse>>>>>>>>>>", e.toString())
                isFinish = true
                return response.toString()
            }
        }

       override fun onPostExecute(result: String?) {
           super.onPostExecute(result)
           try {
               viewAdapter = context?.let { NumberAdapter(contactList){} }
               recyclerView!!.adapter = viewAdapter
               val lm = LinearLayoutManager(context)
               recyclerView!!.layoutManager = lm
               recyclerView!!.setHasFixedSize(true)
           }
           catch (e: Exception) {
               Log.d("fetchContactException>>", e.toString())
           }
       }
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
        loadContact()

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
                val deleteTask = delete_contactTask(contactList[viewHolder.adapterPosition].phoneNumber)
                deleteTask.execute()
                Log.e("AA",viewHolder.adapterPosition.toString())
                adapter.removeAt(viewHolder.adapterPosition)

            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)


        val loadFab: ImageButton = view.findViewById(R.id.loadFab_contact)
        loadFab.setOnClickListener{
            if(readPermission ){
                Toast.makeText(context, "Load contact to server",Toast.LENGTH_SHORT).show()
                requireContext().fetchAllContacts()
            }
        }

        return view
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        swipeRefreshLayout.setOnRefreshListener {
            loadContact()
            swipeRefreshLayout.isRefreshing = false
        }
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

                val send = send_contactTask(contactObjects)
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

    private fun loadContact() {
        val load = load_contactTask()
        load.execute()

        viewAdapter = NumberAdapter(contactList){
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
            } else {
                Toast.makeText(context, "Read Contacts permission denied", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        if (requestCode == NumberFragment.WRITE_CONTACTS_PERMISSIONS_REQUEST) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "Write Contacts permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Write Contacts permission denied", Toast.LENGTH_SHORT).show()
            }
        }

    }


}