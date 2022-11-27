package com.example.newsapp.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.model.Article
import com.example.newsapp.R
import com.example.newsapp.databinding.ItemArticleBinding

class NewsAdapter(
    private val controller: NavController,
    private val mainOrSearchOrFavorite: Int // If on main fragment, then value should be 1, etc.
) : ListAdapter<Article, NewsAdapter.Holder>(Comparator()) {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemArticleBinding.bind(view)
        fun bind(item: Article) = with(binding) {
            Glide.with(itemView).load(item.urlToImage).into(articleImage)
            articleImage.clipToOutline = true
            articleText.text = item.title
            articleDate.text = item.publishedAt
        }
    }
    class Comparator : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("article", getItem(position))
            bundle.putInt("fromWhereOpened", mainOrSearchOrFavorite)
            if (mainOrSearchOrFavorite == 1) {
                controller.navigate(
                    R.id.action_mainFragment_to_detailsFragment,
                    bundle
                )
            } else if (mainOrSearchOrFavorite == 2) {
                controller.navigate(
                    R.id.action_searchFragment_to_detailsFragment,
                    bundle
                )
            } else {
                controller.navigate(
                    R.id.action_favoriteFragment_to_detailsFragment,
                    bundle
                )
            }
        }
    }
}