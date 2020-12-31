package tech.antonio32a.pitpresence.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class ChatLib {
    public static void chat(String text) {
        String prefix = "§8[§dPitPresence§8] ";
        text = text.replace("&", "§");

        Minecraft.getMinecraft().thePlayer.addChatMessage(
                new ChatComponentText(prefix + text)
        );
    }

    public static String removeFormatting(String text) {
        return EnumChatFormatting.getTextWithoutFormattingCodes(text);
    }
}
