package uk.firedev.skylight.modules.titles;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import uk.firedev.daisylib.Loggers;
import uk.firedev.daisylib.VaultManager;

import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;
import uk.firedev.daisylib.utils.ObjectUtils;
import uk.firedev.skylight.Skylight;
import uk.firedev.skylight.config.MessageConfig;
import uk.firedev.skylight.modules.titles.objects.Prefix;
import uk.firedev.skylight.modules.titles.objects.Suffix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TitleManager extends uk.firedev.daisylib.Config {

    private static TitleManager instance = null;

    private List<Prefix> prefixes = new ArrayList<>();
    private List<Suffix> suffixes = new ArrayList<>();
    private boolean loaded = false;

    private TitleManager() {
        super("titles.yml", Skylight.getInstance(), true, false);
    }

    public static TitleManager getInstance() {
        if (instance == null) {
            instance = new TitleManager();
        }
        return instance;
    }

    public void load() {
        if (VaultManager.getChat() == null) {
            Loggers.warning(Skylight.getInstance().getLogger(), "The Title Module cannot load because there is no Vault Chat manager detected. Please enable it to use this module!");
            return;
        }
        PrefixCommand.getInstance().register();
        SuffixCommand.getInstance().register();
        this.prefixes = TitleConfig.getInstance().getPrefixesFromFile();
        this.suffixes = TitleConfig.getInstance().getSuffixesFromFile();
        loaded = true;
    }

    @Override
    public void reload() {
        super.reload();
        this.prefixes = TitleConfig.getInstance().getPrefixesFromFile();
        this.suffixes = TitleConfig.getInstance().getSuffixesFromFile();
    }

    public boolean isLoaded() { return loaded; }

    public NamespacedKey getPrefixKey() {
        return ObjectUtils.createNamespacedKey("player-prefix", Skylight.getInstance());
    }

    public NamespacedKey getSuffixKey() {
        return ObjectUtils.createNamespacedKey("player-suffix", Skylight.getInstance());
    }

    public void setPlayerPrefix(@NotNull Player player, @NotNull String prefix) {
        setPlayerPrefix(player, new ComponentMessage(prefix).getMessage());
    }

    public void setPlayerPrefix(@NotNull Player player, @NotNull Prefix prefix) {
        setPlayerPrefix(player, prefix.getDisplay());
    }

    public void setPlayerPrefix(@NotNull Player player, @NotNull Component prefix) {
        String stringPrefix = new ComponentMessage(prefix).toStringMessage().getMessage();
        player.getPersistentDataContainer().set(getPrefixKey(), PersistentDataType.STRING, stringPrefix);
        ComponentReplacer replacer = new ComponentReplacer().addReplacement("new-prefix", prefix);
        TitleConfig.getInstance().getPrefixSetMessage().applyReplacer(replacer).sendMessage(player);
    }

    public void removePlayerPrefix(@NotNull Player player) {
        player.getPersistentDataContainer().remove(getPrefixKey());
        TitleConfig.getInstance().getPrefixRemovedMessage().sendMessage(player);
    }

    public Component getPlayerPrefix(@NotNull Player player) {
        String prefix = player.getPersistentDataContainer().get(
                getPrefixKey(), PersistentDataType.STRING
        );
        if (prefix == null) {
            if (VaultManager.getChat() == null) {
                return Component.empty();
            } else {
                return LegacyComponentSerializer.legacyAmpersand().deserialize(VaultManager.getChat().getPlayerPrefix(player).replace('§', '&'));
            }
        }
        return new ComponentMessage(prefix).getMessage();
    }

    public String getPlayerPrefixLegacy(@NotNull Player player) {
        return LegacyComponentSerializer.legacySection().serialize(getPlayerPrefix(player)).replace('&', '§');
    }

    public void setPlayerSuffix(@NotNull Player player, @NotNull String suffix) {
        setPlayerSuffix(player, new ComponentMessage(suffix).getMessage());
    }

    public void setPlayerSuffix(@NotNull Player player, @NotNull Suffix suffix) {
        setPlayerSuffix(player, suffix.getDisplay());
    }

    public void setPlayerSuffix(@NotNull Player player, @NotNull Component suffix) {
        String stringSuffix = new ComponentMessage(suffix).toStringMessage().getMessage();
        player.getPersistentDataContainer().set(getSuffixKey(), PersistentDataType.STRING, stringSuffix);
        ComponentReplacer replacer = new ComponentReplacer().addReplacement("new-suffix", suffix);
        TitleConfig.getInstance().getSuffixSetMessage().applyReplacer(replacer).sendMessage(player);
    }

    public void removePlayerSuffix(@NotNull Player player) {
        player.getPersistentDataContainer().remove(getSuffixKey());
        TitleConfig.getInstance().getSuffixRemovedMessage().sendMessage(player);
    }

    public Component getPlayerSuffix(@NotNull Player player) {
        String suffix = player.getPersistentDataContainer().get(
                getSuffixKey(), PersistentDataType.STRING
        );
        if (suffix == null) {
            if (VaultManager.getChat() == null) {
                return Component.empty();
            } else {
                return LegacyComponentSerializer.legacyAmpersand().deserialize(VaultManager.getChat().getPlayerSuffix(player).replace('§', '&'));
            }
        }
        return new ComponentMessage(suffix).getMessage();
    }

    public String getPlayerSuffixLegacy(@NotNull Player player) {
        return LegacyComponentSerializer.legacySection().serialize(getPlayerSuffix(player)).replace('&', '§');
    }

    public List<Prefix> getPrefixes() {
        if (prefixes == null || prefixes.isEmpty()) {
            this.prefixes = TitleConfig.getInstance().getPrefixesFromFile();
        }
        return prefixes;
    }

    public List<Suffix> getSuffixes() {
        if (suffixes == null || suffixes.isEmpty()) {
            this.suffixes = TitleConfig.getInstance().getSuffixesFromFile();
        }
        return suffixes;
    }

}
