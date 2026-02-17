package com.it342.g3.mobile.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.it342.g3.mobile.api.ApiClient
import com.it342.g3.mobile.api.MessageResponse
import com.it342.g3.mobile.api.RegisterRequest
import com.it342.g3.mobile.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val username = findViewById<EditText>(R.id.inputUsername)
        val email = findViewById<EditText>(R.id.inputEmail)
        val password = findViewById<EditText>(R.id.inputPassword)
        val message = findViewById<TextView>(R.id.message)
        val registerBtn = findViewById<Button>(R.id.btnRegister)

        registerBtn.setOnClickListener {
            message.text = ""
            val req = RegisterRequest(username.text.toString(), email.text.toString(), password.text.toString())
            ApiClient.service.register(req).enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    val body = response.body()
                    if (response.isSuccessful) {
                        message.text = body?.message ?: "Registered"
                    } else {
                        message.text = body?.message ?: "Registration failed"
                    }
                }
                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    message.text = "Network error"
                }
            })
        }
    }
}
