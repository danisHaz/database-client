package com.example.aviamirror.presentation.auth.authscreen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.aviamirror.R
import com.example.aviamirror.presentation.auth.AuthActivity
import com.example.aviamirror.utils.auth.User
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class AuthFragment : Fragment() {

    private var username: TextInputEditText? = null
    private var password: TextInputEditText? = null
    private var signIn: Button? = null
    private var loading: ProgressBar? = null

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_auth, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password)
        signIn = view.findViewById(R.id.loginOrRegister)
        loading = view.findViewById(R.id.loading)

        setObservers()
        setListeners()
    }

    private fun setListeners() {
        signIn?.setOnClickListener {
            Log.e(username?.text.toString(), password?.text.toString())
            authViewModel.signInAccount(User(username?.text.toString(), password?.text.toString()))
        }
    }

    private fun setObservers() {
        authViewModel.result.observe(viewLifecycleOwner) { result ->
            if (result.isLoading) {
                loading?.visibility = View.VISIBLE
            } else {
                loading?.visibility = View.INVISIBLE
            }

            if (result.isError) {
                setSnackbar(requireView(), "Log in failed", "Retry") {
                    Log.e(username?.text.toString(), password?.text.toString())
                    authViewModel.signInAccount(
                        User(
                            username?.text.toString(),
                            password?.text.toString()
                        )
                    )
                }
            }

            if (result.data != null) {
                (requireActivity() as AuthActivity).navigateToMainScreen()
            }

        }
    }

    private fun setSnackbar(
        view: View, message: String?,
        action: String?, toPerform: ((View) -> Unit)?
    ) {
        Snackbar.make(
            view,
            message ?: "Success",
            if (message != null) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
        ).setAction(
            action ?: "Success",
            toPerform
        ).show()
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        username = null
        password = null
        signIn = null
    }
}