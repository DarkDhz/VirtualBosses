package dev.darkhorizon.es.black.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {

    public static void sendMessage(Player p, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message.replace("&", "§") + "\"}"), (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }
}
