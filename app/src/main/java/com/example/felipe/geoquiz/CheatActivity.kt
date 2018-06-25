package com.example.felipe.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cheat.*

class CheatActivity : AppCompatActivity() {
    private var answerIsTrue = false
    private var answerWasShown = false

    companion object {
        const val EXTRA_ANSWER_IS_TRUE = "com.example.felipe.geoquiz.answer_is_true"
        const val EXTRA_ANSWER_SHOWN = "com.example.felipe.geoquiz.answer_shown"
        const val ANS_INDEX = "ans_index"
        const val SHW_INDEX = "shw_index"
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            var intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        if (savedInstanceState != null) {
            answerIsTrue = savedInstanceState.getBoolean(ANS_INDEX)
            answerWasShown = savedInstanceState.getBoolean(SHW_INDEX)
            if (answerWasShown) {
                showAnswer()
                setAnswerShowResult(true)
            }
        } else {
            answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        }

        btnShowAnswer.setOnClickListener {
            showAnswer()
            setAnswerShowResult(true)
        }
    }

    private fun showAnswer() {
        if (answerIsTrue) {
            txtAnswer.setText(R.string._true)
        } else {
            txtAnswer.setText(R.string._false)
        }
    }

    fun setAnswerShowResult(isAnswerShown: Boolean) {
        var data = Intent()
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        setResult(Activity.RESULT_OK, data)
        answerWasShown = true
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putBoolean(ANS_INDEX, answerIsTrue)
        savedInstanceState?.putBoolean(SHW_INDEX, answerWasShown)
    }
}
