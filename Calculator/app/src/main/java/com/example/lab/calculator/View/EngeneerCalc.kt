package com.example.lab.calculator.View

import android.content.Intent
import android.os.Bundle
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

class EngeneerCalc : AppCompatActivity(), View.OnClickListener {

    lateinit var myViewModel: CalcViewModel
    lateinit var resultText: TextView
    lateinit var expressionText: TextView
    lateinit var historyButton: ImageView
    lateinit var keyBoard: GridLayout
    val fragment = HistoryFragment.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_engeneer_calc)

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
        findViewById<Button>(R.id.button0)?.setOnClickListener(this)
        findViewById<Button>(R.id.button1)?.setOnClickListener(this)
        findViewById<Button>(R.id.button2)?.setOnClickListener(this)
        findViewById<Button>(R.id.button3)?.setOnClickListener(this)
        findViewById<Button>(R.id.button4)?.setOnClickListener(this)
        findViewById<Button>(R.id.button5)?.setOnClickListener(this)
        findViewById<Button>(R.id.button6)?.setOnClickListener(this)
        findViewById<Button>(R.id.button7)?.setOnClickListener(this)
        findViewById<Button>(R.id.button8)?.setOnClickListener(this)
        findViewById<Button>(R.id.button9)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonC)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonComa)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonPi)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonDev)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonMul)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonPlus)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonExp)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonMinus)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonEqual)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonOB)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonCB)?.setOnClickListener(this)
        findViewById<ImageView>(R.id.im1)?.setOnClickListener(this)
        findViewById<ImageView>(R.id.im2)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonSqr)?.setOnClickListener(this)
        findViewById<Button>(R.id.buttonSqrt)?.setOnClickListener(this)

        resultText = findViewById(R.id.resultText)
        expressionText = findViewById(R.id.expresionText)
        historyButton = findViewById(R.id.im1)
        keyBoard = findViewById(R.id.keyBoard)

        //Настраиваем меню
        val myMeny = PopupMenu(this, findViewById(R.id.menuIcon))
        myMeny.menu.add(0, 0, 0, "Стандартный вид")
        myMeny.menu.add(0, 1, 0, "Конвертер")

        myMeny.setOnMenuItemClickListener {
            when(it.itemId){
                0 -> finish()
                1-> Toast.makeText(this, "В разработке", Toast.LENGTH_SHORT).show()
            }
            return@setOnMenuItemClickListener true
        }

        findViewById<ImageView>(R.id.menuIcon).setOnClickListener {
            myMeny.show()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        if(myViewModel.getHistoryShown().value!!){
            myViewModel.changeHistoryShown()
            openHistory()
        }
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {

        if(myViewModel.getHistoryShown().value!!){
            closeFragment(null)
            myViewModel.changeHistoryShown()
        }

        super.onSaveInstanceState(outState)
    }

    //при нажатии на кнопку истории
    fun openHistory(){
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

    //при закрытии окна истории
    fun closeFragment(v: View?){
        myViewModel.changeHistoryShown()
        changeEnable(keyBoard, true)
        historyButton.setBackgroundColor(0x000000)
        historyButton.setImageResource(R.drawable.history_grey)
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }

    //при нажатии на иконку настроек
    fun goToSettings(v: View?){
        startActivity(Intent(this, Settings::class.java))
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
            R.id.buttonPi -> myViewModel.addPi()
            R.id.buttonExp -> myViewModel.addExp()
            R.id.buttonOB -> myViewModel.addOpenBracket()
            R.id.buttonCB -> myViewModel.addCloseBracket()
            R.id.buttonComa -> myViewModel.addComa()
            R.id.buttonEqual -> myViewModel.evaluate()
            R.id.buttonSqr -> myViewModel.addSqr()
            R.id.buttonSqrt -> myViewModel.addSqrt()

            R.id.im1 -> openHistory()
            R.id.im2 -> myViewModel.backspace()
        }
    }


    //обновление поля выражения при нажатии на любую клавишу
    fun refreshExpression(str: String){
        expressionText.text = str
    }

    //обновление поля результата при нажатии на клавишу =
    fun refreshResult(str: String){
        resultText.text = str
    }
}
