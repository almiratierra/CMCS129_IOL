/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IOL_ProjectPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
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
    private static Stack<String> expression = new Stack<>();
    private static List<String> ERR_LEX = new ArrayList<>();
//    private static int x = 1;
    
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
    
    public static boolean characterChecker(String check){
        for(int i = 0; i < check.length(); i ++){
            char ch = check.charAt(i);
            if(!Character.isDigit(ch) && !Character.isAlphabetic(ch)){
                return true;
            }
        }
        return false;
    }
    
    public static boolean isNumeric(String strNum){
        if (strNum == null) return false;
        try {
            Integer d = Integer.parseInt(strNum);
        } 
        catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static boolean matchTypes(String currWord, List<String> AllKeywords){
        if(AllKeywords.contains(currWord)){
            return true;
        }
        return false;
    }

    public static boolean evalExpr(List<String> varNames, List<String> AllOPR, JTextArea ConsoleTxtArea, List<String> AllKeywords, List<String> IntvarNames, List<String> StrvarNames, int x){
        int a=0;

        while(a<expression.size()){
            if(isNumeric(expression.peek())){
                expression.pop();
            }else{
                if(IntvarNames.contains(expression.peek())){
                    expression.pop();
                }
                else{
                    ConsoleTxtArea.append("Undeclared variable (" + expression.peek() + "). Please review line " + x + ".\n");
                    ERR_LEX.add(expression.peek());
                    expression.pop();
                }
                if(StrvarNames.contains(expression.peek())){
                    ConsoleTxtArea.append("STR cannot be converted to INT. Please review line " + x + ".\n");
                    ERR_LEX.add(expression.peek());
                    expression.pop();
                }
                if(matchTypes(expression.peek(), AllKeywords)){
                    ConsoleTxtArea.append("(" + expression.peek() + ") is a keyword. Please review line " + x + ".\n");
                    ERR_LEX.add(expression.peek());
                    expression.pop();
                }
            }
            a++;
        }
        return true;
    }

    public static List<String> getErrors(String strs, JTextArea ConsoleTxtArea, List<String> AllKeywords, List<String> AllOPR, List<String> varNames, List<String> IntvarNames, List<String> StrvarNames, int x){
        Stack<String> tokens = new Stack<>();
        List<String> tok = new Stack<>();
        StringTokenizer tokenizer = new StringTokenizer(strs," ");
        
        while(tokenizer.hasMoreElements()){
            tokens.push(tokenizer.nextToken());
        }
        x++;//counter for line
        ListIterator<String> iterator = tokens.listIterator();

        int ctr = 0;
        String opr;
        
        while(ctr < tokens.size()){
            opr = tokens.get(ctr);
            
            switch(opr){
                case "INT":
                    if(ctr == tokens.size()-1 && tokens.get(ctr).equals("INT")){
                        ConsoleTxtArea.append("Please declare a variable and/or value for INT at line "+x+".\n");
                        ctr = tokens.size();
                        break;
                    }
                    if(ctr + 1 < tokens.size()){
                        if(matchTypes(tokens.get(ctr + 1), AllKeywords)){
                            ConsoleTxtArea.append(tokens.get(ctr+1) + " is a reserved keyword. Please review line " + x + ".\n");
                            ERR_LEX.add(tokens.get(ctr+1));
                            ctr = ctr + 2;
                            break;
                        }
                        else if(characterChecker(tokens.get(ctr + 1))){
                            ConsoleTxtArea.append(tokens.get(ctr+1)+ " is an invalid variable name. Please review line "+x+".\n");
                            ERR_LEX.add(tokens.get(ctr+1));
                            ctr = ctr + 2;
                            break;
                        }
                        else if(isNumeric(tokens.get(ctr+1))){
                            ConsoleTxtArea.append(tokens.get(ctr+1)+ " is an invalid declaration of a literal. Review line "+x+".\n");
                            ERR_LEX.add(tokens.get(ctr+1));
                            ctr = ctr + 2;
                            break;
                        }
                        else{
                            if(ctr + 2 < tokens.size()){
                                if(tokens.get(ctr + 2).equals("IS")){
                                    if(ctr + 3 < tokens.size()){
                                        if(isNumeric(tokens.get(ctr + 3))){
                                            varNames.add(tokens.get(ctr + 1));
                                            IntvarNames.add(tokens.get(ctr + 1));
                                            ctr = ctr + 4;
                                            break;
                                        }
                                        else{
                                            ConsoleTxtArea.append(tokens.get(ctr+3) + " is not an integer. Please review line " + x + ".\n");
                                            ERR_LEX.add(tokens.get(ctr+3));
                                            ctr = ctr + 4;
                                            break;
                                        }
                                    }
                                    //wala kasulod sa if(ctr+3<tokens.size()) pero naa nay "IS"
                                    ConsoleTxtArea.append("Missing value for variable " + tokens.get(ctr+1) + " at line " + x + ".\n");
                                    ERR_LEX.add(tokens.get(ctr+1));
                                    ctr = ctr + 4;
                                    break;
                                }   //if "IS" ends
                                if(varNames.contains(tokens.get(ctr + 1))){
                                    ConsoleTxtArea.append("Variable name " + tokens.get(ctr+1) + " already exists. Review line " + x + ".\n");
                                    ctr = ctr + 2;//if(tokens.get(ctr+2).equals("IS"))
                                    break;
                                    }
                                else{
                                    varNames.add(tokens.get(ctr + 1));
                                    IntvarNames.add(tokens.get(ctr+1));
                                    ctr = ctr + 2;//if(tokens.get(ctr+2).equals("IS"))
                                    break;
                                }
                            }
                            if(varNames.contains(tokens.get(ctr + 1))){
                                ConsoleTxtArea.append("Variable name "+tokens.get(ctr+1)+" already exists.\n");
                                ctr = ctr + 2;
                                break;
                            }
                            else{
                                varNames.add(tokens.get(ctr + 1));
                                IntvarNames.add(tokens.get(ctr + 1));
                            }
                            ctr = ctr + 2;
                            break;
                        }
                    }
                    break;
                case "STR":
                    if(ctr == tokens.size() - 1 && tokens.get(ctr).equals("STR")){
                        ConsoleTxtArea.append("Please declare a variable for STR at line " + x + ".\n");
                        ctr=tokens.size();
                        break;
                    }
                    else{
                        if(ctr + 1 < tokens.size()){
                            if(matchTypes(tokens.get(ctr + 1), AllKeywords)){
                                ConsoleTxtArea.append(tokens.get(ctr + 1)+ " is a reserved keyword. Please review line " + x + ".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr=ctr+2;
                                break;
                            }
                            else if(characterChecker(tokens.get(ctr + 1))){
                                ConsoleTxtArea.append(tokens.get(ctr + 1) + " is an invalid variable name. Please review line " + x + ".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr = ctr + 2;
                                break;
                            }
                            else if(isNumeric(tokens.get(ctr + 1))){
                                ConsoleTxtArea.append(tokens.get(ctr+1)+ " is an invalid declaration of a literal at line "+x+".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr = ctr + 2;
                                break;
                            }
                            else{
                                if(varNames.contains(tokens.get(ctr + 1))){
                                    ConsoleTxtArea.append("Variable name "+tokens.get(ctr+1)+" already exists.\n");
                                    ctr = ctr + 2;//if(tokens.get(ctr+2).equals("IS"))
                                    break;
                                }
                                else{
                                    varNames.add(tokens.get(ctr + 1));
                                    StrvarNames.add(tokens.get(ctr + 1));
                                    ctr = ctr + 2;
                                    break;
                                }
                            }
                        }
                    }
                    break;
                
                case "IS":
                    ConsoleTxtArea.append("Illegal placement of assignment operation at line "+x+".\n");
//                    ERR_LEX.add(opr);
                    ctr++;
                    break;
                    
                case "INTO":
                    if(ctr == tokens.size()-1 && tokens.get(ctr).equals("INTO")){
                        ConsoleTxtArea.append("Please declare a variable and an expression for INTO at line "+x+".\n");
                        ctr = tokens.size();
                        break;
                    }
                    else{
                        if(ctr + 1 < tokens.size()){
                            if(matchTypes(tokens.get(ctr + 1), AllKeywords)){
                                ConsoleTxtArea.append(tokens.get(ctr+1)+ " is a reserved keyword. Please review line "+x+".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr = ctr + 2;
                                break;
                            }
                            else if(characterChecker(tokens.get(ctr+1))){
                                ConsoleTxtArea.append(tokens.get(ctr+1)+ " is an invalid variable name. Please review line "+x+".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr = ctr + 2;
                                break;
                            }
                            else if(isNumeric(tokens.get(ctr+1))){
                                ConsoleTxtArea.append(tokens.get(ctr+1)+ " is an invalid declaration of a literal at line "+x+".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr = ctr + 2;
                                break;
                            }
                            else{
                                if(varNames.contains(tokens.get(ctr+1))){//var is declared
                                    if(ctr+2<tokens.size()){
                                        if(tokens.get(ctr+2).equals("IS")){
                                            if(ctr+5<tokens.size()){
                                                if(AllOPR.contains(tokens.get(ctr+3))){//valid operation
                                                    try{
                                                        expression.push(tokens.get(ctr+4));
                                                        expression.push(tokens.get(ctr+5));
                                                        evalExpr(varNames, AllOPR, ConsoleTxtArea, AllKeywords, IntvarNames, StrvarNames, ctr);
                                                        ctr = ctr + 6;
                                                        break;
                                                    }
                                                    catch(ArrayIndexOutOfBoundsException e){
                                                        ConsoleTxtArea.append("Syntax error for INTO at line "+x+".\n");
                                                        ctr=ctr+5;
                                                        break;
                                                    }
                                                }
                                                else{
                                                    ConsoleTxtArea.append(tokens.get(ctr+3)+" is an invalid operation. Please review line "+x+".\n");
                                                    ERR_LEX.add(tokens.get(ctr+3));
                                                    ctr = ctr + 4;
                                                    break; 
                                                }
                                            }
                                            else{
                                                ConsoleTxtArea.append("Syntax error for INTO. Please review line "+x+".\n");
                                                ctr = ctr + 6;
                                                break; 
                                            }
                                        }
                                        else{
                                            ConsoleTxtArea.append("Syntax error for INTO. Please review line "+x+".\n");
                                            ctr = ctr + 3;
                                            break; 
                                        }
                                    }
                                    else{
                                        ConsoleTxtArea.append("Syntax error for INTO. Please review line "+x+".\n");
                                        ctr = ctr + 2;
                                        break; 
                                    }
                                }
                                else{
                                    ConsoleTxtArea.append(tokens.get(ctr+1)+ " is an unknown variable. Please review at line "+x+".\n");
                                    ERR_LEX.add(tokens.get(ctr+1));
                                    ctr = ctr + 2;
                                    break;
                                }
                            }
                        }
                    }
                    break;
                
                case "BEG":
                    if(ctr == tokens.size()-1 && tokens.get(ctr).equals("BEG")){//checks if INT is at the top of the stack
                        ConsoleTxtArea.append("Please declare a variable for BEG at line "+x+".\n");
                        ctr = tokens.size();
                        break;
                    }
                    else{
                        if(ctr + 1 < tokens.size()){
                            if(matchTypes(tokens.get(ctr + 1), AllKeywords)){//if true
                                ConsoleTxtArea.append(tokens.get(ctr+1)+ " is a reserved keyword. Please review line "+x+".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr = ctr + 2;
                                break;
                            }
                            else if(characterChecker(tokens.get(ctr + 1))){
                                ConsoleTxtArea.append(tokens.get(ctr+1)+ " is an invalid variable name. Please review line "+x+".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr = ctr + 2;
                                break;
                            }
                            else if(isNumeric(tokens.get(ctr + 1))){
                                ConsoleTxtArea.append(tokens.get(ctr + 1)+ " is an invalid declaration of a literal at line "+x+".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr = ctr + 2;
                                break;
                            }
                            else{
                                if(varNames.contains(tokens.get(ctr + 1))){
                                   /*proceed with asking user for input, pls don't forget to parse user input if matches variable type
                                   can use isNumeric method and map with arrays StrvarNames and IntvarNames*/
                                   ctr = ctr + 2;//if(tokens.get(ctr+2).equals("IS"))
                                   break; 
                                }
                                else{
                                    ConsoleTxtArea.append(tokens.get(ctr+1)+ " is an unknown variable. Please review line "+x+".\n");
                                    ERR_LEX.add(tokens.get(ctr));
                                    ctr = ctr + 2;
                                    break;
                                }
                            }
                        }
                    }
                    break;
                    
                case "PRINT":
                    if(ctr == tokens.size() - 1 && tokens.get(ctr).equals("PRINT")){//checks if INT is at the top of the stack
                        ConsoleTxtArea.append("Please declare a variable/literal to print at line "+x+".\n");
                        ctr = tokens.size();
                        break;
                    }
                    else{
                        if(ctr+1<tokens.size()){
                            if(AllKeywords.contains(tokens.get(ctr + 1))){//if true
                                ConsoleTxtArea.append(tokens.get(ctr + 1)+ " is a reserved keyword. Please review line "+x+".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr = ctr+2;
                                break;
                            }
                            else if(characterChecker(tokens.get(ctr+1))){
                                ConsoleTxtArea.append(tokens.get(ctr+1)+ " is an invalid variable name. Please review line "+x+".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr = ctr+2;
                                break;
                            }
                            else if(isNumeric(tokens.get(ctr+1))){
                                //print the number
                                ctr = ctr + 2;
                                break;
                            }
                            else if(varNames.contains(tokens.get(ctr+1))){
                                //print values
                                ctr = ctr + 2;
                                break;
                            }
                            else if(AllOPR.contains(tokens.get(ctr + 1))){
                                if(ctr + 3 < tokens.size()){
                                    try{
                                        expression.push(tokens.get(ctr+2));
                                        expression.push(tokens.get(ctr+3));
                                        evalExpr(varNames, AllOPR, ConsoleTxtArea, AllKeywords, IntvarNames, StrvarNames, ctr);
                                        ctr=ctr+4;
                                        break;
                                    }
                                    catch(ArrayIndexOutOfBoundsException e){
                                        ConsoleTxtArea.append("Syntax error for PRINT at line "+x+".\n");
                                        ctr=ctr+3;
                                        break;
                                    } 
                                }
                                else{
                                    ConsoleTxtArea.append("Invalid/Missing expression after operation ("+tokens.get(ctr+1)+"). Please review line "+x+".\n");
                                    ctr=ctr+4;
                                }
                            }
                            else{
                                ConsoleTxtArea.append("Unknown variable ("+tokens.get(ctr+1)+") at line "+x+".\n");
                                ERR_LEX.add(tokens.get(ctr+1));
                                ctr=ctr+2;
                                break;
                            }
                        }
                    }
                    break;
                
                case "ADD":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("ADD")){//checks if INT is at the top of the stack
                        ConsoleTxtArea.append("Please declare an expression to add at line "+x+".\n");
                        ctr=tokens.size();
                        break;
                    }
                    else{
                        //dili lang niya e-print ang ADD exp exp e-ignore lang siya sa system
                        if(ctr+2<tokens.size()){
                            expression.push(tokens.get(ctr+1));
                            expression.push(tokens.get(ctr+2));
                            evalExpr(varNames, AllOPR, ConsoleTxtArea, AllKeywords, IntvarNames, StrvarNames, ctr);
                            ctr=ctr+3;
                            break;  
                        }
                        else{
                            ConsoleTxtArea.append("Syntax error for ADD at line "+x+".\n");
                            ctr=ctr+2;
                            break;
                        } 
                    }
                    
                
                case "SUB":
                    if(ctr == tokens.size()-1 && tokens.get(ctr).equals("SUB")){//checks if INT is at the top of the stack
                        ConsoleTxtArea.append("Please declare an expression to subtract at line "+x+".\n");
                        ctr=tokens.size();
                        break;
                    }
                    else{
                        //dili lang niya e-print ang ADD exp exp e-ignore lang siya sa system
                        if(ctr+2<tokens.size()){
                            expression.push(tokens.get(ctr+1));
                            expression.push(tokens.get(ctr+2));
                            evalExpr(varNames, AllOPR, ConsoleTxtArea, AllKeywords,IntvarNames, StrvarNames, ctr);
                            ctr=ctr+3;
                            break;  
                        }
                        else{
                            ConsoleTxtArea.append("Syntax error for SUB at line "+x+".\n");
                            ctr=ctr+2;
                            break;
                        } 
                    }               
                    
                case "MULT":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("MULT")){//checks if INT is at the top of the stack
                        ConsoleTxtArea.append("Please declare an expression to multiply at line "+x+".\n");
                        ctr = tokens.size();
                        break;
                    }
                    else{
                        //dili lang niya e-print ang ADD exp exp e-ignore lang siya sa system
                        if(ctr+2<tokens.size()){
                            expression.push(tokens.get(ctr+1));
                            expression.push(tokens.get(ctr+2));
                            evalExpr(varNames, AllOPR, ConsoleTxtArea, AllKeywords, IntvarNames, StrvarNames, ctr);
                            ctr = ctr + 3;
                            break;  
                        }
                        else{
                            ConsoleTxtArea.append("Syntax error for MULT at line "+x+".\n");
                            ctr = ctr + 2;
                            break;
                        } 
                    }
                
                case "DIV":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("DIV")){//checks if INT is at the top of the stack
                        ConsoleTxtArea.append("Please declare an expression to divide at line "+x+".\n");
                        ctr=tokens.size();
                        break;
                    }
                    else{
                        //dili lang niya e-print ang ADD exp exp e-ignore lang siya sa system
                        if(ctr+2<tokens.size()){
                            expression.push(tokens.get(ctr+1));
                            expression.push(tokens.get(ctr+2));
                            evalExpr(varNames, AllOPR, ConsoleTxtArea, AllKeywords, IntvarNames, StrvarNames, ctr);
                            ctr=ctr+3;
                            break;  
                        }
                        else{
                            ConsoleTxtArea.append("Syntax error for DIV at line "+x+".\n");
                            ctr=ctr+2;
                            break;
                        } 
                    }
                    
                case "MOD":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("MOD")){//checks if INT is at the top of the stack
                        ConsoleTxtArea.append("Please declare an expression for modulo at line "+x+".\n");
                        ctr=tokens.size();
                        break;
                    }
                    else{
                        //dili lang niya e-print ang ADD exp exp e-ignore lang siya sa system
                        if(ctr+2<tokens.size()){
                            expression.push(tokens.get(ctr + 1));
                            expression.push(tokens.get(ctr + 2));
                            evalExpr(varNames, tok, ConsoleTxtArea, AllKeywords, IntvarNames, StrvarNames, ctr);
                            ctr = ctr + 3;
                            break;  
                        }
                        else{
                            ConsoleTxtArea.append("Syntax error for MOD at line " + x + ".\n");
                            ctr = ctr + 2;
                            break;
                        } 
                    }        
                    
                case "NEWLN":
                    //ignore newln
                    ctr++;
                    break;
                    
                default:
                    if(varNames.contains(opr) == false){
                        ConsoleTxtArea.append("Illegal call of variable (" + opr + ") at line" + x + ".\n");
                        ERR_LEX.add(opr);
                    }
                    else if(AllOPR.contains(opr) == false){
                        ConsoleTxtArea.append("Illegal declaration of an operation (" + opr + ") at line " + x + ".\n");
                        ERR_LEX.add(opr);
                    }
                    else if(AllKeywords.contains(opr) == false){
                        ConsoleTxtArea.append("Illegal declaration of an keyword (" + opr + ") at line " + x + ".\n");
                        ERR_LEX.add(opr);
                    }
                    else if(characterChecker(opr)){
                        ConsoleTxtArea.append("Unrecognized character/variable: " + opr + ".\n");
                        ERR_LEX.add(opr);
                    }
//                    else if(){
//                        
//                    }
                    ctr++;   
                    break;
            }
        }
        
        return ERR_LEX;
    }
}


