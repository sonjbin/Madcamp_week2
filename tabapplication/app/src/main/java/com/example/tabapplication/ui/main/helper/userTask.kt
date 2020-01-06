package com.example.tabapplication.ui.main.helper

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import io.github.rybalkinsd.kohttp.dsl.httpDelete
import io.github.rybalkinsd.kohttp.dsl.httpPost
import io.github.rybalkinsd.kohttp.ext.url
import java.net.URL

@RequiresApi(Build.VERSION_CODES.CUPCAKE)
class userTask(user_id:String) : AsyncTask<Void, Void, String>() {
    val userId = user_id
    @SuppressLint("LongLogTag")
    override fun doInBackground(vararg urls: Void?): String{

        var response: String?
        try {
            Log.e("user Id", "ID"+"$userId")
            var post = httpPost{
                url("http://192.249.19.254:6680/users")
                body {
                    form("id=$userId")
                }
            }
            Log.e("POST", "$post")
//            var delete = httpDelete {
//                url("http://192.249.19.254:6680/users/$userId")
//            }
//            Log.e("DELETE", "$delete")

//            var get = URL("http://192.249.19.254:6680/users/gfh").readText()
//            Log.d("get>>>>>>>>>>>>>>>>>>>>", get)
//
//            var post = httpPost{
//                url("http://192.249.19.254:6680/users")
//                body {
//                    form("email=fgh")
//                }
//            }
//            Log.d("Post>>>>>>>>>>>>>>",post.message())
//            get = URL("http://192.249.19.254:6680/users").readText()
//            Log.d("get>>>>>>>>>>>>>>>>>>>>", get)
//
//            var delete = httpDelete{
//                url("http://192.249.19.254:6680/users/gfh")
//            }
//            Log.d("Delete>>>>>>>>>>>>>>",delete.message())
//            get = URL("http://192.249.19.254:6680/users").readText()
//            Log.d("get>>>>>>>>>>>>>>>>>>>>", get)
//            delete = httpDelete{
//                url("http://192.249.19.254:6680/users/fgh")
//            }
//            Log.d("Delete>>>>>>>>>>>>>>",delete.message())
//            get = URL("http://192.249.19.254:6680/users").readText()
//            Log.d("get>>>>>>>>>>>>>>>>>>>>", get)
//            return get
            return post.toString()
        }catch (e: java.lang.Exception){
            response = null
            Log.d("noResponse>>>>>>>>>>>>>>>>>", e.toString())
            return response.toString()
        }
    }

    override fun onPostExecute(feed: String?) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}