package com.example.felipe.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*


class QuizActivity : AppCompatActivity() {
    private val questionBank = arrayOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_americas, false),
            Question(R.string.question_africa, true),
            Question(R.string.question_asia, true)
    )
    private var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        txtQuestion.setText(questionBank.get(index).textResId)
        btnNext.setOnClickListener {
            index = (index + 1) % questionBank.size
            txtQuestion.setText(questionBank.get(index).textResId)
        }
        btnPrev.setOnClickListener {
            index = when ((index - 1) >= 0) {
                true -> index
                false -> questionBank.size - 1
            }
            txtQuestion.setText(questionBank.get(index).textResId)
        }

        btnTrue.setOnClickListener { printResult(true) }
        btnFalse.setOnClickListener { printResult(false) }
    }

    private fun printResult(message: Boolean) {
        val msg = when (message == questionBank.get(index).isTrue) {
            true -> R.string.correct
            false -> R.string.incorrect
        }
        with(Toast.makeText(this, msg, Toast.LENGTH_SHORT)) {
            setGravity(Gravity.TOP, 0, 200)
            show()
        }
    }

}



