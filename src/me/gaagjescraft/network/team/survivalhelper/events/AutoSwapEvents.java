package me.gaagjescraft.network.team.survivalhelper.events;

import me.gaagjescraft.network.team.survivalhelper.files.PlayerFile;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class AutoSwapEvents implements Listener {

    @EventHandler
    public void onBuild(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        PlayerFile pf = new PlayerFile(player);

        if (pf.hasAutoSwapEnabled()) {
            Material placedType = e.getBlockPlaced().getType();
            PlayerInventory inv = player.getInventory();
            int heldSlot = inv.getHeldItemSlot();

            if (inv.getItem(heldSlot) != null && inv.getItem(heldSlot).getAmount()-1 == 0) {

                for (int i = 0; i < 8; i++) {
                    if (i != heldSlot) {
                        if (inv.getItem(i)!=null && inv.getItem(i).getType().equals(placedType)) {
                            inv.setHeldItemSlot(i);
                            return;
                        }
                    }
                }

                for (int i = 9; i < 35; i++) {
                    if (i != heldSlot) {
                        if (inv.getItem(i)!=null && inv.getItem(i).getType().equals(placedType)) {
                            ItemStack item = inv.getItem(i).clone();

                            inv.setItem(heldSlot, item);
                            inv.setItem(i, null);

                            return;
                        }
                    }
                }

            }
        }
    }

    @EventHandler
    public void onItemBreak(PlayerItemBreakEvent e) {
        Player player = e.getPlayer();
        PlayerFile pf = new PlayerFile(player);

        if (pf.hasAutoSwapEnabled()) {
            Material placedType = e.getBrokenItem().getType();
            PlayerInventory inv = player.getInventory();
            int heldSlot = inv.getHeldItemSlot();
            for (int i = 0; i < 8; i++) {
                if (i != heldSlot) {
                    if (inv.getItem(i) != null && inv.getItem(i).getType().equals(placedType)) {
                        inv.setHeldItemSlot(i);
                        return;
                    }
                }
            }

            for (int i = 9; i < 35; i++) {
                if (i != heldSlot) {
                    if (inv.getItem(i) != null && inv.getItem(i).getType().equals(placedType)) {
                        ItemStack item = inv.getItem(i).clone();

                        inv.setItem(heldSlot, item);
                        inv.setItem(i, null);

                        return;
                    }
                }

            }

        }
    }

}
