package com.schoters.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.schoters.newsapp.databinding.ActivityMainBinding
import com.schoters.newsapp.fragment.BlankNewsFragment
import com.schoters.newsapp.fragment.BookmarksFragment
import com.schoters.newsapp.fragment.NewsFragment
import com.schoters.newsapp.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var selectedFragment : Fragment = BlankNewsFragment()
        changeFragment(selectedFragment)

        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.newsMenu -> {selectedFragment = BlankNewsFragment()}
                R.id.bookmarksMenu -> {selectedFragment = BookmarksFragment()}
                R.id.profileMenu -> {selectedFragment = ProfileFragment()}
            }
            changeFragment(selectedFragment)
            true
        }
    }

    private fun changeFragment(selectedFragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, selectedFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack()
        }else {
            super.onBackPressed()
        }
    }
}