package com.example.lab.calculator.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab.calculator.Model.Calculator.Companion.solve
import com.example.lab.calculator.Utils.asInt
import com.example.lab.calculator.Utils.isInt
import com.example.lab.calculator.Utils.symbolCount
import kotlin.math.round

class CalcViewModel: ViewModel() {

    val history = MutableLiveData<ArrayList<String>>()
    val expression = MutableLiveData<String>()
    val result = MutableLiveData<String>()
    val historyShown = MutableLiveData<Boolean>()

    init {
        history.value = ArrayList<String>()
        expression.value = "0"
        result.value = "0"
        historyShown.value = false
    }

    fun getHistory(): LiveData<ArrayList<String>> = history
    fun getExpression(): LiveData<String> = expression
    fun getResult(): LiveData<String> = result
    fun getHistoryShown(): LiveData<Boolean> = historyShown


    fun changeHistoryShown(){
        historyShown.value = !historyShown.value!!
    }

    fun deleteHistory(index: Int){
        try{
            history.value?.removeAt(index)
        }catch (e: IndexOutOfBoundsException){
            Log.d("MyLog", "exception")
        }
    }

    //функция добавления цифры в выражение
    fun addDigit(str: String){
        val txt = expression.value!!

        if(txt.equals("0")){
            expression.value = str
        }else {
            if(txt.last() in '0'..'9'|| txt.last() == ',' || txt.last() == '('|| txt.last() == ' ')
            expression.value = "${expression.value}$str"
        }
    }

    //функция добавления операнда в выражение
    fun addOperand(str: String){
        var text = expression.value!!
        val ch = text.last()

        if(ch in '0'..'9' || ch == ')' || text.endsWith("√x")|| text.endsWith("x²") || ch == 'i'||ch == 'p'){

            expression.value = "$text $str "

        } else { //если мы нажали на операнд, когда последний элемент в выражении - не цифра
            var n = 0 //сколько символов нужно убрать, прежде чем добавить операнд
            if(ch == ',')
                n = 1
            else
                n = 3

            val txtWithoutOperand = text.substring(0, text.length - n)
            expression.value = "$txtWithoutOperand $str "
        }
    }

    //функция добавления запятой в выражение
    fun addComa(){
        val text = expression.value!!

        if(text.last() in '0'..'9'){
            var index = text.length-1       //защита от нескольких запятых в одном числе
            while(index >= 0) {
                if (text.get(index) !in '0'..'9'){
                    if(text.get(index) == ',')
                        return
                    else
                        break
                }
                index--
            }
            expression.value = "${expression.value},"
        }
    }

    //функция добавления скобки (
    fun addOpenBracket(){
        var text = expression.value!!

        if (text.equals("0")){
            text = "("
        }else{
            if(text.last() == ' ' || text.last() == '(')
                text = text + "("
        }

        expression.value = text
    }

    //функция добавления скобки )
    fun addCloseBracket(){
        var text = expression.value!!
        val ch = text.last()

        if(!text.equals("0") && (text.symbolCount('(') > text.symbolCount(')'))){
            if(text.last() in '0'..'9' || text.last() == ')' || text.endsWith("√x")|| text.endsWith("x²")|| ch == 'i'||ch == 'p')
                text = text + ")"
            else
                if(text.last() == ',') {
                    text = text.substring(0,text.length-2) + ")"
                }
        }

        expression.value = text
    }

    //функция добавления квадрата
    fun addSqr(){
        var text = expression.value!!
        val ch = text.last()

        if(!text.equals("0")){
            if(ch in '0'..'9' || ch == ')' || ch == ','|| text.endsWith("√x")|| text.endsWith("x²")|| ch == 'i'||ch == 'p') {
                if(ch == ',') text = text.substring(0, text.length-1)
                text = "$text x²"
            }
        }

        expression.value = text
    }

    //функция добавления корня
    fun addSqrt(){
        var text = expression.value!!
        val ch = text.last()

        if(!text.equals("0")){
           if(ch in '0'..'9' || ch == ')' || ch == ','|| text.endsWith("√x")|| text.endsWith("x²") || ch == 'i'||ch == 'p'){
               if(ch == ',') text = text.substring(0, text.length-1)
               text = "$text √x"
            }
        }

        expression.value = text
    }

    //функция очистки выражения
    fun clearText(){
        result.value = "0"
        expression.value = "0"
    }

    //при нажатии на кнопку backspace(<-)
    fun backspace(){
        val txt = expression.value!!
        val ch = txt.last()
        var n = 0

        if(!txt.equals("0")){

            if((ch in '0'..'9') || ch == ',' || ch == ')'|| ch == '(')
                n = 1
            else
                if(txt.endsWith(" exp"))
                    n = 4
                else
                    if(txt.equals("pi"))
                        n=2
                    else
                        n=3
            val newText = if(txt.length == n) "0" else txt.substring(0, txt.length - n)
            expression.value = newText
        }

    }

    fun addExp(){
        var text = expression.value!!
        val ch = text.last()

        if(text.equals("0")){
           text = "exp"
        }else{
            if(ch == '(' || ch == ' '){
                text = text + " exp"
            }
        }

        expression.value = text
    }


    fun addPi(){
        var text = expression.value!!
        val ch = text.last()

        if(text.equals("0")){
            text = "pi"
        }else{
            if(ch == '(' || ch == ' '){
                text = text + " pi"
            }
        }

        expression.value = text
    }


    //функция вычисления
    fun evaluate(){
        val text = expression.value!!

        //если выражение заканчивается на оператор (лишний), например: 12+34- , то убираем его:
        if(text.last() == ' ') expression.value = text.substring(0, text.length - 3)
        if(text.last() == ',') expression.value = text.substring(0, text.length - 1)

        val res = solve(expression.value!!).toString()
        result.value = if (res.isInt()) res.asInt().toString() else (round(res.toDouble()*100000)/100000).toString()//чтобы сразу округлять выражения вида 123,00

        //сохраняем в историю
        history.value?.add("${expression.value} = ${result.value}")
    }
}