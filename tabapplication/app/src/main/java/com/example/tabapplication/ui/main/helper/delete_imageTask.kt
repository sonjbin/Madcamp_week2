package com.example.tabapplication.ui.main.helper

import android.os.AsyncTask
import android.util.Log
import com.facebook.AccessToken
import io.github.rybalkinsd.kohttp.dsl.httpDelete
import io.github.rybalkinsd.kohttp.ext.url

class delete_imageTask(name:String): AsyncTask<Void, Void, String>(){
    val accessToken = AccessToken.getCurrentAccessToken()
    val uid = accessToken.userId
    val name = name
    override fun doInBackground(vararg params: Void?): String {
        var response: String?
        try {
            var delete = httpDelete {
                url("http://192.249.19.254:6680/images/$uid/$name")
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