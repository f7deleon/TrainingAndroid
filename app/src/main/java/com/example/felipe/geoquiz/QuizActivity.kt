package com.example.felipe.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*


class QuizActivity : AppCompatActivity() {
    private val quizController = QuizController()
    private var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        txtQuestion.setText(quizController.getQuestion(index))
        btnNext.setOnClickListener {
            index = (index + 1) % quizController.size
            txtQuestion.setText(quizController.getQuestion(index))
        }
        btnPrev.setOnClickListener {
            index = when (index - 1 >= 0) {
                true -> index - 1
                false -> quizController.size - 1
            }
            txtQuestion.setText(quizController.getQuestion(index))
        }

        btnTrue.setOnClickListener { showAnswerResult(true) }
        btnFalse.setOnClickListener { showAnswerResult(false) }
    }

    private fun showAnswerResult(answer: Boolean) {
        val answer = when (quizController.checkAnswerResult(answer, index)) {
            true -> R.string.correct
            false -> R.string.incorrect
        }
        with(Toast.makeText(this, answer, Toast.LENGTH_SHORT)) {
            setGravity(Gravity.TOP, 0, 200)
            show()
        }
    }
}
