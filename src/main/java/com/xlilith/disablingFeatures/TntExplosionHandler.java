package com.xlilith.disablingFeatures;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityPlaceEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.minecart.ExplosiveMinecart;

public class TntExplosionHandler implements Listener {

	private final Main plugin;
	private final DiscordHandler discordHandler;
	
	public TntExplosionHandler(Main plugin, DiscordHandler discordHandler) {
		this.plugin = plugin;
		this.discordHandler = discordHandler;		
	}
	
	@EventHandler
	public void onPlayerPlaceTnt(BlockPlaceEvent event) {
		Block block = event.getBlockPlaced();
		if (block.getType() == Material.TNT) {
			if (plugin.isDisabledTnt()) {
				event.setCancelled(true);
				
				Player player = event.getPlayer();

				if (plugin.isEnabledChat()) {
					String messageTnt = plugin.getConfig().getString("Notify.chat.message-tnt");
					player.sendMessage(messageTnt);
				}
				
				if (plugin.isEnabledTitle()) {
	                String titleMessage = "§c§lAVISO";
					String subtitleMessage = plugin.getConfig().getString("Notify.title.message-tnt");
					player.sendTitle(titleMessage, subtitleMessage, 10, 70, 20);
				}
				
				if (plugin.isEnabledActionBar()) {
					String actionBarMessage = plugin.getConfig().getString("Notify.actionbar.message-tnt");
	                player.sendActionBar(actionBarMessage);
				}				
				
				String discordMessage = "**" + player.getName() + "** (*" + player.getUniqueId().toString() + "*) intentó colocar un **bloque de TNT** en **" + player.getWorld().getName() + "** - Coordenadas: **" + (int) player.getLocation().getX() + " " + (int) player.getLocation().getY() + " " + (int) player.getLocation().getZ() + "**";
	            discordHandler.sendToDiscord(discordMessage);
				
			}
		}
	}
	
    @EventHandler
    public void onEntityPlace(EntityPlaceEvent event) {
        if (event.getEntity() instanceof ExplosiveMinecart) {
            ExplosiveMinecart explosiveMinecart = (ExplosiveMinecart) event.getEntity();

            if (plugin.isDisabledTnt()) {
                event.setCancelled(true);

                if (event.getEntity().getWorld() != null && event.getEntity().getWorld().getPlayers().size() > 0) {
                    Player player = (Player) event.getEntity().getWorld().getPlayers().toArray()[0];
                    if (player != null) {
                        if (plugin.isEnabledChat()) {
                            String messageTnt = plugin.getConfig().getString("Notify.chat.message-tnt");
                            player.sendMessage(messageTnt);
                        }

                        if (plugin.isEnabledTitle()) {
                            String titleMessage = "§c§lAVISO";
                            String subtitleMessage = plugin.getConfig().getString("Notify.title.message-tnt");
                            player.sendTitle(titleMessage, subtitleMessage, 10, 70, 20);
                        }

                        if (plugin.isEnabledActionBar()) {
                            String actionBarMessage = plugin.getConfig().getString("Notify.actionbar.message-tnt");
                            player.sendActionBar(actionBarMessage);
                        }

                        String discordMessage = "**" + player.getName() + "** (*" + player.getUniqueId().toString() + "*) intentó colocar una **vagoneta con TNT** en **" + player.getWorld().getName() + "** - Coordenadas: **" + (int) player.getLocation().getX() + " " + (int) player.getLocation().getY() + " " + (int) player.getLocation().getZ() + "**";
                        discordHandler.sendToDiscord(discordMessage);
                    }
                }
            }
        }
    }
}