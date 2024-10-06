package org.example.service;

import java.util.Scanner;


public class ConsoleIOService implements IOService{

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public String readLine() {
        return scanner.nextLine();
    }

    @Override
    public void printLine(String text) {
        System.out.println(text);
    }
}
