package com.xlilith.disablingFeatures;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private DiscordHandler discordHandler;
	private boolean disabledNetherPortal;
	private boolean disabledEndPortal;
	private boolean disabledWither;
	private boolean disabledTnt;
	private boolean disabledElytras;
	private boolean chatEnabled;
	private boolean titleEnabled;
	private boolean actionBarEnabled;
	
	
	@Override
    public void onEnable() {
		saveDefaultConfig();
		String webhookUrl = getConfig().getString("discord-webhook-url", "https://discord.com/api/webhooks/XXXX/XXXXXXXX");
		discordHandler = new DiscordHandler(webhookUrl);
		disabledNetherPortal = getConfig().getBoolean("disable-nether-portal", true);
		disabledEndPortal = getConfig().getBoolean("disable-end-portal", true);
		disabledWither = getConfig().getBoolean("disable-wither", true);
		disabledTnt = getConfig().getBoolean("disable-tnt", true);
		disabledElytras = getConfig().getBoolean("disable-elytras", true);
		chatEnabled = getConfig().getBoolean("Notify.chat.enabled", true);
		titleEnabled = getConfig().getBoolean("Notify.title.enabled", true);
		actionBarEnabled = getConfig().getBoolean("Notify.actionbar.enabled", true);

		getServer().getPluginManager().registerEvents(new NetherPortalHandler(this, discordHandler), this);
		getServer().getPluginManager().registerEvents(new EndPortalHandler(this, discordHandler), this);
		getServer().getPluginManager().registerEvents(new WitherSpawnHandler(this, discordHandler), this);
		getServer().getPluginManager().registerEvents(new TntExplosionHandler(this, discordHandler), this);
		getServer().getPluginManager().registerEvents(new ElytrasPickupHandler(this, discordHandler), this);
		
        getLogger().info("");
        getLogger().info("  _____  _           _     _ _             ______         _                       ");
        getLogger().info(" |  __ \\(_)         | |   | (_)           |  ____|       | |                      ");
        getLogger().info(" | |  | |_ ___  __ _| |__ | |_ _ __   __ _| |__ ___  __ _| |_ _   _ _ __ ___  ___ ");
        getLogger().info(" | |  | | / __|/ _` | '_ \\| | | '_ \\ / _` |  __/ _ \\/ _` | __| | | | '__/ _ \\/ __|");
        getLogger().info(" | |__| | \\__ \\ (_| | |_) | | | | | | (_| | | |  __/ (_| | |_| |_| | | |  __/\\__ \\");
        getLogger().info(" |_____/|_|___/\\__,_|_.__/|_|_|_| |_|\\__, |_|  \\___|\\__,_|\\__|\\__,_|_|  \\___||___/");
        getLogger().info("                                      __/ |                                       ");
        getLogger().info("                                     |___/                                        ");
        getLogger().info(" Made by: xLilith99_                                     Version: 1.0.0");
        getLogger().info("");
        getLogger().info("DisablingFeatures habilitado!");
    }
	
	
	@Override
	public void onDisable() {
        getLogger().info("DisablingFeatures deshabilitado!");
	}
	
	public boolean isDisabledNetherPortal() {
		return disabledNetherPortal;
	}
	
	public boolean isDisabledEndPortal() {
		return disabledEndPortal;
	}
	
	public boolean isDisabledWither() {
		return disabledWither;
	}
	
	public boolean isDisabledTnt() {
		return disabledTnt;
	}
	
	public boolean isDisabledElytras() {
		return disabledElytras;
	}
	
	public boolean isEnabledChat() {
		return chatEnabled;
	}
	
	public boolean isEnabledTitle() {
		return titleEnabled;
	}
	
	public boolean isEnabledActionBar() {
		return actionBarEnabled;
	}

}
