package com.example.lab.calculator.View

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.lab.calculator.R

class StartScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        Handler().postDelayed(Runnable {
            startActivity(Intent(this, StandartCalc::class.java))
            finish()
        },1000)
    }
}
