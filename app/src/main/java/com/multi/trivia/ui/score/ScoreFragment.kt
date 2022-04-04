package com.multi.trivia.ui.score

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.multi.trivia.data.model.Score
import com.multi.trivia.databinding.FragmentScoreBinding
import com.multi.trivia.ui.adapter.ScoreAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreFragment : Fragment() {

    private var _binding: FragmentScoreBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ScoreViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScoreBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ScoreViewModel::class.java]

        viewModel.getScores().observe(viewLifecycleOwner) { scoreList ->
            initRecyclerView(scoreList)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView(scoreList: List<Score>) {

        val scoreAdapter = ScoreAdapter(scoreList)

        binding.rvScores.apply {
            adapter = scoreAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}