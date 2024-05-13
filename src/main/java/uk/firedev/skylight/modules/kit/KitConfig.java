package uk.firedev.skylight.modules.kit;

import org.bukkit.configuration.ConfigurationSection;
import uk.firedev.daisylib.Config;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.skylight.Skylight;
import uk.firedev.skylight.config.MessageConfig;

import java.util.List;
import java.util.Objects;

public class KitConfig extends Config {

    private static KitConfig instance;

    // Does not remove unused config options, as that would wipe custom kits.
    private KitConfig() {
        super("kits.yml", Skylight.getInstance(), true, false);
    }

    public static KitConfig getInstance() {
        if (instance == null) {
            instance = new KitConfig();
        }
        return instance;
    }

    public ComponentMessage getUsageMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.kits.usage", "<color:#F0E68C>Usage: <green>/awardkit <player> <kit>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getNotFoundMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.kits.kit-not-found", "<red>Kit not found.");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getAwardedCommandMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.kits.awarded-command", "<color:#F0E68C>Given {player} the kit {kit}.</color>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getAwardedReceiverMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.kits.awarded-receive", "<color:#F0E68C>You have been given the kit {kit}.</color>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public List<ConfigurationSection> getKitConfigs() {
        ConfigurationSection kitsSection = getConfig().getConfigurationSection("kits");
        if (kitsSection == null) {
            return List.of();
        }
        return kitsSection.getKeys(false).stream()
                .map(kitsSection::getConfigurationSection)
                .filter(Objects::nonNull)
                .toList();
    }

}