package room713;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Henry on 2015/6/19.
 *
 */
public class IR {
    private static String path=System.getProperty("user.home")+"/ir_resource";
    private static Integer passageNum = 0;
    private static Boolean flag = true;

    public static void initialize() {
        if (flag) {
            readIndex();
            flag = false;
        }
        System.out.println("IR init!");
    }

    public static void readFile(Integer from, Integer to){
        String filename;
        BufferedReader bfr;
        String line;
        String passage;
        Tokenizer tknz = new Tokenizer(path +"/stopwords.txt");
        for(Integer i=from; i<=to; i++){
            passage = "";
            filename = path+ "/Reuters/" + i.toString() + ".html";
            try {
                setPassageNum(getPassageNum()+1);
                bfr = new BufferedReader(new FileReader(new File(filename)));
                while((line = bfr.readLine()) != null){
                    passage += line;
                }
                tknz.indexing(i, passage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                setPassageNum(getPassageNum()-1);
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Integer getPassageNum() {
        return IR.passageNum;
    }

    public static void setPassageNum(Integer passageNum) {
        IR.passageNum = passageNum;
    }

    public static void buildIndex(){
        System.out.println("build start!");
        readFile(1, 1000);

        try {
            FileOutputStream fos = new FileOutputStream(path+"/tokenMap1000.ser");
            FileOutputStream fos2 = new FileOutputStream(path +"/passageNum1000.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            oos.writeObject(Tokenizer.tokenMap);
            oos2.writeObject(getPassageNum());
            oos.close();
            oos2.close();
            fos.close();
            fos2.close();
            System.out.println("build done!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readIndex(){
        try {
            FileInputStream fis = new FileInputStream(path+"/tokenMap1000.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);


//            tknz2.tokenMap = Indexer.parseIndex(ois.readObject());
            System.out.println("read start!");
//            tknz2.tokenMap = (HashMap<String, HashMap<Integer, Indexer>>) ois.readObject();
            Tokenizer.tokenMap = (HashMap<String, HashMap<Integer, Indexer>>) ois.readObject();
            ois.close();
            fis.close();
            FileInputStream fis2 = new FileInputStream(path+"/passageNum1000.ser");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            setPassageNum((Integer) ois2.readObject());
            ois2.close();
            fis2.close();
            System.out.println("read done!");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public static  ArrayList<Map.Entry<Integer,Double>> searchEntrance(String query) {
//        Tokenizer tknz2 = new Tokenizer(path+"/stopwords.txt");
//        IR ir2 = new IR();
//        String input;

        VSM vsm = new VSM(getPassageNum());
        HashMap<Integer,Double> scoreresult;

//        System.out.println("input a string");
//        Scanner in = new Scanner(System.in);
//        input = in.nextLine();
        Tokenizer tknz2 = new Tokenizer(path+"/stopwords.txt");
        scoreresult = vsm.score(tknz2.tokenize(query));
        Topk topk = new Topk(scoreresult);
        //PriorityQueue<Map.Entry<Integer,Double>> topKeyResult = topk.getResult();
        return topk.getResult();

//        in.close();
        //return scoreresult.keySet();
    }
}
