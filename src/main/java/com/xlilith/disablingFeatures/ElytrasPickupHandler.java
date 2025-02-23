package com.xlilith.disablingFeatures;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ElytrasPickupHandler implements Listener {
    private final Main plugin;
    private final DiscordHandler discordHandler;

    public ElytrasPickupHandler(Main plugin, DiscordHandler discordHandler) {
        this.plugin = plugin;
        this.discordHandler = discordHandler;
    }

    @EventHandler
    public void onElytraInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();

            if (item != null && item.getType() == Material.ELYTRA) {
                if (plugin.isDisabledElytras()) {
                    player.getInventory().remove(item);
                	event.setCancelled(true);
    				blockElytra(player);
                }
            }
        }
    }
    
    @EventHandler
    public void onElytraPickup(EntityPickupItemEvent event) {
    	if (event.getEntity() instanceof Player) {
    		Player player = (Player) event.getEntity();
    		ItemStack item = event.getItem().getItemStack();
    		
    		if (item.getType() == Material.ELYTRA) {
    			if (plugin.isDisabledElytras()) {
                    player.getInventory().remove(item);
    		    	event.setCancelled(true);
    				blockElytra(player);
    			}
    		}
    	}
    }
    
    @EventHandler
    public void onElytraEquip(PlayerArmorChangeEvent event) {
        Player player = event.getPlayer();
        ItemStack newItem = event.getNewItem();

        if (newItem != null && newItem.getType() == Material.ELYTRA) {
            if (plugin.isDisabledElytras()) {
                player.getInventory().remove(newItem);
                blockElytra(player);
            }
        }
    }
    
    private void blockElytra(Player player) {
    	
    	if (plugin.isEnabledChat()) {
            String messageElytras = plugin.getConfig().getString("Notify.chat.message-elytras");
            player.sendMessage(messageElytras);
        }

        if (plugin.isEnabledTitle()) {
            String titleMessage = "§c§lAVISO";
            String subtitleMessage = plugin.getConfig().getString("Notify.title.message-elytras");
            player.sendTitle(titleMessage, subtitleMessage, 10, 70, 20);
        }

        if (plugin.isEnabledActionBar()) {
            String actionBarMessage = plugin.getConfig().getString("Notify.actionbar.message-elytras");
            player.sendActionBar(actionBarMessage);
        }

        String discordMessage = "**" + player.getName() + "** intentó mover unas **Elytras** en **" + player.getWorld().getName() + "** - Coordenadas: **" + (int) player.getLocation().getX() + " " + (int) player.getLocation().getY() + " " + (int) player.getLocation().getZ() + "**";
        discordHandler.sendToDiscord(discordMessage);
    }
}
