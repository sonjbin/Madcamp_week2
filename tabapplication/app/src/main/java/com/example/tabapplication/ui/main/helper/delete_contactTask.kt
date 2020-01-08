package com.example.tabapplication.ui.main.helper

import android.os.AsyncTask
import android.util.Log
import com.example.tabapplication.ui.main.fragment.NumberFragment.Companion.contactList
import com.facebook.AccessToken
import io.github.rybalkinsd.kohttp.dsl.httpDelete
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url

class delete_contactTask(number:String): AsyncTask<Void, Void, String>(){
    val accessToken = AccessToken.getCurrentAccessToken()
    val uid = accessToken.userId
    val number = number
    override fun doInBackground(vararg params: Void?): String {
        var response: String?
        try {
            var delete = httpDelete {
                url("http://192.249.19.254:6680/contacts/$uid/$number")
            }
            return delete.toString()
        }
        catch (e: java.lang.Exception){
            response = null
            Log.d("noResponse>>>>>>>>>>", e.toString())
            return response.toString()
        }
    }
}