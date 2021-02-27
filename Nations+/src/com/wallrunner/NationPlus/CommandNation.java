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
import org.bukkit.scoreboard.*;

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
                    try {
                        create(Sender, args[1]);
                    } catch (Exception ArrayIndexOutOfBounds) {
                    }
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
                if (args[0].toLowerCase().equals("promote") == true) {
                    try {
                        promote(Sender, args[1]);
                    } catch(Exception ArrayIndexOutOfBounds) {
                        Sender.sendMessage(ChatColor.RED + "Please enter a player to promote.");
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
            Sender.sendMessage(ChatColor.AQUA + "As a nations leader you can replace your position by using 'promote'");
    }
    public void create(CommandSender Sender, String Name) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        try {
            if (sb.getEntryTeam(Sender.getName()) != null) {
                Team newTeam = sb.registerNewTeam(Name);
                newTeam.setDisplayName(Name);
                newTeam.addEntry(Sender.getName());
                Player SenderP = Bukkit.getPlayerExact(Sender.getName());
                SenderP.addScoreboardTag("nleader");
                sb.registerNewObjective(sb.getEntryTeam(Sender.getName()).getName(), "dummy", sb.getEntryTeam(Sender.getName()).getName());
                Sender.sendMessage(ChatColor.GOLD + "You have created a new nation! " + newTeam.getDisplayName().toString());
            } else {
                Sender.sendMessage(ChatColor.RED + "You are already in a team, please leave or disband.");
            }
        } catch(Exception IllegalArgumentException) {
            Sender.sendMessage(ChatColor.RED + "This team already exists!");
        }
    }
    public void tag(CommandSender Sender, String Tag, ChatColor Color) {
        if(Bukkit.getPlayerExact(Sender.getName()).getScoreboardTags().contains("nleader") == true) {
            Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
            Team Team = sb.getEntryTeam(Sender.getName());
            ArrayList<String> TagList = new ArrayList<String>();
            TagList.add(Tag);
            Team.setPrefix(Color + "[" + Tag + "] ");
            Sender.sendMessage(ChatColor.GOLD + "You have successfully set the tag of your nation!");
        } else {
            Sender.sendMessage("You must be the nations leader to change its name.");
        }
        if(Bukkit.getScoreboardManager().getMainScoreboard().getEntryTeam(Sender.getName()) == null) {
            Sender.sendMessage(ChatColor.RED + "You are not part of a team!");
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
            Player.addScoreboardTag(sb.getEntryTeam(Sender.getName()).getPrefix());
            //Sender.sendMessage(String.valueOf(score.getScore()));
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Player.removeScoreboardTag(sb.getEntryTeam(Sender.getName()).getPrefix());
            }, 600L);
        }
    }
    public void accept(CommandSender Sender) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        if(sb.getEntryTeam(Sender.getName()) == null) {
            Player player = Bukkit.getPlayerExact(Sender.getName());
            Set<String> tags = player.getScoreboardTags();
            Set<Team> teams = sb.getTeams();
            Team[] teamsa = teams.toArray(new Team[0]);
            int x = 0;
            if(teams.isEmpty() == false) {
                while(x <= teamsa.length) {
                    if(tags.contains(teamsa[x].getPrefix())) {
                        teamsa[x].addEntry(Sender.getName());
                        Sender.sendMessage(ChatColor.GOLD + "You have successfully joined the team " + teamsa[x].getDisplayName() + "!");
                        break;
                    } else {
                        x = x + 1;
                    }
                }
            } else {
                Sender.sendMessage(ChatColor.RED + "There are currently no teams, create one using /n create.");
            }
        }
    }
    public void disband(CommandSender Sender) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        try {
            if(Bukkit.getPlayerExact(Sender.getName()).getScoreboardTags().contains("nleader") == true) {
                Team team = sb.getEntryTeam(Sender.getName());
                sb.getObjective(team.getDisplayName()).unregister();
                String teamName = team.getDisplayName();
                team.unregister();
                Bukkit.getPlayerExact(Sender.getName()).removeScoreboardTag("nleader");
                Sender.sendMessage(ChatColor.GREEN + "Team " + teamName + " successfully disbanded!");
            } else {
                Sender.sendMessage(ChatColor.RED + "You are not the nations leader!");
            }
        } catch (Exception IllegalArgumentException) {
            Sender.sendMessage(ChatColor.RED + "You are not part of a team!");
        }
    }
    public void leave(CommandSender Sender) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        try {
            if(Bukkit.getPlayerExact(Sender.getName()).getScoreboardTags().contains("nleader") == false) {
                Team team = sb.getEntryTeam(Sender.getName());
                if (team.getSize() == 1) {
                    disband(Sender);
                    Sender.sendMessage(ChatColor.GOLD + "You were the last person on that team and it is being removed.");
                } else {
                    team.removeEntry(Sender.getName());
                }
            } else {
                Sender.sendMessage(ChatColor.RED + "You are the leader of the team, you cannot leave unless you transfer ownership or simply disband the faction.");
            }
        } catch (Exception IllegalArgumentException) {
            Sender.sendMessage(ChatColor.RED + "You are not part of a team!");
        }
    }
    public void promote(CommandSender Sender, String Pn) {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();
        Player SenderP = Bukkit.getPlayerExact(Sender.getName());
        if (SenderP.getScoreboardTags().contains("nleader")) {
            Player player = Bukkit.getPlayerExact(Pn);
            if(player == null) {
                Sender.sendMessage("Player does not exist, make sure the name is correct.");
            } else {
                if (sb.getEntryTeam(player.getName()) == sb.getEntryTeam(Sender.getName())) {
                    Sender.sendMessage(ChatColor.GOLD + "You have promoted" + Pn);
                    SenderP.removeScoreboardTag("nleader");
                    player.addScoreboardTag("nleader");
                    player.sendMessage(ChatColor.GOLD + "You have been promoted to nation leader of '" + sb.getEntryTeam(player.getName()).getDisplayName() + "' by '" + Sender.getName() + "'!");
                } else {
                    Sender.sendMessage(ChatColor.RED + "This player is not a part of your team!");
                }
            }
        } else {
            Sender.sendMessage(ChatColor.RED + "You must be the leader of a team to change the leader.");
        }
    }
}
