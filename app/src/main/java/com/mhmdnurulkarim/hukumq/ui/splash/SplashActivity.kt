package com.mhmdnurulkarim.hukumq.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mhmdnurulkarim.hukumq.R
import com.mhmdnurulkarim.hukumq.ui.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        runBlocking {
            launch {
                delay(2000)
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finishAffinity()
            }
        }
    }
}