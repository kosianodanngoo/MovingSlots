package io.github.kosianodangoo.movingslots.client;

import io.github.kosianodangoo.movingslots.MovingSlots;
import io.github.kosianodangoo.movingslots.SlotMotion;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MovingSlots.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onScreenRenderPre(ScreenEvent.Render.Pre event) {
        Screen screen = event.getScreen();
        if (!(screen instanceof AbstractContainerScreen<?> acs)) return;
        int leftPos = acs.getGuiLeft();
        int topPos = acs.getGuiTop();
        for (Slot slot : acs.getMenu().slots) {
            ((SlotMotion) slot).movingSlots$tick(screen.width, screen.height, leftPos, topPos);
        }
    }
}
