package com.example.aviamirror.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aviamirror.R
import com.example.aviamirror.presentation.auth.authscreen.AuthFragment
import com.example.aviamirror.presentation.main.MainActivity

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState ?: setAuthFragment()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putBoolean("isInitial", false)
    }

    fun navigateToMainScreen() {
        supportFragmentManager.popBackStack()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun setAuthFragment() {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, AuthFragment())
            .setReorderingAllowed(true)
            .commit()
    }
}