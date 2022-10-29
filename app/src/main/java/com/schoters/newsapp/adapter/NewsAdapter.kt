package com.schoters.newsapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.schoters.newsapp.R
import com.schoters.newsapp.databinding.ItemrowBinding
import com.schoters.newsapp.response.TechnologyListResponse

class NewsAdapter(private val onClick: (TechnologyListResponse.Article)->Unit) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private lateinit var binding: ItemrowBinding
    private lateinit var context : Context

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root){
        fun bin(item: TechnologyListResponse.Article){
            binding.apply {
                newsTitle.text = item.title
                newsAuthor.text = item.author
                newsPublishedAt.text = item.publishedAt
                newsImage.load(item.urlToImage){
                    placeholder(R.drawable.tools_placeholder)
                    error(R.drawable.tools_placeholder)
                }
                root.setOnClickListener{
                    onClick(item)
                }

            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemrowBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bin(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private val differCallBak=object : DiffUtil.ItemCallback<TechnologyListResponse.Article>(){
        override fun areItemsTheSame(oldItem: TechnologyListResponse.Article, newItem: TechnologyListResponse.Article): Boolean {
            return oldItem.source.id == newItem.source.id
        }

        override fun areContentsTheSame(oldItem: TechnologyListResponse.Article, newItem: TechnologyListResponse.Article): Boolean {
            return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,differCallBak)
}