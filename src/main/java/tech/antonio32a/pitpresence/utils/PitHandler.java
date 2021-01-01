package tech.antonio32a.pitpresence.utils;

import club.sk1er.mods.core.util.Multithreading;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PitHandler {
    private int tickCounter = 0;
    private String pitPanda = "https://pitpanda.rocks/api/players/";
    @Getter private Boolean inPit = false;
    @Getter private String date = "00/00/0000";
    @Getter private String lobby = "mega00A";
    @Getter private String prestige = "I";
    @Getter private String level = "0";
    @Getter private String neededXP = "0";
    @Getter private String gold = "0g";
    @Getter private String status = "Idling";
    @Getter private String bounty = "0g";
    @Getter private String streak = "0.0";
    @Getter private Integer playtime = 0;
    @Getter private String xpProgress = "0/0";
    @Getter private String goldProgress = "0/0";
    @Getter private String renownProgress = "0/0";
    @Getter private String kdr = "0.0";

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        // update every 15s
        tickCounter++;
        if (tickCounter == 300)
            tickCounter = 0;
        else
            return;

        fetchPitData();
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
        level = "0";
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

    private void fetchPitData() {
        Multithreading.runAsync(() -> {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(pitPanda + Minecraft.getMinecraft().thePlayer.getName());

            try {
                CloseableHttpResponse response = httpclient.execute(httpGet);
                InputStream content = response.getEntity().getContent();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                StringBuilder text = new StringBuilder();

                String output;
                while ((output = buffer.readLine()) != null) {
                    text.append(output);
                }

                JsonObject json = new Gson().fromJson(String.valueOf(text), JsonObject.class);
                if (!json.get("success").getAsBoolean())
                    return;

                JsonObject data = json.get("data").getAsJsonObject();
                playtime = data.get("playtime").getAsInt();
                xpProgress = data.get("xpProgress").getAsJsonObject().get("description").getAsString();
                goldProgress = data.get("goldProgress").getAsJsonObject().get("description").getAsString();
                renownProgress = data.get("renownProgress").getAsJsonObject().get("description").getAsString();
                kdr = round(data.get("doc").getAsJsonObject().get("kdr").getAsDouble());
            } catch (IOException error) {
                error.printStackTrace();
            }
        });
    }

    private static String round (double value) {
        int scale = (int) Math.pow(10, 1);
        return String.format("%.1f", Math.floor(value * scale) / scale);
    }
}
