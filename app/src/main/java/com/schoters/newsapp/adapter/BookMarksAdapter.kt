package com.schoters.newsapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.schoters.newsapp.databinding.ItemBookmarksRowBinding
import com.schoters.newsapp.db.NewsEntity

class BookMarksAdapter : RecyclerView.Adapter<BookMarksAdapter.ViewHolder>(){
    private lateinit var binding: ItemBookmarksRowBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMarksAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemBookmarksRowBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: BookMarksAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: NewsEntity) {
            //InitView
            binding.apply {
                //Set text
                tvTitle.text = item.newsTitle
                tvDesc.text= item.newsDescription

                root.setOnClickListener {
                    // aksi ketika ditekan
                    Snackbar.make(binding.root, "akhirnya berhasil", Snackbar.LENGTH_LONG).show()
                }

            }
        }
    }
    private val differCallback = object : DiffUtil.ItemCallback<NewsEntity>() {
        override fun areItemsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem.newsId == newItem.newsId
        }

        override fun areContentsTheSame(oldItem: NewsEntity, newItem: NewsEntity): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}