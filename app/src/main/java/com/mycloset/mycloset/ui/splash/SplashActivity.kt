package com.mycloset.mycloset.ui.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mycloset.mycloset.R
import com.mycloset.mycloset.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed({

            startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()


        }, 2100)
    }
}
