package uk.firedev.firefly.config;

import uk.firedev.daisylib.command.HelpMessageBuilder;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.daisylib.message.component.ComponentReplacer;
import uk.firedev.firefly.Firefly;

import java.util.Map;

public class MessageConfig extends uk.firedev.daisylib.Config {

    private static MessageConfig instance = null;

    private MessageConfig() {
        super("messages.yml", "messages.yml", Firefly.getInstance(), true);
    }

    public static MessageConfig getInstance() {
        if (instance == null) {
            instance = new MessageConfig();
        }
        return instance;
    }

    public ComponentReplacer getPrefixReplacer() {
        return new ComponentReplacer().addReplacement("prefix", getPrefix().getMessage());
    }

    // GENERAL MESSAGES

    public ComponentMessage getPrefix() {
        return new ComponentMessage(getConfig(), "prefix", "<gray>[Firefly]</gray> ");
    }

    public ComponentMessage getPlayerNotFoundMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "player-not-found", "<red>Player not found.");
        message = message.applyReplacer(getPrefixReplacer());
        return message;
    }

    // MAIN COMMAND MESSAGES

    public ComponentMessage getMainCommandReloadedMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "main-command.reloaded", "<color:#F0E68C>Successfully reloaded the plugin");
        message = message.applyReplacer(getPrefixReplacer());
        return message;
    }

    public ComponentMessage getMainCommandUsageMessage() {
        Map<String, String> usageMap = Map.of(
                "/firefly reload", "Reloads the plugin.",
                "/firefly modules", "Are modules enabled?"
        );
        ComponentMessage header = new ComponentMessage(getConfig(), "main-command.usage.header", "{prefix}<color:#F0E68C>Command Usage:");
        ComponentMessage usage = new ComponentMessage(getConfig(), "main-command.usage.command", "{prefix}<aqua>{command} <color:#F0E68C>- {description}");

        ComponentMessage message = new HelpMessageBuilder(header, usage)
                .buildMessage(usageMap, "command", "description");

        return message.applyReplacer(getPrefixReplacer());
    }

    public ComponentMessage getMainCommandModulesMessage() {
        ComponentMessage message = new ComponentMessage(
                """
                {prefix}<color:#F0E68C>Kits:</color> {kitsEnabled}
                {prefix}<color:#F0E68C>Amethyst Protection:</color> {apEnabled}
                {prefix}<color:#F0E68C>Loot Chest Protection:</color> {lcpEnabled}
                {prefix}<color:#F0E68C>Elevators:</color> {elevatorsEnabled}
                {prefix}<color:#F0E68C>Titles:</color> {titlesEnabled}
                {prefix}<color:#F0E68C>Nicknames:</color> {nicknamesEnabled}"""
        );
        message = message.applyReplacer(getPrefixReplacer());
        return message;
    }

}