import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by Alex on 29.05.2016.
 */
public class FileAPI {
    public static LinkedList readDocxFile(String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            XWPFDocument document = new XWPFDocument(fis);

            List<XWPFParagraph> paragraphs = document.getParagraphs();
            LinkedList<String> text=new LinkedList<String>();
            for(int i=0;i<paragraphs.size();i++){
                text.add(paragraphs.get(i).getParagraphText());

                // System.out.println(paragraphs.get(i).getParagraphText());
                // System.out.println("");
            }
            fis.close();
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static LinkedList<String> readTxtFile(String path){
        try{
            File file = new File(path);
            FileReader fis = new FileReader(file.getAbsolutePath());
            BufferedReader  br=new BufferedReader (fis);
            LinkedList<String> text=new LinkedList<String>();
            String line;
            while((line=br.readLine())!=null){
                text.add(line);
            }
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    public  static Pattern LTRIM = Pattern.compile("^\\s+");
    public static ArrayList<Integer> spaces;

    public static String ltrim(String s) {
        return LTRIM.matcher(s).replaceAll("");
    }
    public  static Pattern RTRIM = Pattern.compile("\\s+$");

    public static String rtrim(String s) {
        return RTRIM.matcher(s).replaceAll("");

    }


    public static String prepareString(String s){
        String k=  rtrim(ltrim((((s.replace("[", "")).replace("]", "")).replace("\"", ""))));
        //  System.out.println(k);
        return k;
    }
}
