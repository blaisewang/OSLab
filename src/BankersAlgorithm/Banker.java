package BankersAlgorithm;

import java.util.Arrays;

class Banker {

    private int[] available;
    private int[] request;
    private int[] safeSequence;
    private int[][] max;
    private int[][] allocation;
    private int[][] need;

    private StringBuilder checkString = new StringBuilder();

    private static int resourceNumber;
    private static int processNumber;

    Banker(int[] available, int[][] max, int[][] allocation) {
        this.available = available;
        this.max = max;
        this.allocation = allocation;

        resourceNumber = available.length;
        processNumber = max.length;

        need = new int[processNumber][resourceNumber];
        safeSequence = new int[processNumber];

        for (int row = 0; row < processNumber; row++) {
            for (int column = 0; column < resourceNumber; column++) {
                need[row][column] = max[row][column] - allocation[row][column];
            }
        }
        System.out.println(toString());

        if (isSecure()) {
            System.out.println(checkString);
            checkString.delete(0, checkString.length());
        } else {
            System.out.println("Illegal initialization.");
            checkString.delete(0, checkString.length());
        }
    }

    void setRequest(int selectedNumber, int[] request) {
        this.request = request;

        if (compareArray(request, need[selectedNumber])) {
            if (compareArray(request, available)) {
                for (int i = 0; i < resourceNumber; i++) {
                    available[i] -= request[i];
                    allocation[selectedNumber][i] += request[i];
                    need[selectedNumber][i] -= request[i];
                }

                System.out.println("After P" + selectedNumber + " requested " + Arrays.toString(request));
                if (isSecure()) {
                    System.out.println(checkString);
                    System.out.println("There is an safe sequence: " + Arrays.toString(safeSequence));
                } else {
                    System.out.println("Unsafe status. Roll back.\n");
                    for (int i = 0; i < resourceNumber; i++) {
                        available[i] += request[i];
                        allocation[selectedNumber][i] -= request[i];
                        need[selectedNumber][i] += request[i];
                    }
                }

                System.out.println(toString());
                checkString.delete(0, checkString.length());

            } else {
                System.out.println("Illegal request " + Arrays.toString(request) + "\n");
            }
        } else {
            System.out.println("Illegal request " + Arrays.toString(request) + "\n");
        }
    }

    private boolean isSecure() {
        int[] work = available.clone();
        boolean[] finish = new boolean[processNumber];
        Arrays.fill(finish, false);

        int time = 0;

        checkString.append("Security check:\n");

        checkString.append("Proc.\t" + "Work\t\t" + "Need\t\t" + "Allocation\t" + "Work+Alloc.\n");
        for (int circle = 0; circle < processNumber; circle++) {
            for (int row = 0; row < processNumber; row++) {
                if (!finish[row] && compareArray(need[row], work)) {
                    checkString.append("P").append(row).append("\t\t");
                    for (int element : work) {
                        checkString.append(element).append("  ");
                    }
                    checkString.append("\t");

                    for (int column = 0; column < resourceNumber; column++) {
                        work[column] = work[column] + allocation[row][column];
                    }

                    finish[row] = true;
                    safeSequence[time] = row;

                    for (int element : need[row]) {
                        checkString.append(element).append("  ");
                    }
                    checkString.append("\t");

                    for (int element : allocation[row]) {
                        checkString.append(element).append("  ");
                    }

                    checkString.append("\t");
                    for (int element : work) {
                        checkString.append(element).append("  ");
                    }
                    checkString.append("\n");
                    time++;
                }
            }

            if (time == processNumber) {
                return true;
            }

            if (circle == processNumber - 1) {
                if (time != processNumber && request != null) {
                    return false;
                }
            }
        }

        return false;
    }

    private boolean compareArray(int[] firstArray, int[] secondArray) {
        if (firstArray.length != secondArray.length) {
            return false;
        }

        for (int i = 0; i < firstArray.length; i++) {
            if (firstArray[i] > secondArray[i]) {
                return false;
            }
        }

        return true;
    }


    public String toString() {
        StringBuilder string = new StringBuilder();

        string.append("Current status:\n");
        string.append("Proc.\t" + "Max\t\t\t" + "Allocation\t\t" + "Need\t\t" + "Available\n");
        for (int row = 0; row < processNumber; row++) {
            string.append("P").append(row).append("\t\t");
            for (int element : max[row]) {
                string.append(element).append("  ");
            }
            string.append("\t");
            for (int element : allocation[row]) {
                string.append(element).append("  ");
            }
            string.append("\t\t");
            for (int element : need[row]) {
                string.append(element).append("  ");
            }
            string.append("\t");
            if (row == 0) {
                for (int element : available) {
                    string.append(element).append("  ");
                }
            }
            string.append("\n");
        }

        return string.toString();
    }
}
