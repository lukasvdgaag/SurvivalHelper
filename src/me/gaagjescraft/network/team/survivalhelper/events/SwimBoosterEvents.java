package me.gaagjescraft.network.team.survivalhelper.events;

import com.google.common.collect.Lists;
import me.gaagjescraft.network.team.survivalhelper.SurvivalHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;

public class SwimBoosterEvents implements Listener {

    private List<Player> playerBoosted = Lists.newArrayList();
    private List<Player> playerChecking = Lists.newArrayList();
    private HashMap<Player, Integer> playerBoostSchedules = new HashMap<>();

    @EventHandler
    public void onSwim(PlayerMoveEvent e) {
        if (e.getTo().add(0,2,0).getBlock().getType() == Material.WATER) {
            // player is underwater
            if (e.getTo().getY() > e.getFrom().getY()) {
                // player is going up
                if (!playerChecking.contains(e.getPlayer())) {
                    playerChecking.add(e.getPlayer());

                    int task = Bukkit.getScheduler().scheduleSyncDelayedTask(SurvivalHelper.get(),() -> {
                        playerBoostSchedules.remove(e.getPlayer());
                        playerBoosted.add(e.getPlayer());

                        Vector veloCity = e.getPlayer().getVelocity();
                        veloCity.multiply(2.3);
                        e.getPlayer().setVelocity(veloCity);
                        Bukkit.getScheduler().scheduleSyncDelayedTask(SurvivalHelper.get(), () -> {
                            playerBoosted.remove(e.getPlayer());
                            playerChecking.remove(e.getPlayer());
                        }, 100L);
                    }, 40L);
                    playerBoostSchedules.put(e.getPlayer(), task);
                }
            }
            else if (e.getTo().getY() <= e.getFrom().getY()) {
                playerChecking.remove(e.getPlayer());
                if (playerBoostSchedules.containsKey(e.getPlayer())) {
                    Bukkit.getScheduler().cancelTask(playerBoostSchedules.get(e.getPlayer()));
                }
            }
        }
    }

}
