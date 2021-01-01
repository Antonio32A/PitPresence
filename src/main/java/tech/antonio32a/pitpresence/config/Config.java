package tech.antonio32a.pitpresence.config;

import club.sk1er.vigilance.Vigilant;
import club.sk1er.vigilance.data.Property;
import club.sk1er.vigilance.data.PropertyType;
import tech.antonio32a.pitpresence.PitPresence;

import java.io.File;

public class Config extends Vigilant {
    @Property(
            type = PropertyType.TEXT,
            name = "Details",
            category = "General"
    )
    public String details = "LVL: ${LEVEL} | ${GOLDPROGRESS}";

    @Property(
            type = PropertyType.TEXT,
            name = "State",
            category = "General"
    )
    public String state = "${STATUS}: ${STREAK}";

    @Property(
            type = PropertyType.TEXT,
            name = "Large Image Text",
            category = "General"
    )
    public String largeImageText = "[${PRESTIGE}-${LEVEL}] ${NAME}";

    @Property(
            type = PropertyType.TEXT,
            name = "Small Image Text",
            category = "General"
    )
    public String smallImageText = "Playtime: ${PLAYTIME} | KDR: ${KDR}";

    public Config() {
        super(new File(PitPresence.configLocation));
        initialize();
    }
}
