package FileSystem.HybridIndex;

import CommonClass.Pair;

import java.util.LinkedList;

/**
 * FileSystem
 * Created by blaisewang on 2016/12/10.
 */
public class HybridIndex {
    private static final int DISK_BLOCK_SIZE = 16;
    private static final int DISK_BLOCK_NUMBER_SIZE = 2;
    private static final int DIRECT_POINTERS_NUMBER = 10;

    private LinkedList<Pair<Integer, Integer>> fileDataBlocks = new LinkedList<>();
    private LinkedList<Pair<Integer, Integer>> singlyIndirectPointers;
    private LinkedList<Pair<String, LinkedList<Pair<Integer, Integer>>>> doublyIndirectPointers;
    private LinkedList<Pair<String, LinkedList<Pair<String, LinkedList<Pair<Integer, Integer>>>>>> triplyIndirectPointers;

    public HybridIndex(int fileSize) {
        if (fileSize > 0) {
            this.INode(fileSize);
        } else {
            System.out.println("Illegal file length.");
        }
    }

    private void INode(int fileSize) {
        int diskBlockNumber = 0;
        int storeSize;
        for (; fileSize > 0 && fileDataBlocks.size() < DIRECT_POINTERS_NUMBER; diskBlockNumber++) {
            if (fileSize >= DISK_BLOCK_SIZE) {
                storeSize = DISK_BLOCK_SIZE;
            } else {
                storeSize = fileSize;
            }
            fileDataBlocks.add(new Pair<>(diskBlockNumber, storeSize));
            fileSize -= storeSize;
        }

        if (fileSize > 0) {
            int diskBlockCapacity = DISK_BLOCK_SIZE / DISK_BLOCK_NUMBER_SIZE;
            singlyIndirectPointers = new LinkedList<>();

            diskBlockNumber++;
            for (; fileSize > 0 && singlyIndirectPointers.size() < diskBlockCapacity; diskBlockNumber++) {
                if (fileSize >= DISK_BLOCK_SIZE) {
                    storeSize = DISK_BLOCK_SIZE;
                } else {
                    storeSize = fileSize;
                }
                singlyIndirectPointers.add(new Pair<>(diskBlockNumber, storeSize));
                fileSize -= storeSize;
            }

            if (fileSize > 0) {
                diskBlockNumber++;
                doublyIndirectPointers = new LinkedList<>();
                for (int i = 0; fileSize > 0 && doublyIndirectPointers.size() < diskBlockCapacity; i++) {
                    doublyIndirectPointers.add(new Pair<>("[" + diskBlockNumber + ", No." + i + " 1st Level Index]", new LinkedList<>()));
                    diskBlockNumber++;
                    for (; fileSize > 0 && doublyIndirectPointers.get(i).second.size() < diskBlockCapacity; diskBlockNumber++) {
                        if (fileSize >= DISK_BLOCK_SIZE) {
                            storeSize = DISK_BLOCK_SIZE;
                        } else {
                            storeSize = fileSize;
                        }
                        doublyIndirectPointers.get(i).second.add(new Pair<>(diskBlockNumber, storeSize));
                        fileSize -= storeSize;
                    }
                }

                if (fileSize > 0) {
                    diskBlockNumber++;
                    triplyIndirectPointers = new LinkedList<>();
                    for (int i = 0; fileSize > 0 && triplyIndirectPointers.size() < diskBlockCapacity; i++) {
                        triplyIndirectPointers.add(new Pair<>("[" + diskBlockNumber + ", No." + i + " 2nd Level Index] ", new LinkedList<>()));
                        diskBlockNumber++;
                        for (int j = 0; fileSize > 0 && triplyIndirectPointers.get(i).second.size() < diskBlockCapacity; j++) {
                            triplyIndirectPointers.get(i).second.add(new Pair<>("[" + diskBlockNumber + ", No." + j + " 1st Level Index]", new LinkedList<>()));
                            diskBlockNumber++;
                            for (; fileSize > 0 && triplyIndirectPointers.get(i).second.get(j).second.size() < diskBlockCapacity; diskBlockNumber++) {
                                if (fileSize >= DISK_BLOCK_SIZE) {
                                    storeSize = DISK_BLOCK_SIZE;
                                } else {
                                    storeSize = fileSize;
                                }
                                triplyIndirectPointers.get(i).second.get(j).second.add(new Pair<>(diskBlockNumber, storeSize));
                                fileSize -= storeSize;
                            }
                        }
                    }

                    if (fileSize > 0) {
                        System.out.println("The file size has exceeded the limit, the remaining " + fileSize + "-byte file has been discarded.");
                    }
                }
            }
        }
    }

    public String getDiskBlockNumber(int address) {
        for (Pair<Integer, Integer> fileDataBlock : fileDataBlocks) {
            if (address > fileDataBlock.second) {
                address -= fileDataBlock.second;
            } else {
                return "[" + fileDataBlock.first + ", No." + fileDataBlock.first + " Direct Index, offset " + address + "]";
            }
        }

        if (address > 0 && singlyIndirectPointers != null) {
            for (int i = 0; i < singlyIndirectPointers.size(); i++) {
                Pair<Integer, Integer> singlyIndirectPointer = singlyIndirectPointers.get(i);
                if (address > singlyIndirectPointer.second) {
                    address -= singlyIndirectPointer.second;
                } else {
                    return "[" + singlyIndirectPointer.first + ", No." + i + " 1st Level Index, offset " + address + "]";
                }
            }

            if (address > 0 && doublyIndirectPointers != null) {
                for (int i = 0; i < doublyIndirectPointers.size(); i++) {
                    Pair<String, LinkedList<Pair<Integer, Integer>>> doublyIndirectPointer = doublyIndirectPointers.get(i);
                    for (int j = 0; j < doublyIndirectPointer.second.size(); j++) {
                        if (address > doublyIndirectPointer.second.get(j).second) {
                            address -= doublyIndirectPointer.second.get(j).second;
                        } else {
                            return "[" + doublyIndirectPointer.second.get(j).first + ", 2nd Level Index, No." + i + " 1st Index, No." + j + " Block, offset " + address + "]";
                        }
                    }
                }

                if (address > 0 && triplyIndirectPointers != null) {
                    for (int i = 0; i < triplyIndirectPointers.size(); i++) {
                        Pair<String, LinkedList<Pair<String, LinkedList<Pair<Integer, Integer>>>>> triplyIndirectPointer = triplyIndirectPointers.get(i);
                        for (int j = 0; j < triplyIndirectPointer.second.size(); j++) {
                            for (int k = 0; k < triplyIndirectPointer.second.get(j).second.size(); k++) {
                                if (address > triplyIndirectPointer.second.get(j).second.get(k).second) {
                                    address -= triplyIndirectPointer.second.get(j).second.get(k).second;
                                } else {
                                    return "[" + triplyIndirectPointer.second.get(j).second.get(k).first + ", 3rd Level Index, No." + i + " 2nd Index, No." + j + " 1st Index, No." + k + " Block, offset " + address + "]";
                                }
                            }
                        }
                    }
                }
            }
        }

        return "-1";
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[Direct Index]\n");
        stringBuilder.append(fileDataBlocks.toString()).append("\n\n");
        if (singlyIndirectPointers != null) {
            stringBuilder.append("[(").append(DIRECT_POINTERS_NUMBER).append(", First Level Index)]").append("\n");
            stringBuilder.append(singlyIndirectPointers.toString()).append("\n");
            stringBuilder.append("\n");

            if (doublyIndirectPointers != null) {
                stringBuilder.append("[(").append(singlyIndirectPointers.getLast().first + 1).append(", Second Level Index)]").append("\n");
                for (Pair<String, LinkedList<Pair<Integer, Integer>>> doublyIndirectPointer : doublyIndirectPointers) {
                    stringBuilder.append(doublyIndirectPointer.toString()).append(",\n");
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 2);
                stringBuilder.append("\n");

                if (triplyIndirectPointers != null) {
                    stringBuilder.append("[(").append(doublyIndirectPointers.getLast().second.getLast().first + 1).append(", Third Level Index)]").append("\n");
                    for (Pair<String, LinkedList<Pair<String, LinkedList<Pair<Integer, Integer>>>>> triplyIndirectPointer : triplyIndirectPointers) {
                        for (int j = 0; j < triplyIndirectPointer.second.size(); j++) {
                            String string = "";
                            if (j != 0) {
                                string = String.format("%" + triplyIndirectPointer.first.length() + "s", string);
                            } else {
                                string = triplyIndirectPointer.first;
                            }
                            stringBuilder.append(string).append(triplyIndirectPointer.second.get(j).toString()).append(",\n");
                        }
                    }
                    stringBuilder.deleteCharAt(stringBuilder.length() - 2);
                }
            }
        }

        return stringBuilder.toString();
    }
}
