package com.multi.trivia.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.multi.trivia.data.model.Category
import com.multi.trivia.databinding.FragmentHomeBinding
import com.multi.trivia.ui.adapter.CategoryAdapter
import com.multi.trivia.utils.Constants.SESSION_MANAGER_NAME
import com.multi.trivia.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sessionManager: SessionManager

    private val categoryList: List<Category> = listOf(
        Category(9, "General Knowledge"),
        Category(10, "Entertainment: Books"),
        Category(11, "Entertainment: Film"),
        Category(15, "Entertainment: Video Games"),
        Category(19, "Science: Mathematics"),
        Category(21, "Sports"),
        Category(23, "History"),
        Category(26, "Celebrities")
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

        val user = sessionManager.getSession(SESSION_MANAGER_NAME)

        binding.user = user

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