package com.aldino.game.command;

public class Command {

    private final CommandType type;
    private final String secondWord;   // contoh: "north", "pedang", dll

    public Command(CommandType type, String secondWord) {
        this.type = type;
        this.secondWord = secondWord;
    }

    public CommandType getType() {
        return type;
    }

    public String getSecondWord() {
        return secondWord;
    }

    public boolean hasSecondWord() {
        return secondWord != null && !secondWord.isBlank();
    }
}