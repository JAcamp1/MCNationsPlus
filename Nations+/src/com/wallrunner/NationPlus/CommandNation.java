package com.wallrunner.NationPlus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNation implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender Sender, Command Cmd, String Label, String[] args) {

        if (Sender instanceof org.bukkit.entity.Player) {
            Player player = (Player) Sender;
            if (args.length >= 0) {
                if (args[0].equals("h") == true) {
                    help(Sender);
                }
                if (args[0].equals("help") == true) {
                    help(Sender);
                }
            } else {
                Sender.sendMessage(ChatColor.RED + "Invalid usage try argument 'help'");
            }
        }


        //if it worked this fires
        return true;

    }
    static void help(CommandSender Sender) {
            Sender.sendMessage(ChatColor.GOLD + "This is the nation tool");
            Sender.sendMessage(ChatColor.AQUA + "'h' or 'help' for help");
    }

}
