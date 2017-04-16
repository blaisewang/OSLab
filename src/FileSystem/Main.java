package FileSystem;

import FileSystem.GroupLink.GroupLink;
import FileSystem.HybridIndex.HybridIndex;

public class Main {

    public static void main(String[] args) {
        int requestAddress = 9504;

        HybridIndex hybridIndexFile = new HybridIndex(10240);
        System.out.print("The disk block number of address " + requestAddress + " is: \n");
        System.out.println(hybridIndexFile.getDiskBlockNumber(requestAddress) + "\n");
        System.out.println(hybridIndexFile.toString());

        GroupLink groupLink = new GroupLink(7);
        groupLink.recycle(3);
        System.out.println(groupLink.toString());
    }
}
