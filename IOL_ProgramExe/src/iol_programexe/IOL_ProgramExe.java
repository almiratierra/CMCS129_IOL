/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.util.LinkedHashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author USER
 */
public class IOL_ProgramExe {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException { 
        // TODO code application logic here
        String[] DT = new String[]{"INT","STR"}; //datatypes
        String[] OPR = new String[]{"MOD","DIV", "MULT","SUB", "ADD",}; //arith operation
        
        List<String> AllDT = Arrays.asList(DT);
        List<String> AllOPR = Arrays.asList(OPR);
        
        ArrayList<String> var = new ArrayList<>(); //list of variables names
        ArrayList<Integer> val = new ArrayList<>();//list of int assigned values
        ArrayList<String> sval = new ArrayList<>();//list of string assigned values
        ArrayList<String> svar = new ArrayList<>(); //list of string variables
        ArrayList<String> ivar = new ArrayList<>(); //list of integer variables
        
        Stack<String> ArithOPR = new Stack<>();
        
        HashMap<String,Integer> varval = new HashMap<>();//hashmap for (varname, assigned value)
        HashMap<String,String> svarval = new HashMap<>();//hasmap for (varname, assgined value)
        HashMap<String, ArrayList<String>> dtsvarval = new HashMap<>(); //hashmap for varnames and their corresponding data type
        
        
        
        String line;
        Scanner input = new Scanner(System.in);
        
        ArrayList<String> inputlines = new ArrayList<>(); //number of rows
        ArrayList <ArrayList<String>> PWord=new ArrayList <>();//per word 
        
        LinkedHashMap<ArrayList<String>, ArrayList<String>> values = new LinkedHashMap<>(); 
        
        File file=new File("C:\\Users\\tierr\\Desktop\\IOLtoken.iol");
        FileReader filer = new FileReader(file);
        BufferedReader brr = new BufferedReader(filer); 
        
        //WHILE LOOP READS THE TEXT FILE AND ADDS EVERY READ LINE IN inputLines arraylist
        //int i=0 records the number of lines
        int i = 0; 
        while((line=brr.readLine())!=null){
            inputlines.add(line);
            i++;  
        }
        
        //FOR LOOP that reads every word in every line (element) in inputLines arraylist
        for(int a=0; a < inputlines.size(); a++){
//             Stack<String> s = new Stack<>();
            //System.out.println(lines[i]);

            //save per word in a line
            String woo[] = inputlines.get(a).split("\\s+");
            ArrayList <String> perword = new ArrayList <>();

            perword.addAll(Arrays.asList(woo));
            PWord.add(perword);//Main arraylist
        }
        
        //FOR LOOP - reads every line and read every word inside line
        for(int a=0; a<inputlines.size(); a++){
            int b = 0;
            
            //WHILE LOOP for every word read
            while(PWord.get(a).size() > b) {
                //DEFINING VARIABLES
                //INT/STR read
                ArrayList<String> readWord = PWord.get(a);
                
                if(!AllDT.contains(readWord.get(b))){
                    //Enters function that reads words that are: NEWLN, IS, INTO, BEG, PRINT
                    ProgramOperations(ArithOPR, AllOPR, input, readWord, b, var, dtsvarval, sval, svarval, val, varval);
                }
                else{
                    CheckDataType(readWord, b, var, dtsvarval, sval, svar, svarval, val, ivar, varval);
                }
                
                //---DO NOT REMOVE---
                if(readWord.size()!=b){
                    b++;
                } else break;   
            }
        }
    }
    
    public static void CheckDataType(ArrayList<String> readWord, int wordIndex, ArrayList<String> var_names, HashMap<String, 
            ArrayList<String>> var_dataType, ArrayList<String> str_Val, ArrayList<String> str_Var, HashMap<String,String> str_VarVal, 
            ArrayList<Integer> int_Val, ArrayList<String> int_Var, HashMap<String,Integer> int_VarVal){
        /*
            This function checks if the word scanned is INT or STR
            b = wordIndex   readWord = PWord.get(a)
            var          = var_names    sval    = str_Val       val    = int_Val    
            var_dataType = dtsvarval    svar    = str_Var       ivar   = int_Var
                                        svarval = str_VarVal    varval = int_VarVal
        */
        
        //IF STR IS READ
        if(readWord.get(wordIndex).equals("STR")){      
//            System.out.println("Data type is a STRING.");
            wordIndex++;
            var_names.add(readWord.get(wordIndex));
            str_Var.add(readWord.get(wordIndex));
            
            str_Val.add(" ");
            str_VarVal.put(readWord.get(wordIndex), str_Val.get(str_Val.size()-1));
            var_dataType.put(readWord.get(wordIndex-1), str_Var);
//            System.out.println("var_dataType STR "+var_dataType);

        } 
        //IF INT IS READ
        else{       
//            System.out.println("Data type is an INTEGER.");
            wordIndex++;
            var_names.add(readWord.get(wordIndex));
            int_Var.add(readWord.get(wordIndex));
            
            int_Val.add(0);
            int_VarVal.put(readWord.get(wordIndex), int_Val.get(int_Val.size()-1));
            var_dataType.put(readWord.get(wordIndex-1), int_Var);
//            System.out.println("var_dataType INT "+var_dataType);
            
            //IF INT variable has assigned value
            if(readWord.size()-1 != wordIndex && readWord.get(wordIndex+1).equals("IS")){
                wordIndex++; wordIndex++;
                int_Val.add(strToInt(readWord.get(wordIndex)));
                int_VarVal.put(readWord.get(wordIndex-2), int_Val.get(int_Val.size()-1));
                var_dataType.put(readWord.get(wordIndex-3), int_Var);
            }
        }
    }
    
    public static void ProgramOperations(Stack<String> ArithOPR, List<String> AllOPR, Scanner input, ArrayList<String> readWord, int wordIndex, 
            ArrayList<String> var_names, HashMap<String, ArrayList<String>> var_dataType, ArrayList<String> str_Val, HashMap<String,String> str_VarVal, 
            ArrayList<Integer> int_Val, HashMap<String,Integer> int_VarVal){
        //This function deals with operations NEWLN, IS, INTO, BEG, PRINT
        
        switch (readWord.get(wordIndex)) {
            case "NEWLN":   //NEWLN read - creats line
                System.out.println();
                break;
                
            case "IS":     //IS read for INT
                wordIndex++;
                if(isInteger(readWord.get(wordIndex))){
                    int_Val.add(strToInt(readWord.get(wordIndex)));
                    int_VarVal.put(var_names.get(var_names.size()-1), int_Val.get(int_Val.size()-1));
//                    System.out.println(int_VarVal);
                }
                break;
                
            case "BEG":     //BEG read - asks for input
                System.out.print("Input for "+ readWord.get(wordIndex+1) +": ");
                String beg_input = input.nextLine();
                wordIndex++;
                
                //TYPE CHECKING------------------
                //IF PRINT VAR (INT)
                if(var_dataType.get("INT") != null && var_dataType.get("INT").contains(readWord.get(wordIndex)) 
                        && isInteger(beg_input)){
                    int_Val.add(strToInt(beg_input));
                    int_VarVal.put(readWord.get(wordIndex), int_Val.get(int_Val.size()-1));
//                    System.out.println(int_VarVal); //TEST ONLY - SHOULD NOT PRINT  
//                    System.out.println(int_VarVal.put(readWord.get(wordIndex), int_Val.get(int_Val.size()-1)));
                }
                //IF PRINT VAR (STR)
                else if(var_dataType.get("STR") != null && var_dataType.get("STR").contains(readWord.get(wordIndex)) 
                        && !isInteger(beg_input)){
                    str_Val.add(beg_input);
                    str_VarVal.put(readWord.get(wordIndex), str_Val.get(str_Val.size()-1));
//                    System.out.println(str_VarVal); //TEST ONLY - SHOULD NOT PRINT

                }
                else{
                    //insert DATA TYPE INCOMPATIBLE ERROR and terminate program
                    System.out.println("WRONG DATA TYPE");
                } 
                break;
            
            case "PRINT":   //PRINT read - prints specified STR variable
                wordIndex++;
                
                //IF strVariable is read
                if(AllOPR.contains(readWord.get(wordIndex))){
                    System.out.println("PRINT: " + readWord.get(wordIndex));
                    String calc = isOPR(readWord, wordIndex, int_VarVal, AllOPR, ArithOPR);
                    Integer arithValue = evaluatePrefix(calc);
                    System.out.print(arithValue);
                }
                else{
                    if(var_dataType.get("INT") != null && var_dataType.get("INT").contains(readWord.get(wordIndex))){
                        System.out.print(int_VarVal.put(readWord.get(wordIndex), int_VarVal.get(readWord.get(wordIndex))));
                    }
                    if(var_dataType.get("STR") != null && var_dataType.get("STR").contains(readWord.get(wordIndex))){
                        System.out.print(str_VarVal.put(readWord.get(wordIndex), str_VarVal.get(readWord.get(wordIndex))));
                    }
                }
                break;
                
            case "INTO":
                //INTO var IS expr
                wordIndex++;  wordIndex++; wordIndex++; 
//                System.err.println(readWord.get(wordIndex));
                if(AllOPR.contains(readWord.get(wordIndex))){
                    String calc = isOPR(readWord, wordIndex, int_VarVal, AllOPR, ArithOPR);
                    Integer arithValue = evaluatePrefix(calc);
//                    System.out.println("arval "+arithValue);
//                    System.out.println("wordIndex "+readWord.get(wordIndex-2));
                    int_VarVal.put(readWord.get(wordIndex-2), arithValue);
                    System.out.println(int_VarVal);
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
    }
    
    public static String isOPR(ArrayList<String> readWord, int wordIndex, HashMap<String,Integer> int_VarVal, List<String> AllOPR, Stack<String> ArithOPR){
    //this functiona allows a statement to do its arithmetic operations
    //INSERT POSTFIX TO INFIX FOR ARITH
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
        return (arithVal);
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
        if(flag == 0){
            return false;
        } else return true;
    } 
    
    public static boolean isVar(String str) {
        int i = 1;
//        int flag=0;
  
        if(checkSpecialChar(str.charAt(0)) == false && (str.charAt(0) >= 'a' || str.charAt(0) <= 'z'|| 
                str.charAt(0)>= 'A' || str.charAt(0) <= 'Z')&&!(str.charAt(0) == '0' || str.charAt(0) <= '9')){
            for (; i < str.length(); i++) {
                char c = str.charAt(i);
                
                if (c >= '0' || c <= '9'||c >= 'a' || c <= 'z'|| c >= 'A' || c <= 'Z') {
                    if(i==str.length()-1) return true;
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
        String exprsn = string.replaceAll("MULT","*").replaceAll("DIV","/").replaceAll("ADD","+").replaceAll("SUB","-").replaceAll("MOD","%").replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", "").replaceAll(" ", "");
        Stack<Double> Stack = new Stack<>();
        
        for (int j = exprsn.length() - 1; j >= 0; j--){
            // Push operand to Stack
            // To convert exprsn[j] to digit subtract
            // '0' from exprsn[j].
            if (isOperand(exprsn.charAt(j))){
                Stack.push((double)(exprsn.charAt(j) - 48));
            }
            else{
                // Operator encountered
                // Pop two elements from Stack
                double o1 = Stack.peek();
                Stack.pop();
                
                double o2 = Stack.peek();
                Stack.pop();
 
                // Use switch case to operate on o1
                // and o2 and perform o1 O o2.
                switch (exprsn.charAt(j)) {
                    case '+':
                        Stack.push(o1 + o2);
                        break;
                    case '-':
                        Stack.push(o1 - o2);
                        break;
                    case '*':
                        Stack.push(o1 * o2);
                        break;
                    case '/':
                        Stack.push(o1 / o2);
                        break;
                    case '%':
                        Stack.push(o1 % o2);
                        break;    
                }
            }
        }
        double answer = Stack.peek();
        int value = (int)Math.round(answer);
        return(value);
    }
}


/*

//Getting Set of keys 
        Set<String> keySet = Lexemes_all.keySet(); 
        ArrayList<String> listOfKeys = new ArrayList<String>(keySet); 
        Collection<ArrayList<String>> values = Lexemes_all.values(); 
        //Creating an ArrayList of values 
       ArrayList<ArrayList<String>> listOfValues = new ArrayList<ArrayList<String>>(values);
       //PRINT KEY:VALUE
         for(int i=0;i<listOfKeys.size();i++)
        { 
            for(int j=0;j<listOfValues.get(i).size();j++)
                { 
                    System.out.println(listOfKeys.get(i)+":"+listOfValues.get(i).get(j)); 
                }
            
        }
        
            System.out.println(listOfValues.get(1).get(2)); 

                IDENT.add(words[j])



INSERTING VARIABLE AND VALUE - -1 is the last element
varval.put(vars.get(vars.size()-1), val.get(val.size()-1)); 

PWord.lastIndexOf(PWord.get(a))

*/
