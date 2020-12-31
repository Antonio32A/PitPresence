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
    public String details = "[${PRESTIGE}-${LEVEL}] ${NAME} | ${STATUS}";

    @Property(
            type = PropertyType.TEXT,
            name = "State",
            category = "General"
    )
    public String state = "Streak: ${STREAK} | Bounty: ${BOUNTY}";

    @Property(
            type = PropertyType.TEXT,
            name = "Large Image Text",
            category = "General"
    )
    public String largeImageText = "${GOLD} | ${NEEDEDXP} XP left";

    @Property(
            type = PropertyType.TEXT,
            name = "Small Image Text",
            category = "General"
    )
    public String smallImageText = "${STATUS} | ${LOBBY} | ${DATE}";

    public Config() {
        super(new File(PitPresence.configLocation));
        initialize();
    }
}
