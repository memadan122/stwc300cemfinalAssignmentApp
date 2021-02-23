package com.madan.bookhotelwear

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.madan.bookhotel.api.ServiceBuilder
import com.madan.bookhotelwear.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private val permissions = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvSignIn: TextView
    private lateinit var ivUsernameIcon: ImageView
    private lateinit var ivIcon: ImageView
    private lateinit var linearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etpassword)
        tvSignIn = findViewById(R.id.tvSignIn)
        ivUsernameIcon = findViewById(R.id.ivUsernameIcon)
        ivIcon = findViewById(R.id.ivicon)
        linearLayout = findViewById(R.id.linearLayout)

        tvSignIn.setOnClickListener {
            login()
        }

    }
    private fun login() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.checkUser(username, password)
                if (response.success == true) {
                    ServiceBuilder.token = "Bearer " + response.token
                    startActivity(
                            Intent(this@LoginActivity, MainActivity::class.java)
                    )
                    finish()
                } else {
                    println(response)
                    withContext(Dispatchers.Main) {
                        val snack = Snackbar.make(
                                linearLayout,
                                "Invalid credentials",
                                Snackbar.LENGTH_LONG
                        )
                        snack.setAction("Ok", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, ex.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
