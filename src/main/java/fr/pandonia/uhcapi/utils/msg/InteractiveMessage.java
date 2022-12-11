package fr.pandonia.uhcapi.utils.msg;

import java.util.Arrays;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class InteractiveMessage {
    private TextComponent message = new TextComponent("");

    public InteractiveMessage add(TextComponent part) {
        this.message.addExtra((BaseComponent)part);
        return this;
    }

    public InteractiveMessage add(String part) {
        this.message.addExtra((BaseComponent)new TextComponent(part));
        return this;
    }

    public void sendMessage(Player... players) {
        Arrays.<Player>stream(players).filter(OfflinePlayer::isOnline).forEach(player -> player.spigot().sendMessage((BaseComponent)this.message));
    }
}