package com.multi.trivia.ui.quiz.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.multi.trivia.data.model.Question
import com.multi.trivia.data.model.Result
import com.multi.trivia.databinding.FragmentQuestionBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private lateinit var questions: Array<Question>

    private var score = 0
    private var currentQuestion = 0

    private val args: QuestionFragmentArgs by navArgs()

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
        binding.question = question
        renderQuestion(view, question)
        binding.currentQuestion = "Question: ${currentQuestion + 1} / ${questions.size}"
    }

    private fun renderQuestion(view: View, question: Question) {
        val radioButtons: MutableList<RadioButton> = mutableListOf()

        binding.radioGroup.removeAllViews()

        val rbCorrectAnswer = RadioButton(view.context)
        rbCorrectAnswer.text = question.correctAnswer
        rbCorrectAnswer.contentDescription = "correct"
        radioButtons.add(rbCorrectAnswer)

        val incorrectAnswers = question.incorrectAnswers

        incorrectAnswers.forEach { incorrectAnswer ->
            val rbIncorrectAnswer = RadioButton(view.context)
            rbIncorrectAnswer.text = incorrectAnswer
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

        val radioButton = view.findViewById<RadioButton>(radioButtonCheckedId)

        if (radioButton.contentDescription?.toString() == "correct") score++

        nextQuestion(view)
    }

    private fun nextQuestion(view: View) {
        currentQuestion++

        if (currentQuestion <= questions.size - 1) {
            val quiz = questions[currentQuestion]
            initQuiz(view, quiz)
        }

        if (currentQuestion == questions.size) {
            val result = Result(score)
            val action = QuestionFragmentDirections.actionQuizFragmentToResultsFragment(result)
            findNavController().navigate(action)
        }
    }
}