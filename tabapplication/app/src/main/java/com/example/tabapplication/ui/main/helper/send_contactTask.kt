package com.example.tabapplication.ui.main.helper

import android.os.AsyncTask
import android.util.Log
import com.facebook.AccessToken

import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import org.json.JSONObject

class send_contactTask (contactList:ArrayList<JSONObject>): AsyncTask<Void, Void, String>(){
    val contact_list = contactList
    val accessToken = AccessToken.getCurrentAccessToken()
    val uid = accessToken.userId

    override fun doInBackground(vararg params: Void?): String {
        var response: String?
        try {
            var post = httpPost{
                url("http://192.249.19.254:6680/contacts/$uid")
                body {
                    form("contacts=$contact_list")
                }
            }
            Log.e("POST", "$post")
            return post.toString()
        }
        catch (e: java.lang.Exception){
            response = null
            Log.d("noResponse>>>>>>>>>>", e.toString())
            return response.toString()
        }
    }
}