package com.example.tabapplication.ui.main.helper

import android.app.Activity
import android.widget.Toast

class BackPressCloseHandler {
    constructor(context:Activity){
        this.activity = context
    }

    private var backKeyPressedTime:Long = 0
    private var toast: Toast? = null

    private var activity: Activity? = null

//    fun BackPressCloseHandler(context: Activity ) {
//        this.activity = context
//    }

    fun onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis()
            showGuide()
            return
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
            activity!!.finish()
            toast!!.cancel()
        }
    }

    private fun showGuide() {
        toast = Toast.makeText(activity,
            "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT)
        toast!!.show()
    }
}



