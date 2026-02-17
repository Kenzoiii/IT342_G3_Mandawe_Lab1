package com.it342.g3.mobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.it342.g3.mobile.api.ApiClient
import com.it342.g3.mobile.api.MessageResponse
import com.it342.g3.mobile.api.UserProfile
import com.it342.g3.mobile.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val username = findViewById<TextView>(R.id.textUsername)
        val email = findViewById<TextView>(R.id.textEmail)
        val role = findViewById<TextView>(R.id.textRole)
        val message = findViewById<TextView>(R.id.message)
        val logoutBtn = findViewById<Button>(R.id.btnLogout)

        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", "") ?: ""
        if (token.isEmpty()) {
            message.text = "Not logged in"
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        ApiClient.service.me("Bearer $token").enqueue(object : Callback<UserProfile> {
            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    username.text = body.username
                    email.text = body.email
                    role.text = body.role
                } else {
                    message.text = "Unauthorized"
                }
            }
            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                message.text = "Network error"
            }
        })

        logoutBtn.setOnClickListener {
            ApiClient.service.logout("Bearer $token").enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    prefs.edit().remove("token").apply()
                    startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                    finish()
                }
                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    prefs.edit().remove("token").apply()
                    startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
                    finish()
                }
            })
        }
    }
}
