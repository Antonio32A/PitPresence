package tech.antonio32a.pitpresence.utils;

import com.jagrosh.discordipc.IPCClient;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tech.antonio32a.pitpresence.PitPresence;
import tech.antonio32a.pitpresence.config.Config;

public class PresenceUpdater {
    private int tickCounter = 0;
    private PitPresence main = PitPresence.getInstance();
    private PitHandler pitHandler = main.getPitHandler();
    private PitIPCListener ipcListener = main.getIpcListener();
    private IPCClient ipcClient = main.getIpcClient();
    private Config config = main.getConfig();

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        // only update every 15s
        tickCounter++;
        if (tickCounter == 300)
            tickCounter = 0;
        else
            return;

        if (!pitHandler.getInPit()) {
            ipcListener.setDefaults();
            ipcListener.updatePresence(ipcClient);
            return;
        }

        ipcListener.setDetails(parseText(config.details));
        ipcListener.setState(parseText(config.state));
        ipcListener.setLargeImageText(parseText(config.largeImageText));
        ipcListener.setSmallImageText(parseText(config.smallImageText));
        ipcListener.updatePresence(ipcClient);
    }

    public static String parseText(String text) {
        PitHandler pitHandler = PitPresence.getInstance().getPitHandler();
        Minecraft minecraft = Minecraft.getMinecraft();

        try {
            text = text.replace("${DATE}", pitHandler.getDate())
                    .replace("${LOBBY}", pitHandler.getLobby())
                    .replace("${PRESTIGE}", pitHandler.getPrestige())
                    .replace("${LEVEL}", pitHandler.getLevel())
                    .replace("${NEEDEDXP}", pitHandler.getNeededXP())
                    .replace("${GOLD}", pitHandler.getGold())
                    .replace("${STATUS}", pitHandler.getStatus())
                    .replace("${BOUNTY}", pitHandler.getBounty())
                    .replace("${STREAK}", pitHandler.getStreak())
                    .replace("${NAME}", minecraft.thePlayer.getName())
                    .replace("${PLAYTIME}", pitHandler.getPlaytime() / 60 + "h")
                    .replace("${XPPROGRESS}", pitHandler.getXpProgress())
                    .replace("${GOLDPROGRESS}", pitHandler.getGoldProgress())
                    .replace("${RENOWNPROGRESS}", pitHandler.getRenownProgress())
                    .replace("${KDR}", pitHandler.getKdr());
            return text;
        } catch (NullPointerException error) {
            error.printStackTrace();
            ChatLib.chat("&cAn error has occurred while parsing the text.");
            ChatLib.chat("&cPlease contact Antonio32A for help with the latest log file.");
            return text;
        }
    }
}
