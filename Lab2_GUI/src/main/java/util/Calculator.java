package util;

public class Calculator {

    public static int calculateAmount(int start, int end) {
        int total = 0;

        for (int i = start; i < end; i++) {

            if ((i >= 0 && i < 7) || (i >= 21 && i < 24)) {
                total += 500;
            } else if ((i >= 7 && i < 14) || (i >= 19 && i < 21)) {
                total += 1000;
            } else if (i >= 14 && i < 19) {
                total += 1500;
            }
        }

        return total;
    }
}