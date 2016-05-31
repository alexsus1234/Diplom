import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import parser.Java8Lexer;
import parser.Java8Parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Alex on 16.04.2016.
 */

public class Main {

    public static void main(String args[]) {
        try {
            LinkedList<String> code;
            code=Code.getCodeFromList(FileAPI.readDocxFile("C:\\Users\\Alex\\Desktop\\111.docx"));
//            code=Code.getCodeFromList(FileAPI.readDocxFile(args[0]));
            int codeType=CodeType.recogniseCode(code);

            if(codeType==2){
                System.out.println("This is Java code");
                FileAPI.outFile("output_files\\java_code",code);
                System.out.println("Do you want to make lexical analysis of java code Y/N");
                Scanner sc = new Scanner(System.in); // создаём объект класса Scanner
                System.out.print("Введите целое число: ");
                String s;
                sc.hasNext();
                s = sc.next();
                if(s.toUpperCase().equals("Y")){
                    CharStream input = new ANTLRInputStream(new FileInputStream(new File("input.txt")));
                    Java8Lexer lexer = new Java8Lexer(input);
                    CommonTokenStream tokens = new CommonTokenStream(lexer);
                    Java8Parser parser = new Java8Parser(tokens);
                    ParseTree tree = parser.elementValue(); // begin parsing at init rule
               //     System.out.println(tree.toStringTree(parser)); // print
                    for(int i=0;i<tokens.size();i++) {
                        System.out.println(tokens.get(i).getLine()+":"+tokens.get(i).getCharPositionInLine()+" "+tokens.get(i).getText()+" "+tokens.get(i).getType()+" ");
                        //System.out.println(tokens.get(i));

                    }
                }
            }else if(codeType==2){
                System.out.println("This is C# code");
                FileAPI.outFile("output_files\\C#_code",code);
            }else if(codeType==3){
                System.out.println("This is C++ code");
                FileAPI.outFile("output_files\\C++ code",code);
            } else {
                System.out.println("This language has not been recognised");

            }

            //getCommentFromList(readDocxFile("C:\\Users\\Alex\\Desktop\\111.docx")) ;
            // getCommentFromList(readDocxFile("C:\\Users\\Alex\\Desktop\\111.docx")) ;




        }catch (Exception e){
            e.printStackTrace();
        }

        // getCodeFromList(readDocxFile("C:\\Users\\Alex\\Desktop\\11.docx"));


    }
}
