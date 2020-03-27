package me.gaagjescraft.network.team.survivalhelper.events;

import com.google.common.collect.Lists;
import me.gaagjescraft.network.team.survivalhelper.files.PlayerFile;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.CocoaPlant;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class AntiDropEvents implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Collection<ItemStack> items = e.getBlock().getDrops(e.getPlayer().getInventory().getItemInMainHand());
        PlayerFile pf = new PlayerFile(e.getPlayer());
        if (pf.hasAntiDropEnabled()) {
            e.setDropItems(false);

            if (e.getBlock().getType() == Material.CROPS) {
                List<Integer> chances = Lists.newArrayList(1,1,2,1,2,1,1,2,1,1);
                int chosen = chances.get(new Random().nextInt(chances.size()));
                items.add(new ItemStack(Material.SEEDS, chosen));
            }
            if (e.getBlock().getType() == Material.CACTUS || e.getBlock().getType() == Material.SUGAR_CANE_BLOCK) {
                Material mat = e.getBlock().getType()==Material.SUGAR_CANE_BLOCK ? Material.SUGAR_CANE : e.getBlock().getType();
                for (int i=(e.getBlock().getY()+1);i<265;i++) {

                    Location loc = e.getBlock().getLocation();
                    loc.setY(i);
                    Block b = loc.getBlock();
                    if (b != null && b.getType() == e.getBlock().getType()) {
                        items.add(new ItemStack(mat));
                    }
                    else {
                        break;
                    }
                }
            }

            if (!items.isEmpty()) {
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ENTITY_ITEM_PICKUP,1,1);
            }
            
            boolean c = false;

            Inventory inv = e.getPlayer().getInventory();
            for (ItemStack item : items) {
                boolean b = false;

                HashMap<Integer, ItemStack> restingItems = inv.addItem(item);
                if (restingItems.isEmpty()) {
                    b=true;
                }

                if (!b) {
                    for (int i : restingItems.keySet()) {
                        e.getBlock().getWorld().dropItem(e.getBlock().getLocation(), restingItems.get(i));
                    }
                    
                    if (!c) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You didn't have enough space in your inventory for all the items, " +
                                "so the remaining items have been dropped.");
                    }
                    c = true;
                }
            }
        }
    }

    @EventHandler
    public void onKill(EntityDeathEvent e) {
        if (e.getEntity().getType() != EntityType.PLAYER && e.getEntity().getKiller() != null) {
            Player player = (Player) e.getEntity().getKiller();
            PlayerFile pf = new PlayerFile(player);
            
            final List<ItemStack> items = Lists.newArrayList(e.getDrops());
            if (pf.hasAntiDropEnabled()) {
                e.getDrops().clear();

                if (!items.isEmpty()) {
                    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP,1,1);
                }

                boolean c = false;


                Inventory inv = player.getInventory();
                for (ItemStack item : items) {
                    boolean b = false;

                    HashMap<Integer, ItemStack> restingItems = inv.addItem(item);
                    if (restingItems.isEmpty()) {
                        b=true;
                    }

                    if (!b) {
                        for (int i : restingItems.keySet()) {
                            e.getEntity().getWorld().dropItem(e.getEntity().getLocation(), restingItems.get(i));
                        }

                        if (!c) {
                            player.sendMessage(ChatColor.RED + "You didn't have enough space in your inventory for all the items, " +
                                    "so the remaining items have been dropped.");
                        }
                        c = true;
                    }
                }
            }
            
            
        }
        
    }

}
