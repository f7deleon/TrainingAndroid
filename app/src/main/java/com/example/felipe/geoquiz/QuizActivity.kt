package com.example.felipe.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*
import timber.log.Timber
import timber.log.Timber.DebugTree


class QuizActivity : AppCompatActivity() {
    private val quizController = QuizController()
    private var index = 0
    private lateinit var answered: Array<Boolean>

    companion object {
        const val KEY_INDEX = "index"
        const val ANS_INDEX = "ans_index"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate(Bundle) called")
        setContentView(R.layout.activity_quiz)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(KEY_INDEX)
            answered = savedInstanceState.getBooleanArray(ANS_INDEX).toTypedArray()
            onQuestionAnswered()
        } else {
            answered = Array<Boolean>(quizController.size) { false }
        }

        txtQuestion.setText(quizController.getQuestion(index).textResId)

        btnNext.setOnClickListener {
            index = (index + 1) % quizController.size
            txtQuestion.setText(quizController.getQuestion(index).textResId)
            onQuestionAnswered()
        }
        btnPrev.setOnClickListener {
            index = when (index - 1 >= 0) {
                true -> index - 1
                false -> quizController.size - 1
            }
            txtQuestion.setText(quizController.getQuestion(index).textResId)
            onQuestionAnswered()
        }

        btnTrue.setOnClickListener {
            showAnswerResult(true)
            answered.set(index, true)
            onQuestionAnswered()
        }
        btnFalse.setOnClickListener {
            showAnswerResult(false)
            answered.set(index, true)
            onQuestionAnswered()
        }
    }


    override fun onResume() {
        super.onResume()
        Timber.d("onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy() called")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putInt(KEY_INDEX, index)
        savedInstanceState?.putBooleanArray(ANS_INDEX, answered.toBooleanArray())
        Timber.i("onSaveInstanceState")
    }

    private fun onQuestionAnswered() {
        when (answered[index]) {
            true -> {
                btnTrue.isEnabled = false
                btnFalse.isEnabled = false
            }
            false -> {
                btnTrue.isEnabled = true
                btnFalse.isEnabled = true
            }
        }
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

