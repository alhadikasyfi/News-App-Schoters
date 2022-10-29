package com.schoters.newsapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import coil.load
import com.google.android.material.snackbar.Snackbar
import com.schoters.newsapp.R
import com.schoters.newsapp.api.ApiClient
import com.schoters.newsapp.api.ApiServices
import com.schoters.newsapp.databinding.FragmentDetailNewsBinding
import com.schoters.newsapp.db.NewsDatabase
import com.schoters.newsapp.db.NewsEntity
import com.schoters.newsapp.response.TechnologyListResponse
import com.schoters.newsapp.utils.Constants.NEWS_DATABASE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailNewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailNewsFragment : Fragment() {

    private lateinit var binding : FragmentDetailNewsBinding
    private val args : DetailNewsFragmentArgs by navArgs()
    private val api: ApiServices by lazy {
        ApiClient().getClient().create(ApiServices::class.java)
    }
    // untuk database
    private val newsDB : NewsDatabase by lazy {
        Room.databaseBuilder(binding.root.context, NewsDatabase::class.java, NEWS_DATABASE)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }
    private lateinit var newsEntity: NewsEntity


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
        binding = FragmentDetailNewsBinding.inflate(inflater, container, false)
        //return inflater.inflate(R.layout.fragment_detail_news, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val argumentTitle = args.titleNews

        binding.apply {
            fabBookmarks.setOnClickListener{
                addToBookmarks(this)
            }

            //Call movies api
            val callDetailNewsApi = api.getDetailNews("id", "technology", "$argumentTitle")
            callDetailNewsApi.enqueue(object : Callback<TechnologyListResponse> {
                override fun onResponse(call: Call<TechnologyListResponse>, response: Response<TechnologyListResponse>) {

                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.articles[0].let { itData ->
                                    newsTitle.text = (itData.title) ?:"no Title"
                                    newsDescription.text = (itData.description) ?:"no Description"
                                    newsContent.text = (itData.content) ?:"no Content"
                                    newsName.text = (itData.author) ?:"nope"
                                    newsImage.load(itData.urlToImage){
                                        placeholder(R.drawable.tools_placeholder)
                                        error(R.drawable.tools_placeholder)
                                    }
                                }
                            }
                        }
                        in 300..399 -> {
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<TechnologyListResponse>, t: Throwable) {
                    Log.e("onFailure", "Err : ${t.message}")
                }
            })
        }
    }

    private fun addToBookmarks(binding: FragmentDetailNewsBinding) {
        newsEntity = NewsEntity(0,"${binding.newsTitle.text}"
            , "${binding.newsDescription.text}"
            , "${binding.newsContent.text}"
            , "${binding.newsName.text}")
        newsDB.dao().insertNews(newsEntity)
        Snackbar.make(binding.root, "Save to Bookmarks", Snackbar.LENGTH_LONG).show()
    }


}