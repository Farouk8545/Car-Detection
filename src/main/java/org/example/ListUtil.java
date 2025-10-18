package org.example;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {
    public static List<String> order(List<String> list) {
        List<String> ordered = new ArrayList<>();
        while (!list.isEmpty()) {
            int left = 0, right = list.size() - 1;
            while (left < right){
                if (Integer.parseInt(list.get(right)) > Integer.parseInt(list.get(left))){
                    right--;
                }else{
                    left++;
                }
            }
            ordered.add(list.get(left));
            list.remove(left);
        }
        return ordered;
    }
}
