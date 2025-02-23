package com.xlilith.disablingFeatures;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class EndPortalHandler implements Listener {
	private final Main plugin;
	private final DiscordHandler discordHandler;
	
	public EndPortalHandler(Main plugin, DiscordHandler discordHandler) {
		this.plugin = plugin;
		this.discordHandler = discordHandler;
	}
	
	@EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getItem() != null && event.getItem().getType() == Material.ENDER_EYE) {
			Block block = event.getClickedBlock();
			if (block != null && block.getType() == Material.END_PORTAL_FRAME) {
				if (plugin.isDisabledEndPortal()) {
					event.setCancelled(true);
					
					Player player = event.getPlayer();
                    
                    
                    if (plugin.isEnabledChat()) {
                        String messageEndPortal = plugin.getConfig().getString("Notify.chat.message-end-portal");
                        player.sendMessage(messageEndPortal);                    	
                    }
                    
                    if (plugin.isEnabledTitle()) {
                        String titleMessage = "§c§lAVISO";
                        String subtitleMessage = plugin.getConfig().getString("Notify.title.message-end-portal");
                        player.sendTitle(titleMessage, subtitleMessage, 10, 70, 20);
                    }
                    
                    if (plugin.isEnabledActionBar()) {
                        String actionBarMessage = plugin.getConfig().getString("Notify.actionbar.message-end-portal");
                        player.sendActionBar(actionBarMessage);
                    }
					
					String discordMessage = "**" + player.getName() + "** (*" + player.getUniqueId().toString() + "*) intentó colocar un **Ojo de Ender en un End Portal Frame** en **" + player.getWorld().getName() + "** - Coordenadas: **" + (int) player.getLocation().getX() + " " + (int) player.getLocation().getY() + " " + (int) player.getLocation().getZ() + "**";
	                discordHandler.sendToDiscord(discordMessage);
				}
			}
		}
	}
}

