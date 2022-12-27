package com.example.aviamirror.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.aviamirror.R
import com.example.aviamirror.presentation.main.mainscreen.MainFragment
import com.example.aviamirror.presentation.main.ticketscreen.BuyTicketFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: setMainFragment()
    }

    fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, null)
            .addToBackStack(fragment::class.java.name)
            .setReorderingAllowed(true)
            .commit()
    }

    private fun setMainFragment() {
        Log.e("MainActivity", "set main")
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, MainFragment())
            .setReorderingAllowed(true)
            .commit()
    }
}