package room713;

import java.io.*;
import java.util.*;


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
            Tokenizer tokenizer = new Tokenizer(path + "/stopwords.txt");
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
                tknz.dicting(passage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                setPassageNum(getPassageNum()-1);
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
            FileOutputStream fos3 = new FileOutputStream(path +"/dictMap1000.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            ObjectOutputStream oos3 = new ObjectOutputStream(fos3);
            oos.writeObject(Tokenizer.tokenMap);
            oos2.writeObject(getPassageNum());
            oos3.writeObject(Tokenizer.dictMap);
            oos.close();
            oos2.close();
            oos3.close();
            fos.close();
            fos2.close();
            fos3.close();
            System.out.println("build done!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readIndex(){
        try {
            System.out.println("read start!");
            FileInputStream fis = new FileInputStream(path+"/tokenMap1000.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Tokenizer.tokenMap = (HashMap<String, TreeMap<Integer, Indexer>>) ois.readObject();
            ois.close();
            fis.close();
            FileInputStream fis2 = new FileInputStream(path+"/passageNum1000.ser");
            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            setPassageNum((Integer) ois2.readObject());
            ois2.close();
            fis2.close();
            FileInputStream fis3 = new FileInputStream(path+"/dictMap1000.ser");
            ObjectInputStream ois3 = new ObjectInputStream(fis3);
            Tokenizer.dictMap = (HashMap<String, Integer>) ois3.readObject();
            ois3.close();
            fis3.close();
            System.out.println("read done!");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Map.Entry<Integer,Double>> searchEntrance(String query, String type) {
//        Tokenizer tknz2 = new Tokenizer(path+"/stopwords.txt");
//        IR ir2 = new IR();
//        String input;

        Topk topk = null;

        switch (type) {
            case "TextSearch":
                VSM vsm = new VSM(getPassageNum());
                HashMap<Integer, Double> scoreresult;

//        System.out.println("input a string");
//        Scanner in = new Scanner(System.in);
//        input = in.nextLine();
//            Tokenizer tknz2 = new Tokenizer(path+"/stopwords.txt");
                scoreresult = vsm.score(Tokenizer.tokenize(query, true));
                topk = new Topk(scoreresult);

                break;
            case "BoolSearch":
                ArrayList<ArrayList<String>> boolQueryList = new ArrayList<>();
                ArrayList<String> queryList = tokenizeWithStopwordNoStem(query);
                ArrayList<String> insideList = new ArrayList<>();
                for (String queryTerm : queryList) {
                    if (!queryTerm.equalsIgnoreCase("and")) {
                        insideList.add(Tokenizer.stemTerm(queryTerm));
                    } else {
                        boolQueryList.add(insideList);
                        insideList = new ArrayList<>();
                    }
                }
                boolQueryList.add(insideList);
                BoolSearch bs = new BoolSearch();
                HashMap<Integer, Double> boolResult = bs.score(bs.find(boolQueryList), Tokenizer.tokenize(query, true));
                topk = new Topk(boolResult);

                break;
            case "PhraseSearch":
                PhraseSearch ps = new PhraseSearch(Tokenizer.tokenize(query, true));
                ArrayList<Integer> psResult = ps.getResult();
                bs = new BoolSearch();
                HashMap<Integer, Double> psboolResult = bs.score(psResult,Tokenizer.tokenize(query, true));
                topk = new Topk(psboolResult);
                break;
            default:
                return null;
        }

        //PriorityQueue<Map.Entry<Integer,Double>> topKeyResult = topk.getResult();
        return topk.getResult();

//        in.close();
        //return scoreresult.keySet();
    }

    public static ArrayList<String> spellCorrect(String query){
//        Tokenizer tknz2 = new Tokenizer(path+"/stopwords.txt");
        SpellCorrector sc = new SpellCorrector();
        return sc.correct(Tokenizer.tokenize(query, false));
    }

    public static ArrayList<String> tokenizeWithStopwordNoStem(String text){
        StringTokenizer st = new StringTokenizer(text, Tokenizer.punctuation);
        String tempWord;
        ArrayList<String> query = new ArrayList<>();
        while (st.hasMoreTokens()){
            tempWord = st.nextToken();
            query.add(tempWord);
        }
        return query;
    }

}
