package FileSystem.GroupLink;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * FileSystem
 * Created by blaisewang on 2016/12/10.
 */
public class GroupLink {
    private static final int GROUP_MEMBER_SIZE = 3;

    private ArrayList<LinkedList<Integer>> diskBlock = new ArrayList<>();
    private int blockNumber = 0;

    public GroupLink(int initialFreeBlock) {
        recycle(initialFreeBlock);
    }

    public void request(int blockSize) {
        for (int i = 0; i < blockSize; i++) {
            if (diskBlock.size() != 0) {
                if (diskBlock.get(0).size() != 1) {
                    diskBlock.get(0).remove(0);
                } else {
                    diskBlock.remove(0);
                }
            } else {
                System.out.println("No enough block to allocate.");
                break;
            }
            blockNumber--;
        }
    }

    public void recycle(int blockSize) {
        for (int i = 0; i < blockSize; i++) {
            if (diskBlock.size() != 0) {
                if (diskBlock.get(0).size() < GROUP_MEMBER_SIZE) {
                    diskBlock.get(0).add(0, blockNumber);
                } else {
                    LinkedList<Integer> linkedList = new LinkedList<>();
                    linkedList.add(blockNumber);
                    diskBlock.add(0, linkedList);
                }
            } else {
                LinkedList<Integer> linkedList = new LinkedList<>();
                linkedList.add(blockNumber);
                diskBlock.add(linkedList);
            }
            blockNumber++;
        }
    }

    public String toString() {
        return diskBlock.toString();
    }
}
