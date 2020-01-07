package com.example.tabapplication.ui.main.fragment

import android.Manifest
import android.app.Activity
import android.content.*
import android.content.res.Configuration
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.R
import com.example.tabapplication.R.layout.fragment_gallery
import com.example.tabapplication.ui.main.adapter.GalleryImageAdapter
import com.example.tabapplication.ui.main.adapter.GalleryImageClickListener
import com.example.tabapplication.ui.main.helper.send_imageTask
import com.facebook.AccessToken
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.thesimplycoder.imagegallery.adapter.Image
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.net.URL
import java.util.*


private const val PICK_FROM_ALBUM = 1
private var tempFile: File? = null
private var isPermission = true
@Suppress("DEPRECATION")
class GalleryFragment : Fragment(), GalleryImageClickListener {
    private val TAG: String = "Jungbin"
    private var SPAN_COUNT = 3
    private var imageList = ArrayList<Image>()
    lateinit var galleryAdapter: GalleryImageAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(
            fragment_gallery,
            container,
            false
        )

        galleryAdapter = GalleryImageAdapter(imageList)
        galleryAdapter.listener = this


        load_imageTask().execute()
        var recyclerview: RecyclerView =  view.findViewById(R.id.recyclerView)
        //가로모드인 경우 col수 4개로 바꿈
        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            SPAN_COUNT = 4
        }
        recyclerview.layoutManager = GridLayoutManager(context, SPAN_COUNT)
        recyclerview.adapter = galleryAdapter
        tedPermission()
//        val mFab: FloatingActionButton = view.findViewById(R.id.plusFab_gallery)
//        mFab.setOnClickListener{
//
//        }

        addButtonAnimation(view, R.id.plusLayout_gallery, R.id.add_album_Layout,R.id.add_server_Layout, R.id.plusFab_gallery,R.id.addFab_album,R.id.addFab_server)

        return view
    }

    override fun onClick(position: Int) {

        val bundle = Bundle()
        bundle.putSerializable("images",imageList)
        bundle.putInt("position",position)

        val fragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
        val galleryFullFragment = GalleryFullscreenFragment()
        galleryFullFragment.arguments = bundle
        galleryFullFragment.show(fragmentTransaction,"gallery")
    }




    inner class load_imageTask() : AsyncTask<Void, Void, String>(){
        val accessToken = AccessToken.getCurrentAccessToken()
        val uid = accessToken.userId
        override fun doInBackground(vararg params: Void?): String {
            var response: String?
            try {
                var get = URL("http://192.249.19.254:6680/images/$uid").readText()
                val iList = JSONArray(get)
                Log.e("get photo", iList.length().toString())
                val loaded: ArrayList<Image> = ArrayList()
                for(i in 0 until iList.length()){
                    val name = iList.getJSONObject(i).getString("name")
                    val photo = iList.getJSONObject(i).getString("name")
                    loaded.add(Image(name,photo))
                }
                imageList = loaded
                galleryAdapter.notifyDataSetChanged()
                return get
            }
            catch (e: java.lang.Exception){
                response = null
                Log.d("noResponse>>>>>>>>>>", e.toString())
                NumberFragment.isFinish = true
                return response.toString()
            }
        }
    }

    private fun tedPermission(){
        var permissionListener: PermissionListener = object: PermissionListener {
            override fun onPermissionGranted() {
                isPermission = true
            }

            override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {
                isPermission = false
            }
        }
        // 마시멜로 이상 권한 체크
    if(Build.VERSION.SDK_INT >= 23) {
        TedPermission.with(context)
            .setPermissionListener(permissionListener)
            .setRationaleMessage(resources.getString(R.string.permission_2))
            .setDeniedMessage(resources.getString(R.string.permission_1))
            .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .check()
    }

    }

    private fun goToAlbum(){
        val intent: Intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
        startActivityForResult(intent, PICK_FROM_ALBUM )
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(resultCode != Activity.RESULT_OK){
            Toast.makeText(context, "취소 되었습니다.", Toast.LENGTH_SHORT).show()

            if(tempFile != null){
                if(tempFile!!.delete()){
                    Log.e(TAG, tempFile!!.absolutePath + "삭제 성공")
                    tempFile = null
                }
            }
            return
        }

        if(requestCode == PICK_FROM_ALBUM){

            if (data != null) {
                if(data.clipData== null){
                    //Toast.makeText(context, "다중 선택이 불가한 기기입니다.", Toast.LENGTH_LONG).show()
                    val photoUri: Uri? = data!!.data
                    imageList.add(Image(photoUri.toString(),photoUri.toString()))
                    /////////////////////////////////////////////////////////////////////////////////
                    val iObject: JSONObject = JSONObject()
                    val iObjects: ArrayList<JSONObject> = ArrayList<JSONObject>()
                    iObject.put("name", "$photoUri")
                    iObject.put("photo","$photoUri")
                    iObjects.add(iObject)
                    val send = send_imageTask(iObjects)
                    send.execute()
                    galleryAdapter.notifyDataSetChanged()
                } else{
                    var clipData: ClipData ?= data.clipData
                    Log.i("clipdata", clipData!!.itemCount.toString())

                    if(clipData.itemCount > 9){
                        Toast.makeText(context,"사진은 9장까지 선택 가능합니다", Toast.LENGTH_LONG).show()
                    }

                    else{
                        val iObjects: ArrayList<JSONObject> = ArrayList<JSONObject>()
                        for(i in 0 until clipData.itemCount){
                            var photoUri = clipData.getItemAt(i).uri.toString()
                            imageList.add(Image(photoUri,photoUri))
                            val iObject: JSONObject = JSONObject()
                            iObject.put("name", "$photoUri")
                            iObject.put("photo","$photoUri")
                            iObjects.add(iObject)
                        }
                        val send = send_imageTask(iObjects)
                        send.execute()
                        galleryAdapter.notifyDataSetChanged()
                    }
                }
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
            if(isPermission) goToAlbum()
            else Toast.makeText(view.context, resources.getString(R.string.permission_2), Toast.LENGTH_LONG).show()
        }

        fab3.setOnClickListener{
            var load = load_imageTask()
            load.execute()
        }
    }

}









