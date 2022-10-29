package com.schoters.newsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.schoters.newsapp.R
import com.schoters.newsapp.adapter.BookMarksAdapter
import com.schoters.newsapp.databinding.FragmentBookmarksBinding
import com.schoters.newsapp.db.NewsDatabase
import com.schoters.newsapp.db.NewsEntity
import com.schoters.newsapp.utils.Constants.NEWS_DATABASE

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookmarksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookmarksFragment : Fragment() {

    lateinit var binding : FragmentBookmarksBinding
    private val newsDB : NewsDatabase by lazy {
        Room.databaseBuilder(binding.root.context,NewsDatabase::class.java,NEWS_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private val bookmarksAdapter by lazy { BookMarksAdapter() }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarksBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_bookmarks, container, false)

        //untuk menghapus database
        binding.fabDeletebookmarks.setOnClickListener{
            newsDB.dao().deleteAll()
            checkItem()
        }

        return binding.root
    }
    override fun onResume() {
        super.onResume()
        checkItem()
    }

    private fun checkItem() {
        binding.apply {
            if(newsDB.dao().getAllNews().isNotEmpty()){
                rvBookmarks.visibility= View.VISIBLE
                tvNobookmarks.visibility=View.GONE
                bookmarksAdapter.differ.submitList(newsDB.dao().getAllNews())
                setupRecyclerView()
            }else{
                rvBookmarks.visibility=View.GONE
                tvNobookmarks.visibility=View.VISIBLE
            }
        }
    }
    private fun setupRecyclerView(){
        binding.rvBookmarks.apply {
            layoutManager= LinearLayoutManager(this.context)
            adapter= bookmarksAdapter
        }

    }
}