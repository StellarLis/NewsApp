package com.example.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentSearchBinding
import com.example.newsapp.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private val vm: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        var job: Job? = null
        binding.edSearch.addTextChangedListener { text ->
            job?.cancel()
            job = MainScope().launch {
                delay(500)
                if (text.toString().isNotEmpty()) {
                    vm.setSearchNewsList(text.toString())
                }
            }
        }
        //
        val fragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val adapter = NewsAdapter(fragment?.findNavController()!!, 2)
        binding.searchRcView.adapter = adapter
        vm.searchNewsList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}