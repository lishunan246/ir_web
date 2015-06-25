package room713;

import java.util.*;

/**
 * Created by lvdon_000 on 2015/6/24.
 */
public class BoolSearch {
    public ArrayList<Integer> find(ArrayList<ArrayList<String>> outerlist){
        ArrayList<Integer> result = new ArrayList<>();//总结果
        ArrayList<ArrayList<Integer>> or_result = new ArrayList<>();//或运算结果集合
        //得到或运算的结果集合
        for (int i = 0;i < outerlist.size(); i ++){// outer loop
            ArrayList<String> innerlist = outerlist.get(i);
            ArrayList<Integer> sub_or_result =new ArrayList<>();
            for(int j = 0;j<innerlist.size();j++) {//inner loop
                if (Tokenizer.tokenMap.containsKey(innerlist.get(j))) {
                    TreeMap<Integer, Indexer> temp = Tokenizer.tokenMap.get(innerlist.get(j));
                    Iterator iter = temp.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        Integer key = (Integer) entry.getKey();
                        if (!sub_or_result.contains(key)) {
                            sub_or_result.add(key);
                        }
                    }
                }
            }
            or_result.add(sub_or_result);
        }
        //与运算
        for(int i = 0; i < or_result.size(); i ++){
            if(i ==0){
                result = or_result.get(0);
            }
            else{
                ArrayList<Integer> secondlist = or_result.get(i);
                int jmax = result.size();
                int kmax = or_result.get(i).size();
                int j = 0;
                int k = 0;

                ArrayList<Integer>temp = new ArrayList<>();
                while((j<jmax)&(k<kmax)){
                    if(result.get(j)<secondlist.get(k)){
                        j++;
                    }else if(result.get(j)>secondlist.get(k)) {
                        k++;
                    }else{
                        temp.add(result.get(j));
                        j++;
                        k++;
                    }
                }
                result = temp;
            }
        }


        return result;
    }
    public  HashMap<Integer,Double> score(ArrayList<Integer> docID,ArrayList<String> Query){
        HashMap<Integer,Double> result = new HashMap<>();

        for(int i = 0;i < docID.size() ; i++){

                double wf,wf_idf = 0,idf;
                double fenmu = 0;  //fenmu- -....

                for(int j = 0;j <Query.size(); j ++){
                    if(Tokenizer.tokenMap.containsKey(Query.get(j))) {
                        TreeMap<Integer, Indexer> temp2 = Tokenizer.tokenMap.get(Query.get(j));
                        if (temp2.containsKey(docID.get(i))) {

                            float tf = temp2.get(docID.get(i)).getTf();
                            wf = 1 + Math.log(tf);
                            wf_idf = wf_idf + wf ;
                            fenmu = fenmu + Math.pow(wf_idf, 2);
                        }
                    }
                }
            result.put(docID.get(i),wf_idf);
        }



        return result;
    }
}
