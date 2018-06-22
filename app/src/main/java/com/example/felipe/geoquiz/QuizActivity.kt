package com.example.felipe.geoquiz

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast
import java.io.BufferedReader

class QuizActivity : AppCompatActivity() {
    private lateinit var trueButton:Button
    private lateinit var falseButton:Button

    fun printResutl(message: Int) =with (Toast.makeText(this ,message, Toast.LENGTH_SHORT)) {
        setGravity(Gravity.TOP,0,200)
        show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        trueButton =  findViewById(R.id.btnTrue)
        trueButton.setOnClickListener { printResutl(R.string.correctToast)  }

        falseButton = findViewById(R.id.btnFalse)
        falseButton.setOnClickListener {printResutl(R.string.incorrectToast) }

    }
}
