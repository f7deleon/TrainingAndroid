package com.example.felipe.geoquiz

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_quiz.*
import timber.log.Timber
import timber.log.Timber.DebugTree


class QuizActivity : AppCompatActivity() {
    companion object {
        const val KEY_INDEX = "index"
        const val KEY_ANS_INDEX = "ans_index"
        const val KEY_CHT_INDEX = "cht_index"
        const val KEY_CHEAT_TIMES_INDEX = "cheat_times_index"
        const val REQUEST_CODE_CHEAT = 0
    }

    private val quizController = QuizController()
    private var index = 0
    private lateinit var answered: Array<Boolean>
    private lateinit var cheated: Array<Boolean>
    private var cheatIndex = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate(Bundle) called")
        setContentView(R.layout.activity_quiz)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        initCollections(savedInstanceState)

        initView()
    }

    private fun initView() {
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
        btnCheat.setOnClickListener {
            val intent = CheatActivity.newIntent(this@QuizActivity, quizController.getQuestion(index).isTrue, cheatIndex)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
    }

    private fun initCollections(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            index = savedInstanceState.getInt(KEY_INDEX)
            answered = savedInstanceState.getBooleanArray(KEY_ANS_INDEX).toTypedArray()
            cheated = savedInstanceState.getBooleanArray(KEY_CHT_INDEX).toTypedArray()
            cheatIndex = savedInstanceState.getInt(KEY_CHEAT_TIMES_INDEX)
            onQuestionAnswered()
        } else {
            answered = Array(quizController.size) { false }
            cheated = Array(quizController.size) { false }
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
        savedInstanceState?.putInt(KEY_CHEAT_TIMES_INDEX, cheatIndex)
        savedInstanceState?.putBooleanArray(KEY_ANS_INDEX, answered.toBooleanArray())
        savedInstanceState?.putBooleanArray(KEY_CHT_INDEX, cheated.toBooleanArray())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return

        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) return
            if (!cheated[index]) {
                cheated.set(index, true)
                cheatIndex++
            }
        }
    }

    private fun showAnswerResult(answer: Boolean) {
        val message =
                if (cheated[index]) {
                    R.string.judgment_alert
                } else
                    when (quizController.checkAnswerResult(answer, index)) {
                        true -> R.string.correct
                        false -> R.string.incorrect
                    }

        with(Toast.makeText(this, message, Toast.LENGTH_SHORT)) {
            setGravity(Gravity.TOP, 0, 200)
            show()
        }
    }
}
