package room713;

import java.util.*;

/**
 * Created by think on 2015/6/21.
 */
public class Topk {
    private int k = 20;//return top k docID
    //private HashMap<Integer,Double> Result;
    //private Vector<Map.Entry<Integer,Double>> MaxHeap;
    private PriorityQueue<Map.Entry<Integer,Double>> queue;
    int initialCapacity = 10;
    ArrayList<Map.Entry<Integer,Double>> result;

    Comparator<Map.Entry<Integer,Double>> cmp = new Comparator<Map.Entry<Integer,Double>>(){
        public int compare(Map.Entry<Integer,Double> entry1,Map.Entry<Integer,Double> entry2){
            if(entry1.getValue()-entry2.getValue()>0)
                return -1;
            else
                return 1;
        }
    };

    Topk(HashMap<Integer,Double> map){
        queue = new PriorityQueue<Map.Entry<Integer,Double>>(initialCapacity,cmp);
        for (Map.Entry<Integer, Double> entry : map.entrySet()){
            queue.add(entry);
        }
//        System.out.println(queue);
    }

//    PriorityQueue<Map.Entry<Integer,Double>> getResult(){
//        return queue;
//    }
    ArrayList<Map.Entry<Integer,Double>> getResult(){
        result = new ArrayList<Map.Entry<Integer,Double>>();
        for(int i=0;i<(k<queue.size()?k:queue.size());i++)
            result.add(queue.poll());
        for(Map.Entry<Integer,Double> entry:queue){
            result.add(entry);
        }
        return result;
    }
}
