/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iol_programexe;
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
        
        int sentenceCount = 0; 
        //CONVERT AllLexemes(Array) TO AllLexemess(LIST)
        
        String[] DT= new String[]{"INT","STR"}; //datatypes
//        String[] input= new String[]{"BEG"}; //input operation
        String[] operations= new String[]{"MOD","DIV", "MULT","SUB", "ADD",}; //arith operation
        List<String> Alloperations = Arrays.asList(operations);
//        String[] output= new String[]{"PRINT"}; //output operation
//        String[] input= new String[]{"BEG"}; //input operation
        List<String> AllDT = Arrays.asList(DT);
        ArrayList<String> var = new ArrayList<>();; //list of variables
        ArrayList<Integer> val = new ArrayList<>();//list of values
        ArrayList<String> sval = new ArrayList<>();//list of string values
        Stack<String> ArithOperations = new Stack<String>();
        HashMap<String,Integer> varval = new HashMap<String, Integer>();
        HashMap<String,String> svarval = new HashMap<String, String>();
       //ArrayList for STRING_LIT
       
       //contains the lexemes Form: <Token name = Attribute_Value>
        LinkedHashMap<String, String> var_val = new LinkedHashMap<String, String>(); 
  
        String line;
       Scanner input=new Scanner(System.in);
        
        
        //used
        ArrayList<String> inputlines = new ArrayList<String>(); //number of rows
        ArrayList <ArrayList<String>> PWord=new ArrayList <>();//per word 
        LinkedHashMap<ArrayList<String>, ArrayList<String>> values = new LinkedHashMap<ArrayList<String>, ArrayList<String>>(); 
            int i=0;
    File file=new File("C:\\Users\\USER\\Desktop\\IOL.iol");
        FileReader filer = new FileReader(file);
        BufferedReader brr = new BufferedReader(filer);    
    while((line=brr.readLine())!=null){
            inputlines.add(line);
            i++;  
        }
    //traverse thru each line
    for(int a=0;a<inputlines.size();a++){
        Stack<String> s = new Stack<String>();
//        System.out.println(lines[i]);
        //save per word in a line
        String woo[]=inputlines.get(a).split("\\s+");
                ArrayList <String> perword=new ArrayList <>();
                for (int j = 0; j < woo.length; j++) {
                    perword.add(woo[j]);
                }
        PWord.add(perword);//Main arraylist
    }
    
//PRINT CHECKERS
        //System.out.println("ptbl: " + PTBL);
        //access dimension 1 element 1(number: 1)
//        System.out.println(PWord.get(1).get(2));//TRAVERSE THRU words 
        
        //traverse thru each word TEMPLATE CHECKING
       for(int a=0;a<=inputlines.size()-1;a++){
       int b=0;
       System.out.println("\n------------------------\nLINE "+(a+1)+" INFORMATION"+"\nnum. of elements:"+PWord.get(a).size()+"\n"+PWord.get(a));
      
       while (PWord.get(a).size() > b) {
            
        //IF NEWLN then create new line
        if(PWord.get(a).get(b).equals("NEWLN")==true){
                  System.out.println("\n");
        }
        
        //DEFINING VARIABLES
        
        //IF DATATYPES ARE SCANNED
        if(AllDT.contains(PWord.get(a).get(b))==true){
            System.out.println("\nElement : "+PWord.get(a).get(b)+"\nIndex: "+b);
            //STR VAR
            if(PWord.get(a).get(b).equals("STR")==true){
               b++;
               var.add(PWord.get(a).get(b)); 
               sval.add(" "); 
               svarval.put(var.get(var.size()-1), sval.get(sval.size()-1)); 
               System.out.println(svarval);
            }
            
            else{
            b++;
            
            //DT VAR
            System.out.println("\nElement : "+PWord.get(a).get(b)+"\nIndex: "+b);
            var.add(PWord.get(a).get(b));
            //DT VAR
            val.add(0);  
            varval.put(var.get(var.size()-1), val.get(val.size()-1));
            System.out.println(varval);
            
            b++;
            System.out.println("\nElement : "+PWord.get(a).get(b)+"\nIndex: "+b);
            //DT VAR IS VAL
            if(PWord.get(a).get(b).equals("IS")){
                b++;
                System.out.println("\nElement : "+PWord.get(a).get(b)+"\nIndex: "+b);
                //DT VAR IS VAL
                if(isInteger(PWord.get(a).get(b))){
                  val.add(strToInt(PWord.get(a).get(b)));  
                  varval.put(var.get(var.size()-1), val.get(val.size()-1));
                  System.out.println(varval);
                }
            }
            }
        } //END ASSIGNMENT
        
        //INPUT VAR
        else if(PWord.get(a).get(b).equals("BEG")==true){
            //prompt input form user
            String beg_input=input.nextLine();
            //find the variable and save input
            b++;
            sval.add(beg_input);
            svarval.put(PWord.get(a).get(b),sval.get(sval.size()-1));
            System.out.println(svarval); //TEST ONLY - SHOULD NOT PRINT
        }
         //OUTPUT VAR
        else if(PWord.get(a).get(b).equals("PRINT")==true){
           b++;
           System.out.println("Print: "+svarval.put(PWord.get(a).get(b),sval.get(sval.size()-1)));
        }
        
        //ASSIGNING VARIABLES - INTO VAR IS EXPR
        else if(PWord.get(a).get(b).equals("INTO")==true){
            b++;
            System.out.println("\nElement : "+PWord.get(a).get(b)+"\nIndex: "+b);
            b++;
            System.out.println("\nElement : "+PWord.get(a).get(b)+"\nIndex: "+b);
            b++;
            System.out.println("\nElement : "+PWord.get(a).get(b)+"\nIndex: "+b);
            //INTO VAR IS EXPR
            if(Alloperations.contains(PWord.get(a).get(b))){                                  
                //INSERT POSTFIX TO INFIX FOR ARITH  
            while(Alloperations.contains(PWord.get(a).get(b))){ //while op are being read, iterate and push 
                ArithOperations.push((PWord.get(a).get(b)));
                b++;
            } 
            
            
            
            //if IDENT
            if(isVar(PWord.get(a).get(b))){
               String value=varval.get((PWord.get(a).get(b))).toString();
               ArithOperations.push(value);
            }
            //if VAL
            if(isInteger(PWord.get(a).get(b))){
                ArithOperations.push((PWord.get(a).get(b)));
            }
            
            b++;
            //if IDENT
            if(isVar(PWord.get(a).get(b))){
               
                String value=varval.get((PWord.get(a).get(b))).toString();
                ArithOperations.push(value);
            }
            //if VAL
            if(isInteger(PWord.get(a).get(b))){
                ArithOperations.push(PWord.get(a).get(b));
            }  
            
            //results from arith
            String valeeee=ArithOperations.toString(); 
            Integer arithvalue=evaluatePrefix(valeeee);
            varval.put(PWord.get(a).get(b-4), arithvalue);
            System.out.println(varval); 
            
            
            
            }
            //INTO VAR IS VAL
            else{
            val.add(strToInt(PWord.get(a).get(b+2)));  
            varval.put(PWord.get(a).get(b), val.get(val.size()-1));
            System.out.println(varval); 
            }
        }
        
        
        
        
        
        //---DO NOT REMOVE---
        if(PWord.get(a).size()!=b)
         b++;
        else break;   
        } 
       
       
       }
    }
 public static boolean checkSpecialChar(char ch) {
        char[] specialCh = {'!','@',']','#','$','%','^','&','*','-','.'}; 
     for (Character c : specialCh) {
      if (ch == c){
          return true;
      }
 }
    
    return false;
    }
    //check f integer
    public static boolean isInteger(String str) {
    int flag=1;
    if (str == null) {
        return false;
    }
    int length = str.length();
    if (length == 0) {
        return false;
    }
    int i = 0;
    if (str.charAt(0) == '-') {
        if (length == 1) {
            return false;
        }
        i = 1;
    }
    for (; i < length; i++) {
        char c = str.charAt(i);
        
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')||c == '.'||checkSpecialChar(c)==true) {
            flag=0;
        }
    }
    if (flag==0){
             return false;
         }
    else{
        return true;
    }
    
}  
  
          
    
          
          
  public static int strToInt(String str){
    int i = 0;
    int num = 0;
    boolean isNeg = false;

    // Check for negative sign; if it's there, set the isNeg flag
    if (str.charAt(0) == '-') {
        isNeg = true;
        i = 1;
    }

    // Process each character of the string;
    while( i < str.length()) {
        num *= 10;
        num += str.charAt(i++) - '0'; // Minus the ASCII code of '0' to get the value of the charAt(i++).
    }

    if (isNeg)
        num = -num;
    return num;
}   

    
    public static boolean isVar(String str) {
    int i = 1;
    int flag=0;
  
        if (checkSpecialChar(str.charAt(0))==false&&(str.charAt(0) >= 'a' || str.charAt(0) <= 'z'|| str.charAt(0)>= 'A' || str.charAt(0) <= 'Z')&&!(str.charAt(0) == '0' || str.charAt(0) <= '9')){
            for (; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c >= '0' || c <= '9'||c >= 'a' || c <= 'z'|| c >= 'A' || c <= 'Z') {
                    if(i==str.length()-1){
                        return true;
                    }
                    
                }
                if(checkSpecialChar(c)==true){
                    return false;
                }
             }
        }
        else return false;
        return false;
        }
    
    static Boolean isOperand(char c)
    {
        // If the character is a digit
        // then it must be an operand
        if (c >= 48 && c <= 57)
            return true;
        else
            return false;
    }
 
    public static Integer evaluatePrefix(String string)
    {
        //change Word operations to oper
        String exprsn = string.replaceAll("MULT","*").replaceAll("DIV","/").replaceAll("ADD","+").replaceAll("SUB","-").replaceAll("MOD","%").replaceAll("\\[", "").replaceAll("]", "").replaceAll(",", "").replaceAll(" ", "");
       
        
        
        Stack<Double> Stack = new Stack<Double>();
 
        for (int j = exprsn.length() - 1; j >= 0; j--) {
 
            // Push operand to Stack
            // To convert exprsn[j] to digit subtract
            // '0' from exprsn[j].
             
            if (isOperand(exprsn.charAt(j)))
                Stack.push((double)(exprsn.charAt(j) - 48));
 
            else {
 
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
 
    //INSERT CODES BEFORE THIS
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
