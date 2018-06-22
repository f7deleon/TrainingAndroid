package com.example.felipe.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast


class QuizActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private lateinit var falseButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        trueButton = findViewById(R.id.btnTrue)
        trueButton.setOnClickListener { printResult(R.string.correct) }

        falseButton = findViewById(R.id.btnFalse)
        falseButton.setOnClickListener { printResult(R.string.incorrect) }
    }

    fun printResult(message: Int) = with(Toast.makeText(this, message, Toast.LENGTH_SHORT)) {
        setGravity(Gravity.TOP, 0, 200)
        show()
    }
}
