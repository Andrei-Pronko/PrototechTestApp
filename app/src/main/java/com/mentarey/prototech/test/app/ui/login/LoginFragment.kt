package com.mentarey.prototech.test.app.ui.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.observe
import com.mentarey.prototech.test.app.R
import com.mentarey.prototech.test.app.ext.observeEvent
import com.mentarey.prototech.test.app.ext.replaceContainer
import com.mentarey.prototech.test.app.ext.toVisibility
import com.mentarey.prototech.test.app.ui.signals.SignalFragment
import com.mentarey.prototech.test.app.ui.state.UserAuthState
import com.mentarey.prototech.test.app.ui.utils.BaseFragment
import com.mentarey.prototech.test.app.ui.utils.EMPTY_LINE
import com.mentarey.prototech.test.app.ui.utils.FragmentToolbar
import kotlinx.android.synthetic.main.fragment_login.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val loginViewModel: LoginViewModel by viewModel()

    override fun builder(): FragmentToolbar = FragmentToolbar.Builder()
        .withId(R.id.toolbar_login)
        .withTitle(R.string.fragment_login_title)
        .build()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setUpAuthButton()
        setUpPasswordImeListener()
    }

    private fun observeViewModel() {
        loginViewModel.userAuthState.observeEvent(viewLifecycleOwner) {
            when (it) {
                is UserAuthState.Success -> {
                    clearAccountData()
                    openSignalsScreen()
                }
                is UserAuthState.Error ->
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }
        }
        loginViewModel.loadingProgress.observe(viewLifecycleOwner) {
            progressBar_login.apply {
                isIndeterminate = it
                visibility = it.toVisibility()
            }
        }
    }

    private fun setUpAuthButton() {
        button_auth_user.setOnClickListener { startAuth() }
    }

    private fun setUpPasswordImeListener() {
        editText_user_password.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> {
                    startAuth()
                    true
                }
                else -> false
            }
        }
    }

    private fun startAuth() {
        hideKeyboard()
        val login = editText_user_login.text.toString()
        val password = editText_user_password.text.toString()
        loginViewModel.authorizationWith(login, password)
    }

    private fun hideKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun clearAccountData() {
        editText_user_login.setText(EMPTY_LINE)
        editText_user_password.setText(EMPTY_LINE)
    }

    private fun openSignalsScreen() {
        activity.replaceContainer(R.id.fragment_container, SignalFragment(), true)
    }
}