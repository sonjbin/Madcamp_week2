package com.example.tabapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tabapplication.ui.main.activity.TabActivity
import com.example.tabapplication.ui.main.helper.LoginCallback
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.login.LoginManager
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.Arrays.asList


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private var mContext: Context? = null


    private var btn_custom_login: Button? = null


    private var mLoginCallback: LoginCallback? = null

    private var mCallbackManager: CallbackManager? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getHashKey()
        val accessToken: AccessToken? = AccessToken.getCurrentAccessToken()
        val isLoggedin: Boolean = accessToken != null && !accessToken.isExpired
        if(isLoggedin){
            val intent: Intent = Intent(this, TabActivity::class.java)
            startActivity(intent)
            this.finish()
        }
//        else {
//            Toast.makeText(this,"AA",Toast.LENGTH_LONG).show()
//        }
        setContentView(R.layout.activity_main)

        mContext = applicationContext
        mCallbackManager = CallbackManager.Factory.create()
        mLoginCallback = LoginCallback()
        btn_custom_login = findViewById(R.id.btn_custom_login) as Button
        btn_custom_login!!.setOnClickListener {
            val loginManager = LoginManager.getInstance()
            loginManager.logInWithReadPermissions(
                this,
                asList("public_profile", "email")
            )
            loginManager.registerCallback(mCallbackManager, mLoginCallback)
        }
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)


        if(resultCode == Activity.RESULT_OK) {
            val intent: Intent = Intent(this, TabActivity::class.java)
            startActivity(intent)
            this.finish();
        }
        else{
            Toast.makeText(this,"로그인에 실패하였습니다", Toast.LENGTH_LONG).show()
        }

    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}
