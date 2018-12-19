package com.example.lab.calculator.Model

import android.util.Log

class InfixToPostfix {
    companion object {

        //разбиваем строку с выражением на маленькие строчки с членами выражения
        fun stringToArray(string: String): ArrayList<String> {
            var result = ArrayList<String>()
            var temp = StringBuilder()

            for (char in string) {
                if (char in '0'..'9' || char == '.') {    //если встретили цифру
                    temp.append(char)
                } else {
                    if(char == 'x'){
                        if(temp.isNotEmpty() && temp.last() != 'e'){
                            temp.append(char)
                            result.add(temp.toString())
                            temp.clear()
                        }else
                            temp.append(char)
                    }else{
                        if(char == 'p'){
                            if(temp.isNotEmpty()){
                                temp.append(char)
                                result.add(temp.toString())
                                temp.clear()
                            }else
                                temp.append(char)
                        }
                        else{
                            if(char == '√'|| char == 'e'){
                                temp.append(char)
                            }else {
                                if (char == '²' || char == 'i') {
                                    temp.append(char)
                                    result.add(temp.toString())
                                    temp.clear()
                                }else {
                                    if (temp.isNotEmpty()) {
                                        result.add(temp.toString())
                                        temp.clear()
                                    }
                                    if (char != ' ') {
                                        result.add(char.toString())
                                        temp.clear()
                                    }
                                }
                            }
                        }
                    }
                }
            }

            //если что-то еще не записали
            if (temp.isNotEmpty()) {
                result.add(temp.toString())
                temp.clear()
            }

            Log.d("MyLog", "str = ${string} arr = ${result}")

            return result
        }

        //переводим в ПОЛИЗ
        fun arrayToPostfix(expression: ArrayList<String>): ArrayList<String>{
            var result = ArrayList<String>()
            var stack = ArrayList<String>()

            for(string in expression){
                if(string.isNotEmpty()){
                        if(string.get(0).isDigit() || string.equals("x²") || string.equals("√x") || string.equals("pi") ||string.equals("exp")){
                            result.add(string)
                        }else{
                            if(string.equals("(")){
                                stack.add(string)
                            }else {
                                if(string.equals(")")){
                                   while(stack.isNotEmpty()){ //выталкиваем все из стека пока не встретим (
                                        if(!stack.last().equals("(")) {
                                            result.add(stack.last())
                                            stack.removeAt(stack.size-1) // -1 ??
                                        } else {
                                            stack.removeAt(stack.size-1) // -1 ??
                                            break
                                        }
                                    }
                                }
                                else{  //иначе когда string - бинарный оператор
                                    if(string.equals("+") || string.equals("-")){ //выталкиваем все пока встречаем +-*/
                                        while(stack.isNotEmpty() && (stack.last() == "+" || stack.last() == "-" ||                                                  stack.last() == "×"|| stack.last() == "/")) {
                                              result.add(stack.last())
                                              stack.removeAt(stack.size - 1)
                                        }
                                        stack.add(string)
                                    }else{
                                        while(stack.isNotEmpty()&& (stack.last() == "×" || stack.last() == "/")){                                                 result.add(stack.last())
                                            stack.removeAt(stack.size-1)
                                        }
                                        stack.add(string)
                                    }
                                }
                            }
                        }
                }
            }


            while (stack.isNotEmpty()){
                result.add(stack.last())
                stack.removeAt(stack.size-1)
            }


            return result
        }

    }

}