/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;
import java.util.Set;

import java.util.Collection; 
import java.util.Map.Entry; 
import java.util.LinkedHashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.ir.BreakNode;
import sun.awt.shell.ShellFolder;
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
        
        ArrayList<String> var = new ArrayList<>(); //list of variables
        ArrayList<Integer> val = new ArrayList<>();//list of values
        ArrayList<String> sval = new ArrayList<>();//list of string values
        
        Stack<String> ArithOPR = new Stack<>();
        
        HashMap<String,Integer> varval = new HashMap<>();
        HashMap<String,String> svarval = new HashMap<>();
        
        String line;
        Scanner input = new Scanner(System.in);
        
        ArrayList<String> inputlines = new ArrayList<>(); //number of rows
        ArrayList <ArrayList<String>> PWord=new ArrayList <>();//per word 
        
        LinkedHashMap<ArrayList<String>, ArrayList<String>> values = new LinkedHashMap<ArrayList<String>, ArrayList<String>>(); 
        
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
//             System.out.println(lines[i]);

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
                
                ArrayList<String> readWord = PWord.get(a);
                
                if(!AllDT.contains(readWord.get(b))){
                    //Enters function that reads words that are: NEWLN, IS, INTO, BEG, PRINT
                    ProgramOperations(ArithOPR, AllOPR,input, readWord, b, var, sval,svarval, val, varval);
                }
                else{
                    //INT or STR read
                    CheckDataType(readWord, b, var, sval,svarval, val, varval);
                }
                
//                if(AllDT.contains(readWord.get(b))){
//                    //Enters function that differentiates if variable is INT or STR
//                    CheckDataType(readWord, b, var, sval,svarval, val, varval);
//                }   
//                else{
////                    ProgramOperations(ArithOPR, AllOPR,input, readWord, b, var, sval,svarval, val, varval);
//                }
                
                //---DO NOT REMOVE---
                if(readWord.size()!=b){
                    b++;
                } else break;   
            }
        }
    }
    
    public static void CheckDataType(ArrayList<String> readWord, int wordIndex, ArrayList<String> var_names, ArrayList<String> str_Val, HashMap<String,String> str_varval, ArrayList<Integer> int_Val, HashMap<String,Integer> int_varval){
        /*
            This function checks if the word scanned is INT or STR
            varval = int_varval,      svarval = str_varval,   var = var_names
            val    = int_Val,         varval  = int_Val,      b   = wordIndex
        */
        
        //IF STR IS READ
        if(readWord.get(wordIndex).equals("STR")){      
//            System.out.println("Data type is a STRING.");
            wordIndex++;
            var_names.add(readWord.get(wordIndex));
            str_Val.add(" ");
            str_varval.put(var_names.get(var_names.size()-1), str_Val.get(str_Val.size()-1));
//            System.out.println("str_vals"+str_varval);

            //For variables with assigned values
            if(readWord.size() != wordIndex){
//                System.out.println(readWord.size());
//                System.out.println(wordIndex);
                wordIndex++;
            }
        } 
        //IF INT IS READ
        else{       
//            System.out.println("Data type is an INTEGER.");
            wordIndex++;
            var_names.add(readWord.get(wordIndex));
            int_Val.add(0);
            int_varval.put(var_names.get(var_names.size()-1), int_Val.get(int_Val.size()-1));
//            System.out.println(int_varval);
            
            //For variables with assigned values
            if(readWord.size() != wordIndex){
                wordIndex++;
            }
        }
    }
    
    public static void ProgramOperations(Stack<String> ArithOPR, List<String> AllOPR, Scanner input, ArrayList<String> readWord, int wordIndex, ArrayList<String> var_names, ArrayList<String> str_Val, HashMap<String,String> str_varval, ArrayList<Integer> int_Val, HashMap<String,Integer> int_varval){
        //This function deals with operations NEWLN, IS, INTO, BEG, PRINT
        
        switch (readWord.get(wordIndex)) {
            case "NEWLN":   //NEWLN read - creats line
                System.out.println();
                break;
                
            case "IS":     //IS read for INT
                wordIndex++;
                
                if(isInteger(readWord.get(wordIndex))){
                    int_Val.add(strToInt(readWord.get(wordIndex)));
                    int_varval.put(var_names.get(var_names.size()-1), int_Val.get(int_Val.size()-1));
//                    System.out.println(int_varval);
                } else{
                    str_Val.add(readWord.get(wordIndex));
                    str_varval.put(var_names.get(var_names.size()-1), str_Val.get(str_Val.size()-1));
                }
                break;
                
            case "BEG":     //BEG read - asks for input
                //BEG should be able to check if variable na gina beg is INT or STR?
                //will only store values in string hashmap
                System.out.print("Input for "+ readWord.get(wordIndex+1) +": ");
                String beg_input = input.nextLine();
                
                wordIndex++;
                str_Val.add(beg_input);
                str_varval.put(readWord.get(wordIndex), str_Val.get(str_Val.size()-1));
                break;
            
            case "PRINT":   //PRINT read - prints specified STR variable
                /*
                    Function can ONLY print STR variable
                    cannot yet:
                        1. do direct print of arithmetic operation value (ex. PRINT MULT num 2)
                        2. PRINT  an INT variable values (ex. INT num IS 0 PRINT num)
                */
                wordIndex++;
                System.out.print(str_varval.put(readWord.get(wordIndex), str_Val.get(str_Val.size()-1)));
                
                
                //TEST CODE FOR PRINT-------------------------------------------------------------------
                /*
                    System.out.println(int_varval);
                    System.out.println(str_varval.get(readWord.get(wordIndex))); 

                    if(str_varval.containsKey(str_varval.get(readWord.get(wordIndex)))){
                        System.out.println("PRINT STR VAL");
                        System.out.print(str_varval.put(readWord.get(wordIndex), str_Val.get(str_Val.size()-1)));
                    } else if(int_varval.containsKey(int_varval.get(readWord.get(wordIndex)))){
                        System.out.println("PRINT INT VAL");
                        System.out.print(int_varval.put(readWord.get(wordIndex), int_Val.get(int_Val.size()-1)));
                    }
                    else{
                        System.out.println("did not enter if else");
                    }
                */
                
                break;
                
            case "INTO":
                //INTO var IS expr
                wordIndex++; wordIndex++; wordIndex++; 
                
                if(AllOPR.contains(readWord.get(wordIndex))){
                    //INSERT POSTFIX TO INFIX FOR ARITH
                    while(AllOPR.contains(readWord.get(wordIndex))){
                        ArithOPR.push(readWord.get(wordIndex));
                        wordIndex++;
                    }
                    
                    //if IDENT
                    if(isVar(readWord.get(wordIndex))){
                        String value = int_varval.get(readWord.get(wordIndex)).toString();
                        ArithOPR.push(value);
                    }
                    
                    //if VAL
                    if(isInteger(readWord.get(wordIndex))){
                        ArithOPR.push(readWord.get(wordIndex));
                    }
                    wordIndex++;
                    //if IDENT
                    if(isVar(readWord.get(wordIndex))){
                        String value = int_varval.get(readWord.get(wordIndex)).toString();
                        ArithOPR.push(value);
                    }
                    //if VAL
                    if(isInteger(readWord.get(wordIndex))){
                        ArithOPR.push(readWord.get(wordIndex));
                    }  
                    
                    //results from arith
                    String valeee = ArithOPR.toString();
                    Integer arithValue = evaluatePrefix(valeee);
                    int_varval.put(readWord.get(wordIndex-4), arithValue);
                    System.out.println("int_varval: " + int_varval);
                } 
                //INTO VAR IS VAL
                else{
                    int_Val.add(strToInt(readWord.get(+2)));
                    int_varval.put(readWord.get(wordIndex), int_Val.get(int_Val.size()-1));
                }
                
                break;
                
            default:
                break;
        }
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
        String exprsn = string.replaceAll("MULT","*").replaceAll("DIV","/").replaceAll("ADD","+").replaceAll("SUB","-").replaceAll("MOD","%").replaceAll("/*\\[*/", "").replaceAll("]", "").replaceAll(",", "").replaceAll(" ", "");
        Stack<Double> Stack = new Stack<Double>();
 
        for (int j = exprsn.length() - 1; j >= 0; j--){
            // Push operand to Stack
            // To convert exprsn[j] to digit subtract
            // '0' from exprsn[j].
             
            if (isOperand(exprsn.charAt(j)))
                Stack.push((double)(exprsn.charAt(j) - 48));
            else{
                // Operator encountered
                // Pop two elements from Stack
                double o1 = Stack.peek();
                double o2 = Stack.peek();
                Stack.pop();
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
