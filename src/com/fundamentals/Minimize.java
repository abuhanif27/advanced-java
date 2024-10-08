package com.fundamentals;

public class Minimize {
    public int findMinimize(int a, int b) {
        int c = (a + b) / 2;
        return (c - a) + (b - c);
    }

    public static void main(String[] args) {
        var obj = new Minimize();
        // Example test cases
        System.out.println(obj.findMinimize(1, 2));
        System.out.println(obj.findMinimize(3, 10));
        System.out.println(obj.findMinimize(5, 5));
    }
}
