package com.example.tabapplication.ui.main.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.adapter.*
import com.example.tabapplication.ui.main.fragment.CookbookFragment
import com.example.tabapplication.ui.main.fragment.CookbookFragment.Companion.myIngredients

class ChooseActivity : AppCompatActivity(), ClickListener{

    var ingArrayList: ArrayList<Ingredient> = ArrayList()
    var adapter: IngredientsAdapter = IngredientsAdapter(ingArrayList)
    lateinit var recyclerView: RecyclerView
    var index:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose)

        recyclerView = findViewById(R.id.recyclerView_choose)
         index = intent.getIntExtra("index",0)
        makeList(index)
        adapter = IngredientsAdapter(ingArrayList)
        adapter.listener = this


        var viewManager : RecyclerView.LayoutManager = LinearLayoutManager(this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = viewManager
    }

    override fun onClick(position: Int) {
        if(index == 4){
            if(myIngredients.size > position) {
                Toast.makeText(this, myIngredients[position] + "deleted", Toast.LENGTH_SHORT).show()
                myIngredients.removeAt(position)
                adapter.notifyDataSetChanged()
            }
        }
        else{
            val intent: Intent = intent
            intent.putExtra("food", ingArrayList[position].ing_name)
            setResult(RESULT_CODE, intent)
            finish()
        }

    }

    private fun makeList(index:Int){
        val vegetableList: ArrayList<Ingredient> = arrayListOf(
            Ingredient("감자","https://cdn.pixabay.com/photo/2013/07/12/16/54/french-fries-151471__340.png"),
            Ingredient("양파","https://cdn.pixabay.com/photo/2013/07/13/13/49/onion-161611__340.png"),
            Ingredient("당근","https://cdn.pixabay.com/photo/2016/04/01/12/10/carrot-1300572__340.png"),
            Ingredient("오이","https://cdn.pixabay.com/photo/2016/04/01/10/16/cucumber-1299833__340.png"),
            Ingredient("토마토","https://cdn.pixabay.com/photo/2013/07/12/18/19/tomato-153272__340.png"),
            Ingredient("무","https://cdn.pixabay.com/photo/2014/12/22/00/02/radish-576640__340.png")
        )
        val fruitList: ArrayList<Ingredient> = arrayListOf(
            Ingredient("사과","https://cdn.pixabay.com/photo/2018/09/05/19/56/mace-3656942__340.png"),
            Ingredient("바나나","https://cdn.pixabay.com/photo/2014/12/21/23/39/bananas-575773__340.png"),
            Ingredient("포도","https://cdn.pixabay.com/photo/2012/04/14/15/31/grapes-34298__340.png"),
            Ingredient("딸기","https://cdn.pixabay.com/photo/2012/04/18/12/54/strawberry-36949__340.png"),
            Ingredient("블루베리","https://cdn.pixabay.com/photo/2014/04/02/16/15/blueberries-306718__340.png")
        )

        val meatList: ArrayList<Ingredient> = arrayListOf(
            Ingredient("소고기","https://cdn.pixabay.com/photo/2012/05/07/01/54/bull-47524__340.png"),
            Ingredient("돼지고기","https://cdn.pixabay.com/photo/2014/12/22/00/00/pig-576570__340.png"),
            Ingredient("닭고기","https://cdn.pixabay.com/photo/2017/01/31/13/30/animals-2024059__340.png"),
            Ingredient("계란","https://cdn.pixabay.com/photo/2012/04/05/00/38/egg-25369__340.png"),
            Ingredient("오리고기","https://cdn.pixabay.com/photo/2014/04/03/11/46/duck-312100__340.png")
        )

        val seafoodList: ArrayList<Ingredient> = arrayListOf(
            Ingredient("생선","https://cdn.pixabay.com/photo/2012/04/12/22/02/fish-30828__340.png"),
            Ingredient("새우","https://cdn.pixabay.com/photo/2018/10/10/12/57/prawn-3737191__340.png"),
            Ingredient("조개","https://cdn.pixabay.com/photo/2014/04/02/14/05/seashell-306124__340.png"),
            Ingredient("오징어","https://cdn.pixabay.com/photo/2016/04/01/08/40/animal-1298875__340.png"),
            Ingredient("미역","https://cdn.pixabay.com/photo/2013/07/12/16/55/seaweed-151497__340.png")
        )

        if(index ==0 )
            ingArrayList = vegetableList
        else if (index == 1){
            ingArrayList = fruitList
        }
        else if (index == 2){
            ingArrayList = meatList
        }
        else if (index == 3){
            ingArrayList = seafoodList
        }
        else {
            for (i in 0 until myIngredients.size) {
                ingArrayList.add(Ingredient(myIngredients[i], ""))
            }

        }

    }
}