import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Created by Alex on 29.05.2016.
 */
public class CodeType {
   /* public ArrayList<Integer> cPlusCode(ArrayList<Integer> a){

    }*/
   public static int cPlusCode(LinkedList<String> fileLines){
       int i = 0, len = fileLines.size();
       int coutMatcher=0;
       Pattern inc=Pattern.compile("(#include)([\\W\\w\\s0-9\\.;:]+)");
       Pattern main=Pattern.compile("((int|void)\\s+main\\s*(\\([\\W\\w\\s0-9\\.,;:]+\\)))");
       Pattern mainImpl=Pattern.compile(main+"((\\s*\\{)*)");
       for (i = 0; i < len; i ++) {
           String current = FileAPI.ltrim(FileAPI.rtrim(fileLines.get(i)));
           if(current.equals("")){

           }
           else{
               if(inc.matcher(current).matches()||mainImpl.matcher(current).matches()){
                   coutMatcher++;
                    System.out.println(current+" ");
               }

           }
       }
       return coutMatcher;


   }
    public static int cSharpCode(LinkedList<String> fileLines){
        int i = 0, len = fileLines.size();
        int coutMatcher=0;
        Pattern inc=Pattern.compile("(using)([\\W\\w\\s0-9\\.;:\\)\\(]+)");
        Pattern main=Pattern.compile("(static\\s+(int|void)\\s+main\\s*(\\([\\W\\w\\s0-9\\.,;:]+\\)))");
        Pattern mainImpl=Pattern.compile(main+"((\\s*\\{)*)");
        Pattern nameSp=Pattern.compile("((namespace)\\s+([\\W\\w\\s0-9\\.]+))");
        Pattern nameSpimpl=Pattern.compile(nameSp+"((\\s*\\{)*)");
        for (i = 0; i < len; i ++) {
            String current = FileAPI.ltrim(FileAPI.rtrim(fileLines.get(i)));
            if(current.equals("")){

            }
            else{
                if(inc.matcher(current).matches()||mainImpl.matcher(current).matches()||nameSpimpl.matcher(current).matches()){
                    coutMatcher++;
                     System.out.println(current+" ");
                }

            }
        }
        return coutMatcher;


    }
    public static int cSharpCodeNO(LinkedList<String> fileLines){
        int i = 0, len = fileLines.size();
        int coutMatcher=0;
        LinkedList<String> methods=FileAPI.readTxtFile("source\\c_sharp\\ConsoleMethods.txt");
        int countMethods=methods.size();
        LinkedList<String> properties=FileAPI.readTxtFile("source\\c_sharp\\ConsoleProperties.txt");
        int countProperties=properties.size();
        for (i = 0; i < len; i ++) {
            String current = FileAPI.ltrim(FileAPI.rtrim(fileLines.get(i)));
            if(current.equals("")){

            }
            else{
                for (int j = 0; j < countMethods; j ++) {
                    String cVar=FileAPI.ltrim(FileAPI.rtrim(methods.get(j)));
                    Pattern method=Pattern.compile("(Console\\.("+cVar+")(\\([\\W\\w\\s0-9\\.,;:]+\\)))");
                    Pattern methodImpl=Pattern.compile(method+"((\\s*;))");
                    if(methodImpl.matcher(current).find()){
                        coutMatcher++;
                        System.out.println(current+" ");
                    }

                }
                for (int j = 0; j < countProperties; j ++) {
                    String cVar=FileAPI.ltrim(FileAPI.rtrim(properties.get(j)));
                    Pattern prop=Pattern.compile("((Console\\.)("+cVar+"))");
                    Pattern propImpl=Pattern.compile(prop+"((\\s*;))");
                    if(propImpl.matcher(current).find()){
                        coutMatcher++;
                        System.out.println(current+" ");
                    }

                }



            }
        }
        return coutMatcher;


    }
    public static int javaCode(LinkedList<String> fileLines){
        int i = 0, len = fileLines.size();
        int coutMatcher=0;
        Pattern inc=Pattern.compile("(import)([\\W\\w\\s0-9\\.;:\\)\\(]+)");
        Pattern main=Pattern.compile("(public\\s+static\\s+(int|void)\\s+main\\s*(\\([\\W\\w\\s0-9\\.,;:]+\\)))");
        Pattern mainImpl=Pattern.compile(main+"((\\s*\\{)*)");
        for (i = 0; i < len; i ++) {
            String current = FileAPI.ltrim(FileAPI.rtrim(fileLines.get(i)));
            if(current.equals("")){

            }
            else{
                if(inc.matcher(current).matches()||mainImpl.matcher(current).matches()){
                    coutMatcher++;
                     System.out.println(current+" ");
                }



            }
        }
        return coutMatcher;


    }

    public static int recogniseCode(LinkedList<String> fileLines){
        int countJava=javaCode(fileLines);
        int coutCsharp=cSharpCode(fileLines);
        int countCplus=cPlusCode(fileLines);
        int counNOCshap=cSharpCodeNO(fileLines);

        System.out.println("j = "+countJava+" c# = "+coutCsharp+" "+counNOCshap+  " c++ ="+countCplus);
        if(countJava>0){
            return 1;
        }else if(coutCsharp>0){
            return 2;
        }else if(countCplus>0){
            return 3;
        }

    return 0;

    }

}
