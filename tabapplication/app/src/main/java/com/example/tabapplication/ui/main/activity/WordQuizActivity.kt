package com.example.tabapplication.ui.main.activity

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.tabapplication.R
import com.example.tabapplication.ui.main.adapter.Word
import kotlin.random.Random


class WordQuizActivity : AppCompatActivity() {
    var isCorrect = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word_quiz)

        var wordArrayList = intent. getParcelableArrayListExtra<Word>("wordArray")
        val totalNum = 3
        var problemNum = intent.getIntExtra("problemNum", totalNum)
        var correctNum = intent.getIntExtra("correctNum",0)
        val wordNum = wordArrayList.size
        val rnd = Random
        var num = 0
        num = if(wordNum == 1)
            0
        else
            rnd.nextInt(wordNum-1)

        val probnum: TextView  = findViewById(R.id.probnum)
        val pnum = totalNum-problemNum+1
        probnum.text = "Problem $pnum/$totalNum"

        val vocab = findViewById<TextView>(R.id.vocab)
        var vocabans = wordArrayList[num].vocabulary
        vocab.text = vocabans
        var meanans = wordArrayList[num].meaning

        //이미 나왔던 단어는 빼기
        wordArrayList.removeAt(num)
        var alpha: Drawable = findViewById<ImageView>(R.id.image).drawable
        alpha.alpha = 50

        //Dialog 형성

        val builderFinish: AlertDialog.Builder = AlertDialog.Builder(this, R.style.CorrectDialog)
        builderFinish.setPositiveButton(
            "OK"
        ) { dialog, id ->
            finish()
        }


        val builder1: AlertDialog.Builder = AlertDialog.Builder(this, R.style.CorrectDialog)
        builder1.setPositiveButton(
            "OK"
        ) { dialog, id ->
            val intent = Intent(this, WordQuizActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if(problemNum == 1){
                correctNum++
                if(correctNum == totalNum){
                    builderFinish.setTitle("Congratulation!").setMessage("You got a perfect score!")
                }else{
                    builderFinish.setTitle("Finish!").setMessage("You got $correctNum/$totalNum problems right")
                }

                val dialogFinish = builderFinish.create()
                dialogFinish.show()
            }
            else {
                intent.putExtra("problemNum", problemNum - 1)
                intent.putExtra("correctNum", correctNum + 1)
                intent.putExtra("wordArray",wordArrayList)
                startActivity(intent)
            }
        }
        builder1.setTitle("Correct!").setMessage("Go to the next problem")
        val dialog1: AlertDialog = builder1.create()

        val builder2: AlertDialog.Builder = AlertDialog.Builder(this, R.style.WrongDialog)
        builder2.setPositiveButton(
            "OK"
        ) { dialog, id ->
            val intent = Intent(this, WordQuizActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            if(problemNum == 1){
                builderFinish.setTitle("Finish!").setMessage("You got $correctNum/$totalNum problems right")
                val dialogFinish = builderFinish.create()
                dialogFinish.show()
            }
            else {
                intent.putExtra("problemNum", problemNum - 1)
                intent.putExtra("correctNum", correctNum)
                intent.putExtra("wordArray",wordArrayList)
                startActivity(intent)
            }
        }
        builder2.setTitle("Wrong!").setMessage("The answer is $meanans")
        val dialog2: AlertDialog = builder2.create()



        val submit: ImageButton = findViewById(R.id.submit)
        submit.setOnClickListener{

            //맞는지 체크
            var answer: EditText = findViewById(R.id.answer)
            var str: String?  = answer.text.toString()

            isCorrect = meanans == str

            if(isCorrect){
                dialog1.show()
            }
            else{
                dialog2.show()
            }

        }

    }


}