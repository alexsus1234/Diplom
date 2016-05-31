import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import parser.Java8Lexer;
import parser.Java8Parser;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedList;

/**
 * Created by Alex on 16.04.2016.
 */

public class Main {

    public static void main(String args[]) {
        try {
            LinkedList<String> code;
            code=Code.getCodeFromList(FileAPI.readDocxFile("C:\\Users\\Alex\\Desktop\\111.docx"));
            CodeType.recogniseCode(code);
            // getCommentFromList(readDocxFile("C:\\Users\\Alex\\Desktop\\111.docx")) ;
            CharStream input = new ANTLRInputStream(new FileInputStream(new File("input.txt")));
            Java8Lexer lexer = new Java8Lexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Java8Parser parser = new Java8Parser(tokens);
           ParseTree tree = parser.elementValue(); // begin parsing at init rule
            System.out.println(tree.toStringTree(parser)); // print
           for(int i=0;i<tokens.size();i++) {
               System.out.println(tokens.get(i).getText()+" "+tokens.get(i).getType()+" "+tokens.get(i).getCharPositionInLine());
           }


        }catch (Exception e){
            e.printStackTrace();
        }

        // getCodeFromList(readDocxFile("C:\\Users\\Alex\\Desktop\\11.docx"));


    }
}
