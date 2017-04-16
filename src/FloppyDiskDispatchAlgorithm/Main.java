package FloppyDiskDispatchAlgorithm;

import FloppyDiskDispatchAlgorithm.SSTF.SSTF;
import FloppyDiskDispatchAlgorithm.Scan.Scan;
import FloppyDiskDispatchAlgorithm.CScan.CScan;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int initialPosition = 53;
        int[] queue = {98, 183, 37, 122, 14, 124, 65, 67};

        System.out.println("SSTF:");
        SSTF sstf = new SSTF(queue, initialPosition);
        System.out.println(sstf.getTotalDistance());
        System.out.println(Arrays.toString(sstf.getPath()) + "\n");

        System.out.println("Scan:");
        Scan scan = new Scan(queue, initialPosition);
        System.out.println(scan.getTotalDistance());
        System.out.println(Arrays.toString(scan.getPath()) + "\n");

        System.out.println("C-Scan:");
        CScan cScan = new CScan(queue, initialPosition);
        System.out.println(cScan.getTotalDistance());
        System.out.println(Arrays.toString(cScan.getPath()) + "\n");
    }
}
