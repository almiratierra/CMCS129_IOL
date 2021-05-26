
package err129;

import java.io.*;
import java.util.*;
import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;

public class Err129 {
    
    private static List<String> list = new ArrayList<String>();
    private static List<String> varNames = new ArrayList<>();
    private static List<String> IntvarNames = new ArrayList<>();
    private static List<String> StrvarNames = new ArrayList<>();
    private static List<String> operations = new ArrayList<String>(Arrays.asList("ADD","MULT","DIV","SUB","MOD"));
    private static List<String> keywords = Arrays.asList("INT","STR","IS","INTO","IS","BEG","PRINT","NEWLN");   
    private static Stack<String> expression = new Stack<>();
    private static int x=1;
    
        //read text file to list
    public static List<String> readFile(String fileName){
        
        List<String> lines = Collections.emptyList();

        try{
            lines = Files.readAllLines(Paths.get(fileName));
        }

        catch(IOException err){
            err.printStackTrace();
        }

        return lines;
    }
    
    //checks IOL and LOI placements
    public static Stack<String> stringArr(String[] words){
        Stack<String> stack = new Stack<String>();       
        int i=0;
        
        //checking for IOL and LOI
        if(words[0].equals("IOL")){
            stack.push(words[0]);
            i++;
            while(i<words.length){
                if(words[i].equals("LOI")&&i!=words.length-1){
                    System.out.println("Error! LOI not end of code.");
                    System.exit(0);//terminates program
                }
                stack.push(words[i]);
                i++;
            }
        }
        else{
            System.out.println("Illegal start of code. IOL missing.");
        }
        
        //System.out.println("Stack: "+ stack);
        
        return stack;
    }
    
    public static boolean matchTypes(String types){
        List<String> lists = Arrays.asList("INT","STR","IS","INTO","IS","BEG","PRINT","ADD","SUB","MULT","DIV","MOD","NEWLN");   
            if(lists.contains(types)){
//                System.out.println("Error, "+types+" is not a legal variable name.");
                return true;
            }
            return false;
    }
    
    //characterChecker accepts number
    public static boolean characterChecker(String check){
        for(int i=0; i<check.length(); i++){
        char ch = check.charAt(i);           
            //checks for special characters
            if(!Character.isDigit(ch)&&!Character.isAlphabetic(ch)){
//                System.out.println("Invalid word/number: "+check);
                return true;
            }
        }
        return false;
    }

    public static boolean isNumeric(String strNum){
        if (strNum == null) {
            return false;
        }
        try {
            Integer d = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
        
    public static boolean evalExpr(Stack<String> expr){
        String var;
        int a=0;

        while(a<expression.size()){
            if(isNumeric(expression.peek())){
                expression.pop();
            }else{
                if(IntvarNames.contains(expression.peek())){
                    expression.pop();
                }
                else{
                    System.out.println("Undeclared variable ("+expression.peek()+"). Please review line "+x+".");
                    expression.pop();
                }
                if(StrvarNames.contains(expression.peek())){
                    System.out.println("STR cannot be converted to INT. Please review line "+x+".");
                    expression.pop();
                }
                if(matchTypes(expression.peek())){
                    System.out.println("("+expression.peek()+") is a keyword. Please review line "+x+".");
                    expression.pop();
                }
            }
            a++;
        }
        
        return true;
    }
    
    public static Stack<String> getTokens(String strs){
       Stack<String> tokens = new Stack<>();
       StringTokenizer tokenizer = new StringTokenizer(strs," ");
       
       while(tokenizer.hasMoreElements()){
           tokens.push(tokenizer.nextToken());
       }
       x++;//counter for line
       ListIterator<String> iterator = tokens.listIterator();
       
       int ctr =0;
       String opr;
       
//       System.out.println(tokens.size());

       while(ctr<tokens.size()){
           opr=tokens.get(ctr);
           switch(opr){
               case "INT":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("INT")){//checks if INT is at the top of the stack
                      System.out.println("Please declare a variable and/or value for INT at line "+x+".");
                      ctr=tokens.size();
                      break;
                    }
                    if(ctr+1<tokens.size()){
                      if(matchTypes(tokens.get(ctr+1))){//if true
                          System.out.println(tokens.get(ctr+1)+ " is a reserved keyword. Please review line "+x+".");
                          ctr=ctr+2;
                          break;
                      }
                      else if(characterChecker(tokens.get(ctr+1))){
                          System.out.println(tokens.get(ctr+1)+ " is an invalid variable name. Please review line "+x+".");
                          ctr=ctr+2;
                          break;
                      }
                      else if(isNumeric(tokens.get(ctr+1))){
                          System.out.println(tokens.get(ctr+1)+ " is an invalid declaration of a literal. Review line "+x+".");
                          ctr=ctr+2;
                          break;
                      }
                      else{
                          if(ctr+2<tokens.size()){
                              if(tokens.get(ctr+2).equals("IS")){
                                  if(ctr+3<tokens.size()){
                                      if(isNumeric(tokens.get(ctr+3))){
                                          varNames.add(tokens.get(ctr+1));
                                          IntvarNames.add(tokens.get(ctr+1));
                                          ctr=ctr+4;
                                          break;
                                      }
                                      else{
                                          System.out.println(tokens.get(ctr+3)+" is not an integer. Please review line "+x+".");
                                          ctr=ctr+4;
                                          break;
                                        }
                                    }
                                  //wala kasulod sa if(ctr+3<tokens.size()) pero naa nay "IS"
                                  System.out.println("Missing value for variable "+tokens.get(ctr+1)+" at line "+x+".");
                                  ctr=ctr+4;
                                  break;
                                }
                              
                              if(varNames.contains(tokens.get(ctr+1))){
                                  System.out.println("Variable name "+tokens.get(ctr+1)+" already exists. Review line "+x+".");
                                  ctr=ctr+2;//if(tokens.get(ctr+2).equals("IS"))
                                  break;
                              }
                              else{
                                varNames.add(tokens.get(ctr+1));
                                IntvarNames.add(tokens.get(ctr+1));
                                ctr=ctr+2;//if(tokens.get(ctr+2).equals("IS"))
                                break;
                              }
                            }
                            
                          if(varNames.contains(tokens.get(ctr+1))){
                            System.out.println("Variable name "+tokens.get(ctr+1)+" already exists.");
                            ctr=ctr+2;//if(tokens.get(ctr+2).equals("IS"))
                            break;
                          }
                            else{
                                varNames.add(tokens.get(ctr+1));
                                IntvarNames.add(tokens.get(ctr+1));
                            }
                          ctr=ctr+2;//if(ctr+2<tokens.size())
                          break;
                        }
                    }
               case "STR":
                   if(ctr==tokens.size()-1&&tokens.get(ctr).equals("STR")){//checks if INT is at the top of the stack
                      System.out.println("Please declare a variable for STR at line "+x+".");
                      ctr=tokens.size();
                      break;
                    }
                   else{
                       if(ctr+1<tokens.size()){
                        if(matchTypes(tokens.get(ctr+1))){//if true
                          System.out.println(tokens.get(ctr+1)+ " is a reserved keyword. Please review line "+x+".");
                          ctr=ctr+2;
                          break;
                        }
                        else if(characterChecker(tokens.get(ctr+1))){
                          System.out.println(tokens.get(ctr+1)+ " is an invalid variable name. Please review line "+x+".");
                          ctr=ctr+2;
                          break;
                        }
                        else if(isNumeric(tokens.get(ctr+1))){
                          System.out.println(tokens.get(ctr+1)+ " is an invalid declaration of a literal at line "+x+".");
                          ctr=ctr+2;
                          break;
                        }
                        else{//tama ang variable name
                            if(varNames.contains(tokens.get(ctr+1))){
                               System.out.println("Variable name "+tokens.get(ctr+1)+" already exists.");
                               ctr=ctr+2;//if(tokens.get(ctr+2).equals("IS"))
                               break; 
                            }
                            else{
                                varNames.add(tokens.get(ctr+1));
                                StrvarNames.add(tokens.get(ctr+1));
                                ctr=ctr+2;
                                break;
                            }
                        }
                       }
                   }
                case "IS":
                    System.out.println("Illegal placement of assignment operation at line "+x+".");
                    ctr++;
                    break;
                case "INTO":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("INTO")){
                      System.out.println("Please declare a variable and an expression for INTO at line "+x+".");
                      ctr=tokens.size();
                      break;
                    }
                    else{
                        if(ctr+1<tokens.size()){
                            if(matchTypes(tokens.get(ctr+1))){//if true
                              System.out.println(tokens.get(ctr+1)+ " is a reserved keyword. Please review line "+x+".");
                              ctr=ctr+2;
                              break;
                            }
                            else if(characterChecker(tokens.get(ctr+1))){
                              System.out.println(tokens.get(ctr+1)+ " is an invalid variable name. Please review line "+x+".");
                              ctr=ctr+2;
                              break;
                            }
                            else if(isNumeric(tokens.get(ctr+1))){
                              System.out.println(tokens.get(ctr+1)+ " is an invalid declaration of a literal at line "+x+".");
                              ctr=ctr+2;
                              break;
                            }
                            else{//pass ang variable
                                if(varNames.contains(tokens.get(ctr+1))){//var is declared
                                    if(ctr+2<tokens.size()){
                                        if(tokens.get(ctr+2).equals("IS")){
                                            if(ctr+5<tokens.size()){
                                                if(operations.contains(tokens.get(ctr+3))){//valid operation
                                                    try{
                                                    expression.push(tokens.get(ctr+4));
                                                    expression.push(tokens.get(ctr+5));
                                                    evalExpr(expression);
                                                    ctr=ctr+6;
                                                    break;
                                                    }catch(ArrayIndexOutOfBoundsException e){
                                                        System.out.println("Syntax error for INTO at line "+x+".");
                                                        ctr=ctr+5;
                                                        break;
                                                    }
                                                }
                                                else{
                                                    System.out.println(tokens.get(ctr+3)+" is an invalid operation. Please review line "+x+".");
                                                    ctr=ctr+4;
                                                    break; 
                                                }
                                            }
                                            else{
                                                System.out.println("Syntax error for INTO. Please review line "+x+".");
                                                ctr=ctr+6;
                                                break; 
                                            }
                                        }
                                        else{
                                            System.out.println("Syntax error for INTO. Please review line "+x+".");
                                            ctr=ctr+3;
                                            break; 
                                        }
                                    }
                                    else{
                                       System.out.println("Syntax error for INTO. Please review line "+x+".");
                                       ctr=ctr+2;
                                       break; 
                                    }
                                }
                                else{
                                    System.out.println(tokens.get(ctr+1)+ " is an unknown variable. Please review at line "+x+".");
                                    ctr=ctr+2;
                                    break;
                                }
                            }
                        }
                    }
                case "BEG":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("BEG")){//checks if INT is at the top of the stack
                      System.out.println("Please declare a variable for BEG at line "+x+".");
                      ctr=tokens.size();
                      break;
                    }
                    else{
                       if(ctr+1<tokens.size()){
                            if(matchTypes(tokens.get(ctr+1))){//if true
                              System.out.println(tokens.get(ctr+1)+ " is a reserved keyword. Please review line "+x+".");
                              ctr=ctr+2;
                              break;
                            }
                            else if(characterChecker(tokens.get(ctr+1))){
                              System.out.println(tokens.get(ctr+1)+ " is an invalid variable name. Please review line "+x+".");
                              ctr=ctr+2;
                              break;
                            }
                            else if(isNumeric(tokens.get(ctr+1))){
                              System.out.println(tokens.get(ctr+1)+ " is an invalid declaration of a literal at line "+x+".");
                              ctr=ctr+2;
                              break;
                            }
                            else{//tama ang variable name
                                if(varNames.contains(tokens.get(ctr+1))){
                                   /*proceed with asking user for input, pls don't forget to parse user input if matches variable type
                                   can use isNumeric method and map with arrays StrvarNames and IntvarNames*/
                                   ctr=ctr+2;//if(tokens.get(ctr+2).equals("IS"))
                                   break; 
                                }
                                else{
                                    System.out.println(tokens.get(ctr+1)+ " is an unknown variable. Please review line "+x+".");
                                    ctr=ctr+2;
                                    break;
                                }
                            }
                        }
                    }
                case "PRINT":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("PRINT")){//checks if INT is at the top of the stack
                      System.out.println("Please declare a variable/literal to print at line "+x+".");
                      ctr=tokens.size();
                      break;
                    }
                    else{
                       if(ctr+1<tokens.size()){
                            if(keywords.contains(tokens.get(ctr+1))){//if true
                              System.out.println(tokens.get(ctr+1)+ " is a reserved keyword. Please review line "+x+".");
                              ctr=ctr+2;
                              break;
                            }
                            else if(characterChecker(tokens.get(ctr+1))){
                              System.out.println(tokens.get(ctr+1)+ " is an invalid variable name. Please review line "+x+".");
                              ctr=ctr+2;
                              break;
                            }
                            else if(isNumeric(tokens.get(ctr+1))){
                              //print the number
                              ctr=ctr+2;
                              break;
                            }
                            else if(varNames.contains(tokens.get(ctr+1))){
                              //print values
                              ctr=ctr+2;
                              break;
                            }
                            else if(operations.contains(tokens.get(ctr+1))){
                                if(ctr+3<tokens.size()){
                                    try{
                                        expression.push(tokens.get(ctr+2));
                                        expression.push(tokens.get(ctr+3));
                                        evalExpr(expression);
                                        ctr=ctr+4;
                                        break;
                                    }catch(ArrayIndexOutOfBoundsException e){
                                        System.out.println("Syntax error for PRINT at line "+x+".");
                                        ctr=ctr+3;
                                        break;
                                    } 
                                }
                                else{
                                    System.out.println("Invalid/Missing expression after operation ("+tokens.get(ctr+1)+"). Please review line "+x+".");
                                    ctr=ctr+4;
                                }
                            }
                            else{
                                System.out.println("Unknown variable ("+tokens.get(ctr+1)+") at line "+x+".");
                                ctr=ctr+2;
                                break;
                            }
                        }
                    }
                case "ADD":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("ADD")){//checks if INT is at the top of the stack
                        System.out.println("Please declare an expression to add at line "+x+".");
                         ctr=tokens.size();
                        break;
                    }
                    else{
                        //dili lang niya e-print ang ADD exp exp e-ignore lang siya sa system
                        if(ctr+2<tokens.size()){
                            expression.push(tokens.get(ctr+1));
                            expression.push(tokens.get(ctr+2));
                            evalExpr(expression);
                            ctr=ctr+3;
                            break;  
                        }
                        else{
                            System.out.println("Syntax error for ADD at line "+x+".");
                            ctr=ctr+2;
                            break;
                        } 
                    }
                case "SUB":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("SUB")){//checks if INT is at the top of the stack
                        System.out.println("Please declare an expression to subtract at line "+x+".");
                         ctr=tokens.size();
                        break;
                    }
                    else{
                        //dili lang niya e-print ang ADD exp exp e-ignore lang siya sa system
                        if(ctr+2<tokens.size()){
                            expression.push(tokens.get(ctr+1));
                            expression.push(tokens.get(ctr+2));
                            evalExpr(expression);
                            ctr=ctr+3;
                            break;  
                        }
                        else{
                            System.out.println("Syntax error for SUB at line "+x+".");
                            ctr=ctr+2;
                            break;
                        } 
                    }                    
                case "MULT":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("MULT")){//checks if INT is at the top of the stack
                        System.out.println("Please declare an expression to multiply at line "+x+".");
                         ctr=tokens.size();
                        break;
                    }
                    else{
                        //dili lang niya e-print ang ADD exp exp e-ignore lang siya sa system
                        if(ctr+2<tokens.size()){
                            expression.push(tokens.get(ctr+1));
                            expression.push(tokens.get(ctr+2));
                            evalExpr(expression);
                            ctr=ctr+3;
                            break;  
                        }
                        else{
                            System.out.println("Syntax error for MULT at line "+x+".");
                            ctr=ctr+2;
                            break;
                        } 
                    }
                case "DIV":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("DIV")){//checks if INT is at the top of the stack
                        System.out.println("Please declare an expression to divide at line "+x+".");
                         ctr=tokens.size();
                        break;
                    }
                    else{
                        //dili lang niya e-print ang ADD exp exp e-ignore lang siya sa system
                        if(ctr+2<tokens.size()){
                            expression.push(tokens.get(ctr+1));
                            expression.push(tokens.get(ctr+2));
                            evalExpr(expression);
                            ctr=ctr+3;
                            break;  
                        }
                        else{
                            System.out.println("Syntax error for DIV at line "+x+".");
                            ctr=ctr+2;
                            break;
                        } 
                    }
                case "MOD":
                    if(ctr==tokens.size()-1&&tokens.get(ctr).equals("MOD")){//checks if INT is at the top of the stack
                        System.out.println("Please declare an expression for modulo at line "+x+".");
                         ctr=tokens.size();
                        break;
                    }
                    else{
                        //dili lang niya e-print ang ADD exp exp e-ignore lang siya sa system
                        if(ctr+2<tokens.size()){
                            expression.push(tokens.get(ctr+1));
                            expression.push(tokens.get(ctr+2));
                            evalExpr(expression);
                            ctr=ctr+3;
                            break;  
                        }
                        else{
                            System.out.println("Syntax error for MOD at line "+x+".");
                            ctr=ctr+2;
                            break;
                        } 
                    }                    
                case "NEWLN":
                    //ignore newln
                    ctr++;
                    break;
               default:
//                  if(opr.equals("NEWLN")){
//                      System.out.println("newln");
//                  }
                    if(varNames.contains(opr)){
                        System.out.println("Illegal call of variable ("+opr+") at line"+x+".");
                    }
                    else if(operations.contains(opr)){
                        System.out.println("Illegal declaration of an operation ("+opr+") at line "+x+".");
                    }
                    else if(keywords.contains(opr)){
                        System.out.println("Illegal declaration of an keyword ("+opr+") at line "+x+".");
                    }
                    else if(characterChecker(opr)){
                        System.out.println("Unrecognized character/variable: "+opr);
                    }
                    ctr++;          
           }
       }
       return tokens;
    }

    public static void main(String[] args) {
        String filename="test";
        String home = System.getProperty("user.home"); //gets system property of PC
        Path path = Paths.get(filename);

        //code below prints the path of the file
        //System.out.println(path.toAbsolutePath().toString());

        list = readFile(path.toAbsolutePath().toString());

        //uncomment below to check file if read properly
//         Iterator<String> itr = list.iterator();
//         while(itr.hasNext()){
//             System.out.println(itr.next());
//         }

        String delim = " ";
        String str = String.join(delim,list);

        String[] words=str.split("\\s+");//splits contents for array
        
        Stack<String> stacks = new Stack<String>();
        stacks = stringArr(words);//function to check LOI and IOL
        
        List<String> preTokens = new ArrayList<>();
        List<String> finalTokens = new ArrayList<>();
        
        int ctr=1;
        
        while(ctr<list.size()-1){  
           String newStr = list.get(ctr).trim();
           preTokens= getTokens(newStr);
//           System.out.println(preTokens);
           ctr++;
        }
        
        //System.out.println(finalTokens.size());
    }
    
}
