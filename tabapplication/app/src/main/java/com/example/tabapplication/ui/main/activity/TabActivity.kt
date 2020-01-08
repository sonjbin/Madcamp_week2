package com.example.tabapplication.ui.main.activity

import android.app.AlertDialog
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.tabapplication.MainActivity
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.adapter.SectionsPagerAdapter
import com.example.tabapplication.ui.main.helper.BackPressCloseHandler
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout


@Suppress("DEPRECATION")
class TabActivity : AppCompatActivity() {
    lateinit var  backPressCloseHandler: BackPressCloseHandler
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tab)

        backPressCloseHandler = BackPressCloseHandler(this)

        val sectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        tabs.getTabAt(0)?.setIcon(R.drawable.ic_perm_identity_24px)
        tabs.getTabAt(1)?.setIcon(R.drawable.ic_photo_library_24px)
        tabs.getTabAt(2)?.setIcon(R.drawable.ic_explicit_24px)



        val btn_custom_logout = findViewById<FloatingActionButton>(R.id.btn_custom_logout)

        val builderLogout: AlertDialog.Builder = AlertDialog.Builder(this, R.style.CorrectDialog)
        builderLogout.setTitle("정말 로그아웃 하시겠습니까?")
        builderLogout.setPositiveButton(
            "OK"
        ) { dialog, id ->
            LoginManager.getInstance().logOut()
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            this.finish()
        }
        builderLogout.setNegativeButton("Cancel"){
                dialog, id ->
        }

        btn_custom_logout.setOnClickListener {
            builderLogout.show()
        }

    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        if(Build.VERSION.SDK_INT != Build.VERSION_CODES.O) {
            super.setRequestedOrientation(requestedOrientation)
        }
    }

    //뒤로가기 두 번 눌러야 꺼지게 변경
    override fun onBackPressed() {
        backPressCloseHandler.onBackPressed()
    }
}


