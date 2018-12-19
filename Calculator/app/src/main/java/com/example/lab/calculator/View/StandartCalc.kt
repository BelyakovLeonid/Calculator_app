package com.example.lab.calculator.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.lab.calculator.R
import com.example.lab.calculator.Utils.changeEnable
import com.example.lab.calculator.ViewModel.CalcViewModel

class StandartCalc : AppCompatActivity(), View.OnClickListener {

    lateinit var resultText: TextView
    lateinit var expressionText: TextView
    lateinit var historyButton: ImageView
    lateinit var keyBoard: GridLayout
    lateinit var myViewModel: CalcViewModel
    val fragment = HistoryFragment.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standart_calc)

       //устанавливаем toolBar
        val toolBar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolBar)

        //подключаемся к viewModel
        myViewModel = ViewModelProviders.of(this).get(CalcViewModel::class.java)
        myViewModel.getExpression().observe(this, Observer{
            refreshExpression(it)
        })

        myViewModel.getResult().observe(this, Observer{
            refreshResult(it)
        })

        //устанавливаем обработчик нажатий клавиш
        findViewById<Button>(R.id.button0).setOnClickListener(this)
        findViewById<Button>(R.id.button1).setOnClickListener(this)
        findViewById<Button>(R.id.button2).setOnClickListener(this)
        findViewById<Button>(R.id.button3).setOnClickListener(this)
        findViewById<Button>(R.id.button4).setOnClickListener(this)
        findViewById<Button>(R.id.button5).setOnClickListener(this)
        findViewById<Button>(R.id.button6).setOnClickListener(this)
        findViewById<Button>(R.id.button7).setOnClickListener(this)
        findViewById<Button>(R.id.button8).setOnClickListener(this)
        findViewById<Button>(R.id.button9).setOnClickListener(this)
        findViewById<Button>(R.id.buttonC).setOnClickListener(this)
        findViewById<Button>(R.id.buttonComa).setOnClickListener(this)
        findViewById<Button>(R.id.buttonExp).setOnClickListener(this)
        findViewById<Button>(R.id.buttonDev).setOnClickListener(this)
        findViewById<Button>(R.id.buttonMul).setOnClickListener(this)
        findViewById<Button>(R.id.buttonPlus).setOnClickListener(this)
        findViewById<Button>(R.id.buttonMinus).setOnClickListener(this)
        findViewById<Button>(R.id.buttonEqual).setOnClickListener(this)
        findViewById<ImageView>(R.id.im1).setOnClickListener(this)
        findViewById<ImageView>(R.id.im2).setOnClickListener(this)

        resultText = findViewById(R.id.resultText)
        expressionText = findViewById(R.id.expresionText)
        historyButton = findViewById(R.id.im1)
        keyBoard = findViewById(R.id.keyBoard)

        val menu = PopupMenu(this, findViewById(R.id.menuIcon))
        menu.menu.add(0, 0, 0, "Инженерный вид")
        menu.menu.add(0, 1, 0, "Конвертер")

        menu.setOnMenuItemClickListener {
            when(it.itemId){
                0 -> goToEngeneer(null)
                1-> Toast.makeText(this, R.string.toast, Toast.LENGTH_SHORT).show()
            }
            return@setOnMenuItemClickListener true
        }

        findViewById<ImageView>(R.id.menuIcon).setOnClickListener {
           menu.show()
        }
    }

    //при нажатии на кнопку истории
    fun openHistory(){
        val his = myViewModel.getHistory().value
        Log.d("MyLog", "${his}")

        if(myViewModel.getHistoryShown().value!!){ //повторное нажатие на иконку истории закроет историю
            closeFragment(null)
        }else{
            myViewModel.changeHistoryShown()
            changeEnable(keyBoard, false)
            historyButton.setBackgroundResource(R.color.colorDark)
            historyButton.setImageResource(R.drawable.history_white)
            supportFragmentManager.beginTransaction()
                .add(R.id.historyFragment, fragment)
                .commit()
        }
    }


    //при нажатии на иконку настроек
    fun goToSettings(v: View?){
        startActivity(Intent(this, Settings::class.java))
    }

    //для перехода в инженерный вид
    fun goToEngeneer(v: View?){
        startActivity(Intent(this, EngeneerCalc::class.java))
    }

    //обработчик нажатий на клавиатуру калькулятора
    override fun onClick(v: View?) {
        when (v?.id){
            R.id.button0 -> myViewModel.addDigit("0")
            R.id.button1 -> myViewModel.addDigit("1")
            R.id.button2 -> myViewModel.addDigit("2")
            R.id.button3 -> myViewModel.addDigit("3")
            R.id.button4 -> myViewModel.addDigit("4")
            R.id.button5 -> myViewModel.addDigit("5")
            R.id.button6 -> myViewModel.addDigit("6")
            R.id.button7 -> myViewModel.addDigit("7")
            R.id.button8 -> myViewModel.addDigit("8")
            R.id.button9 -> myViewModel.addDigit("9")
            R.id.buttonC -> myViewModel.clearText()
            R.id.buttonPlus -> myViewModel.addOperand("+")
            R.id.buttonMinus -> myViewModel.addOperand("-")
            R.id.buttonDev -> myViewModel.addOperand("/")
            R.id.buttonMul -> myViewModel.addOperand("×")
            R.id.buttonExp -> myViewModel.addPi()
            R.id.buttonComa -> myViewModel.addComa()
            R.id.buttonEqual -> myViewModel.evaluate()
            R.id.im1 -> openHistory()
            R.id.im2 -> myViewModel.backspace()
        }
    }

    fun closeFragment(v: View?){
        myViewModel.changeHistoryShown()
        changeEnable(keyBoard, true)
        historyButton.setBackgroundColor(0x000000)
        historyButton.setImageResource(R.drawable.history_grey)
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }

    fun refreshExpression(str: String){
        expressionText.text = str
    }

    fun refreshResult(str: String){
        resultText.text = str
    }

}
