package me.gaagjescraft.network.team.survivalhelper.events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageEvents implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() == EntityType.PLAYER) {
            if (e.getEntity() instanceof LivingEntity) {
                Player player = (Player) e.getDamager();
                LivingEntity entity = (LivingEntity) e.getEntity();
                double currentHealth = entity.getHealth()-e.getFinalDamage();
                double maxHealth = entity.getMaxHealth();
                double progress = (float) currentHealth / maxHealth * 100;
                float green = (float) (progress/100) * 10;
                StringBuilder line = new StringBuilder();
                if (currentHealth > 0) {
                    line.append("&bEntity's health: ");
                    for (int i = 0; i < green; i++) {
                        line.append("&a❤");
                    }
                    for (int i = 1; i < (10 - (int) green); i++) {
                        line.append("&8❤");
                    }

                    line.append(" &c-").append((int) Math.round(e.getFinalDamage())).append(" HP");
                }
                else {
                    line.append("&a&lYou killed the entity!");
                }
                BaseComponent comp = new TextComponent(ChatColor.translateAlternateColorCodes('&', line.toString()));
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, comp);

            }
        }
    }

}
