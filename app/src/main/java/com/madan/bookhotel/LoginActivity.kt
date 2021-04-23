package com.madan.bookhotel

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.madan.bookhotel.api.ServiceBuilder
import com.madan.bookhotel.notification.NotificationChannels
import com.madan.bookhotel.repository.UserRepository
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity(){

    private val permissions = arrayOf(
        android.Manifest.permission.CAMERA,
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvSignIn: TextView
    private lateinit var tvSignUp: TextView
    private lateinit var ivUsernameIcon: ImageView
    private lateinit var ivIcon: ImageView
    private lateinit var linearLayout: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etpassword)
        tvSignIn = findViewById(R.id.tvSignIn)
        tvSignUp = findViewById(R.id.tvSignUp)
        ivUsernameIcon = findViewById(R.id.ivUsernameIcon)
        ivIcon = findViewById(R.id.ivicon)
        linearLayout = findViewById(R.id.linearLayout)
        checkRunTimePermission()

        tvSignIn.setOnClickListener{
            login()
            loginNotification()
        }
        tvSignUp.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
        }
    }

    private fun checkRunTimePermission(){
        if( !hasPermission()){
            requestPermission()
        }
    }

    private fun hasPermission(): Boolean{
        var hasPermission = true
        for (permission in permissions) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    permission
            ) != PackageManager.PERMISSION_GRANTED){
                hasPermission = false
                break
            }
        }
        return hasPermission
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(this@LoginActivity, permissions, 1)
    }

    private fun login() {
        val username = etUsername.text.toString()
        val password = etPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository()
                val response = repository.checkUser(username, password)
                if (response.success == true){
                    ServiceBuilder.token = "Bearer " + response.token
                    ServiceBuilder.user = response.data

                    startActivity(
                        Intent(this@LoginActivity,MainActivity::class.java)
                    )
                    finish()
                }
                else{
                    println(response)
                    withContext(Dispatchers.Main){
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
            }catch (ex : Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(this@LoginActivity, "Login error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


//    for Notification
    private fun loginNotification(){
        val notificationManager = NotificationManagerCompat.from(this)
        val notificationChannels = NotificationChannels(this)
        notificationChannels.createNotificationChannels()

    val notification = NotificationCompat.Builder(this, notificationChannels.CHANNEL_1)
            .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
            .setContentTitle("Login Notification")
            .setContentText("Thank you for Login")
            .setColor(Color.BLUE)
            .build()

    notificationManager.notify(1, notification)

    }
}

