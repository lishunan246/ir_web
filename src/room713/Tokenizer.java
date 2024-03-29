package room713;

import java.io.*;
import java.util.*;

/**
 * Created by Henry on 2015/6/17.
 */
public class Tokenizer {
    private String passage;
    public static String punctuation = " \t\n\r\f,.:;?![]0123456789()+-*/<>\"@#$%^&~\\|";
    public static ArrayList<String> stopwords = new ArrayList<>();
    public static HashMap<String, TreeMap<Integer, Indexer>> tokenMap = new HashMap<>();
    public static HashMap<String, Integer> dictMap = new HashMap<>();

    Tokenizer(String stopwordsPath){
        try {
            passage = "";
            BufferedReader bfr1 = new BufferedReader(new FileReader(stopwordsPath));
            String line;
            while((line = bfr1.readLine()) != null){
                stopwords.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void indexing(Integer docID, String text){
        StringTokenizer st = new StringTokenizer(text, punctuation);
        String tempWord;
        Integer pos = 0;

        while(st.hasMoreTokens()){
            pos += 1;
            if(!isStopword(tempWord = st.nextToken())) {
                tempWord = stemTerm(tempWord);
                if(!tokenMap.containsKey(tempWord)){
                    TreeMap<Integer, Indexer> docIDMap = new TreeMap<>();
                    Indexer indexer = new Indexer(docID, pos);
                    docIDMap.put(docID, indexer);
                    tokenMap.put(tempWord, docIDMap);
                }else{
                    if(!tokenMap.get(tempWord).containsKey(docID)){
                        Indexer indexer = new Indexer(docID, pos);
                        tokenMap.get(tempWord).put(docID, indexer);
                    }else{
                        tokenMap.get(tempWord).get(docID).updateTf();
                        tokenMap.get(tempWord).get(docID).updatePosList(pos);
                    }
                }
            }
        }
    }

    public void dicting(String text){
        StringTokenizer st = new StringTokenizer(text, punctuation);
        String tempWord;
//        Integer pos = 0;

        while(st.hasMoreTokens()){
//            pos += 1;
            if(!isStopword(tempWord = st.nextToken())) {
//                tempWord = stemTerm(tempWord);
                tempWord = tempWord.toLowerCase();
                if(!dictMap.containsKey(tempWord)){
                    dictMap.put(tempWord, 1);
                }else {
                    dictMap.put(tempWord, dictMap.get(tempWord)+1);
                }
            }
        }
    }

    public static ArrayList<String> tokenize(String text, Boolean doStem){
        StringTokenizer st = new StringTokenizer(text, punctuation);
        String tempWord;
        ArrayList<String> query = new ArrayList<>();
        while (st.hasMoreTokens()){
            if(!isStopword((tempWord = st.nextToken()))){
                if(doStem) {
                    tempWord = stemTerm(tempWord);
                }
                query.add(tempWord);
            }
        }
        return query;
    }

    public static Boolean isStopword(String word){
        return stopwords.contains(word);
    }

    public static String stemTerm(String term){
        char[] w = new char[501];
        Stemmer s = new Stemmer();
        StringBuilder sb = new StringBuilder();

        int i = 0;
        while(i < term.length()){
            int ch;
            ch = term.charAt(i++);
            if (Character.isLetter((char) ch))
            {
                int j = 0;
                while(true)
                {  ch = Character.toLowerCase((char) ch);
                    w[j] = (char) ch;
                    if (j < 500) j++;
                    if(i < term.length()) {
                        ch = term.charAt(i++);
                    }else {
                        ch = -1;
                    }
                    if (!Character.isLetter((char) ch))
                    {
                       /* to test add(char ch) */
                        for (int c = 0; c < j; c++) s.add(w[c]);

                       /* or, to test add(char[] w, int j) */
                       /* s.add(w, j); */

                        s.stem();
                        {  String u;

                          /* and now, to test toString() : */
                            u = s.toString();

                          /* to test getResultBuffer(), getResultLength() : */
                          /* u = new String(s.getResultBuffer(), 0, s.getResultLength()); */

                            sb.append(u);
                        }
                        break;
                    }
                }
            }
            if (ch < 0) break;
            sb.append((char)ch);

        }
        return sb.toString();
    }



//    public static void main(String[] args) {
//        Tokenizer tknz = new Tokenizer("resource/stopwords.txt");
//        tknz.passage = "March/April-June/July,";
//
//        ArrayList<String> queryList;
//        queryList = tknz.tokenize(tknz.passage);
//        System.out.println(queryList);
//    }
}
