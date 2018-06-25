package com.example.felipe.geoquiz

class QuizController() {
    private val questionBank = arrayOf(
            Question(R.string.question_australia, true),
            Question(R.string.question_oceans, true),
            Question(R.string.question_mideast, false),
            Question(R.string.question_americas, false),
            Question(R.string.question_africa, true),
            Question(R.string.question_asia, true)
    )

    public fun checkAnswerResult(message: Boolean, index: Int) = message == questionBank.get(index).isTrue

    public fun getQuestion(index: Int) = questionBank.get(index = index).textResId

    public val size get() = questionBank.size
}
