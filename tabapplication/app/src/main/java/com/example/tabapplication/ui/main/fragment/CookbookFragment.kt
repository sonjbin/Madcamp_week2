package com.example.tabapplication.ui.main.fragment

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.activity.ChooseActivity
import com.example.tabapplication.ui.main.activity.RESULT_CODE
import com.example.tabapplication.ui.main.adapter.Ingredient
import com.example.tabapplication.ui.main.adapter.Recipe
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_cookbook.*
import kotlin.random.Random

class CookbookFragment : Fragment() {

    companion object {
        var myIngredients: ArrayList<String> = ArrayList()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View? = inflater.inflate(R.layout.fragment_cookbook, container, false)

        val btnVegetable:Button = view!!.findViewById<Button>(R.id.btn_vegetable)
        val btnFruit:Button = view!!.findViewById<Button>(R.id.btn_fruit)
        val btnMeat:Button = view!!.findViewById<Button>(R.id.btn_meat)
        val btnSeafood:Button = view!!.findViewById<Button>(R.id.btn_seafood)
        val btnRefrigerator:Button = view.findViewById(R.id.btn_refrigerator)
        btnVegetable.setOnClickListener{
            val intent = Intent(activity, ChooseActivity::class.java)
            intent.putExtra("index",0)
            startActivityForResult(intent,0)
        }
        btnFruit.setOnClickListener{
            val intent = Intent(activity, ChooseActivity::class.java)
            intent.putExtra("index",1)
            startActivityForResult(intent,0)
        }
        btnMeat.setOnClickListener{
            val intent = Intent(activity, ChooseActivity::class.java)
            intent.putExtra("index",2)
            startActivityForResult(intent,0)
        }
        btnSeafood.setOnClickListener{
            val intent = Intent(activity, ChooseActivity::class.java)
            intent.putExtra("index",3)
            startActivityForResult(intent,0)
        }
        btnRefrigerator.setOnClickListener{
            val intent = Intent(activity, ChooseActivity::class.java)
            intent.putExtra("index",4)
            startActivity(intent)
        }

        val btnRecipe: FloatingActionButton = view.findViewById(R.id.plusFab_cook)
        btnRecipe.setOnClickListener{

            var todayMenu = ""
            var availableList = ArrayList<String>()
            val recipeList = arrayListOf<Recipe>(
                Recipe("감자볶음", arrayListOf("감자", "당근")),
                Recipe("제육볶음", arrayListOf("돼지고기", "양파")),
                Recipe("감바스", arrayListOf("새우", "양파")),
                Recipe("과일 샐러드", arrayListOf("사과", "딸기", "바나나" )),
                Recipe("조개탕", arrayListOf("조개", "무")),
                Recipe("오징어무침", arrayListOf("오징어", "당근")),
                Recipe("조개미역국", arrayListOf("조개", "미역")),
                Recipe("감자전", arrayListOf("감자")),
                Recipe("짬뽕", arrayListOf("오징어", "조개")),
                Recipe("소고기 뭇국", arrayListOf("소고기","무")),
                Recipe("생선 무 조림", arrayListOf("생선","무")),
                Recipe("계란말이", arrayListOf("계란","당근")),
                Recipe("오이 냉채", arrayListOf("오이"))
            )

            for(i in 0 until recipeList.size){
                if(recipeList[i].ings.intersect(myIngredients).size == recipeList[i].ings.size){
                    availableList.add(recipeList[i].rName)
                }
            }
            val rnd = Random
            var num = if(availableList.size <= 1)
                0
            else
                rnd.nextInt(availableList.size-1)


            val builderFinish: AlertDialog.Builder = AlertDialog.Builder(context, R.style.CorrectDialog)
            if(availableList.size == 0){
                builderFinish.setTitle("냉장고가 비었어요..")
            }
            else {
                builderFinish.setTitle("오늘의 추천메뉴").setMessage("-" + availableList[num] +"-")
            }
            builderFinish.setPositiveButton(
                "OK"
            ) { dialog, id ->

            }

            builderFinish.show()
        }

            return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(RESULT_CODE == resultCode){
            val added = data!!.getStringExtra("food")
            if(added in myIngredients){
                Toast.makeText(context, "Already in my refrigerator",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "$added added",Toast.LENGTH_SHORT).show()
                myIngredients.add(added)
            }
        }
    }
}