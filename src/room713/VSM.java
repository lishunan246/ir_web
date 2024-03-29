package room713;

import java.util.*;


/**
 * Created by cAOGUANGBIAO on 2015/6/20.
 */
public class VSM {
    /**
     * member
     */

//    public HashMap<String, HashMap<Integer, Indexer>> Tokenizer.tokenMap;
    double N;

    /**
     * function
     */

    VSM(Integer n){
        N = n;
    }


    public  HashMap<Integer,Double> score(ArrayList Query){

        HashMap<Integer,Double> result = new HashMap<>(); //result

        HashMap<Integer, Indexer>  doclist = new HashMap<>();
        HashMap<String, Integer> dflist = new HashMap<>();
        TreeMap<Integer, Indexer> temp;
        TreeMap<Integer, Indexer>  temp2;

//        double score;

        //build doclist,calculate df
        for (Object aQuery : Query) {
            if (Tokenizer.tokenMap.containsKey(aQuery)) {
                temp = Tokenizer.tokenMap.get(aQuery);

                int df = temp.size();
                dflist.put((String) aQuery, df);

                Iterator iter = temp.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    Integer key = (Integer) entry.getKey();
                    Indexer val = (Indexer) entry.getValue();
                    doclist.put(key, val);
                }

            }

        }



        Iterator iterForCos = doclist.entrySet().iterator();
        while (iterForCos.hasNext()) {
            double wf,wf_idf = 0,idf;
            double fenmu = 0;  //fenmu- -....


            Map.Entry entry = (Map.Entry) iterForCos.next();
            Integer key = (Integer)entry.getKey();

            for(int j = 0;j <Query.size(); j ++){
                if(Tokenizer.tokenMap.containsKey(Query.get(j))) {
                    temp2 = Tokenizer.tokenMap.get(Query.get(j));
                    if (temp2.containsKey(key)) {
                        double dfForCos = dflist.get(Query.get(j));
                        idf = Math.log(N / dfForCos);
                        float tf = temp2.get(key).getTf();
                        wf = 1 + Math.log(tf);
                        wf_idf = wf_idf + wf * idf;
                        fenmu = fenmu + Math.pow(wf_idf, 2);
                    }
                }
            }

            result.put(key, wf_idf);



        }






        return result;
    }
}

