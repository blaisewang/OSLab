package MemoryManagement.Optimal;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * MemoryManagement
 * Created by blaisewang on 2016/12/25.
 */

public class Optimal {

    private int[] pageNumber;
    private int capacity;
    private ArrayList<Integer> array = new ArrayList<>();

    public Optimal(int[] pageNumber, int capacity) {
        this.pageNumber = pageNumber;
        this.capacity = capacity;
    }

    private int findIndex(int[] array, int value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }
        return -1;
    }

    private int findMaxIndex(int[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == -1) {
                return i;
            }
            if (array[max] < array[i]) {
                max = i;
            }
        }
        return max;
    }

    private String get(int index) {
        array.clear();
        int[] tempIndex = new int[capacity];

        for (int i = 0; i < index; i++) {

            if (array.size() < capacity) {
                array.add(i, pageNumber[i]);
            } else if (array.size() == capacity) {
                int[] arrayCopy = new int[array.size()];
                for (int j = 0; j < array.size(); j++) {
                    arrayCopy[j] = array.get(j);
                }

                if (findIndex(arrayCopy, pageNumber[i]) == -1) {
                    int[] tempArray = Arrays.copyOfRange(pageNumber, i + 1, pageNumber.length);

                    for (int j = 0; j < capacity; j++) {
                        tempIndex[j] = findIndex(tempArray, array.get(j));
                    }

                    int max = findMaxIndex(tempIndex);
                    array.remove(max);
                    array.add(max, pageNumber[i]);
                }
            }
        }

        return array.toString();
    }

    public String toString(int index) {
        return get(index);
    }
}
