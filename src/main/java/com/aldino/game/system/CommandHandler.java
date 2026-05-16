package com.aldino.game.system;

import com.aldino.game.command.Command;
import com.aldino.game.model.Player;
import com.aldino.game.util.GoRoom;
import com.aldino.game.util.Look;
import com.aldino.game.util.PrintHelp;
import com.aldino.game.util.ShowHealth;
import com.aldino.game.util.ShowInventory;
import com.aldino.game.util.TakeItem;

public class CommandHandler {

    private final Player player;
    private final GameWorld gameWorld;
    private final int activeSaveSlot;

    public CommandHandler(Player player, GameWorld gameWorld, int activeSaveSlot) {
        this.player = player;
        this.gameWorld = gameWorld;
        this.activeSaveSlot = activeSaveSlot;
    }

    public boolean process(Command command) {
        switch (command.getType()) {
            case HELP:
                PrintHelp.displayHelp();
                break;
            case GO:
                GoRoom.execute(command, player, gameWorld, activeSaveSlot);
                break;
            case TAKE:
                TakeItem.execute(command, player, gameWorld.getCurrentRoom(), activeSaveSlot);
                break;
            case LOOK:
                Look.display(gameWorld.getCurrentRoom());
                break;
            case INVENTORY:
                ShowInventory.display(player);
                break;
            case HEALTH:
                ShowHealth.display(player);
                break;
            case QUIT:
                return true;
            case UNKNOWN:
                System.out.println("Perintah tidak dikenali. Ketik 'help' untuk bantuan.");
                break;
            default:
                System.out.println("Perintah '" + command.getType() + "' belum diimplementasikan.");
        }
        return false;
    }
}
