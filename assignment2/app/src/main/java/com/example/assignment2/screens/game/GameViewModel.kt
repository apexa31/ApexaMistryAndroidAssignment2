package com.example.assignment2.screens.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.assignment2.R


data class Question (
    val questionId: Int,
    val answer: Boolean,
    var attempted: Boolean = false,
    var answered: Boolean = false
)

class GameViewModel: ViewModel() {
    private var questionIndex = 0
    private lateinit var questionList: MutableList<Question>

    // current question
    private val _question = MutableLiveData<Int>()
    val question: LiveData<Int>
        get() = _question

    // question attempted
    private val _attempted = MutableLiveData<Boolean>()
    val attempted: LiveData<Boolean>
        get() = _attempted

    // select true
    private val _selectTrue = MutableLiveData<Boolean>()
    val selectTrue: LiveData<Boolean>
        get() = _selectTrue

    // select false
    private val _selectFalse = MutableLiveData<Boolean>()
    val selectFalse: LiveData<Boolean>
        get() = _selectFalse

    // is answer correct
    private val _ansCorrect = MutableLiveData<Boolean>()
    val ansCorrect: LiveData<Boolean>
        get() = _ansCorrect

    // current score in string
    private val _scoreStr = MutableLiveData<String>()
    val scoreStr: LiveData<String>
        get() = _scoreStr

    // game finish
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    init {
        newGame()
    }

    private fun onGameFinish() {
        _eventGameFinish.value = true
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    private fun questionAttempted() = questionList.count { it.attempted }

    private fun newGame() {
        resetAndShuffleQuestion()
        questionIndex = 0
        _eventGameFinish.value = false
        updateQuestion()
    }

    private fun resetAndShuffleQuestion() {
        questionList = mutableListOf(
            Question(R.string.question_1, false),
            Question(R.string.question_2, true),
            Question(R.string.question_3, true),
            Question(R.string.question_4, false),
            Question(R.string.question_5, false),
            Question(R.string.question_6, true),
            Question(R.string.question_7, false),
            Question(R.string.question_8, true),
            Question(R.string.question_9, false),
            Question(R.string.question_10, false),
            Question(R.string.question_11, false),
            Question(R.string.question_12, true),
            Question(R.string.question_13, false),
            Question(R.string.question_14, true),
            Question(R.string.question_15, false),
            Question(R.string.question_16, false),
            Question(R.string.question_17, true),
            Question(R.string.question_18, false),
            Question(R.string.question_19, false),
            Question(R.string.question_20, true)
        )
        questionList.shuffle()
    }

    private fun updateQuestion() {
        _question.value = questionList[questionIndex].questionId
        _attempted.value = questionList[questionIndex].attempted
        _ansCorrect.value = questionList[questionIndex].answer == questionList[questionIndex].answered

        _selectFalse.value = questionList[questionIndex].attempted && !questionList[questionIndex].answered
        _selectTrue.value = questionList[questionIndex].attempted && questionList[questionIndex].answered

        _scoreStr.value = "Your Score: ${questionList.count { it.attempted && it.answered == it.answer }}/${questionList.count()}"

        if(questionAttempted() == questionList.size) {
            onGameFinish()
        }
    }

    fun nextQuestion(){
        questionIndex = questionIndex.plus(1);
        if(questionIndex >= questionList.size) {
            questionIndex = 0;
        }
        updateQuestion();
    }

    fun prevQuestion() {
        questionIndex = questionIndex.minus(1);
        if(questionIndex < 0) {
            questionIndex = questionList.size.minus(1)
        }
        updateQuestion();
    }

    fun onChecked(checked: Boolean) {
        questionList[questionIndex].attempted = true;
        questionList[questionIndex].answered = checked
        updateQuestion()

    }

}