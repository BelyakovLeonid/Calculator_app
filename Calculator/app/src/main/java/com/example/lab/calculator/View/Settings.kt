package com.example.lab.calculator.View

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.lab.calculator.R

class Settings : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        //устанавливаем toolBar
        val toolBar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolBar)

        findViewById<LinearLayout>(R.id.settings1).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.settings2).setOnClickListener(this)
        findViewById<LinearLayout>(R.id.settings3).setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.settings1 -> Toast.makeText(this,R.string.toast,Toast.LENGTH_SHORT).show()
            R.id.settings2 -> Toast.makeText(this,R.string.toast,Toast.LENGTH_SHORT).show()
            R.id.settings3 -> Toast.makeText(this,R.string.toast,Toast.LENGTH_SHORT).show()
        }
    }

    fun goBack(v: View){
        finish()
    }
}
