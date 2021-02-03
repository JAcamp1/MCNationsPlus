package com.wallrunner.NationPlus;

import com.wallrunner.NationPlus.events.TestEvents;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        //Startup
        getServer().getPluginManager().registerEvents(new TestEvents(), this);
        this.getCommand("nation").setExecutor(new CommandNation());
        getServer().getConsoleSender().sendMessage(ChatColor.GOLD + "{NationsPlus}: Plugin Enabled");
    }
    @Override
    public void onDisable() {
        //Shutdown


    }

}
