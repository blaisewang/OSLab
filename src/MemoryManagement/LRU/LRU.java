package MemoryManagement.LRU;

import CommonClass.Pair;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class LRU {
    private ArrayList<Pair<Integer, Integer>> array = new ArrayList<>();
    private int capacity;

    public LRU(int capacity) {
        this.capacity = capacity;
    }

    public void add(int value) {
        boolean isExist = false;
        boolean isProper = false;

        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).second.equals(value)) {
                if (array.get(i).first == array.size() - 1) {
                    isProper = true;
                } else {
                    array.get(i).first = array.size();
                }
                isExist = true;
            }
        }

        if (isExist && !isProper) {
            array.stream().filter(pair -> pair.first > 0).forEach(pair -> pair.first -= 1);
        }

        if (array.size() == capacity && !isExist) {
            for (int i = 0; i < array.size(); i++) {
                if (array.get(i).first == 0) {
                    array.remove(i);
                    array.add(i, new Pair<>(array.size(), value));
                    isExist = true;
                } else {
                    array.get(i).first -= 1;
                }
            }
        }

        if (!isExist) {
            array.add(new Pair<>(array.size(), value));
        }

    }

    public String toString() {
        return array.stream().map(pair -> pair.second).collect(Collectors.toCollection(ArrayList::new)).toString();
    }
}
