package com.example.newsapp.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.data.model.Article
import com.example.newsapp.MainActivity
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentDetailsBinding
import com.example.newsapp.some_values.SomeValues.mainActivity
import com.example.newsapp.viewmodels.DetailsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val vm: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //
        val bottomMenu = mainActivity.binding.bottomNavMenu
        bottomMenu.visibility = View.INVISIBLE
        val fragment = parentFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val controller = fragment?.findNavController()!!
        //
        val article = arguments?.getSerializable("article") as Article
        val fromWhereOpened = arguments?.getInt("fromWhereOpened")!!
        Glide.with(this).load(article.urlToImage).into(binding.headerImage)
        binding.headerImage.clipToOutline = true
        binding.articleTitle.text = article.title
        binding.articleDescription.text = article.description
        binding.articleButton.setOnClickListener {
            try {
                startActivity(Intent().setAction(Intent.ACTION_VIEW)
                    .addCategory(Intent.CATEGORY_BROWSABLE)
                    .setData(Uri.parse(article.url)))
            } catch (e: java.lang.Exception) {
                Toast.makeText(context, "Unable to open website", Toast.LENGTH_SHORT).show()
            }
        }
        binding.iconFavorite.setOnClickListener {
            vm.saveFavoriteArticle(article)
            Toast.makeText(context, "Successfully saved as favourite!", Toast.LENGTH_LONG).show()
        }
        binding.iconBack.setOnClickListener {
            when (fromWhereOpened) {
                1 -> controller.navigate(R.id.action_detailsFragment_to_mainFragment)
                2 -> controller.navigate(R.id.action_detailsFragment_to_searchFragment)
                3 -> controller.navigate(R.id.action_detailsFragment_to_favoriteFragment)
            }
            bottomMenu.visibility = View.VISIBLE
        }
    }
}