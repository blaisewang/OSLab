package FloppyDiskDispatchAlgorithm.Scan;

import java.util.Arrays;

/**
 * FloppyDiskDispatchAlgorithm
 * Created by blaisewang on 2016/12/7.
 */
public class Scan {
    private int[] path;
    private int totalDistance;

    public Scan(int[] queue, int initialPosition) {
        int length = queue.length;
        path = new int[length];
        Arrays.parallelSort(queue);

        if (initialPosition <= queue[0]) {
            path = queue;
            totalDistance = queue[length - 1] - initialPosition;
        } else if (initialPosition >= queue[length - 1]) {
            totalDistance = initialPosition - queue[0];
            for (int i = 0; i < length; i++) {
                path[i] = queue[length - i - 1];
            }
        } else {
            int index = 0;
            int lastPosition = initialPosition;
            int leftDistance = initialPosition - queue[0];
            for (int i = 0; i < length; i++) {
                if (queue[i] >= initialPosition) {
                    index = i;
                    break;
                }
            }

            if (leftDistance < (queue[length - 1] - queue[0] - leftDistance)) {
                for (int i = 0; i < length; i++) {
                    if (i < index) {
                        path[i] = queue[index - i - 1];
                    } else {
                        path[i] = queue[i];
                    }
                    totalDistance += Math.abs(path[i] - lastPosition);
                    lastPosition = path[i];
                }
            } else {
                for (int i = 0; i < length; i++) {
                    if (i < length - index) {
                        path[i] = queue[index + i];
                    } else {
                        path[i] = queue[length - i - 1];
                    }
                    totalDistance += Math.abs(path[i] - lastPosition);
                    lastPosition = path[i];
                }
            }
        }
    }


    public int[] getPath() {
        return path;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

}
