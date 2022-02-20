package com.example.hanwha_kimdahye

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.hanwha_kimdahye.databinding.ActivityMainBinding
import com.example.hanwha_kimdahye.ui.view.CategorySelectFragment
import com.example.hanwha_kimdahye.ui.view.FavoritesFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.fl_main, CategorySelectFragment())
            .commit()
        binding.bottomNaviMain.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_search -> changeFragment(CategorySelectFragment())
                else -> changeFragment(FavoritesFragment())
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fl_main, fragment)
            .commit()
    }
}
