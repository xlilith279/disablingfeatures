package com.xlilith.disablingFeatures;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;

public class NetherPortalHandler implements Listener {
	private final Main plugin;
	private final DiscordHandler discordHandler;
	
	public NetherPortalHandler(Main plugin, DiscordHandler discordHandler) {
		this.plugin = plugin;
		this.discordHandler = discordHandler;
	}
	
	@EventHandler
	public void onPortalCreate(PortalCreateEvent event) {
		if (event.getReason() == PortalCreateEvent.CreateReason.FIRE) {
			if (plugin.isDisabledNetherPortal()) {
				event.setCancelled(true);
				if (event.getEntity() instanceof Player player) {
                    
                    
                    if (plugin.isEnabledChat()) {
                        String messageNetherPortal = plugin.getConfig().getString("Notify.chat.message-nether-portal");
                        player.sendMessage(messageNetherPortal);                    	
                    }
                    
                    if (plugin.isEnabledTitle()) {
                        String titleMessage = "§c§lAVISO";
                        String subtitleMessage = plugin.getConfig().getString("Notify.title.message-nether-portal");
                        player.sendTitle(titleMessage, subtitleMessage, 10, 70, 20);
                    }
                    
                    if (plugin.isEnabledActionBar()) {
                        String actionBarMessage = plugin.getConfig().getString("Notify.actionbar.message-nether-portal");
                        player.sendActionBar(actionBarMessage);
                    }
					
					String discordMessage = "**" + player.getName() + "** (*" + player.getUniqueId().toString() + "*) intentó abrir un portal al **Nether** en **" + player.getWorld().getName() + "** - Coordenadas: **" + (int) player.getLocation().getX() + " " + (int) player.getLocation().getY() + " " + (int) player.getLocation().getZ() + "**";

                    discordHandler.sendToDiscord(discordMessage);
				}
			}
		}
	}
}
