package BankersAlgorithm;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        int[] available = {3, 3, 2};
        int[][] max = {{7, 5, 3}, {3, 2, 2}, {9, 0, 2}, {2, 2, 2}, {4, 3, 3}};
        int[][] allocation = {{0, 1, 0}, {2, 0, 0}, {3, 0, 2}, {2, 1, 1}, {0, 0, 2}};

        Banker banker = new Banker(available, max, allocation);

        int resourceNumber = available.length;
        int processNumber = max.length;
        int[] request = new int[resourceNumber];

        int num = 0;
        Scanner scanner = new Scanner(System.in);

        while (num != -1) {
            System.out.print("Enter process Number with -1 to exit: ");
            num = scanner.nextInt();
            if (num >= 0 && num < processNumber) {
                System.out.println("Request resources for P" + num + ":");
                for (int i = 0; i < resourceNumber; i++) {
                    System.out.print("Resource " + i + " = ");
                    request[i] = scanner.nextInt();
                }
                System.out.println();
                banker.setRequest(num, request);
            } else if (num != -1) {
                System.out.println("Illegal process number entered.");
            }
        }
    }
}
