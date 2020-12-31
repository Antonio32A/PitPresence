package tech.antonio32a.pitpresence.utils;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.RichPresence;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import tech.antonio32a.pitpresence.PitPresence;

import java.time.OffsetDateTime;

public class PitIPCListener implements IPCListener {
    private OffsetDateTime timestamp = OffsetDateTime.now();
    private Logger logger = PitPresence.getInstance().getLogger();

    @Getter private Boolean active = false;
    @Getter @Setter private String details;
    @Getter @Setter private String state;
    @Getter @Setter private String largeImage;
    @Getter @Setter private String largeImageText;
    @Getter @Setter private String smallImage;
    @Getter @Setter private String smallImageText;

    @Override
    public void onReady(IPCClient client) {
        active = true;
        logger.info("Discord Presence started.");
        setDefaults();
        updatePresence(client);
    }

    public void updatePresence(IPCClient client) {
        if (!active) return;
        RichPresence.Builder builder = new RichPresence.Builder();
        builder.setState(state)
                .setDetails(details)
                .setLargeImage(largeImage, largeImageText)
                .setSmallImage(smallImage, smallImageText)
                .setStartTimestamp(timestamp);
        client.sendRichPresence(builder.build());
    }

    public void setDefaults() {
        details = "Powered by PitPresence v" + PitPresence.VERSION;
        state = "github.com/Antonio32A/PitPresence";
        largeImage = "pit";
        largeImageText = "Gaming";
        smallImage = "hypixel";
        smallImageText = "Gaming";
    }

    @Override
    public void onClose(IPCClient client, JSONObject json) {
        active = false;
        logger.info("Discord Presence closed.");
    }

    @Override
    public void onDisconnect(IPCClient client, Throwable t) {
        active = false;
        logger.info("Discord Presence disconnected.");
    }
}