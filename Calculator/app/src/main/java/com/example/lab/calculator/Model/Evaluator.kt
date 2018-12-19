package com.example.lab.calculator.Model

import android.util.Log
import com.example.lab.calculator.Utils.isBynaryOperator
import com.example.lab.calculator.Utils.isNumber
import com.example.lab.calculator.Utils.removeLast
import com.example.lab.calculator.Utils.sqr
import kotlin.math.sqrt

class Evaluator {
    companion object {
        fun getResult(expression: ArrayList<String>): Double{
            val stack = ArrayList<Double>()

            for (element in expression){
                if(element.isNumber()){
                    stack.add(element.toDouble())
                }else{
                    if(element.equals("pi"))
                        stack.add(3.14159)
                    else
                        if(element.equals("exp"))
                            stack.add(2.718281)
                        else{
                            var result = 0.0

                            if(element.isBynaryOperator()){
                                val operand2 = stack.last()
                                stack.removeLast()
                                val operand1 = stack.last()
                                stack.removeLast()

                                when(element){
                                    "+"-> result = operand1+operand2
                                    "-"-> result = operand1-operand2
                                    "×"-> result = operand1*operand2
                                    "/"-> try{result = operand1/operand2}
                                    catch(e: ArithmeticException){
                                        Log.d("MyLog", "деление на ноль")}
                                }

                            }else{
                                val operand1 = stack.last()
                                stack.removeLast()

                                when(element){
                                    "√x" -> result = sqrt(operand1)
                                    "x²" -> result = sqr(operand1)
                                }
                            }

                            stack.add(result)
                        }
                }
            }

            return stack.last()
        }
    }
}