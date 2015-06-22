package room713;

import java.util.ArrayList;

/**
 * Created by Henry on 2015/6/22.
 */
public class SpellCorrector {

    public Integer editDistance(String s1, String s2){
        int l1 = s1.length();
        int l2 = s2.length();
        int m[][] = new int[l1+1][l2+1];
        m[0][0] = 0;
        m[l1][l2] = 0;
        int i,j,temp;
        for(i=1; i<=l1; i++){
            m[i][0] = i;
        }
        for(j=1; j<=l2; j++){
            m[0][j] = j;
        }
        for(i=1; i<=l1; i++){
            for(j=1; j<=l2; j++){
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    temp = 0;
                }else{
                    temp = 1;
                }
                m[i][j] = Math.min(Math.min(m[i-1][j-1] + temp, m[i-1][j] + 1), m[i][j-1] + 1);
            }
        }
        return m[l1][l2];
    }

    public ArrayList<String> correct(ArrayList<String> queryList){
        ArrayList<String> corrected = new ArrayList<>();
        String minTerm;
        Integer maxCount;
        Integer count;
        Integer minED;
        Integer eD;
        for(String query: queryList){
            minTerm = query;
            maxCount = 0;
            minED = 100;
            for(String term: Tokenizer.dictMap.keySet()){
                if((eD = editDistance(query, term)) == 0){
                    minTerm = query;
                    break;
                }else{
                    if(eD < minED){
                        minED = eD;
                        minTerm = term;
                        maxCount = Tokenizer.dictMap.get(term);
                    }else if(eD.equals(minED)){
                        if(maxCount < (count = Tokenizer.dictMap.get(term))){
                            minTerm = term;
                            maxCount = count;
                        }
                    }
                }
            }
            corrected.add(minTerm);
        }
        return corrected;
    }

//    public static void main(String[] args) {
//        SpellCorrector sc = new SpellCorrector();
//        System.out.println(sc.editDistance("art", "chats"));
//    }
}
