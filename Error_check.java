

package error_check;
import java.io.*;
import java.util.*;
import java.io.File;
import java.nio.file.*;
import java.util.ArrayList;

public class Error_check {
    
    private static List<String> varNames = new ArrayList<>();
    private static List<String> operations = new ArrayList<String>(Arrays.asList("ADD","MULT","DIV","SUB","MOD"));

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
    
    public static void characterChecker(String check){
        for(int i=0; i<check.length(); i++){
        char ch = check.charAt(i);           
            //checks for special characters
            if(!Character.isDigit(ch)&&!Character.isAlphabetic(ch)){
                System.out.println("The following word/character is not recognized: "+check);
                System.exit(0);
            }  
        }
    }
    
    public static void matchTypes(String types, int k){
        List<String> lists = Arrays.asList("INT","STR","IS","INTO","IS","BEG","PRINT","ADD","SUB","MULT","DIV","MOD","NEWLN");   
            
            if(lists.contains(types)==true&&k!=2){
                System.out.println("Error, "+types+" is not a legal variable name");
                System.exit(0);
            }
            else if(types=="INT"&&k==2){
                characterChecker(types);
            }
            else{
                characterChecker(types);
            }      
    }
    
    public static boolean isNumeric(String strNum){
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    public static void evalExpr(Stack<String> expr){
        String var;
        int z=0,d=0;
        
        while(d<2){
            try{
            if(varNames.contains(expr.get(d))){
                //if true
            d++; 
            }
            else{
                var = expr.get(d).toString();
                while(z<var.length()){
                char ch = var.charAt(z);           
                    //checks for special characters
                    if(!Character.isDigit(ch)&&!Character.isAlphabetic(ch)){
                        System.out.println("The following word/character is not recognized: "+var);
                        System.exit(0);
                    }
                    else if(Character.isDigit(ch)){//means it's a literal
                        z++;
                    }
                    else{
                        System.out.println("Unrecognized variable: "+var);
                        System.exit(0);
                    }
                }//while end
            d++;
            }
            //d++; original placement
            }catch(ArrayIndexOutOfBoundsException e){
                //do nothing
                d=3;
            }
        }//while end
    }
    
    public static Stack<String> getTokens(String strs){
       Stack<String> tokens = new Stack<>();
       StringTokenizer tokenizer = new StringTokenizer(strs," ");
       Stack<String> expression = new Stack<>();
       
       int x=tokenizer.countTokens();
       
       while(tokenizer.hasMoreElements()){
           tokens.push(tokenizer.nextToken());
       }
       
       //System.out.println(tokens);
       
       int ctr =0;
       String opr;
       
       while(ctr<tokens.size()){
          opr = tokens.get(ctr);
          
          switch(opr){
              case "INT":
                  int a=0;
                  int b=ctr+1;//value of ctr is INT
                  String peeksInt = tokens.peek();
                  
                  if(peeksInt.equals("INT")){//checks if INT is at the top of the stack
                      System.out.println("Please declare a variable and value for INT.");
                      System.exit(0);
                  }
                  else{
                    while(a<2){
                        if(a==0){
                            if(isNumeric(tokens.get(b))==false){//checks variable name
                               
                               if(varNames.contains(tokens.get(b))){
                                    System.out.println("Variable name "+tokens.get(b)+" already exists.");
                                    System.exit(0);
                               }
                               else{
                                    matchTypes(tokens.get(b),0); 
                                    varNames.add(tokens.get(b));
                               }
                            }
                            else{
                                System.out.println(tokens.get(b)+" is an invalid variable name.");
                            }
                            
                            if(b==tokens.size()-1){
                                b=b-1;
                                ctr=tokens.size();
                            }
                            else{
                                b++;
                            }
                        }
                        else if(a==1){//checks operation IS
                            if(tokens.get(b).equals("IS")){
                                if(tokens.lastElement().equals("IS")){
                                    System.out.println("A variable has no set value.");
                                    System.exit(0);
                                }

                                try{
                                    int d = Integer.parseInt(tokens.get(b+1));
                                }catch(NumberFormatException nfe){
                                    System.out.println(nfe + " is not an integer.");
                                    System.exit(0);
                                }
                                ctr=ctr+4;
                                //System.out.println(ctr+" ctr rn");
                            }
                            else{
                                ctr=ctr+2;
                            }
                        }
                        a++;//counter for while
                    }                      
                  }
                  //System.out.println(varNames);
                  break;
              case "STR":
                  int k=ctr+1;
                  String peeksStr = tokens.peek();
                  
                  if((k)==tokens.size()||peeksStr.equals("STR")){//checks if the line only contains INT
                    System.out.println("Please declare a variable for STR");
                  }
                  else{
                    
                    if(varNames.contains(tokens.get(k))){
                        System.out.println("Variable name "+tokens.get(k)+" already exists.");
                        System.exit(0);                        
                    }
                    else{
                        if(isNumeric(tokens.get(k))==false){
                            matchTypes(tokens.get(k),0); 
                            varNames.add(tokens.get(k));
                        }
                        else{
                            System.out.println(tokens.get(k)+" is an invalid variable name.");
                        }   
                    }//end else for boolean checkingSTR
                  }
                  ctr=ctr+2;
                  System.out.println(varNames);
                  break;
              case "IS": 
                  String peeksIs = tokens.peek();
                  if(peeksIs.equals("IS")){//checks if INT is at the top of the stack
                      System.out.println("Illegal placement of assignment operation.");
                      System.exit(0);
                  }      
                  break;
              case "INTO": 
                  String peeksInto = tokens.peek();
                  
                  if(peeksInto.equals("INTO")){//checks if INT is at the top of the stack
                      System.out.println("Syntax error for INTO");
                      System.exit(0);
                  }
                  else{
                        try{
                            if(tokens.get(ctr+2).equals("IS")){
                                matchTypes(tokens.get(ctr+1),0);//if passes through w/o err
                                if(varNames.contains(tokens.get(ctr+1))){
                                    if(operations.contains(tokens.get(ctr+3))){
                                        try{
                                            expression.push(tokens.get(ctr+4));
                                            expression.push(tokens.get(ctr+5));
                                            evalExpr(expression);
                                        }catch(ArrayIndexOutOfBoundsException e){
                                            System.out.println("Syntax Error for operation. Out of bounds.");
                                            System.exit(0);
                                        }
                                        ctr=ctr+6;
                                    }
                                    else{
                                        System.out.println(tokens.get(ctr+3)+" is an invalid operation.");
                                        System.exit(0);
                                    }
                                }
                                else{
                                    System.out.println("Variable name "+tokens.get(ctr+1)+" is not declared.");
                                    System.exit(0);
                                }
                            }
                            else{
                                System.out.println("Syntax Error for INTO. IS operation is missing.");
                                System.exit(0);
                            }
                        }catch(ArrayIndexOutOfBoundsException e){
                            System.out.println("Syntax Error for INTO. Out of bounds.");
                            System.exit(0);
                        }                      
                  }                  
                  break;
              case "BEG": 
                  int l=ctr+1;
                  String peeksBeg = tokens.peek();
                  
                  if((l)==tokens.size()||peeksBeg.equals("BEG")){//checks if the line only contains INT
                    System.out.println("Please declare a variable for BEG");
                    System.exit(0);
                  }
                  else{
                    if(varNames.contains(tokens.get(l))){
                        //since it's true, do nothing
                    }
                    else{
                        System.out.println("Variable name "+tokens.get(l)+" is not allowed.");
                        System.exit(0);
                    }
                  }
                  ctr=ctr+2;   
                  System.out.println(varNames);
                  break;
              case "PRINT":
                  String peeksPrint = tokens.peek();

                  if(peeksPrint.equals("PRINT")){//checks if INT is at the top of the stack
                      System.out.println("Syntax Error for PRINT operation.");
                      System.exit(0);
                  }
                  else{
                      if(varNames.contains(tokens.get(ctr+1))){
                          //if it's a variable name, gets accepted
                          ctr=ctr+2;
                      }
                      else if(operations.contains(tokens.get(ctr+1))){
                        try{
                            expression.push(tokens.get(ctr+2));
                            expression.push(tokens.get(ctr+3));
                            evalExpr(expression);
                        }catch(ArrayIndexOutOfBoundsException e){
                            System.out.println("Syntax Error for ADD operation. Out of bounds.");
                            System.exit(0);
                        }
                        ctr=ctr+4;
                      }
                      else{
                            String print = tokens.get(ctr+1).toString();
                            int counts=0;
                            while(counts<print.length()){
                            char ch = print.charAt(counts);           
                                //checks for special characters
                                if(!Character.isDigit(ch)&&!Character.isAlphabetic(ch)){
                                    System.out.println("The following word/character is not recognized: "+print);
                                    System.exit(0);
                                }
                                else if(Character.isDigit(ch)){//means it's a literal
                                    counts++;
                                }
                                else{
                                    System.out.println("The following word/character is not recognized: "+print);
                                    System.exit(0);
                                }
                            }
                            System.out.println("literal: "+print);
                            ctr=ctr+2;
                        }
                  }
                  break;                  
              case "ADD":
                  String peeksAdd = tokens.peek();

                  if(peeksAdd.equals("ADD")){//checks if INT is at the top of the stack
                      System.out.println("Syntax Error for ADD operation.");
                      System.exit(0);
                  }
                  else{                  
                      try{
                          expression.push(tokens.get(ctr+1));
                          expression.push(tokens.get(ctr+2));
                          evalExpr(expression);
                      }catch(ArrayIndexOutOfBoundsException e){
                          System.out.println("Syntax Error for ADD operation. Out of bounds.");
                      }
                  }
                  ctr=ctr+3;
                  break;
              case "SUB":
                  String peeksSub = tokens.peek();

                  if(peeksSub.equals("SUB")){//checks if INT is at the top of the stack
                      System.out.println("Syntax Error for SUB operation.");
                      System.exit(0);
                  }
                  else{                  
                      try{
                          expression.push(tokens.get(ctr+1));
                          expression.push(tokens.get(ctr+2));
                          evalExpr(expression);
                      }catch(ArrayIndexOutOfBoundsException e){
                          System.out.println("Syntax Error for SUB operation. Out of bounds.");
                      }
                  }
                  ctr=ctr+3;
                  break;              
              case "MULT":
                  String peeksMult = tokens.peek();

                  if(peeksMult.equals("SUB")){//checks if INT is at the top of the stack
                      System.out.println("Syntax Error for MULT operation.");
                      System.exit(0);
                  }
                  else{                  
                      try{
                          expression.push(tokens.get(ctr+1));
                          expression.push(tokens.get(ctr+2));
                          evalExpr(expression);
                      }catch(ArrayIndexOutOfBoundsException e){
                          System.out.println("Syntax Error for MULT operation. Out of bounds.");
                      }
                  }
                  ctr=ctr+3;
                  break;                  
              case "MOD":
                  String peeksMod = tokens.peek();

                  if(peeksMod.equals("SUB")){//checks if INT is at the top of the stack
                      System.out.println("Syntax Error for MOD operation.");
                      System.exit(0);
                  }
                  else{                  
                      try{
                          expression.push(tokens.get(ctr+1));
                          expression.push(tokens.get(ctr+2));
                          evalExpr(expression);
                      }catch(ArrayIndexOutOfBoundsException e){
                          System.out.println("Syntax Error for MOD operation. Out of bounds.");
                      }
                  }
                  ctr=ctr+3;
                  break;                  
              case "NEWLN": ctr++; break;
              default:
                  if(opr.equals("NEWLN")){
                      System.out.println("newln");
                  }
                  else{
                    System.out.println("Unrecognized character: "+opr);
                    characterChecker(opr);
                    System.exit(0);                     
                  }
            }//switch
        //ctr++;
       }//while
       return tokens;
    }
    
    public static void main(String[] args) {
        String filename="test";
        String home = System.getProperty("user.home"); //gets system property of PC
        Path path = Paths.get(filename);

        //code below prints the path of the file
        //System.out.println(path.toAbsolutePath().toString());

        List<String> list = new ArrayList<String>();
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
        String tkn;
        
        int ctr=1;
        
        while(ctr<list.size()-1){
           int count=0;
           
           String newStr = list.get(ctr).trim();
           preTokens= getTokens(newStr);
           //System.out.println(preTokens);
           ctr++;
        }
        
        //System.out.println(finalTokens.size());
    }
    
}
