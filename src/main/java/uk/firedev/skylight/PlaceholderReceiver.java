package uk.firedev.skylight;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import uk.firedev.skylight.chat.titles.TitleManager;

public class PlaceholderReceiver extends PlaceholderExpansion {

    @Override
    public boolean persist() { return true; }

    @Override
    public @NotNull String getIdentifier() { return "skylight"; }

    @Override
    public @NotNull String getAuthor() { return "FireML"; }

    @Override
    public @NotNull String getVersion() { return "1.0-SNAPSHOT"; }

    @Override
    public boolean canRegister() { return true; }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return null;
        }
        return switch (identifier) {
            case "player_prefix" -> TitleManager.getInstance().getPlayerPrefix(player);
            case "player_suffix" -> TitleManager.getInstance().getPlayerSuffix(player);
            default -> null;
        };
    }

}
