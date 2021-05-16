/*
Function: Parser 
 */
package iol_project;
import java.util.Set;
import java.util.Collection; 
import java.util.Map.Entry; 
import java.util.LinkedHashMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author USER
 */
public class IOL_Project {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException { 
        // TODO code application logic here
        
        int sentenceCount = 0; 
        String[] AllLexemes = new String[]{"LOI","IOL","NEWLN", "MOD","DIV", "MULT","SUB", "ADD","PRINT", "BEG","PRINT","INTO","IS","STR","INT", "IOL","DIV", "MOD"};
        //CONVERT AllLexemes(Array) TO AllLexemess(LIST)
        List<String> AllLexemess = Arrays.asList(AllLexemes);
        
        String[] DT= new String[]{"INT","STR"}; //datatypes
//        String[] input= new String[]{"BEG"}; //input operation
        String[] operations= new String[]{"MOD","DIV", "MULT","SUB", "ADD",}; //arith operation
//        String[] output= new String[]{"PRINT"}; //output operation
//        String[] input= new String[]{"BEG"}; //input operation
        List<String> AllDT = Arrays.asList(DT);
        List<String> Alloperations = Arrays.asList(operations);



        int flagStart=0;  

       
       int del=0;
       int x=0;//code line counter
       
       File f=new File("C:\\Users\\USER\\Desktop\\IOLtoken.iol");
       InputStream stream = new FileInputStream(f);       
       Scanner sc = new Scanner(stream);
       FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        StringBuffer sb=new StringBuffer(); 
        StringBuffer newsb=new StringBuffer();
        StringBuffer stringers=new StringBuffer();
        List <String> content = new ArrayList<String>();
        List <String> inputs = new ArrayList<String>();
        String line;
        int g=0;
        Stack<String> stack = new Stack<String>();
        List <String> input = new ArrayList<String>();
        List <String> action = new ArrayList<String>();

        
        
        //used
        ArrayList<String> inputlines = new ArrayList<String>(); //number of rows
        ArrayList<String> inpline = new ArrayList<String>(); //
        ArrayList <ArrayList<String>> PWord=new ArrayList <>();//per word
//        while((line=br.readLine())!=null){
//            x++;
//             line=line.trim();
//            if(line.isEmpty()){
//                //ignore
//                continue;
//            }
//            else{
//                content.add(line);
//                newsb.append(line);
//            }
//            // SPLITTING 
//            String[] words = line.split("\\s+"); //split per word
//            String[] lines = line.split("\\n"); //split per line
//            
            
            
         //print lines
//          for(int i=0;i<lines.length;i++){{ 
//               ArrayList<String> Keywords = new ArrayList<String>(); 
//               System.out.println(lines[i]);
//         }       
         //print words
//         for(int j=0;j<words.length;j++){ 
//               System.out.println(words[j]);
//         }   
            int i=0;
    File file=new File("C:\\Users\\USER\\Desktop\\IOLtoken.iol");
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
    System.out.println(PWord.size());
        //traverse thru each word TEMPLATE CHECKING
       for(int a=0;a<=inputlines.size()-1;a++){ 
//               System.out.println(PWord.get(1)); //gets array per line
               for (int b = 0; b<=PWord.get(a).size()-1; b++) {
//                   System.out.println(PWord.get(a).get(b));//gets each word (INT) line by line         
         }   
       } 
        
     
         
 /*        
    IMPLEMENT USING STACK     
         
   */  

 for(int a=0;a<=inputlines.size()-1;a++){ 
      int b=0;
      //print info per line
      System.out.println("\n------------------------\nLINE "+(a+1)+" INFORMATION"+"\nnum. of elements:"+PWord.get(a).size()+"\n"+PWord.get(a));
//               System.out.println(PWord.get(a)); //gets array per line
//               System.out.println(PWord.get(a).get(b));//gets each word  line by line 
                //INVALID when only one element is present
                if(PWord.get(a).size()==1&&((PWord.get(a).get(b).equals("NEWLN"))==false)){
                  System.out.println(PWord.get(a).get(b)+" invalid wrong syntax, only one element"+PWord.get(a).get(b));
                }
               //-------Line per line SYNTAX CHECKING starts here--------
               while (PWord.get(a).size()-1 > b) {
   
                System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b); 
               
                //saving variables (first word of the line)--- DT VAR | DT VAR IS VAL
                if(AllDT.contains(PWord.get(a).get(b))==true&&PWord.get(a).size()!=b){
                     b++;
                        //INT DT
                         if(PWord.get(a).get(b).equals("IDENT")){
                          System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b); 
                         //IF DT VAR
                            if(PWord.get(a).size()-1==b||AllDT.contains(PWord.get(a).get(b+1))){
                                 System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b+"\nDT VAR = VALID "); 
                            }  
                            //IF DT VAR IS VAL
                            else if(PWord.get(a).size()-1!=b){
                                b++;
                                if(PWord.get(a).get(b).equals("IS")){
                                    System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b); ; 
                                    b++;
                                    if(PWord.get(a).get(b).equals("INT_LIT")){
                                        System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b+"\nDT VAR IS VAL = VALID ");
                                    }
                                    else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax-DT VAR IS (missing VAL)"+PWord.indexOf(b));break; }
                                }
                                else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax-DT VAR IS (missing VAL)"+PWord.indexOf(b));break; }
                            }
                            else{System.out.println(PWord.get(a).get(b)+"invalid"+PWord.indexOf(b)); }
                        }
                         else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax-DT (missing VAR)"+PWord.indexOf(b));break; }
                  }
                //INPUT VAR - Only input is BEG 
                //current problem - accepts only BEG, need to check if it's the last element in the array
                else if(PWord.get(a).get(b).equals("BEG")&&b!=PWord.lastIndexOf(PWord.get(a))-1){
                    
                    b++;
                   //BEG IDENT
                   if(PWord.get(a).get(b).equals("IDENT")){
                    System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b+"\nINPUT VAR = VALID ");
                    }
                   //INVALID
                   else{ System.out.println(PWord.get(a).get(b)+"invalid"+PWord.indexOf(b)); }
                }
                
                //OUTPUT VAR | OUTPUT VAR- Print Variable 
                else if(PWord.get(a).get(b).equals("PRINT")&&b!=PWord.lastIndexOf(PWord.get(a))-1){
                    b++;
                   //PRINT IDENT
                   if(PWord.get(a).get(b).equals("IDENT")||PWord.get(a).get(b).equals("INT_LIT")){
                        System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b+"\nINPUT VAR = VALID ");
                    }
                   //OP EXPR EXPR
                   else if(Alloperations.contains(PWord.get(a).get(b))){
                               
                                    //INTO VAR IS OP 
                                    while(Alloperations.contains(PWord.get(a).get(b))){ //while op are being read, iterate and push 
                                        System.out.println("\nElement : "+PWord.get(a).get(b)+"\nIndex: "+b); 
                                        b++;
                                    } 
                                    //OP INT_LIT|OP IDENT 
                                    if(PWord.get(a).get(b).equals("INT_LIT")||PWord.get(a).get(b).equals("IDENT")&&PWord.get(a).size()-1!=b){
                                        System.out.println("\nElement : "+PWord.get(a).get(b)+"\nIndex: "+b); 
                                        b++;
                                        if(PWord.get(a).get(b).equals("INT_LIT")||PWord.get(a).get(b).equals("IDENT")){
                                            System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b+"\nOP EXPR EXPR = VALID");      
                                        }
                                        else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax - only OP EXPR !EXPR is found "+b);break; }
                                        
                                    } 
                                    else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax - only OP EXPR is found "+b);break; }
                    }   
                   else{ System.out.println(PWord.get(a).get(b)+"whyinvalid"+PWord.indexOf(b));break;}
                }                
                
                
                //ASSIGN → INTO VAR IS EXPR 
                //saving variables (first word of the line)--- DT VAR | DT VAR IS VAL
                else if(PWord.get(a).get(b).equals("INTO")&&b!=PWord.lastIndexOf(PWord.get(a))-1){
                     b++;
                        //INTO IDENT IS MULT
                         if(PWord.get(a).get(b).equals("IDENT")){
                          System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b); 
                          b++;
                          if(PWord.get(a).get(b).equals("IS")){
                            System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b); 
                            b++; 
                            //INTO VAR IS EXPR 
                            if(Alloperations.contains(PWord.get(a).get(b))){
                               System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b); 
                                    //INTO VAR IS OP 
                                    while(Alloperations.contains(PWord.get(a).get(b))){ //while op are being read, iterate and push 
                                        stack.push(PWord.get(a).get(b));
                                        b++;
                                    } 
                                    //read numerical value or NUMERICAL variable
                                    //INTO VAR IS OP INT_LIT|IDENT
                                    if(PWord.get(a).get(b).equals("INT_LIT")){
                                        stack.push(PWord.get(a).get(b));
                                    } 
                                    else if(PWord.get(a).get(b).equals("IDENT")){//PROBLEM: CHECK DT OF IDENT
                                        stack.push(PWord.get(a).get(b));
                                    }
                                    else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax - only OP are found "+b);break; }

                                    b++;
                                    //INTO VAR IS OP INT_LIT|IDENT INT_LIT|IDENT 
                                    if(PWord.get(a).get(b).equals("INT_LIT")){
                                        stack.push(PWord.get(a).get(b));
                                    } 
                                    else if(PWord.get(a).get(b).equals("IDENT")){//PROBLEM: CHECK DT OF IDENT
                                        stack.push(PWord.get(a).get(b));
                                    }
                                    else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax - only OP VAL|OP VAL are found "+b);break; }
                                    System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b+"\nINTO IDENT IS OP EXPR EXPR = VALID "); 
                            }
                            else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax - only INTO IS found "+b);break; }  
                          } 
                          
                        }
                    else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax - only INTO is found "+b);break; }     
                }        
                //EXPR  → ADD EXPR EXPR | SUB EXPR EXPR | MULT EXPR EXPR | DIV EXPR EXPR | MOD EXPR EXPR | INT |
                //if EXPR starts with OPERATION
                else if(Alloperations.contains(PWord.get(a).get(b))){
                   
                   while(Alloperations.contains(PWord.get(a).get(b))){ //while op are being read, iterate and push 
                       stack.push(PWord.get(a).get(b));
                       b++;
                   } 
                   //read numerical value or NUMERICAL variable
                   if(PWord.get(a).get(b).equals("INT_LIT")){
                       stack.push(PWord.get(a).get(b));
                   } 
                   else if(PWord.get(a).get(b).equals("IDENT")){//PROBLEM: CHECK DT OF IDENT
                       stack.push(PWord.get(a).get(b));
                   }
                   else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax - only OP are found "+b);break; }
                    
                   b++;
                   if(PWord.get(a).get(b).equals("INT_LIT")){
                       stack.push(PWord.get(a).get(b));
                   } 
                   else if(PWord.get(a).get(b).equals("IDENT")){//PROBLEM: CHECK DT OF IDENT
                       stack.push(PWord.get(a).get(b));
                   }
                   else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax - only OP VAL|OP VAL are found "+b);break; }
                   System.out.println("\nElement: "+PWord.get(a).get(b)+"\nIndex: "+b+"\n OP EXPR EXPR = VALID "); 
                }
                
                
               //PROBLEM:if line has only one element(b=0)
               
               
               
               
               
                        
                    
//               else{System.out.println(PWord.get(a).get(b)+"invalid wrong syntax"+PWord.indexOf(b)); }  
        //if last in array
      if(PWord.get(a).size()!=b)
       b++;
      else break;
       } 
 
 
 
 
 
//      if(words[0].equals("IOL")){
//          //CONTINUE
//          //check if lexeme(DATA TYPE)
//        if(words[j].equals("INT")||words[j].equals("STRING")){
//        int flag=0;
//        Keywords.add(words[j]);
//        Lexemes_all.put(words[j], Keywords);
//        
//        //if data type literals(NUMBERS) || words[j+3] = value
//        if(isInteger(words[j+3])==true){
//            //if(words.matches("-?\\d+")){ //dont delete
//            INT_LIT.add(words[j+3]); 
//            Lexemes_all.put("INT_LIT", INT_LIT);
//            flag=1;
//            System.out.println(words[j]);
//        }
//     
//      }        
//      else {
//      //SHOW ERROR NOT COMPATIBLE
////      System.out.println("NO IOL");  
//      break;
//      } 
//        
//        
// //END       
//        }
      
    }
      
    }
 
    
public static boolean isVar(String str){
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
//if ## is read
public static boolean isStartComment(String str) {
    int i = 0;
        char c = str.charAt(i);
        if (c=='#') {
            i++;
            if (c=='#') {      
            return true;
            }
        }   
        return false;
}
//if $ is read
public static boolean isStartStr(String str) {
    int i = 0;
       
        if (str.charAt(0)=='$') {
            return true;
        }
        return false;
}
//checks if there is $ in mid 
public static boolean isStartStr3(String str) {
    int i = 1;
        for (; i < str.length()-1; i++) {
            char c = str.charAt(i);
            if (c=='$') {
            return true;
            }
        }
        return false;
}
//if 2 '$'
public static boolean isEndStr2(String str) {
       
        if (str.charAt(1)=='$') {
            return true;
        }
        return false;
}
//is $ is read at the end of a word
public static boolean isEndStr(String str){
    int i = 0;
        if(str.charAt(str.length()-1)=='$'){
            return true;
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
    public static boolean checkSpecialChar(char ch) {
        char[] specialCh = {'!','@',']','#','$','%','^','&','*','-','.'}; 
     for (Character c : specialCh) {
      if (ch == c){
          return true;
      }
 }
    
    return false;
    }
    //REMOVES THE START SIGN FOR PRINT
    public static String removeSignStart(String str) {
        int i = 1;
         char a[]= new char[30];
         char c[] = str.toCharArray(); 
    for (; i < str.length()-1; i++) {
        if(c[i]=='$'){
            i++;
        }
       a[i]=c[i];
    }
    a[str.length()-1]=c[str.length()-1];
    str=String.valueOf(a);
    return str;
}
public static String removeSignEnd(String str) {
     int i = 0;
         char a[]= new char[30];
         char c[] = str.toCharArray();
    if(str.charAt((str.length()-1))=='$'){
        for (; i < str.length()-1; i++) {
            a[i]=c[i];
        }  
    }     
     str=String.valueOf(a);
    return str;  
    }
    
    public static boolean isFloat(String str) {
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
       
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')||c == '.'||checkSpecialChar(c)==true||str.charAt(0)=='.') {
            flag=0;
        }
        if(c=='.'){
            flag=1;
        }
    }
    if (flag==0){
             return false;
         }
   
        return true;
    
    
    
}
 public boolean equals(Stack<String> s1, Stack<String> s2){
    Stack<String> aux = new Stack<>();
    boolean equal = true;
    while(!s1.isEmpty() && !s2.isEmpty()){
        String first = s1.pop();
        String second = s2.pop();
        aux.push(first);
        aux.push(second);
        if(first != second){
            equal = false;
            break;
        }
    }
    // Equal if both stacks are empty
     if(!(s1.isEmpty() || s2.isEmpty())){
        equal = false;
     }

    // Reconstruct
    while(!aux.isEmpty()){
        s2.push(aux.pop());
        s1.push(aux.pop());
    }
    return equal;
}    
    
}

