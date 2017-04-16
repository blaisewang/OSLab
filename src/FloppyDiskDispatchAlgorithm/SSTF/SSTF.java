package FloppyDiskDispatchAlgorithm.SSTF;

/**
 * FloppyDiskDispatchAlgorithm
 * Created by blaisewang on 2016/12/7.
 */
public class SSTF {
    private int[] path;
    private int totalDistance = 0;

    public SSTF(int[] queue, int initialPosition) {
        int currentPosition = initialPosition;
        int length = queue.length;

        int currentIndex;
        for (int circle = 0; circle < length; circle++) {
            int minDistance = Integer.MAX_VALUE;
            currentIndex = circle;
            for (int index = circle; index < length; index++) {
                int distance = Math.abs(currentPosition - queue[index]);
                if (distance < minDistance) {
                    currentIndex = index;
                    minDistance = distance;
                }
            }
            int temp = queue[circle];
            queue[circle] = queue[currentIndex];
            queue[currentIndex] = temp;

            totalDistance += minDistance;
            currentPosition = queue[circle];
        }

        path = queue;
    }

    public int[] getPath() {
        return path;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

}
