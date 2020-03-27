package me.gaagjescraft.network.team.survivalhelper;

import me.gaagjescraft.network.team.survivalhelper.commands.SurvivalHelperCommand;
import me.gaagjescraft.network.team.survivalhelper.events.*;
import me.gaagjescraft.network.team.survivalhelper.files.PlayerFile;
import me.gaagjescraft.network.team.survivalhelper.menus.OptionsMenu;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvivalHelper extends JavaPlugin {

    private static SurvivalHelper it;
    public static SurvivalHelper get() { return it; }

    @Override
    public void onEnable() {
        it = this;

        PlayerFile.setup();
        PlayerFile.getFile().options().copyDefaults(true).copyHeader(true);
        PlayerFile.save();
        PlayerFile.reload();

        Bukkit.getPluginManager().registerEvents(new OptionsMenu(),this);
        Bukkit.getPluginManager().registerEvents(new AntiDropEvents(),this);
        Bukkit.getPluginManager().registerEvents(new AutoSwapEvents(), this);
        Bukkit.getPluginManager().registerEvents(new AutoFarmEvents(), this);
        Bukkit.getPluginManager().registerEvents(new DamageEvents(), this);
        Bukkit.getPluginManager().registerEvents(new SwimBoosterEvents(), this);
        getCommand("survivalhelper").setExecutor(new SurvivalHelperCommand());
    }
}
