package me.gaagjescraft.network.team.survivalhelper.commands;

import me.gaagjescraft.network.team.survivalhelper.menus.OptionsMenu;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SurvivalHelperCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("survivalhelper")) {
            if (!(commandSender instanceof Player)) {
                commandSender.sendMessage(ChatColor.RED+"You must be a player to perform this command.");
                return true;
            }

            new OptionsMenu().openMenu((Player)commandSender);
            return true;
        }
        return false;
    }
}
