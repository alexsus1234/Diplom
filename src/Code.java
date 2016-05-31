import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.regex.Pattern;

/**
 * Created by Alex on 29.05.2016.
 */
public class Code {




  /*  public static ArrayList<Integer> sort(ArrayList<Integer> a){
        int[] arr=new int[a.size()];
        //Collections.sor

    }*/


    public static LinkedList readLex(String path){
        try(BufferedReader file = new BufferedReader(new InputStreamReader(new FileInputStream(path))))  {

            LinkedList<String> arr=new LinkedList();
            String line;
            while ((line=file.readLine())!=null){
                arr.add(line.trim());
            }
            return arr;

        }catch (Exception e){
            e.printStackTrace();
        }



        return null;
    }
    public static ArrayList<Integer>  removeItem(int found,ArrayList<Integer> removeFrom)  {
        int i=0;
        do{
            if(removeFrom.get(i)<found){
                removeFrom.remove(i);
            }else i++;

        }while(i<removeFrom.size());
        return removeFrom;

    }

    private static ArrayList<Integer> getCommentFromList(LinkedList<String> fileLines) {
        int i = 0, len = fileLines.size();
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> spaceNumber= new ArrayList<>();
        ArrayList<Integer> start= new ArrayList<>();
        ArrayList<Integer> end= new ArrayList<>();
        Pattern simpleComment=Pattern.compile("((([\\w\\W\\s]*)((//)|#)([\\w\\W\\s]*))|((([\\w\\W\\s]*)/\\*)([\\w\\W\\s]*)\\*/))");



        for (i = 0; i < len; i ++) {
            String current = FileAPI.ltrim(FileAPI.rtrim(fileLines.get(i)));
            if(current.equals("")){
                spaceNumber.add(i);
            }
            else{
                if(simpleComment.matcher(current).matches()){
                    result.add(i);
                    // System.out.println(current+" ");
                }

            }
        }
        for (i = 0; i < len; i ++) {
            String current = FileAPI.ltrim(FileAPI.rtrim(fileLines.get(i)));
            if((result.indexOf(i)==-1)&&(spaceNumber.indexOf(i)==-1)){
                if(current.indexOf("/*")>=0){
                    start.add(i);
                }  if(current.indexOf("*/")>=0){
                    end.add(i);
                }
            }

        }
        //   System.out.println(start.size()+" "+end.size());
        if(start.size()!=0&&end.size()!=0){

            if(start.size()==end.size()){
                for (int j=0;j<start.size();j++){
                    int first=start.get(j);
                    int last=end.get(j);
                    if(first<last){
                        for (int jj=first;jj<=last;jj++){
                            if(result.indexOf(jj)==-1&&spaceNumber.indexOf(jj)==-1){
                                result.add(jj);
                            }
                        }
                    }

                }

            }
        }
      /*  result.sort(new Comparator<Integer>(){
            public int compare(Integer o1, Integer o2) {
                return (o2+o1);
            }
        });*/
        Collections.sort(result);

     /*   for (int j=0;j<result.size();j++){
            String current = ltrim(rtrim(fileLines.get(result.get(j))));
            System.out.println(current);
        }
        System.out.println(spaceNumber.size());*/
        FileAPI.spaces=spaceNumber;


        return result;

    }
    public static LinkedList<String> getCodeFromList(LinkedList<String> fileLines) {

        int i = 0, len = fileLines.size();
        ArrayList<Integer> resultSet = new ArrayList<Integer>();
        ArrayList<Integer> comments=getCommentFromList(fileLines);
        //  resultSet=comments;

        Pattern iden = Pattern.compile("(([a-zA-Z])([A-Za-z0-9_:])*)");
        Pattern ar= Pattern.compile("(((\\[)|(\\]))*)");

        Pattern params=Pattern.compile("((['\":;=><\\[a-zA-Z0-9\\.,_\\s\\(\\)\\+\\-=\\\\/\\*])*)");

        Pattern param=Pattern.compile("\\((['\\[a-zA-Z0-9\\.\\,\\:_\\s\\(\\)\\+\\-=\\\\/\\*])*\\)");

        //  Pattern param=Pattern.compile("(\\((("+params+ar+params+ar+params+"))\\))");

        Pattern idenM=Pattern.compile("("+iden+"\\s*"+param+")");
        Pattern mIden=Pattern.compile("(("+iden+("\\."+iden)+"|"+iden+"\\."+idenM+"|"+idenM+"\\."+iden+"|"+idenM+"\\."+idenM+")+)");

        Pattern uIden=Pattern.compile("("+iden+"|"+mIden+"|"+idenM+")");

        Pattern opr=Pattern.compile("\\+|\\-|=|\\\\");
        Pattern oprSign=Pattern.compile("(=|\\+=|\\-=|\\*=|/=)");
        Pattern inkr=Pattern.compile("(\\+\\+)|(\\-\\-)");
        Pattern unarOpr=Pattern.compile("("+inkr+"\\s*"+uIden+")|"+"("+uIden+"\\s*"+inkr+")|"+"("+inkr+"\\s*"+uIden+"\\s+"+inkr+")");

        Pattern expItem=Pattern.compile("((\\s*((([a-zA-Z0-9\\.\\(\\)_])*)"+"|"+uIden+"|"+inkr+")\\s*))");
        Pattern exp1=Pattern.compile("(\\(*"+expItem+"\\)*)");
        Pattern exp=Pattern.compile("(((\\(*"+exp1+"\\s*"+oprSign+"\\s*"+exp1+"\\)*)("+oprSign+"*))+)"); //

        Pattern accessMod=Pattern.compile("(public|protected|private)");
        Pattern modSAF=Pattern.compile("((final|static|abstract)|(final\\s+static)|(static\\s+final))");
        Pattern modOther=Pattern.compile("(strictfp|transient|volatile|synchronized|native)");

        Pattern init=Pattern.compile("((("+accessMod+"\\s+)*)(("+modSAF+"\\s+)*)(("+modOther+"\\s+)*)("+iden+"|"+mIden+"))");//(("+iden+"\s+){0,1})

        Pattern initialisationFirst=Pattern.compile("((("+init+"\\s+)*("+iden+"|"+mIden+"|"+idenM+")))");
        Pattern initialisation=Pattern.compile("("+initialisationFirst+"\\s*"+oprSign+"\\s*("+iden+"|"+idenM+"|"+mIden+"|"+inkr+"|"+exp+"|[0-9]))");
        //  +"+\\s*"+oprSign+"\\s*)(("+idenM+"|"+mIden+"|"+iden+")("+inkr+"*)))");

        Pattern initLine=Pattern.compile(initialisationFirst+"\\s*(;|\\{)");//1
        Pattern initLineEqual=Pattern.compile(initialisation+"\\s*(;|\\{)");//2
        Pattern rInitLine=Pattern.compile("\\}(\\s*)"+initialisationFirst+"\\s*(;|\\{)");//1
        Pattern rInitLineEqual=Pattern.compile("\\}(\\s*)"+initialisation+"\\s*(;|\\{)");//2
        // Pattern initLineExpr=Pattern.compile()
        //Pattern bracket=Pattern.compile("((([[a-zA-Z0-9_\]\\s]*)(\\{)([\\w\\W\\s]*)))");
        Pattern bracket=Pattern.compile("("+iden+"*)"+"\\{");
        Pattern iwf=Pattern.compile("(if|while|for|switch)(\\s*\\(([\\W\\w\\s0-9]+)\\))");
        Pattern iwf1=Pattern.compile(iwf+"([\\W\\w\\s0-9]*)");
        Pattern iwf2=Pattern.compile("([\\W\\w\\s0-9]*)"+iwf);
        Pattern els=Pattern.compile("(else)|(else+s\\*("+initLine+"|"+initLineEqual+"|"+rInitLine+"|"+rInitLineEqual+"))");
        Pattern swc=Pattern.compile("((case)\\s*:)");
        Pattern swcImpl=Pattern.compile(swc+"(\\s*\\(([\\W\\w\\s0-9]+)\\))"+"(break)\\s*;");




        for (i = 0; i < len; i ++) {
            // String current = ltrim (rtrim(fileLines.get(i)));
            String current = FileAPI.prepareString(fileLines.get(i));
            if(FileAPI.spaces.indexOf(i)==-1&&comments.indexOf(i)==-1) {
                if(initLine.matcher(current).matches())
                    resultSet.add(i);else
                if(initLineEqual.matcher(current).matches())
                    resultSet.add(i);else
                if(rInitLine.matcher(current).matches())
                    resultSet.add(i);else
                if(rInitLineEqual.matcher(current).matches())
                    resultSet.add(i);


            }
            //System.out.println(current);
        }

        int buff;
        for (i = 0; i < len; i ++) {
            String current = FileAPI.prepareString(fileLines.get(i));//(ltrim (rtrim(fileLines.get(i))));
            if(FileAPI.spaces.indexOf(i)==-1&&comments.indexOf(i)==-1&&resultSet.indexOf(i)==-1) {
                if((initialisationFirst.matcher(current).matches())||(initialisation.matcher(current).matches())||(iwf.matcher(current).matches())) {

                    buff=i;
                //    System.out.println(i+" "+current);
                    do{
                        //   System.out.println(i+" yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
                        i++;
                        //  current = ltrim (rtrim(fileLines.get(i)));
                        current = FileAPI.prepareString(fileLines.get(i));
                    }while (FileAPI.spaces.indexOf(i)!=-1&&(i<len));
                    if(bracket.matcher(current).matches()){
                        resultSet.add(buff);
                        resultSet.add(i);
                    }
                }
                if((iwf.matcher(current).matches())||(iwf1.matcher(current).matches())||(iwf2.matcher(current).matches())||(els.matcher(current).matches())||(swcImpl.matcher(current).matches())){
                    resultSet.add(i);
                }

            }

        }
        Collections.sort(resultSet);
        FileAPI.spaces=removeItem(resultSet.get(0),FileAPI.spaces);
        comments=removeItem(resultSet.get(0),comments);


        for (int c=0;c<comments.size();c++){
            buff=comments.get(c);
            int flag=0;
            for (int j=0;j<resultSet.size();j++){
                if(resultSet.indexOf(buff)!=-1){
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                resultSet.add(buff);
            }

        }
        for (int c=0;c<FileAPI.spaces.size();c++){
            buff=FileAPI.spaces.get(c);
            int flag=0;
            for (int j=0;j<resultSet.size();j++){
                if(resultSet.indexOf(buff)!=-1){
                    flag=1;
                    break;
                }
            }
            if(flag==0){
                resultSet.add(buff);
            }
        }
        Collections.sort(resultSet);



   /*     for (int j=0;j<resultSet.size();j++){
            String current = FileAPI.ltrim(FileAPI.rtrim(fileLines.get(resultSet.get(j))));
            System.out.println(current+" "+resultSet.get(j));
        }
*/
      /*  for (int j=0;j<spaces.size();j++) {
            String current = ltrim(rtrim(fileLines.get(spaces.get(j))));
            System.out.println(current);
        }
*/



        LinkedList<String> res= new LinkedList<String>();
        for (int j=0;j<resultSet.size();j++){
            res.add(FileAPI.ltrim(FileAPI.rtrim(fileLines.get(resultSet.get(j)))));
           // System.out.println(current+" "+resultSet.get(j));
        }

        return res;


    }

}
