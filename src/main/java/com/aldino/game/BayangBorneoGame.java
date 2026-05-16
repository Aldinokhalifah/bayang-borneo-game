package com.aldino.game;

import com.aldino.game.command.Command;
import com.aldino.game.command.CommandParser;
import com.aldino.game.model.Player;
import com.aldino.game.system.CommandHandler;
import com.aldino.game.system.GameStartup;
import com.aldino.game.system.GameWorld;
import com.aldino.game.util.CheckGameStatus;
import com.aldino.game.util.PrintWelcome;

public class BayangBorneoGame {

    private static final int MAX_SAVE_SLOT = 3;
    private final CommandParser parser;
    private final Player player;
    private final int activeSaveSlot;
    private final GameWorld gameWorld;
    private final CommandHandler commandHandler;

    public BayangBorneoGame() {
        this.parser = new CommandParser();
        this.gameWorld = new GameWorld();

        GameStartup startup = new GameStartup();
        GameStartup.Session session = startup.chooseProgress(gameWorld, MAX_SAVE_SLOT);
        this.player = session.getPlayer();
        this.activeSaveSlot = session.getActiveSaveSlot();
        this.commandHandler = new CommandHandler(player, gameWorld, activeSaveSlot);

        PrintWelcome.display();
    }

    public void play() {
        boolean finished = false;

        while (!finished) {
            Command command = parser.getCommand();
            finished = commandHandler.process(command);

            if (!finished) {
                finished = CheckGameStatus.check(player);
            }
        }
    }

    public static void main(String[] args) {
        BayangBorneoGame game = new BayangBorneoGame();
        game.play();
    }
}
