/*
This class was created by Gelacio. 
Code testing by Tierra, debugging by Gelacio.
Cleaned for readability and adjusted for integration to main file by Tierra.
This class is called in the IOL_GUI.java (main class).
This class is responsible for the Execution functions of the IOL_GUI. It is composed of:
    1. CheckDataType - checks and stores variables with data types (INT, STR)
    2. ProgramOperations - executed actions that corresponds to the keyword (Assignment, input, output operations)
    3. isOPR - checks if word is operation (ADD, SUB, MULT, DIV, MOD) and will call evaluatePrefix
    4. evaluatePrefix - converts line with OPR word to prefix expression and executes arithmetic operation, returns value to isOPR
    5. checkSpecialChar
    6. isInteger
    7. isVar
    8. strToInt
    9. isOperand
*/
package IOL_ProjectPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import javax.swing.JOptionPane;


public class ExecuteFunctions {
    
    //executes input
    public static void CheckDataType(ArrayList<String> readWord, int wordIndex, List<String> var_names, HashMap<String, 
            ArrayList<String>> var_dataType, ArrayList<String> str_Val, ArrayList<String> str_Var, HashMap<String,String> str_VarVal, 
            ArrayList<Integer> int_Val, ArrayList<String> int_Var, HashMap<String,Integer> int_VarVal){
        /*
            This function checks if the word scanned is INT or STR
            b = wordIndex   readWord = PWord.get(a)
            VarFound          = var_names    sval    = str_Val       val    = int_Val    
            var_dataType = dtsvarval    svar    = str_Var       ivar   = int_Var
                                        svarval = str_VarVal    varval = int_VarVal
        */
        
        //IF STR IS READ
        if(readWord.get(wordIndex).equals("STR")){      
            wordIndex++;
            var_names.add(readWord.get(wordIndex));
            str_Var.add(readWord.get(wordIndex));
            
            str_Val.add(" ");
            str_VarVal.put(readWord.get(wordIndex), str_Val.get(str_Val.size()-1));
            var_dataType.put(readWord.get(wordIndex-1), str_Var);

        } 
        //IF INT IS READ
        else{       
            wordIndex++;
            var_names.add(readWord.get(wordIndex));
            int_Var.add(readWord.get(wordIndex));
            
            int_Val.add(0);
            int_VarVal.put(readWord.get(wordIndex), int_Val.get(int_Val.size()-1));
            var_dataType.put(readWord.get(wordIndex-1), int_Var);
            
            //IF INT variable has assigned value
            if(readWord.size()-1 != wordIndex && readWord.get(wordIndex+1).equals("IS")){
                wordIndex++; wordIndex++;
                int_Val.add(strToInt(readWord.get(wordIndex)));
                int_VarVal.put(readWord.get(wordIndex-2), int_Val.get(int_Val.size()-1));
                var_dataType.put(readWord.get(wordIndex-3), int_Var);
            }
        }
    }
    
    public static String ProgramOperations(Stack<String> ArithOPR, List<String> AllOPR, ArrayList<String> readWord, int wordIndex, 
            List<String> var_names, HashMap<String, ArrayList<String>> var_dataType, ArrayList<String> str_Val, HashMap<String,String> str_VarVal, 
            ArrayList<Integer> int_Val, HashMap<String,Integer> int_VarVal){
        //This function deals with operations NEWLN, IS, INTO, BEG, PRINT
        String output = "";
        switch (readWord.get(wordIndex)) {
            case "NEWLN":   //NEWLN read - creats line
                output = "\n";
                break;
                
            case "IS":     //IS read for INT
                wordIndex++;
                if(isInteger(readWord.get(wordIndex))){
                    int_Val.add(strToInt(readWord.get(wordIndex)));
                    int_VarVal.put(var_names.get(var_names.size()-1), int_Val.get(int_Val.size()-1));
                }
                break;
                
            case "BEG":     //BEG read - asks for input
                String beg_input = JOptionPane.showInputDialog(
                              null, "Input for "+ readWord.get(wordIndex+1) +": ");
                output = "Input for "+ readWord.get(wordIndex+1) +": " + beg_input + "\n";
                wordIndex++;
                
                //TYPE CHECKING------------------
                //IF PRINT VAR (INT)
                if(var_dataType.get("INT") != null && var_dataType.get("INT").contains(readWord.get(wordIndex)) 
                        && isInteger(beg_input)){
                    int_Val.add(strToInt(beg_input));
                    int_VarVal.put(readWord.get(wordIndex), int_Val.get(int_Val.size()-1));
                }
                //IF PRINT VAR (STR)
                else if(var_dataType.get("STR") != null && var_dataType.get("STR").contains(readWord.get(wordIndex)) 
                        && !isInteger(beg_input)){
                    str_Val.add(beg_input);
                    str_VarVal.put(readWord.get(wordIndex), str_Val.get(str_Val.size()-1));

                }
                else{
                    //insert DATA TYPE INCOMPATIBLE ERROR and terminate program
                    output = "Wrong data type. \n";
                } 
                break;
            
            case "PRINT":   //PRINT read - prints specified STR variable
                wordIndex++;
                
                //IF strVariable is read
                if(AllOPR.contains(readWord.get(wordIndex))){
                    String calc = isOPR(readWord, wordIndex, int_VarVal, AllOPR, ArithOPR);
                    Integer arithValue = evaluatePrefix(calc);
                    output = arithValue.toString();
                }
                else{
                    if(var_dataType.get("INT") != null && var_dataType.get("INT").contains(readWord.get(wordIndex))){
                        output = int_VarVal.put(readWord.get(wordIndex), int_VarVal.get(readWord.get(wordIndex))).toString();
                    }
                    if(var_dataType.get("STR") != null && var_dataType.get("STR").contains(readWord.get(wordIndex))){
                        output = (str_VarVal.put(readWord.get(wordIndex), str_VarVal.get(readWord.get(wordIndex))));
                    }
                }
                break;
                
            case "INTO":
                //INTO var IS expr
                wordIndex++;  wordIndex++; wordIndex++; 
                if(AllOPR.contains(readWord.get(wordIndex))){
                    String calc = isOPR(readWord, wordIndex, int_VarVal, AllOPR, ArithOPR);
                    Integer arithValue = evaluatePrefix(calc);
                    int_VarVal.put(readWord.get(wordIndex-2), arithValue);
                } 
                //INTO VAR IS VAL
                else{
                    int_Val.add(strToInt(readWord.get(wordIndex+2)));
                    int_VarVal.put(readWord.get(wordIndex), int_Val.get(int_Val.size()-1));
                }
                
                break;
                
            default:
                break;
        }
        return output;
    }
    
    public static String isOPR(ArrayList<String> readWord, int wordIndex, HashMap<String,Integer> int_VarVal, List<String> AllOPR, Stack<String> ArithOPR){
    //this function allows a statement to do its arithmetic operations
        while(AllOPR.contains(readWord.get(wordIndex))){
            ArithOPR.push(readWord.get(wordIndex));
            wordIndex++;
        }
        //if IDENT
        if(isVar(readWord.get(wordIndex))){
            String value = (int_VarVal.get(readWord.get(wordIndex))).toString();
            ArithOPR.push(value);
        }

        //if VAL
        if(isInteger(readWord.get(wordIndex))){
            ArithOPR.push(readWord.get(wordIndex));
        }
        wordIndex++;

        //if IDENT
        if(isVar(readWord.get(wordIndex))){
            String value = (int_VarVal.get(readWord.get(wordIndex))).toString();
            ArithOPR.push(value);
        }

        //if VAL
        if(isInteger(readWord.get(wordIndex))){
            ArithOPR.push(readWord.get(wordIndex));
        }  

        //results from arith
        String arithVal = ArithOPR.toString();
        ArithOPR.clear();    //clears stack for next arith oper
        return (arithVal);
    
    
    /*
        code snippet causes an empty stack error
        while(wordIndex != readWord.size()-1){
            System.out.println("ind " + wordIndex);
            System.out.println("size " + readWord.size());
            //if VAL
            if(!AllOPR.contains(readWord.get(wordIndex)) && isVar(readWord.get(wordIndex))){ 
                String value = (int_VarVal.get(readWord.get(wordIndex))).toString();
                ArithOPR.push(value);
            }
            //IF OP or VAL
            else{ ArithOPR.push(readWord.get(wordIndex));}
            wordIndex++;
        }
        ArithOPR.push(readWord.get(wordIndex));   
        String arithVal = ArithOPR.toString();
        ArithOPR.clear();    //clears stack for next arith oper
        return (arithVal);
    */
    
    }
        
    static boolean checkSpecialChar(char ch) {
        char[] specialCh = {'!','@',']','#','$','%','^','&','*','-','.'}; 
        
        for (Character c : specialCh) {
            if (ch == c) return true;
        }
        return false;
    }
    
    public static boolean isInteger(String str) {
        int flag=1;
        if (str == null) return false;
        
        int length = str.length();
        if (length == 0) return false;
        
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) return false;
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);

            if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')||c == '.'||checkSpecialChar(c)==true) {
                flag=0;
            }
        }
        return flag != 0;
    } 
    
    public static boolean isVar(String str) {
        int i = 1;
  
        if(checkSpecialChar(str.charAt(0)) == false && (str.charAt(0) >= 'a' || str.charAt(0) <= 'z'|| 
                str.charAt(0)>= 'A' || str.charAt(0) <= 'Z')&&!(str.charAt(0) == '0' || str.charAt(0) <= '9')){
            for (; i < str.length(); i++) {
                char c = str.charAt(i);
                
                if (c >= '0' || c <= '9'||c >= 'a' || c <= 'z'|| c >= 'A' || c <= 'Z') {
                    if(i == str.length()-1) return true;
                }
                if(checkSpecialChar(c)==true) return false;
             }
        }
        else return false;
        return false;
    }
    
    public static int strToInt(String str){
        int i = 0;
        int num = 0;
        boolean isNeg = false;

        // Check for negative sign; if it's there, set the isNeg flag
        if(str.charAt(0) == '-'){
            isNeg = true;
            i = 1;
        }

        // Process each character of the string;
        while( i < str.length()) {
            num *= 10;
            // Minus the ASCII code of '0' to get the value of the charAt(i++).
            num += str.charAt(i++) - '0'; 
        }

        if (isNeg) num = -num;
        return num;
    }   
    
    static Boolean isOperand(char c){
        // If the character is a digit
        // then it must be an operand
        if (c >= 48 && c <= 57)
            return true;
        else
            return false;
    }
    
    public static Integer evaluatePrefix(String string){
        //change Word operations to oper
        //change Word operations to oper
        
        String p ,n = "";
        StringBuffer b;
        int i, op1, op2;
        char c;
        
        Stack<Integer> s=new Stack<Integer>();
        p = string.replaceAll("MULT","*").replaceAll("DIV","/").replaceAll("ADD","+").replaceAll("SUB","-").replaceAll("MOD","%").replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", "").replaceAll(" ", " ");
               
        i = p.length()-1;
        while(i>=0) {
            c = p.charAt(i);
            if(c >= 48 && c <= 57) n = n+c;
            else if(c == ' ' && !n.equals("")){
            /*handles both single and multidigit numbers*/
                b = new StringBuffer(n);b.reverse();n=b.toString();
                s.push(Integer.parseInt(n));
                n = "";
            }
            else {
                switch (c) {
                    case '+':
                        op1=s.pop();
                        op2=s.pop();
                        s.push(op1+op2);
                        break;
                    case '-':
                        op1=s.pop();
                        op2=s.pop();
                        s.push(op1-op2);
                        break;
                    case '*':
                        op1=s.pop();
                        op2=s.pop();
                        s.push(op1*op2);
                        break;
                    case '%':
                        op1=s.pop();
                        op2=s.pop();
                        s.push(op1%op2);
                        break;
                    case '/':
                        op1=s.pop();
                        op2=s.pop();
                        s.push(op1/op2);
                        break;
                    default:
                        break;
                }
            }
            i--;
        }
        double answer = s.peek();
        int value = (int)Math.round(answer);
        return value;
    }

}
