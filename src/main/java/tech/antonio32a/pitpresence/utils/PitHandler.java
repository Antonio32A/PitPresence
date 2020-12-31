package tech.antonio32a.pitpresence.utils;

import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PitHandler {
    private int tickCounter = 0;
    @Getter private Boolean inPit = false;
    @Getter private String date;
    @Getter private String lobby;
    @Getter private String prestige;
    @Getter private String level;
    @Getter private String neededXP;
    @Getter private String gold;
    @Getter private String status;
    @Getter private String bounty;
    @Getter private String streak;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        // update every 15s
        tickCounter++;
        if (tickCounter == 300)
            tickCounter = 0;
        else
            return;

        World world = Minecraft.getMinecraft().theWorld;
        if (world == null) return;
        Scoreboard scoreboard = world.getScoreboard();
        if (scoreboard == null) return;
        ScoreObjective sidebar = scoreboard.getObjectiveInDisplaySlot(1);
        if (sidebar == null) return;

        String title = sidebar.getName();
        Collection<Score> lines = scoreboard.getSortedScores(sidebar);
        Collections.reverse((List<?>) lines);

        if (!title.equals("Pit")) {
            inPit = false;
            return;
        }
        inPit = true;

        // reset all values
        date = "00/00/0000";
        lobby = "mega00A";
        prestige = "I";
        level = "[0]";
        neededXP = "0";
        gold = "0g";
        status = "Idling";
        bounty = "0g";
        streak = "0.0";

        for (Score score : lines) {
            String line = ScorePlayerTeam.formatPlayerName(
                    scoreboard.getPlayersTeam(score.getPlayerName()),
                    score.getPlayerName()
            );
            line = ChatLib.removeFormatting(line);
            line = line.replaceAll("[^\\x00-\\x7F]", "");

            if (line.equals("www.hypixel.net")) continue;
            else if (!line.contains(":")) {
                if (line.contains("mega")) {
                    date = line.split(" ")[0];
                    lobby = line.split(" ")[1];
                }
                continue;
            }

            String key = line.split(": ")[0];
            String value = line.split(": ")[1];

            switch (key) {
                case "Prestige":
                    prestige = value;
                    break;
                case "Level":
                    level = value.replace("[", "")
                            .replace("]", "");
                    break;
                case "Needed XP":
                    neededXP = value;
                    break;
                case "Gold":
                    gold = value;
                    break;
                case "Status":
                    status = value;
                    break;
                case "Bounty":
                    bounty = value;
                    break;
                case "Streak":
                    streak = value;
                    break;
            }
        }
    }
}
