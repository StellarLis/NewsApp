package com.example.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentFavoriteBinding
import com.example.newsapp.viewmodels.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val vm: FavoriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        val fragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val adapter = NewsAdapter(fragment?.findNavController()!!, 3)
        binding.rcView.adapter = adapter
        vm.favouriteList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        vm.setFavouriteList()
        binding.iconDelete.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Alert")
                .setMessage("Are you sure you want to delete all your favourite news?")
                .setPositiveButton("Yes") { dialog, int ->
                    vm.deleteAllFavorite()
                    vm.setFavouriteList()
                    dialog.cancel()
                }
                .setNegativeButton("No") { dialog, int ->
                    dialog.cancel()
                }
                .create().show()
        }
    }
}