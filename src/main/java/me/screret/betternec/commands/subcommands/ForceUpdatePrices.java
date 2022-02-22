package me.screret.betternec.commands.subcommands;

import me.screret.betternec.Config;
import me.screret.betternec.Main;
import me.screret.betternec.utils.ApiHandler;
import net.minecraft.command.ICommandSender;

import java.io.IOException;

public class ForceUpdatePrices implements Subcommand {
    @Override
    public String getCommandName() {
        return "update";
    }

    @Override
    public boolean isHidden() {
        return !Config.debug;
    }

    @Override
    public String getCommandUsage() {
        return "";
    }

    @Override
    public String getCommandDescription() {
        return "Sends a forced price update. FOR TESTING";
    }

    @Override
    public boolean processCommand(ICommandSender sender, String[] args) {
        try {
            if(Config.newAverage){
                ApiHandler.updateAvgAH();

            }else{
                ApiHandler.updateAvgAHOldSystem();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
