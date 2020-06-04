package com.mentarey.prototech.test.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mentarey.prototech.test.app.ext.replaceContainer
import com.mentarey.prototech.test.app.ui.login.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openLoginScreen()
    }

    private fun openLoginScreen() {
        replaceContainer(R.id.fragment_container, LoginFragment())
    }
}