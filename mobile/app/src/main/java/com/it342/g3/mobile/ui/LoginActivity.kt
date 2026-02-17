package com.it342.g3.mobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.it342.g3.mobile.api.ApiClient
import com.it342.g3.mobile.api.LoginRequest
import com.it342.g3.mobile.api.LoginResponse
import com.it342.g3.mobile.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val email = findViewById<EditText>(R.id.inputEmail)
        val password = findViewById<EditText>(R.id.inputPassword)
        val message = findViewById<TextView>(R.id.message)
        val loginBtn = findViewById<Button>(R.id.btnLogin)
        val toRegister = findViewById<Button>(R.id.btnToRegister)

        loginBtn.setOnClickListener {
            message.text = ""
            val req = LoginRequest(email.text.toString(), password.text.toString())
            ApiClient.service.login(req).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    val body = response.body()
                    if (response.isSuccessful && body?.token != null) {
                        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                        prefs.edit().putString("token", body.token).apply()
                        startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                        finish()
                    } else {
                        message.text = body?.message ?: "Login failed"
                    }
                }
                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    message.text = "Network error"
                }
            })
        }

        toRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
