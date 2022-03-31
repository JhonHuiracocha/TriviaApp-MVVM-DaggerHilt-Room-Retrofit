package com.multi.trivia.ui.quiz.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.multi.trivia.R
import com.multi.trivia.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initSpinners(view)

        binding.bntStart.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToQuizFragment()
            findNavController().navigate(action)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initSpinners(view: View) {
        val amounts = resources.getStringArray(R.array.Amounts)
        val difficulties = resources.getStringArray(R.array.Difficulties)
        val types = resources.getStringArray(R.array.Types)

        val amountsAdapter =
            ArrayAdapter(view.context, android.R.layout.simple_expandable_list_item_1, amounts)

        val difficultyAdapter =
            ArrayAdapter(view.context, android.R.layout.simple_expandable_list_item_1, difficulties)

        val typeAdapter =
            ArrayAdapter(view.context, android.R.layout.simple_expandable_list_item_1, types)

        binding.amountAutoComplete.setAdapter(amountsAdapter)
        binding.difficultyAutoComplete.setAdapter(difficultyAdapter)
        binding.typeAutoComplete.setAdapter(typeAdapter)
    }
}