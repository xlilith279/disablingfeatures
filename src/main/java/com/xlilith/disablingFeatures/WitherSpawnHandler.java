package com.xlilith.disablingFeatures;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class WitherSpawnHandler implements Listener {
    private final Main plugin;
    private final DiscordHandler discordHandler;

    public WitherSpawnHandler(Main plugin, DiscordHandler discordHandler) {
        this.plugin = plugin;
        this.discordHandler = discordHandler;
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntityType() == EntityType.WITHER) {
            if (plugin.isDisabledWither()) {
                event.setCancelled(true);

                Player player = event.getEntity().getWorld().getPlayers().stream()
                        .filter(p -> p.getLocation().distance(event.getEntity().getLocation()) < 5)
                        .findFirst()
                        .orElse(null);
                
                
                if (plugin.isEnabledChat()) {
                    String messageWither = plugin.getConfig().getString("Notify.chat.message-wither");
                    player.sendMessage(messageWither);                    	
                }
                
                if (plugin.isEnabledTitle()) {
                    String titleMessage = "§c§lAVISO";
                    String subtitleMessage = plugin.getConfig().getString("Notify.title.message-wither");
                    player.sendTitle(titleMessage, subtitleMessage, 10, 70, 20);
                }
                
                if (plugin.isEnabledActionBar()) {
                    String actionBarMessage = plugin.getConfig().getString("Notify.actionbar.message-wither");
                    player.sendActionBar(actionBarMessage);
                }
                
                if (player != null) {
                    String discordMessage = "**" + player.getName() + "** (*" + player.getUniqueId().toString() + "*) intentó invocar al **Wither** en **" + player.getWorld().getName() + "** - Coordenadas: **" + (int) player.getLocation().getX() + " " + (int) player.getLocation().getY() + " " + (int) player.getLocation().getZ() + "**";
                    discordHandler.sendToDiscord(discordMessage);
                }
            }
        }
    }
}
