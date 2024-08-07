package net.fishwifi.nathmtils.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry.Gui.Tooltip;
import net.fishwifi.nathmtils.nathmUtils;

@SuppressWarnings("FieldMayBeFinal")
@Config(name = nathmUtils.NAMESPACE)
public class ModConfig implements ConfigData {
    @Tooltip
    private String titleScreenText = "nathm's utils";
    @Tooltip
    private boolean showPlayerTags = true;
    @Tooltip
    private String playerTagDataURL = "https://raw.githubusercontent.com/nathm-bit/nathmutils-data/main/data/tags.json";


    public String getTitleScreenText() {
        return titleScreenText;
    }

    public boolean PlayerTagsEnabled() {
        return showPlayerTags;
    }

    public String getPlayerTagDataURL() {
        return playerTagDataURL;
    }

}
