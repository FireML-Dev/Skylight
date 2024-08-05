package uk.firedev.firefly.modules.nickname;

import uk.firedev.daisylib.Config;
import uk.firedev.daisylib.message.component.ComponentMessage;
import uk.firedev.firefly.Firefly;
import uk.firedev.firefly.config.MessageConfig;

import java.util.List;

public class NicknameConfig extends Config {

    private static NicknameConfig instance;

    private NicknameConfig() {
        super("nicknames.yml", "nicknames.yml", Firefly.getInstance(), true);
    }

    public static NicknameConfig getInstance() {
        if (instance == null) {
            instance = new NicknameConfig();
        }
        return instance;
    }

    public int getMaxLength() {
        return getConfig().getInt("settings.max-length");
    }

    public int getMinLength() {
        return getConfig().getInt("settings.min-length");
    }

    public List<String> getBlacklistedNames() {
        return getConfig().getStringList("nicknames.blacklisted-names");
    }

    public ComponentMessage getRealHoverMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.real-name-hover", "<color:#F0E68C>Real Username:</color> <white>{username}</white>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getCommandTooLongMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.command.too-long", "<red>That nickname is too long! Maximum length {max-length}</red>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getCommandTooShortMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.command.too-short", "<red>That nickname is too short! Minimum length {min-length}</red>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getCommandBlacklistedMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.command.blacklisted", "<red>That name is blacklisted! Please try something else.</red>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getCommandNoUniqueMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.command.no-unique-name", "<red>You don't have permission to set a unique nickname!</red>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getCommandSetOwnNicknameMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.command.set-own-nickname", "<color:#F0E68C>Set your nickname to</color> <white>{nickname}</white>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getCommandRemovedNicknameMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.command.removed-nickname", "<color:#F0E68C>Removed your nickname.</color>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getCommandAdminUsageMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.command.admin.usage", "<red>Usage: /nicknameadmin <player> <nickname>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getCommandAdminSetNicknameMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.command.admin.set-others-nickname", "<color:#F0E68C>Set {target}'s nickname to</color> <white>{nickname}</white>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getCommandAdminRemovedNicknameMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.command.admin.removed-others-nickname", "<color:#F0E68C>Removed {player}'s nickname.</color>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

    public ComponentMessage getCommandCheckInfoMessage() {
        ComponentMessage message = new ComponentMessage(getConfig(), "messages.command.check-info", "<color:#F0E68C>{player}'s Nickname:</color> <white>{nickname}</white>");
        message = message.applyReplacer(MessageConfig.getInstance().getPrefixReplacer());
        return message;
    }

}