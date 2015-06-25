package room713;

import java.util.*;

/**
 * Created by think on 2015/6/24.
 */
public class PhraseSearch {

    ArrayList<String> Query;
    ArrayList<Integer> result;
    PhraseSearch(ArrayList<String> q){

        Query = q;
        //System.out.println(Query);
        result = new ArrayList<>();
    }


    public ArrayList<Integer> getResult(){
        Set<Integer> docSet = new HashSet<>();
        boolean first = true;
        for(String item :Query){
            System.out.println(item);
            TreeMap<Integer,Indexer> temp = Tokenizer.tokenMap.get(item);
//            System.out.println(temp);
            Set<Integer> s = null;
            if(temp==null)
                return result;
            s = temp.keySet();     //the set of docID which contains item
            //System.out.println(s);
            if(first){
                docSet.addAll(s);
                first = false;
            }
            else{
                docSet.retainAll(s);
            }
            if(docSet.size()==0)
                return result;
        }
        for(int docId:docSet){
//            for(String item:Query){
//                Map<Integer,Indexer> temp = Tokenizer.tokenMap.get(item);
//                Indexer idx = temp.get(docId);
//                List<Integer> position = idx.getPosList();
//                for(int pos:position){
//
//                }
                //idx = temp.get(docId);
                //List postion = temp.get();
            Map<Integer,Indexer> start = Tokenizer.tokenMap.get(Query.get(0));
            Indexer idx_start = start.get(docId);
            List<Integer> startPosList = idx_start.getPosList();

            for(int startpos:startPosList){
                int i;
                for(i=1;i<Query.size();i++){
                    Map<Integer,Indexer> tmp = Tokenizer.tokenMap.get(Query.get(i));
                    Indexer idx = tmp.get(docId);
                    List<Integer> posList = idx.getPosList();
                    if(!posList.contains(startpos+i))
                        break;
                }
                if(i==Query.size()){
                    result.add(docId);
                    break;
                }
            }
        }
        return result;
    }
}
