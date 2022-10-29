package com.schoters.newsapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.schoters.newsapp.R
import com.schoters.newsapp.adapter.NewsAdapter
import com.schoters.newsapp.api.ApiClient
import com.schoters.newsapp.api.ApiServices
import com.schoters.newsapp.databinding.FragmentNewsBinding
import com.schoters.newsapp.response.TechnologyListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {

    private lateinit var binding: FragmentNewsBinding
    private lateinit var newsAdapter: NewsAdapter
    private val api: ApiServices by lazy {
        ApiClient().getClient().create(ApiServices::class.java)
    }

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

        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        newsAdapter=NewsAdapter(onClick = {
            val action =NewsFragmentDirections.actionNewsFragmentToDetailNewsFragment("${it.title}")
            Navigation.findNavController(binding.root).navigate(action)
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            //show loading
            prgBarMovies.visibility = View.VISIBLE
            //Call movies api
            val callNewsApi = api.getheadLinesTechnology("id", "technology")
            callNewsApi.enqueue(object : Callback<TechnologyListResponse> {
                override fun onResponse(call: Call<TechnologyListResponse>, response: Response<TechnologyListResponse>) {
                    prgBarMovies.visibility = View.GONE
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            response.body()?.let { itBody ->
                                itBody.articles.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        newsAdapter.differ.submitList(itData)
                                        //Recycler
                                        rvNews.apply {
                                            layoutManager = LinearLayoutManager(rvNews.context)
                                            Log.d("kou koku kou koku okkkuko", "asldkjaldskfj")
                                            adapter = newsAdapter
                                        }
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
                    prgBarMovies.visibility = View.GONE
                    Log.e("onFailure", "Err : ${t.message}")
                }
            })
        }
    }

}