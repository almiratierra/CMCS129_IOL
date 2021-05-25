/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOL_ProjectPackage;

import com.sun.org.apache.xpath.internal.compiler.Keywords;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;
import javax.swing.JTextArea;

/**
 *
 * @author tierr
 */
public class ErrorChecker {
//    Boolean errorFlag = false;
    //Error checkers
    
    public static List<String> stringArr(String[] words, JTextArea ConsoleTxtArea){
        List<String> scannedWords = new ArrayList<>();
        
        if(words[0].equals("IOL")){
            scannedWords.add(words[0]);
            for(int i = 1; i < words.length; i++){
                scannedWords.add(words[i]);
                if(words[i].equals("LOI") && i != words.length-1){
                    ConsoleTxtArea.append("Error! LOI not end of code.\n");
                }
                else if(i == words.length-1 && !words[i].equals("LOI") && scannedWords.contains("LOI") == true){
                    ConsoleTxtArea.append("Error! End of code not found.\n");
                }
                
            }
        }
        else{
            ConsoleTxtArea.append("Illegal start of code. IOL missing.\n");
        }
        return scannedWords;
    }
    
    public static void characterChecker(String check, JTextArea ConsoleTxtArea){
        for(int i = 0; i < check.length(); i++){
            char ch = check.charAt(i);           
                //checks for special characters
            if(!Character.isDigit(ch) && !Character.isAlphabetic(ch)){
                ConsoleTxtArea.append("The following word/character is not recognized: "+check +"\n");
            }  
        }
    }
    
    public static void matchTypes(String currWord, int k, JTextArea ConsoleTxtArea){
        List<String> lists = Arrays.asList("INT", "STR", "IS", "INTO", "BEG", "PRINT", "ADD", "SUB", "MULT", "DIV", "MOD", "NEWLN");   

        if(lists.contains(currWord) == true && k != 2){
            ConsoleTxtArea.append("Error, "+currWord+" is not a legal variable name");
        }
        else if(currWord.equals("INT") && k == 2) characterChecker(currWord, ConsoleTxtArea);
        else characterChecker(currWord, ConsoleTxtArea);
    }

    public static boolean isNumeric(String strNum){
        if (strNum == null) return false;
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static void evalExpr(Stack<String> expr, List<String> varNames, List<String> operations, JTextArea ConsoleTxtArea){
        String var;
        //change stack expr to string?
        int z = 0, d = 0;

        while(d < 2){
            try{
                //if true
                if(varNames.contains(expr.get(d))) d++; 
                else{
                    var = expr.get(d);
                    
                    while(z < var.length()){
                        char ch = var.charAt(z);           
                        //checks for special characters
                        if(!Character.isDigit(ch)&&!Character.isAlphabetic(ch)){
                            ConsoleTxtArea.append("The following word/character is not recognized: "+var);
                            z++;
                        }
                        //means it's a literal
                        else if(Character.isDigit(ch)) z++;
                        else{
                            ConsoleTxtArea.append("Unrecognized variable: "+var);
                            z++;
                        }
                    }//while end
                d++;
                }
                //d++; original placement
            }catch(ArrayIndexOutOfBoundsException e){
                //do nothing
                d = 3;
            }
        }//while end
    }
    
    public static List<String> getTokens(String strs, List<String> varNames, List<String> operations, JTextArea ConsoleTxtArea){
        Stack<String> tokens = new Stack<>();
        List<String> tok = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(strs," ");
        Stack<String> expression = new Stack<>();

        int x = tokenizer.countTokens();
        int ctr = 0;
        String opr;

//        while(tokenizer.hasMoreElements()){
//            tokens.push(tokenizer.nextToken());
//            tok.add(tokenizer.nextToken());
//        }
        System.out.println("x " + x);
        System.out.println("tokens: " + tokens);
        System.out.println("Stirngtokens: " + tokenizer.toString());
        //okay man ata na stack diri, need i empty everytime i call?
       //ConsoleTxtArea.append(tokens);
       
//        while(ctr < tokens.size()){
//            opr = tokens.get(ctr);
//
//            switch(opr){
//                //if current word is int, it should read INT IDENT IS INT_LIT. otherwise, error
//                case "INT":
//                    //INT res(a=0) IS(a=1) 0(a=2)
//                    int a = 0;
//                    int b = ctr + 1;//value of ctr is INT
//                    String peeksInt = tokens.peek();
//
//                    if(peeksInt.equals("INT")){//checks if INT is at the top of the stack
//                        ConsoleTxtArea.append("Please declare a variable and value for INT.");
//                        ctr++;
//                    }
//                    else{
//                        while(a < 2){
//                            if(a == 0){
//                                if(isNumeric(tokens.get(b)) == false){//checks variable name
//                                    if(varNames.contains(tokens.get(b))){
//                                        ConsoleTxtArea.append("Variable name "+tokens.get(b)+" already exists.");
//                                        a++;
//                                    } else{
//                                        matchTypes(tokens.get(b), a, ConsoleTxtArea); 
////                                        matchTypes(tokens.get(b),0, ConsoleTxtArea); 
//                                        varNames.add(tokens.get(b));
//                                        a++;
//                                    }
//                                } 
//                                else{
//                                    ConsoleTxtArea.append(tokens.get(b)+" is an invalid variable name.");
//                                    a++;
//                                }
//                                if(b==tokens.size()-1){
//                                    b = b - 1;
//                                    ctr = tokens.size();
//                                } else b++;
//                            }
//                            else if(a == 1){//checks operation IS
//                                if(tokens.get(b).equals("IS")){
//                                    if(tokens.lastElement().equals("IS")){
//                                        ConsoleTxtArea.append("A variable has no set value.");
//    //                                    System.exit(0);
//                                        a++;
//                                    }
//
//                                    try{
//                                        int d = Integer.parseInt(tokens.get(b+1));
//                                    }catch(NumberFormatException nfe){
//                                        ConsoleTxtArea.append(nfe + " is not an integer.");
//                                        a++;
//    //                                    System.exit(0);
//                                    }
//                                    ctr=ctr+4;
//                                }
//                                else ctr=ctr+2;
//                            }
//                            a++;//counter for while
//                        }                      
//                    }
//                    
////                        if(a ==2){
////                            store 0 for value
////                        }
//                    
//                break;
//                  
//                case "STR":
//                    int k = ctr+1;
//                    String peeksStr = tokens.peek();
//
//                    if(k == tokens.size() || peeksStr.equals("STR")){//checks if the line only contains INT
//                        ConsoleTxtArea.append("Please declare a variable for STR");
//                    }
//                    else{
//                        if(varNames.contains(tokens.get(k))){
//                            ConsoleTxtArea.append("Variable name "+tokens.get(k)+" already exists.");
//    //                        System.exit(0);                        
//                        }
//                        else{
//                            if(isNumeric(tokens.get(k))==false){
//                                matchTypes(tokens.get(k),0, ConsoleTxtArea); 
//                                varNames.add(tokens.get(k));
//                            }
//                            else{
//                                ConsoleTxtArea.append(tokens.get(k)+" is an invalid variable name.");
//                            }   
//                        }//end else for boolean checkingSTR
//                    }
//                    ctr=ctr+2;
//                    ConsoleTxtArea.append(varNames.toString());
//                break;
//                  
//                case "IS": 
//                    String peeksIs = tokens.peek();
//                    if(peeksIs.equals("IS")){//checks if INT is at the top of the stack
//                        ConsoleTxtArea.append("Illegal placement of assignment operation.");
//  //                      System.exit(0);
//                    }      
//                break;
//                  
//                case "INTO": 
//                    String peeksInto = tokens.peek();
//
//                    if(peeksInto.equals("INTO")){//checks if INT is at the top of the stack
//                        ConsoleTxtArea.append("Syntax error for INTO");
//  //                      System.exit(0);
//                    }
//                    else{
//                        try{
//                            if(tokens.get(ctr+2).equals("IS")){
//                                matchTypes(tokens.get(ctr+1),0, ConsoleTxtArea);//if passes through w/o err
//                                
//                                if(varNames.contains(tokens.get(ctr+1))){
//                                    if(operations.contains(tokens.get(ctr+3))){
//                                        try{
//                                            expression.push(tokens.get(ctr+4));
//                                            expression.push(tokens.get(ctr+5));
//                                            evalExpr(expression, varNames, operations, ConsoleTxtArea);
//                                        }catch(ArrayIndexOutOfBoundsException e){
//                                            ConsoleTxtArea.append("Syntax Error for operation. Out of bounds.");
////                                            System.exit(0);
//                                        }
//                                        ctr=ctr+6;
//                                    } else{
//                                        ConsoleTxtArea.append(tokens.get(ctr+3)+" is an invalid operation.");
////                                        System.exit(0);
//                                        ctr++;
//                                    }
//                                } else{
//                                    ConsoleTxtArea.append("Variable name "+tokens.get(ctr+1)+" is not declared.");
//                                    ctr++;
////                                    System.exit(0);
//                                }
//                            } else{
//                                ConsoleTxtArea.append("Syntax Error for INTO. IS operation is missing.");
//                                ctr++;
////                                System.exit(0);
//                            }
//                        } catch(ArrayIndexOutOfBoundsException e){
//                            ConsoleTxtArea.append("Syntax Error for INTO. Out of bounds.");
//                            ctr++;
////                            System.exit(0);
//                        }                      
//                    }                  
//                break;
//                  
//                case "BEG": 
//                    int l = ctr+1;
//                    String peeksBeg = tokens.peek();
//
//                    if((l)==tokens.size()||peeksBeg.equals("BEG")){//checks if the line only contains INT
//                        ConsoleTxtArea.append("Please declare a variable for BEG");
//                        ctr++;
//    //                    System.exit(0);
//                    }
//                    else{
//                        if(varNames.contains(tokens.get(l))){
//                        //since it's true, do nothing
//                        } else{
//                            ConsoleTxtArea.append("Variable name "+tokens.get(l)+" is not allowed.");
//                            ctr++;
//    //                        System.exit(0);
//                        }
//                    }
//                    ctr = ctr+2;   
//                    ConsoleTxtArea.append(varNames.toString());
//                break;
//                  
//                case "PRINT":
//                    String peeksPrint = tokens.peek();
//
//                    if(peeksPrint.equals("PRINT")){//checks if INT is at the top of the stack
//                        ConsoleTxtArea.append("Syntax Error for PRINT operation.");
//                        ctr++;
//  //                      System.exit(0);
//                    }
//                    else{
//                        //if it's a variable name, gets accepted
//                        if(varNames.contains(tokens.get(ctr+1))) ctr=ctr+2;
//                        else if(operations.contains(tokens.get(ctr+1))){
//                            try{
//                                expression.push(tokens.get(ctr+2));
//                                expression.push(tokens.get(ctr+3));
//                                evalExpr(expression, varNames, operations, ConsoleTxtArea);
//                        }catch(ArrayIndexOutOfBoundsException e){
//                            ConsoleTxtArea.append("Syntax Error for ADD operation. Out of bounds.");
//                            ctr++;
////                            System.exit(0);
//                        }
//                        ctr=ctr+4;
//                        }
//                        else{
//                            String print = tokens.get(ctr+1).toString();
//                            int counts = 0;
//                            
//                            while(counts < print.length()){
//                                char ch = print.charAt(counts);           
//                                    //checks for special characters
//                                if(!Character.isDigit(ch)&&!Character.isAlphabetic(ch)){
//                                    ConsoleTxtArea.append("The following word/character is not recognized: "+print); 
//                                    counts++;
//    //                                    System.exit(0);
//                                }
//                                //means it's a literal
//                                else if(Character.isDigit(ch)) counts++;
//                                else{
//                                    ConsoleTxtArea.append("The following word/character is not recognized: "+print);
//    //                                    System.exit(0);
//                                    counts++;
//                                }
//                            }
//                        ConsoleTxtArea.append("literal: "+print);
//                        ctr = ctr+2;
//                        }
//                    }
//                break;        
//                  
//                case "ADD":
//                    String peeksAdd = tokens.peek();
//
//                    if(peeksAdd.equals("ADD")){//checks if INT is at the top of the stack
//                        ConsoleTxtArea.append("Syntax Error for ADD operation.");
//                        ctr++;
//  //                      System.exit(0);
//                    } 
//                    else{                  
//                        try{
//                            expression.push(tokens.get(ctr+1));
//                            expression.push(tokens.get(ctr+2));
//                            evalExpr(expression, varNames, operations, ConsoleTxtArea);
//                        }catch(ArrayIndexOutOfBoundsException e){
//                            ConsoleTxtArea.append("Syntax Error for ADD operation. Out of bounds.");
//                            ctr++;
//                        }
//                    }
//                    ctr = ctr+3;
//                break;
//                  
//                case "SUB":
//                    String peeksSub = tokens.peek();
//
//                    if(peeksSub.equals("SUB")){//checks if INT is at the top of the stack
//                        ConsoleTxtArea.append("Syntax Error for SUB operation.");
//                        ctr++;
//  //                      System.exit(0);
//                    }
//                    else{                  
//                        try{
//                            expression.push(tokens.get(ctr+1));
//                            expression.push(tokens.get(ctr+2));
//                            evalExpr(expression, varNames, operations, ConsoleTxtArea);
//                        }catch(ArrayIndexOutOfBoundsException e){
//                            ConsoleTxtArea.append("Syntax Error for SUB operation. Out of bounds.");
//                            ctr++;
//                        }
//                    }
//                    ctr=ctr+3;
//                break;     
//                  
//                case "MULT":
//                    String peeksMult = tokens.peek();
//
//                    if(peeksMult.equals("SUB")){//checks if INT is at the top of the stack
//                        ConsoleTxtArea.append("Syntax Error for MULT operation.");
//                        ctr++;
//  //                      System.exit(0);
//                    }
//                    else{                  
//                        try{
//                            expression.push(tokens.get(ctr+1));
//                            expression.push(tokens.get(ctr+2));
//                            evalExpr(expression, varNames, operations, ConsoleTxtArea);
//                        }catch(ArrayIndexOutOfBoundsException e){
//                            ConsoleTxtArea.append("Syntax Error for MULT operation. Out of bounds.");
//                            ctr++;
//                        }
//                    }
//                    ctr=ctr+3;
//                break;   
//                  
//                case "MOD":
//                    String peeksMod = tokens.peek();
//
//                    if(peeksMod.equals("SUB")){//checks if INT is at the top of the stack
//                        ConsoleTxtArea.append("Syntax Error for MOD operation.");
//                        ctr++;
//  //                      System.exit(0);
//                    }
//                    else{                  
//                        try{
//                            expression.push(tokens.get(ctr+1));
//                            expression.push(tokens.get(ctr+2));
//                            evalExpr(expression, varNames, operations, ConsoleTxtArea);
//                        }catch(ArrayIndexOutOfBoundsException e){
//                            ConsoleTxtArea.append("Syntax Error for MOD operation. Out of bounds.");
//                            ctr++;
//                        }
//                    }
//                    ctr=ctr+3;
//                break;       
//                  
//                case "NEWLN": ctr++; break;
//              
//                default:
//                    if(opr.equals("NEWLN")){
////                        ConsoleTxtArea.append("newln");
//                    }
//                    else{
//                      ConsoleTxtArea.append("Unrecognized character: "+opr);
////                      characterChecker(opr, ConsoleTxtArea);
//  //                    System.exit(0);                     
//                    }
//                    ctr++;
//                break;
//                  
//            }//switch
//        //ctr++;
//       }//while
        
       return tok;
    }
}
