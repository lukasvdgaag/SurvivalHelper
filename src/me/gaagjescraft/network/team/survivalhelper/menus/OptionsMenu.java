package me.gaagjescraft.network.team.survivalhelper.menus;

import com.google.common.collect.Lists;
import me.gaagjescraft.network.team.survivalhelper.files.PlayerFile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class OptionsMenu implements Listener {

    private static HashMap<Player, Boolean> inMenu = new HashMap<>();

    public void openMenu(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, "Survial options menu");

        PlayerFile pf = new PlayerFile(player);

        byte byt = pf.hasAntiDropEnabled() ? (byte) 5 : (byte) 14;

        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, byt);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lAnti Drop"));
        meta.setLore(Lists.newArrayList(
                ChatColor.GRAY + "If this feature is enabled, all drop",
                ChatColor.GRAY + "from blocks that you break will be",
                ChatColor.GRAY + "automatically added to your inventory",
                "", ChatColor.translateAlternateColorCodes('&', "&7Status: " +
                        (pf.hasAntiDropEnabled() ? "&aenabled" : "&cdisabled")),
                ChatColor.YELLOW + "Click to toggle this option"));
        item.setItemMeta(meta);
        gui.setItem(2, item);

        byte byty = pf.hasAutoSwapEnabled() ? (byte) 5 : (byte) 14;

        item = new ItemStack(Material.STAINED_GLASS_PANE, 1, byty);
        meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lAuto Swap"));
        meta.setLore(Lists.newArrayList(
                ChatColor.GRAY + "If you break an item/empty a slot while",
                ChatColor.GRAY + "building, and you have the same item in a",
                ChatColor.GRAY + "different slot, it will automatically select",
                ChatColor.GRAY + "that slot so you can continue",
                "", ChatColor.translateAlternateColorCodes('&', "&7Status: " +
                        (pf.hasAutoSwapEnabled() ? "&aenabled" : "&cdisabled")),
                ChatColor.YELLOW + "Click to toggle this option"));
        item.setItemMeta(meta);
        gui.setItem(4, item);

        byte bytf = pf.hasAutoFarmEnabled() ? (byte) 5 : (byte) 14;

        item = new ItemStack(Material.STAINED_GLASS_PANE, 1, bytf);
        meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b&lAuto Farm"));
        meta.setLore(Lists.newArrayList(
                ChatColor.GRAY + "If right-click a crop with a",
                ChatColor.GRAY + "hoe, it will automatically harvest",
                ChatColor.GRAY + "all other crops in a 8x8 radius",
                "", ChatColor.translateAlternateColorCodes('&', "&7Status: " +
                        (pf.hasAutoFarmEnabled() ? "&aenabled" : "&cdisabled")),
                ChatColor.YELLOW + "Click to toggle this option"));
        item.setItemMeta(meta);
        gui.setItem(6, item);

        player.openInventory(gui);
        inMenu.put(player, true);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        if (inMenu.getOrDefault((Player)e.getPlayer(), false)) {
            inMenu.remove((Player)e.getPlayer());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        PlayerFile pf = new PlayerFile(player);
        if (inMenu.getOrDefault(player, false)) {
            e.setCancelled(true);
            if (e.getClickedInventory() != null && e.getClickedInventory().equals(e.getView().getTopInventory())) {

                if (e.getSlot() == 2) {
                    if (pf.hasAntiDropEnabled()) {
                        pf.setAntiDropEnabled(false);
                        player.sendMessage(ChatColor.GRAY + "The anti drop option has been " + ChatColor.RED + "disabled");
                    }
                    else {
                        pf.setAntiDropEnabled(true);
                        player.sendMessage(ChatColor.GRAY + "The anti drop option has been " + ChatColor.GREEN + "enabled");
                    }
                    player.closeInventory();
                }
                else if (e.getSlot() == 4) {
                    if (pf.hasAutoSwapEnabled()) {
                        pf.setAutoSwapEnabled(false);
                        player.sendMessage(ChatColor.GRAY + "The auto swap option has been " + ChatColor.RED + "disabled");
                    }
                    else {
                        pf.setAutoSwapEnabled(true);
                        player.sendMessage(ChatColor.GRAY + "The auto swap option has been " + ChatColor.GREEN + "enabled");
                    }
                    player.closeInventory();
                }
                else if (e.getSlot() == 6) {
                    if (pf.hasAutoFarmEnabled()) {
                        pf.setAutoFarmEnabled(false);
                        player.sendMessage(ChatColor.GRAY + "The auto farm option has been " + ChatColor.RED + "disabled");
                    }
                    else {
                        pf.setAutoFarmEnabled(true);
                        player.sendMessage(ChatColor.GRAY + "The auto farm option has been " + ChatColor.GREEN + "enabled");
                    }
                    player.closeInventory();
                }
            }
        }
    }

}
