package com.jiesoul.corejava;

import java.util.Scanner;

public class InputTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("what is your name?");
        String name = in.nextLine();

        System.out.println("how old are you?");
        String age = in.nextLine();
        System.out.println(name + " 's age is " +  age);

//        Console console = System.console();
//        String username = console.readLine("username:");
//        char[] password = console.readPassword("password:");
//
//
//        System.out.println(username + "'s password is " + password);
    }
}
