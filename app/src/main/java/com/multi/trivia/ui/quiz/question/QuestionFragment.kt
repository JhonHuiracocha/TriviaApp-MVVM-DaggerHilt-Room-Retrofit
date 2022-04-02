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
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.multi.trivia.R
import com.multi.trivia.data.model.Question
import com.multi.trivia.databinding.FragmentQuestionBinding
import com.multi.trivia.ui.quiz.exit.ExitDialogDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private lateinit var categoryName: String
    private lateinit var questions: Array<Question>

    private var points = 0
    private var currentQuestion = 0

    private val args: QuestionFragmentArgs by navArgs()

    private val layoutParams = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = QuestionFragmentDirections.actionQuestionFragmentToExitQuizDialog()
            findNavController().navigate(action)
        }

    }

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
        categoryName = args.categoryName

        initQuiz(view, questions[currentQuestion])

        observeSubscribers()

        binding.btnNext.setOnClickListener { checkQuestion(view) }

    }

    private fun observeSubscribers() {
        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData("selectedOption", "")
            ?.observe(viewLifecycleOwner) { result ->
                if (result == "Proceed") {
                    val action = ExitDialogDirections.actionExitQuizDialogToHomeFragment()
                    findNavController().navigate(action)
                }
            }
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
        rbCorrectAnswer.id = R.id.correct_radio_button_id
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

        if (radioButtonCheckedId == R.id.correct_radio_button_id) points++

        binding.radioGroup.clearCheck()

        nextQuestion(view)
    }

    private fun nextQuestion(view: View) {
        currentQuestion++

        if (currentQuestion <= questions.size - 1) {
            val quiz = questions[currentQuestion]
            initQuiz(view, quiz)
        }

        if (currentQuestion == questions.size) {
            val action =
                QuestionFragmentDirections.actionQuizFragmentToResultsFragment(points, categoryName)
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

        return radioButton
    }
}