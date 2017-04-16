package FloppyDiskDispatchAlgorithm.CScan;

import java.util.Arrays;

/**
 * FloppyDiskDispatchAlgorithm
 * Created by blaisewang on 2016/12/7.
 */
public class CScan {
    private int[] path;
    private int totalDistance;

    public CScan(int[] queue, int initialPosition) {
        int length = queue.length;
        path = new int[length];

        Arrays.parallelSort(queue);
        totalDistance = 2 * queue[0];

        int index = 0;
        for (int i = 0; i < length; i++) {
            if (queue[i] >= initialPosition) {
                index = i;
                break;
            }
        }

        int lastPosition = initialPosition;
        for (int i = 0; i < length; i++) {
            if (i < length - index) {
                path[i] = queue[index + i];
            } else {
                path[i] = queue[i - length + index];
            }
            totalDistance += Math.abs(path[i] - lastPosition);
            lastPosition = path[i];
        }

    }

    public int[] getPath() {
        return path;
    }

    public int getTotalDistance() {
        return totalDistance;
    }
}
