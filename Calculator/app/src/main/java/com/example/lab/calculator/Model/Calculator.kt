package com.example.lab.calculator.Model

import android.util.Log

class Calculator {
    companion object {
        fun solve(expression: String): Double{
            Log.d("MyLog",expression)
            val newExpression = expression.replace(',','.')
            Log.d("MyLog",newExpression)
            val expInArray = InfixToPostfix.stringToArray(newExpression)
            Log.d("MyLog","$expInArray")
            val expInPostfix = InfixToPostfix.arrayToPostfix(expInArray)
            Log.d("MyLog","$expInPostfix")
            return Evaluator.getResult(expInPostfix)
        }
    }
}