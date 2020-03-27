package me.gaagjescraft.network.team.survivalhelper.files;

import me.gaagjescraft.network.team.survivalhelper.SurvivalHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerFile {

    private static FileConfiguration fc=null;
    private static File f = new File(SurvivalHelper.get().getDataFolder(), "players.yml");

    public static void setup() {
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fc = YamlConfiguration.loadConfiguration(f);
    }
    
    public static void save() {
        try {
            fc.save(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void reload() {
        fc = YamlConfiguration.loadConfiguration(f);
    }
    
    public static FileConfiguration getFile() {
        return fc;
    }


    Player player;
    public PlayerFile(Player player) {
        this.player = player;
    }

    public boolean hasAntiDropEnabled() {
        return getFile().getBoolean(player.getUniqueId() + ".anti_drop", false);
    }

    public void setAntiDropEnabled(boolean b) {
        getFile().set(player.getUniqueId()+".anti_drop", b);
        save();
        reload();
    }

    public boolean hasAutoSwapEnabled() { return getFile().getBoolean(player.getUniqueId()+".auto_swap",false); }

    public void setAutoSwapEnabled(boolean b) {
        getFile().set(player.getUniqueId()+".auto_swap",b);
        save();
        reload();
    }

    public boolean hasAutoFarmEnabled() { return getFile().getBoolean(player.getUniqueId()+".auto_farm",false); }

    public void setAutoFarmEnabled(boolean b) {
        getFile().set(player.getUniqueId()+".auto_farm",b);
        save();
        reload();
    }



}
