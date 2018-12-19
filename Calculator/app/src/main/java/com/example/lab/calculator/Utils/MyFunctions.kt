package com.example.lab.calculator.Utils

import android.view.ViewGroup

//ЯВЛЯЕТСЯ ЛИ СИМВОЛ ЦИФРОЙ?
fun Char.isDigit(): Boolean = if(this in '0'..'9') true else false //9 будет включено?

//ЯВЛЯЕТСЯ ЛИ СТРОКА ЧИСЛОМ?
fun String.isNumber(): Boolean{
    var commaCount = 0
    for (char in this){
        if (!char.isDigit()){
            if(char != '.'){
                return false
            }
        }
    }
    return true //есть косяк: на "34534," вернет true
}

//ЯВЛЯЕТСЯ ЛИ СТРОКА ЦЕЛЫМ ЧИСЛОМ?
fun String.isInt(): Boolean {
    if(!this.isNumber()) return false
    var flag = false

    for (char in this){
        if (char == '.'){
            flag = true
        }else {
            if(flag){
                if(char != '0')
                    return false
            }
        }
    }
    return true
}

//ЯВЛЯЕТСЯ ЛИ СТРОКА ОПЕРАТОРОМ?
fun String.isBynaryOperator():Boolean{
    val operators = arrayOf("+","-","×","/")
    return operators.contains(this)
}

//ПРИВОДИМ СТРОКУ К ЦЕЛОМУ ЧИСЛУ (ВЫЗЫВАЕТСЯ ТОЛЬКО КОГДА МЫ УВЕРЕНЫ, ЧТО ЭТО ВОЗМОЖНО)
fun String.asInt() = this.toDouble().toInt()


//ПОДСЧЕТ ОПРЕДЕЛЕННЫХ СИМВОЛОВ В СТРОКЕ
fun String.symbolCount(c: Char): Int{
    var count = 0

    for(char in this){
        if(char == c)
            count++
    }

    return count
}

//УДАЛЕНИЕ ПОСЛЕДНЕГО ЭЛЕМЕНТА
fun ArrayList<Double>.removeLast(){
    if(this.size > 0){
        this.removeAt(this.size-1)
    }
}

//КВАДРАТ
fun sqr(d: Double) = d*d

//делаем неактивным viewgroup
fun changeEnable(v: ViewGroup, flag: Boolean){
    for (i in 0..v.childCount-1){
        var child = v.getChildAt(i);
        child.isEnabled = flag
    }
}

fun ArrayList<String>.myReverse(): ArrayList<String>{
    if(this.isNotEmpty()){
        for (i in 0..(size/2)-1){
            this.swap(i, size-1-i)
        }
    }

    return this
}

fun ArrayList<String>.swap(index1: Int, index2: Int) : ArrayList<String> {
    val temp = this.get(index1)
    this.set(index1, this.get(index2))
    this.set(index2, temp)

    return this
}




