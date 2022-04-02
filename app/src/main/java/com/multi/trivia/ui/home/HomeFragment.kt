package com.multi.trivia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.multi.trivia.data.model.Category
import com.multi.trivia.databinding.FragmentHomeBinding
import com.multi.trivia.ui.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val categoryList: List<Category> = listOf(
        Category(9, "General Knowledge"),
        Category(11, "Entertainment: Film"),
        Category(12, "Entertainment: Music"),
        Category(15, "Entertainment: Video Games"),
        Category(21, "Sports"),
        Category(22, "Geography"),
        Category(23, "History"),
        Category(25, "Art"),
        Category(29, "Entertainment: Comics"),
        Category(31, "Entertainment: Japanese Anime & Manga")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view, categoryList)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView(view: View, categoryList: List<Category>) {

        val categoryAdapter = CategoryAdapter(categoryList)

        binding.rvCategories.apply {
            adapter = categoryAdapter
            layoutManager = LinearLayoutManager(view.context)
            addItemDecoration(
                DividerItemDecoration(
                    view.context,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }
}