package me.screret.notenoughcoins.events;

import gg.essential.api.EssentialAPI;
import me.screret.notenoughcoins.utils.updater.GitHub;
import me.screret.notenoughcoins.utils.updater.UpdateAvailableScreen;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OnGuiOpen {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onGuiOpen(GuiOpenEvent event) {
        if (event.gui == null || event.gui.getClass() != GuiMainMenu.class) return;
        if (!GitHub.shownGUI) {
            GitHub.fetchLatestRelease();
            if (!GitHub.isLatest()) {
                EssentialAPI.getGuiUtil().openScreen(new UpdateAvailableScreen());
            }
            GitHub.shownGUI = true;
        }
    }
}
