package net.fishwifi.nathmtils;

import net.fishwifi.nathmtils.tag.TagManager;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;

import net.fishwifi.nathmtils.config.ModConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class nathmUtils implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("nathmtils");
	public static final String NAMESPACE = "nathmUtils";
	private static ConfigHolder<ModConfig> configHolder;

	public static ModConfig getConfig() {
		return configHolder.getConfig();
	}

	@Override
	public void onInitializeClient() {
		configHolder = AutoConfig.register(ModConfig.class, GsonConfigSerializer::new);
		TagManager.loadTags();
		Commands.registerCommands();
	}
}