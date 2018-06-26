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
    private var cheatIndex = 0

    companion object {
        const val EXTRA_ANSWER_IS_TRUE = "com.example.felipe.geoquiz.answer_is_true"
        const val CHEAT_TIMES = "com.example.felipe.geoquiz.cheated_times"
        const val EXTRA_ANSWER_SHOWN = "com.example.felipe.geoquiz.answer_shown"
        const val ANS_INDEX = "ans_index"
        const val SHW_INDEX = "shw_index"
        const val CHEAT_TIMES_INDEX = "cheat_times_index"
        const val CHEAT_LIMIT = 3
        fun newIntent(packageContext: Context, answerIsTrue: Boolean, cheatIndex: Int): Intent {
            var intent = Intent(packageContext, CheatActivity::class.java)
            intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            intent.putExtra(CHEAT_TIMES, cheatIndex)
            return intent
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        if (savedInstanceState != null) {
            answerIsTrue = savedInstanceState.getBoolean(ANS_INDEX)
            answerWasShown = savedInstanceState.getBoolean(SHW_INDEX)
            cheatIndex = savedInstanceState.getInt(CHEAT_TIMES_INDEX)
            if (answerWasShown) {
                showAnswer()
                setAnswerShowResult(true)
                btnShowAnswer.isEnabled = false
            }
        } else {
            answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
            cheatIndex = intent.getIntExtra(CHEAT_TIMES, 0)
        }
        updateCheatCounter()
        if (CHEAT_LIMIT == cheatIndex) {
            btnShowAnswer.isEnabled = false
        } else {
            btnShowAnswer.setOnClickListener {
                showAnswer()
                setAnswerShowResult(true)
                cheatIndex++
                updateCheatCounter()
                btnShowAnswer.isEnabled = false
            }
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

    fun updateCheatCounter() {
        val cheatText = getText(R.string.cheats_left)
        val cheatLeft = CHEAT_LIMIT - cheatIndex
        txtCheatCounter.text = "$cheatText $cheatLeft"
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putBoolean(ANS_INDEX, answerIsTrue)
        savedInstanceState?.putBoolean(SHW_INDEX, answerWasShown)
        savedInstanceState?.putInt(CHEAT_TIMES_INDEX, cheatIndex)
    }
}
