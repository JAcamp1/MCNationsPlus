package com.wallrunner.NationPlus;

import com.wallrunner.NationPlus.events.TestEvents;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        //Startup
        getServer().getPluginManager().registerEvents(new TestEvents(), this);
        this.getCommand("nation").setExecutor(new CommandNation());
        this.getCommand("n").setExecutor(new CommandNation());
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Objective nown = sb.getObjective("nown");
        if(nown == null) {
            sb.registerNewObjective("nown", "dummy", "nown");
        }
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "{NationsPlus}: Plugin Enabled");
    }
    @Override
    public void onDisable() {
        //Shutdown


    }

}
