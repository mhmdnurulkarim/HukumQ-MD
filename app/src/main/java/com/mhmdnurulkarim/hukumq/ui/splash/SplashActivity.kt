package com.mhmdnurulkarim.hukumq.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.mhmdnurulkarim.hukumq.R
import com.mhmdnurulkarim.hukumq.ui.ViewModelFactory
import com.mhmdnurulkarim.hukumq.ui.login.SignInActivity
import com.mhmdnurulkarim.hukumq.utils.Const.TIME_SPLASH
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val splashViewModel: SplashViewModel by viewModels { ViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        runBlocking {
            launch {
                delay(TIME_SPLASH)
                splashViewModel.getThemeSetting().observe(this@SplashActivity) { isDarkMode ->
                    if (isDarkMode) {
                        move()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    } else {
                        move()
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    }
                }
            }
        }
    }

    private fun move() {
        startActivity(Intent(this, SignInActivity::class.java))
        finishAffinity()
    }
}