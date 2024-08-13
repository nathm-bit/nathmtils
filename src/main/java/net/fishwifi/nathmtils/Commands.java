package net.fishwifi.nathmtils;

import net.fishwifi.nathmtils.tag.TagManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

import static net.fabricmc.fabric.api.client.command.v2.ClientCommandManager.*;
import net.minecraft.network.chat.Component;

public class Commands {
    public static void registerCommands() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) ->
                dispatcher.register(literal("nathmtils")
                        .then(literal("reloadtags")
                                .executes(context -> {
                                    TagManager.loadTags();
                                    context.getSource().sendFeedback(Component.literal("Reloading tags!"));
                                    return 1;
                                }))
                ));
    }
}