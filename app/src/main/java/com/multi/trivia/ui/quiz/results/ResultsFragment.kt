package com.multi.trivia.ui.quiz.results

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.multi.trivia.data.model.Score
import com.multi.trivia.databinding.FragmentResultsBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class ResultsFragment : Fragment() {

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ResultsViewModel

    private val args: ResultsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            val action = ResultsFragmentDirections.actionResultsFragmentToWelcomeFragment()
            findNavController().navigate(action)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val points = args.points
        val categoryName = args.categoryName
        val score = Score(0, points, categoryName, Date())

        binding.points = points.toString()

        viewModel = ViewModelProvider(this)[ResultsViewModel::class.java]

        viewModel.insert(score)

        viewModel.statusRegister.observe(viewLifecycleOwner) { scoreId ->
            if (scoreId == null) {
                Toast.makeText(
                    view.context,
                    "An error occurred while registering the score",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }

            Toast.makeText(
                view.context,
                "Score registered successfully",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.btnPlayAgain.setOnClickListener {
            val action = ResultsFragmentDirections.actionResultsFragmentToHomeFragment()
            findNavController().navigate(action)
        }

        binding.btnExit.setOnClickListener {
            val action = ResultsFragmentDirections.actionResultsFragmentToWelcomeFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}