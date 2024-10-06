package org.example.service;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleIOService implements IOService {

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
