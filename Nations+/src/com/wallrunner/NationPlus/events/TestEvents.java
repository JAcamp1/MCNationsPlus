package com.wallrunner.NationPlus.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;

public class TestEvents implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent event) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Player player = event.getPlayer();
        try {
            player.sendMessage(ChatColor.GOLD + "You are part of the nation " + sb.getEntryTeam(player.getName()).getDisplayName() + "!");
        } catch(Exception IllegalArgumentException) {
            player.sendMessage(ChatColor.GOLD + "You are not currently a part of a team.");
        }
    }
}
