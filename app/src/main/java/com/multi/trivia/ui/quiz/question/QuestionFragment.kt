package com.multi.trivia.ui.quiz.question

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.multi.trivia.R
import com.multi.trivia.data.model.Question
import com.multi.trivia.databinding.FragmentQuestionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private lateinit var questions: Array<Question>

    private var score = 0
    private var currentQuestion = 0
    private var isCorrect = false

    private val args: QuestionFragmentArgs by navArgs()

    private val layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuestionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questions = args.questions

        initQuiz(view, questions[currentQuestion])

        binding.btnNext.setOnClickListener { checkQuestion(view) }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initQuiz(view: View, question: Question) {
        println(question.correctAnswer)
        binding.question = question
        renderQuestion(view, question)
        binding.currentQuestion = "Question: ${currentQuestion + 1} / ${questions.size}"
    }

    private fun renderQuestion(view: View, question: Question) {
        val radioButtons: MutableList<RadioButton> = mutableListOf()

        binding.radioGroup.removeAllViews()

        val rbTextColor = ContextCompat.getColor(view.context, R.color.white)
        val rbColor = ContextCompat.getColor(view.context, R.color.primaryLightColor)
        val margin = resources.getDimension(R.dimen.text_margin).toInt()
        layoutParams.setMargins(0, 0, 0, margin)

        val rbCorrectAnswer = createRadioButton(view, question.correctAnswer, rbColor, rbTextColor)
        radioButtons.add(rbCorrectAnswer)

        val incorrectAnswers = question.incorrectAnswers

        incorrectAnswers.forEach { incorrectAnswer ->
            val rbIncorrectAnswer = createRadioButton(view, incorrectAnswer, rbColor, rbTextColor)
            radioButtons.add(rbIncorrectAnswer)
        }

        val suffleList = radioButtons.shuffled()
        suffleList.forEach { radioButton -> binding.radioGroup.addView(radioButton) }
    }

    private fun checkQuestion(view: View) {
        val radioButtonCheckedId = binding.radioGroup.checkedRadioButtonId

        if (radioButtonCheckedId == -1) {
            Toast.makeText(view.context, "You must select an option", Toast.LENGTH_SHORT).show()
            return
        }

        if (radioButtonCheckedId == R.id.correct_radio_button_id) score++

        nextQuestion(view)
    }


    private fun nextQuestion(view: View) {
        currentQuestion++

        if (currentQuestion <= questions.size - 1) {
            val quiz = questions[currentQuestion]
            initQuiz(view, quiz)
        }

        if (currentQuestion == questions.size) {
            val action = QuestionFragmentDirections.actionQuizFragmentToResultsFragment()
            findNavController().navigate(action)
        }
    }

    private fun createRadioButton(
        view: View,
        text: String,
        rbColor: Int,
        textColor: Int
    ): RadioButton {
        val radioButton = RadioButton(view.context)
        radioButton.text = text
        radioButton.setTextColor(textColor)
        radioButton.buttonTintList = ColorStateList.valueOf(rbColor)
        radioButton.layoutParams = layoutParams

        if (!isCorrect) {
            radioButton.id = R.id.correct_radio_button_id
            isCorrect = true
        }

        return radioButton
    }
}