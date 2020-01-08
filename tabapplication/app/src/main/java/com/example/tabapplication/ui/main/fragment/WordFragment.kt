package com.example.tabapplication.ui.main.fragment


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.tabapplication.R
import com.example.tabapplication.ui.main.activity.RESULT_CODE
import com.example.tabapplication.ui.main.activity.WordAddActivity

import com.example.tabapplication.ui.main.activity.WordQuizActivity
import com.example.tabapplication.ui.main.adapter.WordListAdapter
import com.example.tabapplication.ui.main.adapter.Word

import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.DataOutputStream
import java.lang.AssertionError

/**
 * A simple [Fragment] subclass.
 */
class WordFragment : Fragment() {

    var wordArrayList: ArrayList<Word> = ArrayList()
    var adapter: WordListAdapter = WordListAdapter(wordArrayList)
    lateinit var recyclerView: RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view:View? = inflater.inflate(R.layout.fragment_word, container, false)

        wordArrayList=
            arrayListOf<Word> (

            Word("secretary", "비서"),
            Word("identification", "신분증"),
               Word("clerk", "점원"),
                Word("cliff", "절벽"),
                Word("flexable", "유연한"),
                Word("retailer", "소매상인"),
                Word("hedge", "울타리"),
                Word("apple", "사과"),
                Word("orange", "오렌지"),
                Word("grape", "포도"),
                Word("ideal", "이상적인"),
                Word("impartial", "공평한"),
                Word("accomodation", "수용"),
                Word("refrigerator", "냉장고"),
                Word("replica", "복제품")

        )

        recyclerView = view!!.findViewById(R.id.my_recycler_view)
        adapter = WordListAdapter(wordArrayList)

        var viewManager : RecyclerView.LayoutManager = LinearLayoutManager(context)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = viewManager

        addButtonAnimation(view, R.id.plusLayout, R.id.quizLayout, R.id.addLayout, R.id.plusFab, R.id.quizFab, R.id.addFab)

        return view
    }

    override fun  onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_CODE){
            val vocabulary = data!!.getStringExtra("vocabulary")
            val meaning = data!!.getStringExtra("meaning")
            if(vocabulary == "" || meaning == ""){
                Toast.makeText(context, "Please write something", Toast.LENGTH_SHORT).show()
            }
            else {
                wordArrayList.add(Word(vocabulary, meaning))
                adapter = WordListAdapter(wordArrayList)
                recyclerView!!.adapter = adapter
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
            val intent = Intent(activity, WordQuizActivity::class.java)
            intent.putParcelableArrayListExtra("wordArray", wordArrayList)
            startActivity(intent)
        }

        fab3.setOnClickListener{

            val addintent = Intent(activity, WordAddActivity::class.java)
            startActivityForResult(addintent, 0)

        }
    }


}


