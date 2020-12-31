package tech.antonio32a.pitpresence;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.antonio32a.pitpresence.commands.PitPresenceCommand;
import tech.antonio32a.pitpresence.config.Config;
import tech.antonio32a.pitpresence.utils.PitHandler;
import tech.antonio32a.pitpresence.utils.PitIPCListener;
import tech.antonio32a.pitpresence.utils.PresenceUpdater;

@Mod(modid = PitPresence.MODID, version = PitPresence.VERSION)
public class PitPresence {
    public static final String MODID = "pitpresence";
    public static final String VERSION = "1.0";
    public static final String configLocation = "./config/pitpresence.toml";
    public static final Long clientId = 793558849247641642L;

    @Getter private static PitPresence instance;
    @Getter private Logger logger;
    @Getter private IPCClient ipcClient;
    @Getter private PitIPCListener ipcListener;
    @Getter private PitHandler pitHandler;
    @Getter private PresenceUpdater presenceUpdater;
    @Getter private Config config;

    public PitPresence() {
        instance = this;
        logger = LogManager.getLogger();
        config = new Config();
        ipcClient = new IPCClient(clientId);
        ipcListener = new PitIPCListener();
        pitHandler = new PitHandler();
        presenceUpdater = new PresenceUpdater();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        ModCoreInstaller.initializeModCore(Minecraft.getMinecraft().mcDataDir);
        config.preload();

        logger.info("Starting PitPresence!");
        ipcClient.setListener(ipcListener);

        try {
            ipcClient.connect();
        } catch (NoDiscordClientException error) {
            logger.info("Couldn't find Discord!");
        }

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(pitHandler);
        MinecraftForge.EVENT_BUS.register(presenceUpdater);
        ClientCommandHandler.instance.registerCommand(new PitPresenceCommand());
    }
}
