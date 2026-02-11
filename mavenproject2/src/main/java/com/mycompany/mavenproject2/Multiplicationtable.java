package com.mycompany.mavenproject2;

public class Multiplicationtable {

    void multiplication() {
        int i, j;

        for (i = 1; i <= 12; i++) {
            for (j = 1; j <= 12; j++) {
                int x = i * j;
                System.out.println(i + " x " + j + " = " + x);
            }
        }
    }
}
