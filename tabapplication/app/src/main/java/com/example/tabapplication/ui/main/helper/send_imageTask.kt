package com.example.tabapplication.ui.main.helper

import android.os.AsyncTask
import android.util.Log
import com.facebook.AccessToken

import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import org.json.JSONObject

class send_imageTask(imageList: ArrayList<JSONObject>): AsyncTask<Void, Void, String>(){
    val image_list = imageList
    val accessToken = AccessToken.getCurrentAccessToken()
    val uid = accessToken.userId

    override fun doInBackground(vararg params: Void?): String {
        var response: String?
        try {
            Log.e("send list", image_list.toString())
            var post = httpPost{
                url("http://192.249.19.254:6680/images/$uid")
                body {
                    form("images=$image_list")
                }
            }
            Log.e("POST", "$post")
            return post.toString()
        }
        catch (e: java.lang.Exception){
            response = null
            Log.d("image send noResponse>>", e.toString())
            return response.toString()
        }
    }
}