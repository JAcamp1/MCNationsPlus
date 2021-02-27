package com.wallrunner.NationPlus;

import com.sun.istack.internal.NotNull;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Scoreboard;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class CommandNation implements CommandExecutor {
    private Plugin plugin = Main.getPlugin(Main.class);

    @Override
    public boolean onCommand(CommandSender Sender, Command Cmd, String Label, String[] args) {

        if (Sender instanceof org.bukkit.entity.Player) {
            if (args.length >= 0) {
                if (args[0].toLowerCase().equals("h") == true) {
                    help(Sender);
                }
                if (args[0].toLowerCase().equals("help") == true) {
                    help(Sender);
                }
                if (args[0].toLowerCase().equals("create") == true) {
                    //if(args.length == 1) {
                    create(Sender, args[1]);
                    //} else {
                    //    Sender.sendMessage(ChatColor.RED + "Invalid arguments, put in team name.");
                    //}
                }
                if (args[0].toLowerCase().equals("tag") == true) {
                    try {
                        tag(Sender, args[1], ChatColor.valueOf(args[2]));
                    } catch(Exception IllegalArgumentException) {
                        Sender.sendMessage(ChatColor.RED + "Not a proper color. Do the Tag followed by a valid color.");
                    }
                }
                if (args[0].toLowerCase().equals("color") == true) {
                    try {
                        color(Sender, ChatColor.valueOf(args[1]));
                    } catch(Exception IllegalArgumentException) {
                        Sender.sendMessage(ChatColor.RED + "Not a proper color. Do the Tag followed by a valid color.");
                    }
                }
                if (args[0].toLowerCase().equals("invite") == true) {
                    try {
                        invite(Sender, args[1]);
                    } catch(Exception ArrayIndexOutOfBounds) {
                        Sender.sendMessage(ChatColor.RED + "Please enter a player to invite.");
                    }
                }
                if (args[0].toLowerCase().equals("accept") == true) {
                    try {
                        accept(Sender);
                    } catch(Exception ArrayIndexOutOfBounds) {
                    }
                }
                if (args[0].toLowerCase().equals("disband") == true) {
                    try {
                        disband(Sender);
                    } catch(Exception ArrayIndexOutOfBounds) {
                    }
                }
                if (args[0].toLowerCase().equals("leave") == true) {
                    try {
                        leave(Sender);
                    } catch(Exception ArrayIndexOutOfBounds) {
                    }
                }
            } else {
                Sender.sendMessage(ChatColor.RED + "Invalid usage try argument 'help'");
            }
        }


        //if it worked this fires
        return true;

    }
    static void help(CommandSender Sender) {
            Sender.sendMessage(ChatColor.GOLD + "This is the nation tool do /nation or /n!");
            Sender.sendMessage(ChatColor.AQUA + "'h' or 'help' for help.");
            Sender.sendMessage(ChatColor.AQUA + "To create a new nation use 'create.'");
            Sender.sendMessage(ChatColor.AQUA + "To make a Nation Tag do 'tag' followed by tag and color.");
            Sender.sendMessage(ChatColor.AQUA + "To invite someone to your nation use 'invite' followed by their name.");
            Sender.sendMessage(ChatColor.AQUA + "To leave a faction simply do 'leave.'");
            Sender.sendMessage(ChatColor.AQUA + "To fully remove a nation from the list do 'disband.'");
    }
    public void create(CommandSender Sender, String Name) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        try {
            Team newTeam = sb.registerNewTeam(Name);
            newTeam.setDisplayName(Name);
            newTeam.addEntry(Sender.getName());
            sb.registerNewObjective(sb.getEntryTeam(Sender.getName()).getName(), "dummy", sb.getEntryTeam(Sender.getName()).getName());
            Sender.sendMessage(ChatColor.GOLD + "You have created a new nation! " + newTeam.getDisplayName().toString());
        } catch(Exception IllegalArgumentException) {
            Sender.sendMessage(ChatColor.RED + "This team already exists!");
        }
    }
    public void tag(CommandSender Sender, String Tag, ChatColor Color) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Team Team = sb.getEntryTeam(Sender.getName());
        ArrayList<String> TagList = new ArrayList<String>();
        TagList.add(Tag);
        Team.setPrefix(Color + "[" + Tag + "] ");
        try
        {
            FileOutputStream fos = new FileOutputStream("Tags");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(TagList);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
    public void color(CommandSender Sender, ChatColor color) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Team Team = sb.getEntryTeam(Sender.getName());
        Team.setColor(color);
    }
    public void invite(CommandSender Sender, String opposite) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Player Player = Bukkit.getPlayer(opposite);
        if(Player == null) {
            Sender.sendMessage(ChatColor.RED + "Player could not be found.");
        } else {
            Player.sendMessage(ChatColor.GREEN + "You have been invited to join " + sb.getEntryTeam(Sender.getName()).getDisplayName() + " by " + Sender.getName() + "!");
            Sender.sendMessage(ChatColor.GREEN + "Invite sent to " + Player.getName() + "!");
            Objective Obj = sb.getObjective(sb.getEntryTeam(Sender.getName()).getName());
            Score score = Obj.getScore(Player.getName());
            score.setScore(1);
            //Sender.sendMessage(String.valueOf(score.getScore()));
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                score.setScore(0);
            }, 600L);
        }
    }
    public void accept(CommandSender Sender) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Set<Team> Teams = sb.getTeams();
        int n = Teams.size();
        Team Teamsa[] = new Team[n];
        Teamsa = Teams.toArray(Teamsa);
        Integer x = 0;
        Sender.sendMessage(String.valueOf(Teamsa.length));
        Sender.sendMessage(Teamsa.toString());
        while(x <= Teamsa.length) {
            Team Select = Teamsa[x];
            Objective Obj = sb.getObjective(Select.getName());
            Sender.sendMessage(Select.getName());
            if(Obj == null) {
                Sender.sendMessage("No invites found.");
                x = x + 1;
            } else {
                Score score = Obj.getScore(Sender.getName());
                if(score.getScore() == 1) {
                    score.setScore(0);
                    Sender.sendMessage("You have successfully joined the team: " + ChatColor.BLUE + Select.getDisplayName());
                    Select.addEntry(Sender.getName());
                    x = x + 500;
                } else {
                    x = x + 1;
                }
            }
        }
    }
    public void disband(CommandSender Sender) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        try {
            Team team = sb.getEntryTeam(Sender.getName());
            sb.getObjective(team.getDisplayName()).unregister();
            String teamName = team.getDisplayName();
            team.unregister();
            Sender.sendMessage(ChatColor.GREEN + "Team " + teamName + " successfully disbanded!");
        } catch (Exception IllegalArgumentException) {
            Sender.sendMessage(ChatColor.RED + "You are not part of a team!");
        }
    }
    public void leave(CommandSender Sender) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        try {
            Team team = sb.getEntryTeam(Sender.getName());
            if(team.getSize() == 1) {
                disband(Sender);
                Sender.sendMessage(ChatColor.GOLD + "You were the last person on that team and it is being removed.");
            } else {
                team.removeEntry(Sender.getName());
            }
        } catch (Exception IllegalArgumentException) {
            Sender.sendMessage(ChatColor.RED + "You are not part of a team!");
        }
    }
}
