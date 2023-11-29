package com.mhmdnurulkarim.hukumq.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mhmdnurulkarim.hukumq.MainActivity
import com.mhmdnurulkarim.hukumq.R
import com.mhmdnurulkarim.hukumq.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(mainLooper).postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }, 2000)
    }
}