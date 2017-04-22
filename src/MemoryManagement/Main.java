package MemoryManagement;

import MemoryManagement.LRU.LRU;
import MemoryManagement.Optimal.Optimal;

public class Main {
    public static void main(String[] args) {
        int testSequence[] = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};

        LRU lru = new LRU(3);
        Optimal optimal = new Optimal(testSequence, 3);

        String lruLastString = "";
        String optLastString = "";

        System.out.println("Sequence for Least Recently Used:");
        for (int pageNumber : testSequence) {
            lru.add(pageNumber);
            if (!lruLastString.equals(lru.toString())) {
                System.out.println(lru.toString());
            }
            lruLastString = lru.toString();
        }
        System.out.println();

        System.out.println("Sequence for Optimal:");
        for (int i = 1; i < testSequence.length; i++) {
            if (!optLastString.equals(optimal.toString(i))) {
                System.out.println(optimal.toString(i));
            }
            optLastString = optimal.toString(i);
        }
    }
}
