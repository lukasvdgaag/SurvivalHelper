package me.gaagjescraft.network.team.survivalhelper.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class AutoFarmEvents implements Listener {

    @EventHandler
    public void onItemClick(PlayerInteractEvent e) {
        Block clicked = e.getClickedBlock();
        if (clicked != null && e.getPlayer().isSneaking()&&e.getItem().getType().toString().contains("_HOE")) {
            // player uses the super harvest power tool.
            // todo check if enabled

           Block lowerBlock =clicked.getLocation().add(0,-1,0).getBlock();
           int farmed = 0;
            if (lowerBlock != null && lowerBlock.getType()==Material.SOIL) {
                e.getPlayer().sendMessage(ChatColor.GREEN + "Starting super farmer...");
                // is farmland
                for (int i=-8;i<8;i++) {
                    for (int f=-8;f<8;f++) {
                        Block left = lowerBlock.getLocation().add(i, 0, f).getBlock();
                        Block middle = lowerBlock.getLocation().add(f, 0, i).getBlock();

                        if (left.getType() == Material.SOIL) {
                            Block up = left.getLocation().add(0, 1, 0).getBlock();
                            if (up.getData() == 7 && up.getType() != Material.MELON_STEM && up.getType() != Material.PUMPKIN_STEM) {
                                // plant is fully grown
                                Bukkit.getPluginManager().callEvent(new BlockBreakEvent(up, e.getPlayer()));
                                if (up.getType() == Material.POTATO || up.getType() == Material.CARROT || up.getType() == Material.CROPS ||
                                        up.getType() == Material.BEETROOT) {
                                    Material ab = up.getType();
                                    if (ab == Material.CROPS) {
                                        ab = Material.SEEDS;
                                    }
                                    e.getPlayer().getInventory().removeItem(new ItemStack(ab));
                                    up.setType(up.getType());
                                    up.setData((byte) 0);
                                    up.getWorld().playEffect(up.getLocation(), Effect.VILLAGER_PLANT_GROW, 1);
                                    farmed++;
                                }
                            }
                        }
                        if (middle.getType() == Material.SOIL) {
                            Block up = middle.getLocation().add(0, 1, 0).getBlock();
                            if (up.getData() == 7 && up.getType() != Material.MELON_STEM && up.getType() != Material.PUMPKIN_STEM) {
                                // plant is fully grown
                                Bukkit.getPluginManager().callEvent(new BlockBreakEvent(up, e.getPlayer()));
                                if (up.getType() == Material.POTATO || up.getType() == Material.CARROT || up.getType() == Material.CROPS ||
                                        up.getType() == Material.BEETROOT) {
                                    Material ab = up.getType();
                                    if (ab == Material.CROPS) {
                                        ab = Material.SEEDS;
                                    }
                                    e.getPlayer().getInventory().removeItem(new ItemStack(ab));
                                    up.setType(up.getType());
                                    up.setData((byte) 0);
                                    up.getWorld().playEffect(up.getLocation(), Effect.VILLAGER_PLANT_GROW, 1);
                                    farmed++;
                                }
                            }
                        }
                    }
                }

                if (farmed==0) {
                    e.getPlayer().sendMessage(ChatColor.RED+"No fully grown plants were found.");
                }
                else {
                    e.getPlayer().sendMessage(ChatColor.GRAY + "Auto farmed a total of " + ChatColor.AQUA + farmed + ChatColor.GRAY + " plants");
                }

            }
        }
    }

}
