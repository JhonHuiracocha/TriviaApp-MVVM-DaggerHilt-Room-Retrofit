package com.multi.trivia.ui.quiz.question

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.addCallback
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.multi.trivia.data.model.Question
import com.multi.trivia.databinding.FragmentQuestionBinding
import com.multi.trivia.ui.quiz.confirm.ConfirmDialogDirections
import com.multi.trivia.utils.Decoder.fromBase64
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private var _binding: FragmentQuestionBinding? = null
    private val binding get() = _binding!!

    private lateinit var type: String
    private lateinit var categoryName: String
    private lateinit var correctAnswer: String
    private lateinit var questions: Array<Question>

    private var score = 0
    private var currentQuestion = 0

    private val args: QuestionFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = QuestionFragmentDirections.actionQuestionFragmentToConfirmQuizDialog()
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        questions = args.questions
        categoryName = args.categoryName
        type = args.type

        val question = questions[currentQuestion]

        decode(question)

        initQuiz(question)

        observeSubscribers()

        binding.progressBar.progress = currentQuestion + 1
        binding.progressBar.max = questions.size

        binding.btnSkip.setOnClickListener {
            nextQuestion()
        }

        binding.btnNext.setOnClickListener { checkQuestion(correctAnswer) }

    }

    private fun observeSubscribers() {
        findNavController()
            .currentBackStackEntry
            ?.savedStateHandle
            ?.getLiveData("selectedOption", "")
            ?.observe(viewLifecycleOwner) { result ->
                if (result == "Proceed") {
                    val action = ConfirmDialogDirections.actionConfirmDialogToHomeFragment()
                    findNavController().navigate(action)
                }
            }
    }

    private fun decode(question: Question) {
        val decodedQuestion = question.question.fromBase64()
        val decodedCorrectAnswer = question.correctAnswer.fromBase64()
        val decodedIncorrectAnswer = mutableListOf<String>()

        question.incorrectAnswers.forEach { answer ->
            val decodedAnswer = answer.fromBase64()
            decodedIncorrectAnswer.add(decodedAnswer)
        }

        question.question = decodedQuestion
        question.correctAnswer = decodedCorrectAnswer
        question.incorrectAnswers = decodedIncorrectAnswer
    }

    private fun initQuiz(question: Question) {
        initQuestion(question)
        binding.question = question.question
        correctAnswer = question.correctAnswer
        binding.currentQuestion = "${currentQuestion + 1} / ${questions.size}"
    }

    private fun initQuestion(question: Question) {
        val answers = shuffleAnswers(question)

        binding.rbA.text = answers[0]
        binding.rbB.text = answers[1]

        if (type == "boolean") {
            binding.rbC.visibility = View.GONE
            binding.rbD.visibility = View.GONE
            return
        }

        binding.rbC.text = answers[2]
        binding.rbD.text = answers[3]
    }

    private fun checkQuestion(correctAnswer: String) {
        val rbCheckedId = binding.radioGroup.checkedRadioButtonId

        binding.radioGroup.clearCheck()

        if (rbCheckedId == -1) {
            Toast.makeText(context, "You must select an option", Toast.LENGTH_SHORT).show()
            return
        }

        val radioButton = view?.findViewById<RadioButton>(rbCheckedId)

        if (radioButton?.text == correctAnswer) score++

        nextQuestion()
    }

    private fun nextQuestion() {
        currentQuestion++

        if (currentQuestion <= questions.size - 1) {
            val question = questions[currentQuestion]
            decode(question)
            initQuiz(question)
            binding.progressBar.progress = currentQuestion + 1
        }

        if (currentQuestion == questions.size) {
            val action =
                QuestionFragmentDirections.actionQuizFragmentToResultsFragment(
                    score,
                    categoryName,
                    questions.size
                )
            findNavController().navigate(action)
        }
    }

    private fun shuffleAnswers(question: Question): List<String> {
        val answers: MutableList<String> = mutableListOf(question.correctAnswer)
        question.incorrectAnswers.forEach { answer -> answers.add(answer) }
        return answers.shuffled()
    }
}