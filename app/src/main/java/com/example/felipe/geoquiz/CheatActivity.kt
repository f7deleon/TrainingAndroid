package com.example.felipe.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cheat.*
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric




class CheatActivity : AppCompatActivity() {
    companion object {
        val EXTRA_ANSWER_IS_TRUE = "${CheatActivity::class.java.canonicalName}.answer_is_true"
        val EXTRA_CHEAT_TIMES = "${CheatActivity::class.java.canonicalName}.cheated_times"
        val EXTRA_ANSWER_SHOWN = "${CheatActivity::class.java.canonicalName}.answer_shown"
        const val KEY_ANS_INDEX = "ans_index"
        const val KEY_SHW_INDEX = "shw_index"
        const val KEY_CHEAT_TIMES_INDEX = "cheat_times_index"
        const val CHEAT_LIMIT = 3

        fun newIntent(packageContext: Context, answerIsTrue: Boolean, cheatIndex: Int): Intent {
            return Intent(packageContext, CheatActivity::class.java)
                    .putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
                    .putExtra(EXTRA_CHEAT_TIMES, cheatIndex)
        }
    }

    private var answerIsTrue = false
    private var answerWasShown = false
    private var cheatIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        Fabric.with(this, Crashlytics())
        initVariables(savedInstanceState)
        initViews()
    }

    private fun initViews() {
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

    private fun initVariables(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            answerIsTrue = savedInstanceState.getBoolean(KEY_ANS_INDEX)
            answerWasShown = savedInstanceState.getBoolean(KEY_SHW_INDEX)
            cheatIndex = savedInstanceState.getInt(KEY_CHEAT_TIMES_INDEX)
            if (answerWasShown) {
                showAnswer()
                setAnswerShowResult(true)
                btnShowAnswer.isEnabled = false
            }
        } else {
            answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
            cheatIndex = intent.getIntExtra(EXTRA_CHEAT_TIMES, 0)
        }
    }

    private fun showAnswer() {
        when (answerIsTrue) {
            true -> txtAnswer.setText(R.string._true)
            false -> txtAnswer.setText(R.string._false)
        }
    }

    fun setAnswerShowResult(isAnswerShown: Boolean) {
        var data = Intent()
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        setResult(Activity.RESULT_OK, data)
        answerWasShown = true
    }

    fun updateCheatCounter() {
        txtCheatCounter.text = "${getText(R.string.cheats_left)} ${CHEAT_LIMIT - cheatIndex}"
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle?) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState?.putBoolean(KEY_ANS_INDEX, answerIsTrue)
        savedInstanceState?.putBoolean(KEY_SHW_INDEX, answerWasShown)
        savedInstanceState?.putInt(KEY_CHEAT_TIMES_INDEX, cheatIndex)
    }
}
