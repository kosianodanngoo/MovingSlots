package io.github.kosianodangoo.movingslots.client;

import io.github.kosianodangoo.movingslots.Config;
import io.github.kosianodangoo.movingslots.MovingSlots;
import io.github.kosianodangoo.movingslots.SlotMotion;
import io.github.kosianodangoo.movingslots.WidgetMotion;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = MovingSlots.MODID, value = Dist.CLIENT)
public class ClientEvents {
    @SubscribeEvent
    public static void onScreenRenderPre(ScreenEvent.Render.Pre event) {
        Screen screen = event.getScreen();
        if (Config.shouldMoveWidgets()) {
            moveWidgets(screen);
        }
        if (Config.shouldMoveSlots()) {
            moveSlots(screen);
        }
    }

    private static void moveWidgets(Screen screen) {
        for (Renderable renderable : screen.renderables) {
            if (!(renderable instanceof AbstractWidget widget)) continue;
            ((WidgetMotion) widget).movingSlots$tick(screen.width, screen.height);
        }
    }

    private static void moveSlots(Screen screen) {
        if (!(screen instanceof AbstractContainerScreen<?> acs)) return;
        int leftPos = acs.getGuiLeft();
        int topPos = acs.getGuiTop();
        for (Slot slot : acs.getMenu().slots) {
            ((SlotMotion) slot).movingSlots$tick(screen.width, screen.height, leftPos, topPos);
        }
    }
}
