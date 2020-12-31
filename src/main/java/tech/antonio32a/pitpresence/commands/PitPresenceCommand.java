package tech.antonio32a.pitpresence.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import tech.antonio32a.pitpresence.PitPresence;
import tech.antonio32a.pitpresence.config.Config;
import tech.antonio32a.pitpresence.utils.ChatLib;
import tech.antonio32a.pitpresence.utils.PitIPCListener;
import tech.antonio32a.pitpresence.utils.PresenceUpdater;

import java.util.ArrayList;
import java.util.Arrays;

public class PitPresenceCommand extends CommandBase {
    private Config config = PitPresence.getInstance().getConfig();
    private String commandUsage = "/pitpresence [details|state|large|small] [new value]";
    private PitPresence main = PitPresence.getInstance();
    private PitIPCListener ipcListener = main.getIpcListener();

    @Override
    public String getCommandName() {
        return "pitpresence";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return commandUsage;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(args));
        if (arguments.size() < 2) {
            ChatLib.chat("&cInvalid command usage!");
            ChatLib.chat("&a" + commandUsage);
            return;
        }

        String field = arguments.get(0);
        String value = String.join(" ", arguments.subList(1, arguments.size()));

        config.markDirty();
        switch (field.toLowerCase()) {
            case "details":
                config.details = value;
                config.writeData();
                ipcListener.updatePresence(main.getIpcClient());
                ChatLib.chat("&aSuccessfully set the details to: &d" + value);
                break;
            case "state":
                config.state = value;
                config.writeData();
                ipcListener.updatePresence(main.getIpcClient());
                ChatLib.chat("&aSuccessfully set the state to: &d" + value);
                break;
            case "large":
                config.largeImageText = value;
                config.writeData();
                ipcListener.updatePresence(main.getIpcClient());
                ChatLib.chat("&aSuccessfully set the large image text to: &d" + value);
                break;
            case "small":
                config.smallImageText = value;
                config.writeData();
                ipcListener.updatePresence(main.getIpcClient());
                ChatLib.chat("&aSuccessfully set the small image text to: &d" + value);
                break;
            case "preview":
                String output = PresenceUpdater.parseText(value);
                ChatLib.chat("&e" + output);
                break;
            default:
                ChatLib.chat("&cInvalid command usage!");
                ChatLib.chat("&a" + commandUsage);
                break;
        }

    }
}
