package com.aldino.game.command;

import java.util.Scanner;

public class CommandParser {
    
    private final Scanner scanner;

    public CommandParser() {
        this.scanner = new Scanner(System.in);
    }

    public Command getCommand() {
        System.out.print("> ");
        String inputLine = scanner.nextLine();

        if(inputLine.isEmpty()) {
            return new Command(CommandType.UNKNOWN, "Input is null");
        }

        String[] words = inputLine.toLowerCase().split("\\s+");

        String firstWord = words[0];
        
        // Semua kata setelah firstWord digabung menjadi secondWord
        String secondWord = null;
        if (words.length > 1) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < words.length; i++) {
                if (i > 1) sb.append(" ");
                sb.append(words[i]);
            }
            secondWord = sb.toString();
        }

        CommandType type;
        switch (firstWord) {
            case "go":
                type = CommandType.GO;
                break;
            case "take":
                type = CommandType.TAKE;
                break;
            case "look":
                type = CommandType.LOOK;
                break;
            case "inv":    
            case "inventory":
                type = CommandType.INVENTORY;
                break;
            case "health":
            case "hp":
                type = CommandType.HEALTH;
                break;
            case "help":
                type = CommandType.HELP;
                break;
            case "exit":
            case "quit":
                type = CommandType.QUIT;
                break;
            default:
                type = CommandType.UNKNOWN;
        }

        return new Command(type, secondWord);
    }

    public void close() {
        scanner.close();
    }
}
