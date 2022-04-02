package com.multi.trivia.ui.quiz.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.multi.trivia.R
import com.multi.trivia.data.model.Category
import com.multi.trivia.data.model.Question
import com.multi.trivia.databinding.FragmentSettingsBinding
import com.multi.trivia.ui.quiz.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var category: Category
    private lateinit var viewModel: QuizViewModel

    private val args: SettingsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category = args.category

        binding.category = category

        initMenus()

        viewModel = ViewModelProvider(this)[QuizViewModel::class.java]

        binding.bntStart.setOnClickListener { startQuiz() }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initMenus() {
        val amounts = resources.getStringArray(R.array.Amounts)
        val difficulties = resources.getStringArray(R.array.Difficulties)
        val types = resources.getStringArray(R.array.Types)

        val amountsAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            amounts
        )

        val difficultyAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            difficulties
        )

        val typeAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_expandable_list_item_1,
            types
        )

        binding.amountAutoComplete.setAdapter(amountsAdapter)

        binding.difficultyAutoComplete.setAdapter(difficultyAdapter)

        binding.typeAutoComplete.setAdapter(typeAdapter)
    }

    private fun startQuiz() {
        val amount = binding.amountAutoComplete.text.trim()
        val difficulty = binding.difficultyAutoComplete.text.trim()
        val type = binding.typeAutoComplete.text.trim()

        if (amount.isEmpty() || difficulty.isEmpty() || type.isEmpty()) {
            Toast.makeText(requireContext(), "You must enter all fields", Toast.LENGTH_SHORT).show()
            return
        }

        val amountSetting = amount.toString().toInt()
        val difficultySetting = difficulty.toString().lowercase()
        val typeSetting = if (type.toString() == "Multiple Choice") "multiple" else "boolean"

        showProgressBar()

        viewModel.fetchQuiz(amountSetting, category.id, difficultySetting, typeSetting)

        viewModel.questionList.observe(viewLifecycleOwner) { response ->
            hideProgressBar()

            if (response.isSuccessful) {
                response.body()?.let { quizResponse ->
                    if (quizResponse.responseCode == 0) {
                        val questionList = quizResponse.questionList
                        val questions: Array<Question> = questionList.map { it }.toTypedArray()

                        val action =
                            SettingsFragmentDirections.actionSettingsFragmentToQuizFragment(
                                questions,
                                category.name
                            )
                        findNavController().navigate(action)

                        return@observe
                    }

                    Toast.makeText(
                        requireContext(),
                        "No quiz found with these settings",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }
}